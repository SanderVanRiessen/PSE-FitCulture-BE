CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO roles (name)
values
    ('ADMIN'),
    ('USER'),
    ('AUTHOR');

ALTER TABLE users
    ADD COLUMN role_id BIGINT;

ALTER TABLE users
    ADD CONSTRAINT fk_role_id
        FOREIGN KEY (role_id)
            REFERENCES roles(id);

UPDATE users
SET role_id = 2;

ALTER TABLE users
DROP COLUMN role;

ALTER TABLE users
    CHANGE COLUMN role_id role BIGINT(20) NOT NULL;