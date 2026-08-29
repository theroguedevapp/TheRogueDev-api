# TheRogueDev API — Instruções para Claude Code

## Sobre

Backend da plataforma **TheRogueDev** — plataforma gamificada de aprendizado colaborativo (Spring Boot 3.5.5, Java 21, PostgreSQL).

Leia `BACKEND.md` para o mapeamento completo do projeto antes de qualquer desenvolvimento.

## Stack

| Layer | Tech |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.5.5 |
| Build | Gradle |
| Banco | PostgreSQL (Docker, porta 5455) |
| Migrations | Flyway |
| Segurança | Spring Security 6 + JWT + OAuth2 Google |
| ORM | Spring Data JPA / Hibernate |
| Mapeamento | MapStruct 1.5.5 + Lombok |
| HTTP Client | Spring Cloud OpenFeign |
| Upload | ImgBB API |
| Docs | Springdoc OpenAPI → `/swagger/index.html` |

## Pacote Raiz

`br.com.theroguedev.api`

## Módulos

Rotas conferidas no `@RequestMapping` de cada controller. A convenção é
**singular e hierárquica** — não plural com hífen.

| Módulo | Pacote | Controller | Rota base |
|---|---|---|---|
| Usuários | `user/` | `AuthController` | `/api/v1/auth/` |
| | | `UserController` | `/api/v1/user` |
| | | `UserProfileController` | `/api/v1/user/profile` |
| | | `PermissionController` | `/api/v1/user/permission` |
| | | `SystemRoleController` | `/api/v1/user/system-role` |
| Publicações | `publication/` | `ForumPublicationController` | `/api/v1/forum/publication` |
| | | `StatusController` | `/api/v1/publication/status` |
| | | `ToolController` | `/api/v1/publication/tool` |
| | | `TopicController` | `/api/v1/publication/topic` |
| | | `TypeController` | `/api/v1/publication/type` |
| Moeda Virtual | `currency/virtual/` | `VirtualCurrencyController` | `/api/v1/currency/virtual` |
| | | `UserWalletController` | `/api/v1/currency/virtual/user/wallet` |
| | | `TransactionController` | `/api/v1/currency/virtual/transaction` |
| | | `TransactionTypeController` | `/api/v1/currency/virtual/transaction/type` |
| | | `TransactionParameterController` | `/api/v1/currency/virtual/transaction/parameter` |
| | | `ForumPublicationBalanceController` | `/api/v1/currency/virtual/forum/publication/balance` |
| Upload | `file/` | `ImgBBController` | `/api/v1/image` |

`AuthController` é o único com barra no fim (`/api/v1/auth/`) — inconsistência
existente, não replique em controller novo.

## Convenções Obrigatórias

- Padrão: `Controller → Service → Repository → Entity`
- DTOs separados em `dto/request/` e `dto/response/`
- MapStruct para toda conversão Entity ↔ DTO (nunca converter manualmente)
- Interfaces Swagger em `controller/doc/` separadas do controller
- Soft delete via campo `deleted_at` (não deletar fisicamente)
- Permissões via annotations: `@CanCreate*`, `@CanRead*`, `@CanUpdate*`
- Exceções customizadas em `exceptions/` (Bad Request, Not Found, Unauthorized, Unique)
- Lombok em todas as entidades e DTOs

## Banco de Dados

- PostgreSQL porta **5455** (via docker-compose)
- Migrations Flyway em `src/main/resources/db/migration/`
- Nomear novas migrations: `V{n}__{descricao}.sql`

## Segurança

- JWT em cookie HTTP-only, expiração 600s, chaves RSA (`authz.pub` / `authz.pem`)
- OAuth2 Google: `CustomOAuth2UserService` → `OAuth2LoginSuccessHandler`
- CORS: `CorsConfig.java`

## Variáveis de Ambiente (.env)

```env
DATABASE_URL=jdbc:postgresql://localhost:5455/the_rogue_dev
DATABASE_NAME=the_rogue_dev
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres
POSTGRES_PORT=5455
SECRET=<jwt-secret>
IMGBB_UPLOAD_URL=https://api.imgbb.com/1/upload?key=<key>
GOOGLE_CLIENT_ID=<id>
GOOGLE_CLIENT_SECRET=<secret>
```

## Scripts

```bash
docker-compose up       # Sobe PostgreSQL
./gradlew bootRun       # API em http://localhost:8080
./gradlew build         # Build
./gradlew test          # Testes
```

## Estado dos testes

Só existe `ApiApplicationTests` (o smoke test gerado pelo Spring Initializr).
Não há suíte nem cobertura configurada — não descreva o projeto como se houvesse.
