CREATE TABLE transaction_types (
    transaction_type_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE virtual_currencies (
    virtual_currency_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    symbol VARCHAR(10) UNIQUE,
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

CREATE TABLE virtual_currency_transaction_parameters (
    virtual_currency_transaction_parameter_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    cost BIGINT DEFAULT 0,
    reward BIGINT DEFAULT 0,
    virtual_currency_id INT NOT NULL REFERENCES virtual_currencies(virtual_currency_id),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO virtual_currencies (name, symbol, exchange_rate, description) VALUES
('RogueDollar', 'RD', 1.0000, 'Moeda virtual oficial para recompensas e interações dentro da plataforma.');

INSERT INTO virtual_currency_transaction_parameters (name, description, cost, reward, virtual_currency_id) VALUES
('create_forum_article', 'Usuário ganha 20 moedas ao criar um artigo', 0, 200, 1),
('create_forum_question', 'Usuário ganha 20 moedas ao criar uma questão', 0, 200, 1),
('create_forum_comment', 'Usuário ganha 20 moedas ao criar um comentário', 0, 75, 1),
('upvote_forum_article', 'Custa 5 moedas para dar like em um artigo', 50, 0, 1),
('downvote_forum_article', 'Custa 5 moedas para dar dislike  em um artigo', 50, 0, 1),
('upvote_forum_question', 'Custa 5 moedas para dar like em uma questão', 50, 0, 1),
('downvote_forum_question', 'Custa 3 moedas para dar dislike em uma questão', 50, 0, 1),
('upvote_forum_comment', 'Custa 3 moedas para dar like em um comentário', 25, 0, 1),
('downvote_forum_comment', 'Custa 3 moedas para dar dislike em um comentário', 25, 0, 1);

INSERT INTO transaction_types (name, description) VALUES
('EARN', 'Crédito de moedas por interações ou submissões'),
('SPEND', 'Débito de moedas ao gastar em serviços ou recursos'),
('BONUS', 'Moedas concedidas como bônus ou recompensa especial'),
('REFUND', 'Moedas devolvidas ao usuário, estorno de gasto');