CREATE TABLE publication_status (
    publication_status_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE publication_types (
    publication_type_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE forum_publications (
    forum_publication_id UUID PRIMARY KEY,
    slug VARCHAR(255) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    image_url TEXT,
    parent_id UUID REFERENCES forum_publications(forum_publication_id),
    submitted_by UUID NOT NULL REFERENCES users(user_id),
    status_id INT REFERENCES publication_status(publication_status_id) NOT NULL DEFAULT 1,
    type_id INT REFERENCES publication_types(publication_type_id) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    deleted_at TIMESTAMP DEFAULT NULL
);

CREATE TABLE forum_publications_authors (
    forum_publication_id UUID NOT NULL REFERENCES forum_publications(forum_publication_id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(user_id),
    PRIMARY KEY (forum_publication_id, user_id)
);

CREATE TABLE publication_tools (
    publication_tool_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    hex_color VARCHAR(9),
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE forum_publications_tools (
    forum_publication_tool_id SERIAL PRIMARY KEY,
    forum_publication_id UUID NOT NULL REFERENCES forum_publications(forum_publication_id) ON DELETE CASCADE,
    publication_tool_id INT NOT NULL REFERENCES publication_tools(publication_tool_id) ON DELETE CASCADE,
    UNIQUE (forum_publication_id, publication_tool_id)
);

CREATE TABLE publication_topics (
    publication_topic_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    hex_color VARCHAR(9),
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE forum_publications_topics (
    forum_publication_topic_id SERIAL PRIMARY KEY,
    forum_publication_id UUID NOT NULL REFERENCES forum_publications(forum_publication_id) ON DELETE CASCADE,
    publication_topic_id INT NOT NULL REFERENCES publication_topics(publication_topic_id) ON DELETE CASCADE,
    UNIQUE (forum_publication_id, publication_topic_id)
);