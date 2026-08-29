---
name: TheRogueDev — backend (Spring Boot)
description: Convenções do TheRogueDev-api. Java 21, Spring Boot 3.5.5, Gradle, PostgreSQL, Flyway.
globs: "**/*.{java,yml,yaml,sql,gradle}"
---

# Backend — TheRogueDev-api

Pacote raiz: `br.com.theroguedev.api`. Camadas: **Controller → Service → Repository → Entity**.

Módulos de domínio: `user/`, `publication/`, `currency/virtual/`, `file/`.
Transversais: `config/`, `config/security/`, `shared/`, `exceptions/`.
Cada módulo tem `controller/`, `controller/doc/`, `service/`, `repository/`,
`mapper/`, `entity/`, `dto/request/`, `dto/response/`.

## Controller

- `@RestController` + `@RequestMapping` + `@RequiredArgsConstructor` (Lombok).
  Injeção por campo `private final` — nunca `@Autowired`.
- **Implementa a interface de doc** correspondente em `controller/doc/`
  (ex.: `ForumPublicationController implements ForumPublicationControllerDoc`).
  As annotations Swagger/OpenAPI ficam **na interface**, não no controller.
  Endpoint novo exige método novo na interface de doc.
  16 dos 17 controllers seguem; `ImgBBController` é a única exceção.
  `VirtualCurrencyController` implementa `CurrencyVirtualControllerDoc` — nome
  invertido, é assim mesmo.
- Retorna `ResponseEntity<XxxResponse>`. Nunca exponha entity na assinatura pública.
- Autorização por annotation method-level do projeto: `@CanCreate*`, `@CanRead*`,
  `@CanUpdate*` (em `config/security/annotation/{create,read,update}/`).
  Recurso novo precisa de annotation nova + seed em `PermissionInitializer`.
- Usuário autenticado vem de
  `(JWTUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal()`.
- Body de entrada: `@RequestBody @Valid XxxRequest`. IDs são `UUID`.

**Rotas** seguem convenção **singular e hierárquica**: `/api/v1/user/profile`,
`/api/v1/forum/publication`, `/api/v1/publication/topic`,
`/api/v1/currency/virtual/user/wallet`, `/api/v1/image`.
Nunca plural com hífen. `AuthController` (`/api/v1/auth/`) é o único com barra
no fim — inconsistência existente, não replique.
A tabela completa está no `BACKEND.md`; ainda assim, confirme no `@RequestMapping`
antes de afirmar uma URL.

## Service

- `@Service` + `@RequiredArgsConstructor`. Toda a regra de negócio mora aqui.
- Escrita que toca mais de uma tabela leva `@Transactional`
  (`org.springframework.transaction.annotation.Transactional`).
- Leitura que pode não achar nada retorna `Optional<T>`; quem estoura
  `CustomNotFoundException` é o chamador ou um método privado `findXxxById`.

## Mapper

MapStruct. Um mapper por agregado, em `mapper/`. Métodos `toResponse(Entity)`,
`toEntity(Request)`. Conversão Entity ↔ DTO **só** no mapper — nunca à mão no controller.

## Persistência

- PK `UUID`. Soft delete via coluna `deleted_at` — ao filtrar, exclua os deletados.
- Migration nova: arquivo Flyway `V<n>__descricao.sql` em `src/main/resources/db/migration`.
  **Nunca edite uma migration já aplicada** — crie a próxima versão.
- Mudança de entity sem migration correspondente é bug. Sempre entregue as duas.

## Exceções

Use as do projeto, em `exceptions/`: `CustomBadRequestException`,
`CustomNotFoundException`, `UnauthorizedException`, `UniqueAlreadyExistsException`.
São tratadas em `ApplicationControllerAdvice`. Não crie exceção nova sem necessidade
e não lance `RuntimeException` cru.

## Testes

O projeto hoje só tem `ApiApplicationTests`. **Não invente uma suíte que não existe**
e não cite cobertura como se houvesse. Se pedirem teste, escreva JUnit 5 + Mockito
para service, ou `@SpringBootTest` para integração, e diga que é o primeiro do tipo.
