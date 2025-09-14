CREATE TABLE permissions (
    permission_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE system_roles (
    system_role_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE users (
    user_id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    system_role_id INT REFERENCES system_roles(system_role_id) ON DELETE SET NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    last_login TIMESTAMP DEFAULT NULL,
    deleted_at timestamp DEFAULT NULL
);

CREATE TABLE user_profiles (
    user_id UUID PRIMARY KEY REFERENCES users(user_id) ON DELETE CASCADE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    profile_pic_url TEXT,
    profile_banner_url TEXT,
    biography VARCHAR(255),
    discord VARCHAR(255),
    linkedin VARCHAR(255),
    github VARCHAR(255),
    personal_website TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE user_logs (
    user_log_id SERIAL PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(user_id),
    activity_type VARCHAR(100),
    description TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE roles_permissions (
    role_permission_id SERIAL PRIMARY KEY,
    system_role_id INT NOT NULL REFERENCES system_roles(system_role_id) ON DELETE CASCADE,
    permission_id INT NOT NULL REFERENCES permissions(permission_id) ON DELETE CASCADE,
    UNIQUE (system_role_id, permission_id)
);

INSERT INTO system_roles (
    name,
    description
) VALUES
('ADMIN', 'Usuário que administra o sistema'),
('USER', 'Usuário comum'),
('MODERATOR', 'Usuário que tem algumas permissões para moderação');