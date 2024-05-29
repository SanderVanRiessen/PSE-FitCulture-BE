CREATE TABLE IF NOT EXISTS posts (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     body LONGTEXT NOT NULL,
                                     user_id BIGINT NOT NULL,
                                     topic_id BIGINT NOT NULL,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     CONSTRAINT fk_user_id_posts FOREIGN KEY (user_id) REFERENCES users(id),
                                     CONSTRAINT fk_forum_id FOREIGN KEY (topic_id) REFERENCES topics(id)
);