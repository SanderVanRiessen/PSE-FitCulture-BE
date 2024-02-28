CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    hashed_password CHAR(60) NOT NULL,
    role VARCHAR(50) NOT NULL
    );
