# Plano de upgrade da IA local — 16 GB → 32 GB de RAM

Documento de execução para trocar o modelo de código do TheRogueDev depois do
upgrade de memória. Escrito em 2026-08-29, com números medidos nesta máquina.

---

## 1. Linha de base — o que existe hoje

| Item | Valor (medido) |
|---|---|
| CPU | Intel i7-4790K, 4c/8t, 4.0 GHz (2014) |
| Placa-mãe | MSI Z97-G43 GAMING (MS-7816) — **4 slots DDR3, máx. 32 GB** |
| RAM | 2 × 8 GB Kingston HyperX `KHX1600C10D3/8G` @ 1600 MHz, dual channel |
| Slots livres | 2 (`ChannelA-DIMM1`, `ChannelB-DIMM1`) |
| GPU | RTX 2060 SUPER, **8 GB** VRAM, driver 610.88 |
| Ollama | 0.33.2 |
| Modelo de código | `qwen-coder-dev` (Qwen2.5-Coder 7B q4_K_M) |
| **Velocidade atual** | **60,2 tok/s, 100% GPU, sem spill** |
| Load a frio | ~32 s |

`Win32_VideoController` reporta 4 GB de VRAM. Está **errado** (limite de uint32).
Use sempre `nvidia-smi --query-gpu=memory.total --format=csv`.

---

## 2. O que 32 GB compra — e o que não compra

Esta é a parte que decide se o upgrade vale a pena para você.

**Compra: capacidade.** Modelos MoE de 30B (19 GB em q4) passam a caber.
Hoje eles simplesmente não carregam.

**Não compra: banda.** DDR3-1600 em dual channel dá **~25,6 GB/s teóricos**.
Encher os 4 slots **não** aumenta isso — continua dual channel, não vira quad.
E a banda de RAM é exatamente o gargalo de qualquer modelo que não caiba na VRAM.

Consequência direta, com a conta:

> Um MoE de 30B-A3B lê ~3,3 B parâmetros ativos por token. Em q4_K_M
> (~0,56 byte/parâmetro) são ~1,85 GB lidos **por token**. A 25,6 GB/s isso
> dá um teto teórico de ~14 tok/s — e o número real fica abaixo, porque parte
> da banda vai para o resto do sistema.

| Cenário | tok/s esperado |
|---|---|
| Hoje: 7B denso, 100% VRAM | **60** (medido) |
| Depois: 30B-A3B com experts na RAM | **8–15** (estimado) |

**Você vai trocar ~4× de velocidade por um salto grande de qualidade.**
Se o que te incomoda é lentidão, este upgrade piora. Se é o modelo errar API e
não entender o pedido, é o upgrade certo.

---

## 3. Compra da memória

Adicione **2 × 8 GB DDR3-1600** nos slots livres.

- **Ideal:** o mesmo part number, `KHX1600C10D3/8G`. Kit idêntico evita 90% dos
  problemas de POST e de timing.
- **Aceitável:** qualquer DDR3-1600 CL10 non-ECC unbuffered de 8 GB.
- **Não misture** 1600 com 1333/1866. A placa nivela todos pela mais lenta.

**Risco conhecido do DDR3:** com os 4 slots populados, o controlador de memória
do Haswell às vezes não estabiliza em 1600 e cai para 1333 — o que **reduz** a
banda de 25,6 para ~21 GB/s e piora justamente o cenário MoE. Se acontecer,
entre na BIOS, habilite o perfil **XMP** e force 1600 MHz. Se não estabilizar,
afrouxe o Command Rate para 2T.

---

## 4. Execução

### Fase 0 — antes da memória chegar (faça agora)

Registre a linha de base para poder comparar depois:

```powershell
nvidia-smi --query-gpu=memory.total,memory.free --format=csv
ollama ps
```

```bash
# Benchmark reproduzível do estado atual
curl -s http://localhost:11434/api/chat -d '{
  "model":"qwen-coder-dev","stream":false,
  "messages":[{"role":"user","content":"Escreva um record Java de 20 linhas."}]
}' | python -c "import sys,json;d=json.load(sys.stdin);print(round(d['eval_count']/(d['eval_duration']/1e9),1),'tok/s')"
```

Guarde o número. **É o seu ponto de comparação.**

### Fase 1 — instalar e validar o hardware

```powershell
# Confirmar 32 GB e, principalmente, que continuou em 1600 MHz
Get-CimInstance Win32_PhysicalMemory |
  Select-Object DeviceLocator, @{N='GB';E={$_.Capacity/1GB}}, ConfiguredClockSpeed
```

Critério de aceite: **4 DIMMs, 32 GB total, `ConfiguredClockSpeed = 1600`.**
Se vier 1333, volte à BIOS e ative XMP antes de seguir.

Rode um `memtest86` de pelo menos uma passada. DDR3 usado falha mais do que se
imagina, e RAM instável com LLM aparece como resposta corrompida, não como BSOD.

### Fase 2 — retunar as variáveis do Ollama

As atuais foram calibradas para 16 GB. Depois do upgrade:

```powershell
[Environment]::SetEnvironmentVariable('OLLAMA_MAX_LOADED_MODELS','2','User')
[Environment]::SetEnvironmentVariable('OLLAMA_CONTEXT_LENGTH','32768','User')
[Environment]::SetEnvironmentVariable('OLLAMA_KV_CACHE_TYPE','q8_0','User')
[Environment]::SetEnvironmentVariable('OLLAMA_FLASH_ATTENTION','1','User')
```

Reinicie o **serviço** do Ollama (não só o terminal) para as variáveis pegarem.

> **Nota sobre contexto:** hoje o ambiente pede `OLLAMA_CONTEXT_LENGTH=16384`
> mas o `ollama ps` mostra `CONTEXT 12288`. Isso é o `LLAMA_ARG_FIT` do Ollama
> encolhendo o contexto para caber na VRAM. **É comportamento correto, não bug.**
> Depois do upgrade ele terá mais espaço. Sempre confira o valor efetivo em
> `ollama ps` — nunca assuma o que você pediu.

### Fase 3 — baixar e medir os candidatos

Ordem de teste, do mais provável ao mais arriscado:

```bash
# 1) O alvo principal — coder especialista, 30B total / 3,3B ativos
ollama pull qwen3-coder:30b          # 19 GB

# 2) Alternativa se o tool calling do modo Agent estiver ruim
ollama pull gpt-oss:20b              # 14 GB, 3,6B ativos, forte em tool use

# 3) Denso moderno, cabe quase todo na VRAM — o mais rápido dos três
ollama pull gemma4:12b               # 7,6 GB
```

Após cada `pull`, o teste que importa:

```bash
ollama run qwen3-coder:30b "oi"      # força o load
ollama ps                            # <-- OLHE AQUI
```

**Critério de aceite:** a coluna `PROCESSOR`. `100% GPU` é ótimo. Algo como
`45%/55% CPU/GPU` é esperado num MoE — o que decide é o tok/s a seguir.
`100% CPU` significa que algo deu errado: o modelo não achou a GPU, nem adianta
medir.

Rode o mesmo benchmark da Fase 0 em cada um e preencha:

| Modelo | tok/s medido | Processor | Veredito |
|---|---|---|---|
| `qwen-coder-dev` (baseline) | 60,2 | 100% GPU | — |
| `qwen3-coder:30b` | | | |
| `gpt-oss:20b` | | | |
| `gemma4:12b` | | | |

Abaixo de **~6 tok/s o modelo é inutilizável para uso interativo** — mais lento
que você digitando. Nesse caso descarte e siga para o próximo.

### Fase 4 — só se o Ollama decepcionar: llama.cpp com `--n-cpu-moe`

O Ollama 0.33 **não expõe** controle de offload de experts (confirmado em
`ollama serve --help`). Ele tem o `LLAMA_ARG_FIT`, que decide sozinho — e o
corte automático tende a ser "camada inteira para a CPU", que é o corte
**errado** para MoE.

O corte certo mantém atenção e KV cache na VRAM (lidos a cada token) e manda só
os pesos dos experts roteados para a RAM (quase nunca lidos). Quem faz isso é o
`--n-cpu-moe N` do llama.cpp.

```powershell
# Binário pronto com CUDA — NÃO compile no Windows, não traz benefício algum
# https://github.com/ggml-org/llama.cpp/releases
#   llama-<versao>-bin-win-cuda-x64.zip  (+ o cudart correspondente)
#   descompacte em C:\llama.cpp

# Modelo em GGUF
pip install huggingface_hub
huggingface-cli download unsloth/Qwen3-Coder-30B-A3B-Instruct-GGUF --include "*Q4_K_M*" --local-dir C:\models

# Subir — o script já existe no material de referência
powershell -ExecutionPolicy Bypass -File $env:USERPROFILE\Downloads\ollama-local\scripts\llama-server-moe.ps1
```

**Calibração do `--n-cpu-moe`:** comece alto (ex.: 24), baixe de 2 em 2
observando `nvidia-smi`, e quando a VRAM estourar volte um passo. Menos experts
na CPU = mais rápido, até o ponto em que não cabe.

O `llama-server` é OpenAI-compatible, então o Continue continua funcionando —
basta trocar a `apiBase` para `http://127.0.0.1:8080/v1`.

### Fase 5 — apontar o Continue para o modelo novo

Em `~/.continue/config.yaml`, **adicione** o modelo novo sem apagar o antigo:

```yaml
  # Novo: raciocínio pesado, code review, arquitetura. Lento e esperto.
  - name: Qwen3 Coder 30B (Review/Arquitetura)
    provider: ollama                 # ou: openai + apiBase http://127.0.0.1:8080/v1
    model: qwen3-coder:30b
    apiBase: http://localhost:11434
    roles: [chat, edit, apply]
    defaultCompletionOptions:
      contextLength: 32768
      temperature: 0.15
```

**Mantenha o `qwen-coder-dev` (7B) na config.** A estratégia certa não é trocar,
é ter os dois e escolher pela tarefa:

| Tarefa | Modelo | Por quê |
|---|---|---|
| Autocomplete inline | `qwen2.5-coder:1.5b` | latência manda; **nunca** troque este |
| Edição rápida, boilerplate, seguir padrão existente | `qwen-coder-dev` 7B | 60 tok/s ganha do 30B aqui |
| Code review, bug difícil, decisão de arquitetura | `qwen3-coder:30b` | 8–15 tok/s se paga em qualidade |
| Modo Agent (tool calling) | `gpt-oss:20b` ou `qwen3-coder:30b` | o Qwen2.5-Coder erra o formato de function call |

Regra prática: **se você vai ler a resposta com atenção, use o 30B. Se só quer
que preencha o óbvio, use o 7B.**

Como `OLLAMA_MAX_LOADED_MODELS=2`, alternar entre 7B e 30B custa um reload de
~30–60 s. É esperado, não é travamento.

Depois de editar, valide **antes** de abrir o editor:

```bash
python -c "import yaml,io,os;yaml.safe_load(io.open(os.path.expanduser('~/.continue/config.yaml'),encoding='utf-8'));print('YAML ok')"
```

---

## 5. Rollback

Nada aqui é destrutivo se você seguir a ordem.

- **Config do Continue:** há backup em `~/.continue/config.yaml.bak-*`.
  Copiar de volta e reiniciar o editor resolve.
- **Modelos:** `ollama rm qwen3-coder:30b` libera os 19 GB. O `qwen-coder-dev`
  nunca é tocado.
- **Variáveis de ambiente:** os valores atuais estão na seção 1 deste documento
  e em `~/Downloads/ollama-local/02-INSTALACAO-windows.md`.
- **RAM:** se a máquina ficar instável, remova os 2 DIMMs novos. Voltar para
  2×8 GB restaura exatamente o estado de hoje.

---

## 6. Critério de decisão final

Depois de medir tudo:

- `qwen3-coder:30b` **acima de 10 tok/s** → adote como modelo de review. É o
  melhor resultado possível nesta máquina.
- **Entre 6 e 10 tok/s** → adote só para review e arquitetura, nunca para edição
  interativa.
- **Abaixo de 6 tok/s** → tente a Fase 4 (`--n-cpu-moe`). Se continuar abaixo,
  desista do 30B e fique com `gemma4:12b` + o 7B atual.

---

## 7. O que este upgrade não resolve

- **Nenhum modelo local nesta máquina chega perto do Claude ou do GPT-5.**
  Um 30B-A3B equivale grosseiramente a um denso de ~9B (média geométrica entre
  total e ativo — regra grosseira, não lei). É bom para completar padrão e
  revisar trecho; é fraco para raciocínio longo e para projetar sistema do zero.
- **A CPU continua sendo de 2014.** O prompt processing (ler o contexto antes de
  gerar) é lento e piora com contexto grande. 32k de contexto custa caro aqui.
- **O próximo gargalo real é VRAM, não RAM.** Depois dos 32 GB, o salto seguinte
  de qualidade exige uma GPU de 16–24 GB — aí `devstral:24b` e o
  `qwen3-coder:30b` inteiro na VRAM entram em jogo, com 40+ tok/s.

Se depois de tudo a qualidade ainda não servir, o dinheiro rende mais numa GPU
usada de 16 GB do que em mais DDR3.

---

## 8. Referências

- [llama.cpp — o que `--n-cpu-moe` faz](https://aliteq.com/n-cpu-moe-llama-cpp-what-it-actually-does)
- [Guia de offload MoE — Hugging Face](https://huggingface.co/blog/Doctor-Shotgun/llamacpp-moe-offload-guide)
- [ollama.com/library/qwen3-coder/tags](https://ollama.com/library/qwen3-coder/tags)
- [ollama.com/library/gpt-oss/tags](https://ollama.com/library/gpt-oss/tags)
- [ollama.com/library/gemma4/tags](https://ollama.com/library/gemma4/tags)
- Material de referência local: `~/Downloads/ollama-local/07-UPGRADE-MOE.md`
