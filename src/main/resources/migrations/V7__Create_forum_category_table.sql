CREATE TABLE IF NOT EXISTS categories (
                                          id BIGINT PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL,
                                          description LONGTEXT
);