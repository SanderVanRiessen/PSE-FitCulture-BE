CREATE TABLE IF NOT EXISTS articles (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
    body LONGTEXT NOT NULL,
    author CHAR(60) NOT NULL,
    date DATE NOT NULL
    );