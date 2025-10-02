CREATE TABLE transaction_types (
    transaction_type_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE virtual_currencies (
    virtual_currency_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    symbol VARCHAR(10),
    exchange_rate DECIMAL(10,4) DEFAULT 1,
    description TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE user_virtual_wallets (
    user_virtual_wallet_id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(user_id),
    virtual_currency_id INT NOT NULL REFERENCES virtual_currencies(virtual_currency_id),
    balance BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(user_id, virtual_currency_id)
);

CREATE TABLE forum_publication_virtual_balances (
    virtual_publication_balance_id UUID PRIMARY KEY,
    forum_publication_id UUID NOT NULL REFERENCES forum_publications(forum_publication_id),
    virtual_currency_id INT NOT NULL REFERENCES virtual_currencies(virtual_currency_id),
    credit BIGINT NOT NULL,
    debit BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(forum_publication_id, virtual_currency_id)
);

CREATE TABLE virtual_currency_transactions (
    virtual_currency_transaction_id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(user_id),
    virtual_currency_id INT NOT NULL REFERENCES virtual_currencies(virtual_currency_id),
    transaction_type_id INT NOT NULL REFERENCES transaction_types(transaction_type_id),
    amount BIGINT NOT NULL,
    related_forum_publication_id UUID REFERENCES forum_publications(forum_publication_id),
    related_user_id UUID REFERENCES users(user_id),
    description TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO transaction_types (name, description) VALUES
('EARN', 'Crédito de moedas por interações ou submissões'),
('SPEND', 'Débito de moedas ao gastar em serviços ou recursos'),
('BONUS', 'Moedas concedidas como bônus ou recompensa especial'),
('REFUND', 'Moedas devolvidas ao usuário, estorno de gasto');