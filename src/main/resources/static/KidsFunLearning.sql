-- ===============================
-- TABLE: parent
-- ===============================
CREATE TABLE parent (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL
);

-- ===============================
-- TABLE: kid
-- ===============================
CREATE TABLE kid (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT,
    name VARCHAR(100) NOT NULL,
    age INT,
    grade VARCHAR(50),
    created_at DATETIME NOT NULL,
    child_loginId VARCHAR(255),
    password VARCHAR(255) NOT NULL, -- new password column
    FOREIGN KEY (parent_id) REFERENCES parent(id) ON DELETE CASCADE
);

-- ===============================
-- TABLE: learning_session
-- ===============================
CREATE TABLE learning_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kid_id BIGINT,
    subject_id BIGINT,
    start_time DATETIME,
    end_time DATETIME,
    FOREIGN KEY (kid_id) REFERENCES kid(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE CASCADE
);

-- ===============================
-- TABLE: attempt
-- ===============================
CREATE TABLE attempt (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT,
    question_id BIGINT,
    given_answer VARCHAR(255),
    correct BOOLEAN,
    time_taken_ms BIGINT,
    attempted_at DATETIME,
    FOREIGN KEY (session_id) REFERENCES learning_session(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);

-- ===============================
-- TABLE: analytics_snapshot
-- ===============================
CREATE TABLE analytics_snapshot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kid_id BIGINT,
    snapshot_date DATE,
    total_time_minutes INT,
    total_attempts INT,
    correct_attempts INT,
    accuracy DOUBLE,
    FOREIGN KEY (kid_id) REFERENCES kid(id) ON DELETE CASCADE
);

-- ===============================
-- TABLE: counter
-- ===============================
CREATE TABLE counter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    kid_id BIGINT,
    count INT NOT NULL DEFAULT 0,
    last_updated DATETIME NOT NULL,
    FOREIGN KEY (kid_id) REFERENCES kid(id) ON DELETE CASCADE
);

-- ===============================
-- TABLE: recommendation
-- ===============================
CREATE TABLE recommendation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kid_id BIGINT,
    topic_id BIGINT,
    message VARCHAR(500),
    status VARCHAR(50),
    created_at DATETIME,
    FOREIGN KEY (kid_id) REFERENCES kid(id) ON DELETE CASCADE,
    FOREIGN KEY (topic_id) REFERENCES topic(id) ON DELETE CASCADE
);

-- ===============================
-- TABLE: parent_topic_settings
-- ===============================
CREATE TABLE parent_topic_settings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT,
    kid_id BIGINT,
    topic_id BIGINT,
    enabled BOOLEAN,
    min_difficulty INT,
    max_difficulty INT,
    adaptive_enabled BOOLEAN,
    UNIQUE(parent_id, kid_id, topic_id),
    FOREIGN KEY (parent_id) REFERENCES parent(id) ON DELETE CASCADE,
    FOREIGN KEY (kid_id) REFERENCES kid(id) ON DELETE CASCADE,
    FOREIGN KEY (topic_id) REFERENCES topic(id) ON DELETE CASCADE
);

-- ===============================
-- TABLE: topic_progress
-- ===============================
CREATE TABLE topic_progress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kid_id BIGINT,
    topic_id BIGINT,
    total_attempts INT,
    correct_attempts INT,
    accuracy DOUBLE,
    mastery_level VARCHAR(255),
    current_difficulty INT,
    last_updated DATETIME,
    UNIQUE(kid_id, topic_id),
    FOREIGN KEY (kid_id) REFERENCES kid(id) ON DELETE CASCADE,
    FOREIGN KEY (topic_id) REFERENCES topic(id) ON DELETE CASCADE
);

-- ===============================
-- TABLE: todo
-- ===============================
CREATE TABLE todo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kid_id BIGINT,
    text VARCHAR(255) NOT NULL,
    note VARCHAR(500),
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    completed_at DATETIME,
    priority VARCHAR(10),
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (kid_id) REFERENCES kid(id) ON DELETE CASCADE
);
