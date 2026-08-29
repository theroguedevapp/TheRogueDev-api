# TheRogueDev — Backend Map

## Stack

| Layer | Tech |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5.5 |
| Build | Gradle |
| Database | PostgreSQL (Docker, porta 5455) |
| Migrations | Flyway |
| Security | Spring Security 6 + JWT + OAuth2 Google |
| ORM | Spring Data JPA / Hibernate |
| Mapping | MapStruct 1.5.5 + Lombok |
| HTTP Client | Spring Cloud OpenFeign |
| Docs | Springdoc OpenAPI (Swagger) |
| File Upload | ImgBB API |

## Entry Point

`src/main/java/br/com/theroguedev/api/ApiApplication.java`
- `@EnableFeignClients` + `@SpringBootApplication`

## Package Structure

```
br.com.theroguedev.api/
├── config/
│   ├── ApplicationControllerAdvice.java   # Global exception handler (@RestControllerAdvice)
│   ├── PermissionInitializer.java         # Seed de permissões no startup
│   ├── SwaggerConfig.java
│   └── security/
│       ├── SecurityConfig.java            # Filtros, CORS, OAuth2, JWT
│       ├── CorsConfig.java
│       ├── CookieBearerTokenResolver.java # Lê JWT do cookie HTTP-only
│       ├── JWTUserData.java               # Claims do token
│       ├── CustomOAuth2UserService.java   # Provisiona usuário OAuth2 no DB
│       ├── OAuth2LoginSuccessHandler.java # Gera JWT após login Google
│       └── annotation/                   # Custom security annotations
│           ├── create/  (11 annotations @CanCreate*)
│           ├── read/    (10 annotations @CanRead*)
│           └── update/  (6 annotations @CanUpdate*)
├── user/
│   ├── controller/
│   │   ├── AuthController.java           # /api/v1/auth/
│   │   ├── UserController.java           # /api/v1/user
│   │   ├── UserProfileController.java    # /api/v1/user/profile
│   │   ├── PermissionController.java     # /api/v1/user/permission
│   │   └── SystemRoleController.java     # /api/v1/user/system-role
│   ├── service/    (5 services)
│   ├── repository/ (4 repositories)
│   ├── mapper/     (4 MapStruct mappers)
│   ├── entity/     User, UserProfile, SystemRole, Permission
│   └── dto/        request/ + response/
├── publication/
│   ├── controller/
│   │   ├── ForumPublicationController.java  # /api/v1/forum/publication
│   │   ├── StatusController.java            # /api/v1/publication/status
│   │   ├── ToolController.java              # /api/v1/publication/tool
│   │   ├── TopicController.java             # /api/v1/publication/topic
│   │   └── TypeController.java              # /api/v1/publication/type
│   ├── service/    (5 services)
│   ├── repository/ (5 repositories)
│   ├── mapper/     (5 mappers)
│   └── entity/     ForumPublication, Status, Tool, Topic, Type
├── currency/virtual/
│   ├── controller/
│   │   ├── VirtualCurrencyController.java        # /api/v1/currency/virtual
│   │   ├── UserWalletController.java              # .../user/wallet
│   │   ├── TransactionController.java             # .../transaction
│   │   ├── TransactionTypeController.java         # .../transaction/type
│   │   ├── TransactionParameterController.java    # .../transaction/parameter
│   │   └── ForumPublicationBalanceController.java # .../forum/publication/balance — votos/rating
│   ├── service/    (6 services)
│   ├── repository/ (6 repositories)
│   ├── mapper/     (6 mappers)
│   └── entity/     VirtualCurrency, UserVirtualWallet, Transaction, TransactionType, TransactionParameter, ForumPublicationBalance
├── file/
│   ├── controller/ ImgBBController.java   # /api/v1/image
│   ├── service/    ImgBBService.java
│   └── client/     ImgBBClient.java       # OpenFeign → ImgBB API
├── shared/
│   └── record/     VoteDirectionEnum.java
└── exceptions/
    ├── CustomBadRequestException.java
    ├── CustomNotFoundException.java
    ├── UnauthorizedException.java
    └── UniqueAlreadyExistsException.java
```

## API Endpoints

> Rotas abaixo extraídas do `@RequestMapping` de cada um dos 17 controllers da
> branch `main` em 2026-08-29.
> A convenção é **singular e hierárquica** — não plural com hífen.
> Confirme no fonte antes de mudar qualquer uma.

### Auth — `/api/v1/auth/`
| Method | Path | Descrição |
|---|---|---|
| POST | `/login` | Login com email/senha → JWT cookie |
| POST | `/register` | Cadastro |
| GET | `/validate` | Valida token ativo |
| POST | `/logout` | Limpa cookie de sessão |

Único controller com barra no fim da rota base. Não replique.

### Users — módulo `user/`
| Rota base | Controller |
|---|---|
| `/api/v1/user` | `UserController` |
| `/api/v1/user/profile` | `UserProfileController` |
| `/api/v1/user/permission` | `PermissionController` |
| `/api/v1/user/system-role` | `SystemRoleController` |

### Publications — módulo `publication/`
| Rota base | Controller |
|---|---|
| `/api/v1/forum/publication` | `ForumPublicationController` |
| `/api/v1/publication/status` | `StatusController` |
| `/api/v1/publication/tool` | `ToolController` |
| `/api/v1/publication/topic` | `TopicController` |
| `/api/v1/publication/type` | `TypeController` |

- CRUD de publicações com suporte a threads (`parent_id`)
- Gerenciamento de status, tipo, ferramentas e tópicos

### Virtual Currency — módulo `currency/virtual/`
| Rota base | Controller |
|---|---|
| `/api/v1/currency/virtual` | `VirtualCurrencyController` |
| `/api/v1/currency/virtual/user/wallet` | `UserWalletController` |
| `/api/v1/currency/virtual/transaction` | `TransactionController` |
| `/api/v1/currency/virtual/transaction/type` | `TransactionTypeController` |
| `/api/v1/currency/virtual/transaction/parameter` | `TransactionParameterController` |
| `/api/v1/currency/virtual/forum/publication/balance` | `ForumPublicationBalanceController` |

- Moedas virtuais, carteiras por usuário, histórico de transações, sistema de votos

### File Upload — módulo `file/`
| Rota base | Controller |
|---|---|
| `/api/v1/image` | `ImgBBController` |

- Upload de imagem via ImgBB (OpenFeign)

## Database Schema (Flyway)

| Migration | Tabelas |
|---|---|
| V1 | permissions, system_roles, users, user_profiles, user_logs, roles_permissions |
| V2 | publication_status, publication_types, publication_tools, publication_topics, forum_publications, forum_publications_authors, forum_publications_topics |
| V3 | transaction_types, virtual_currencies, user_virtual_wallets, transactions, transaction_parameters, forum_publication_balances |
| V4 | Seed data das tabelas de publicações |

### Destaques do Schema
- `users`: UUID PK, soft delete via `deleted_at`, `last_login`
- `forum_publications`: suporte a threads com `parent_id`, `slug` único, soft delete
- `user_virtual_wallets`: uma carteira por moeda por usuário

## Segurança

- **JWT**: token em cookie HTTP-only, expiração 600s, chaves RSA (authz.pub / authz.pem)
- **OAuth2**: Google login → `CustomOAuth2UserService` → `OAuth2LoginSuccessHandler` emite JWT
- **Autorização**: annotations method-level `@CanCreate*`, `@CanRead*`, `@CanUpdate*`
- **CORS**: configurado em `CorsConfig.java`

## Configurações (application.yml)

- Flyway habilitado com auto-migrate
- Swagger UI: `/swagger/index.html`
- API Docs: `/api/api-docs`
- Cookie JWT não-secure por padrão (dev)

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

## Profiles

| Profile | Arquivo | Ativar com |
|---|---|---|
| `dev` | `application-dev.yml` | `SPRING_PROFILES_ACTIVE=dev` |
| `prod` | `application-prod.yml` | `SPRING_PROFILES_ACTIVE=prod` |

### Dev
- Swagger habilitado (`/swagger/index.html`)
- `jwt.cookie-secure: false`
- `jpa.show-sql: true` + format SQL
- Logging DEBUG para pacote `br.com.theroguedev` e Spring Security
- CORS: `http://localhost:4200` (fixo no yml)

### Prod
- Swagger **desabilitado**
- `jwt.cookie-secure: true`
- `jpa.show-sql: false`
- Logging WARN (root) / INFO (aplicação)
- CORS: lido de `CORS_ALLOWED_ORIGINS` (env, suporta múltiplas origens separadas por vírgula)
- Porta: `SERVER_PORT` env var (default 8080)

## Scripts

```bash
./gradlew bootRun                                         # Dev (requer SPRING_PROFILES_ACTIVE=dev no .env)
./gradlew bootRun --args='--spring.profiles.active=prod' # Forçar prod local
./gradlew build     # Build
./gradlew test      # Testes
docker-compose up   # Subir PostgreSQL
```

## Convenções do Projeto

- Todo controller tem uma interface `doc/` com anotações Swagger separadas
- Padrão: Controller → Service → Repository → Entity
- DTOs separados em `request/` e `response/`
- MapStruct para conversão Entity ↔ DTO
- Soft delete em entidades principais (`deleted_at`)
- Lombok para reduzir boilerplate
