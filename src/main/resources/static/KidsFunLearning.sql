-- ==========================
-- Root Tables
-- ==========================

CREATE TABLE parent (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    loginId VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE subject (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- ==========================
-- Second-Level Tables
-- ==========================

CREATE TABLE topic (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject_id BIGINT,
    name VARCHAR(255),
    display_order INT,
    FOREIGN KEY (subject_id) REFERENCES subject(id)
);

CREATE TABLE kid (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT,
    name VARCHAR(100) NOT NULL,
    age INT,
    grade VARCHAR(50),
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES parent(id)
);

-- ==========================
-- Many-to-Many / Join Tables
-- ==========================

CREATE TABLE parent_role (
    parent_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (parent_id, role_id),
    FOREIGN KEY (parent_id) REFERENCES parent(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE role_permission (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
);

CREATE TABLE parent_permission (
    parent_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    granted_at TIMESTAMP NOT NULL,
    PRIMARY KEY (parent_id, permission_id),
    FOREIGN KEY (parent_id) REFERENCES parent(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
);

-- ==========================
-- Dependent Tables
-- ==========================

CREATE TABLE parent_topic_settings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT,
    kid_id BIGINT,
    topic_id BIGINT,
    enabled BOOLEAN DEFAULT TRUE,
    min_difficulty INT,
    max_difficulty INT,
    adaptive_enabled BOOLEAN,
    UNIQUE (parent_id, kid_id, topic_id),
    FOREIGN KEY (parent_id) REFERENCES parent(id),
    FOREIGN KEY (kid_id) REFERENCES kid(id),
    FOREIGN KEY (topic_id) REFERENCES topic(id)
);

CREATE TABLE login_audit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT,
    login_time TIMESTAMP,
    success BOOLEAN,
    ip_address VARCHAR(255),
    FOREIGN KEY (parent_id) REFERENCES parent(id)
);

CREATE TABLE question (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    topic_id BIGINT,
    difficulty INT,
    question_text VARCHAR(1000),
    correct_answer VARCHAR(255),
    FOREIGN KEY (topic_id) REFERENCES topic(id)
);

CREATE TABLE learning_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kid_id BIGINT,
    subject_id BIGINT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    FOREIGN KEY (kid_id) REFERENCES kid(id),
    FOREIGN KEY (subject_id) REFERENCES subject(id)
);

CREATE TABLE attempt (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT,
    question_id BIGINT,
    given_answer VARCHAR(255),
    correct BOOLEAN,
    time_taken_ms BIGINT,
    attempted_at TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES learning_session(id),
    FOREIGN KEY (question_id) REFERENCES question(id)
);

CREATE TABLE counter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    kid_id BIGINT,
    count INT,
    last_updated TIMESTAMP NOT NULL,
    FOREIGN KEY (kid_id) REFERENCES kid(id)
);

CREATE TABLE analytics_snapshot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kid_id BIGINT,
    snapshot_date DATE,
    total_time_minutes INT,
    total_attempts INT,
    correct_attempts INT,
    accuracy DOUBLE,
    FOREIGN KEY (kid_id) REFERENCES kid(id)
);

CREATE TABLE recommendation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kid_id BIGINT,
    topic_id BIGINT,
    message VARCHAR(500),
    status VARCHAR(50),
    created_at TIMESTAMP,
    FOREIGN KEY (kid_id) REFERENCES kid(id),
    FOREIGN KEY (topic_id) REFERENCES topic(id)
);

CREATE TABLE todo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kid_id BIGINT,
    text VARCHAR(255) NOT NULL,
    note VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    completed_at TIMESTAMP,
    priority VARCHAR(10),
    completed BOOLEAN DEFAULT FALSE,
    archived BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (kid_id) REFERENCES kid(id)
);

CREATE TABLE topic_progress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kid_id BIGINT,
    topic_id BIGINT,
    total_attempts INT,
    correct_attempts INT,
    accuracy DOUBLE,
    mastery_level VARCHAR(255),
    current_difficulty INT,
    last_updated TIMESTAMP,
    UNIQUE (kid_id, topic_id),
    FOREIGN KEY (kid_id) REFERENCES kid(id),
    FOREIGN KEY (topic_id) REFERENCES topic(id)
);
