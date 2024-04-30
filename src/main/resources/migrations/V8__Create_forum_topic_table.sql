CREATE TABLE IF NOT EXISTS topics (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      title VARCHAR(255) NOT NULL,
                                      text LONGTEXT,
                                      category_id BIGINT NOT NULL,
                                      user_id BIGINT NOT NULL,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                      CONSTRAINT fk_user_id_topics FOREIGN KEY (user_id) REFERENCES users(id),
                                      CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES categories(id)
);
