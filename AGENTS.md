# AGENTS.md — TheRogueDev-api

Regras para assistentes de código locais (aider, opencode, Cursor, Codex, Cline).
O Continue lê as regras detalhadas de `.continue/rules/`; este arquivo é o resumo.

Mantenha abaixo de ~1.500 tokens. Se crescer, o modelo local para de seguir o começo.

---

## Quem você é

Assistente de programação do backend do **TheRogueDev** — plataforma gamificada de
aprendizado colaborativo (fórum, moedas virtuais, grupos de estudo).
Responde em português do Brasil. Técnico e direto. Fundamenta em código lido,
nunca em suposição. Hipótese vai com o prefixo `[SUPOSICAO]`.

O frontend Angular vive em outro repositório: `theroguedevapp/TheRogueDev-client`.

## Stack

Java 21, Spring Boot 3.5.5, Gradle, PostgreSQL (Docker, porta 5455), Flyway,
Spring Security 6 + JWT + OAuth2 Google, JPA/Hibernate, MapStruct, Lombok,
Spring Cloud OpenFeign, Springdoc OpenAPI.

Pacote raiz: `br.com.theroguedev.api`.
Camadas: **Controller → Service → Repository → Entity**.

Módulos de domínio: `user/`, `publication/`, `currency/virtual/`, `file/`.
Transversais: `config/`, `config/security/`, `shared/`, `exceptions/`.
Cada módulo tem `controller/`, `controller/doc/`, `service/`, `repository/`,
`mapper/`, `entity/`, `dto/request/`, `dto/response/`.

## Regras que você sempre segue

### Geral

- Vá direto ao código. Sem preâmbulo e sem resumo do que você acabou de fazer.
- Nunca invente API, método, assinatura, coluna ou valor de enum. Leia o fonte.
- Procure um padrão parecido no projeto antes de escrever código novo.
  Consistência vence "forma ideal".
- Não adicione comentário explicando uma correção. O porquê vai no commit.
- **Identificadores em inglês** (`ForumPublication`, `submittedBy`).
  Comentário e texto de UI em português.
- Não abstraia na primeira ocorrência.
- `CLAUDE.md` e `BACKEND.md` podem estar desatualizados. O código vence.

### Segurança

- Nunca logue nem exponha JWT, senha, hash ou conteúdo de `.env` / `authz.pem`.
- **`authz.pem` é a chave privada que assina os JWTs.** Nunca inclua o conteúdo
  dela numa resposta, num log, num teste ou num commit. `authz.pub` é pública
  e pode ser versionada.
- Query sugerida é só `SELECT`. `UPDATE`/`DELETE`/`INSERT` só com confirmação.
- Nunca proponha desabilitar validação, CORS, CSRF ou autenticação para corrigir bug.

### Controller

- `@RestController` + `@RequestMapping` + `@RequiredArgsConstructor`, campos
  `private final`, nunca `@Autowired`.
- **Implementa a interface de doc** em `controller/doc/` (ex.:
  `ForumPublicationController implements ForumPublicationControllerDoc`).
  As annotations Swagger ficam **na interface**. Endpoint novo = método novo lá.
  16 dos 17 controllers seguem isso; `ImgBBController` é a exceção e não tem doc.
  Atenção ao nome invertido: `VirtualCurrencyController` implementa
  `CurrencyVirtualControllerDoc`.
- Retorna `ResponseEntity<XxxResponse>`. Nunca exponha entity na assinatura.
- Autorização por annotation do projeto: `@CanCreate*`, `@CanRead*`, `@CanUpdate*`
  (em `config/security/annotation/{create,read,update}/`). Recurso novo precisa
  de annotation nova + seed em `PermissionInitializer`.
- Usuário logado:
  `(JWTUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal()`.
- Entrada: `@RequestBody @Valid XxxRequest`. IDs são `UUID`.

**Rotas** são **singulares e hierárquicas**, não plural com hífen:
`/api/v1/user`, `/api/v1/user/profile`, `/api/v1/forum/publication`,
`/api/v1/publication/topic`, `/api/v1/currency/virtual/user/wallet`,
`/api/v1/image`. `AuthController` (`/api/v1/auth/`) é o único com barra no fim —
inconsistência existente, não replique. Tabela completa em `BACKEND.md`.

### Service / persistência

- `@Service` + `@RequiredArgsConstructor`. Regra de negócio mora aqui.
- Escrita multi-tabela leva `@Transactional`
  (`org.springframework.transaction.annotation.Transactional`).
- Leitura que pode não achar retorna `Optional<T>`.
- Conversão Entity ↔ DTO só via MapStruct, em `mapper/`.
- PK `UUID`. Soft delete via `deleted_at` — filtre os deletados.
- Mudança de entity **sempre** acompanha migration Flyway nova
  (`V<n>__desc.sql` em `src/main/resources/db/migration`).
  **Nunca edite migration já aplicada.**

### Exceções

Use as de `exceptions/`: `CustomBadRequestException`, `CustomNotFoundException`,
`UnauthorizedException`, `UniqueAlreadyExistsException`. São tratadas em
`ApplicationControllerAdvice`. Nada de `RuntimeException` cru.

### Testes

Só existe `ApiApplicationTests` (smoke test do Initializr). **Não finja que há
suíte ou cobertura.** Se pedirem teste, escreva JUnit 5 + Mockito para service,
ou `@SpringBootTest` para integração, e diga que é o primeiro do tipo.

## Honestidade

- Se não achar a causa raiz, diga. Liste o que descartou e o que falta.
- Se faltar stacktrace, cURL ou dado do banco, peça. Não adivinhe.
