CREATE TABLE IF NOT EXISTS exerciseplans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description LONGTEXT NOT NULL
);

CREATE TABLE exercises (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description LONGTEXT NOT NULL,
    exerciseplan_id BIGINT,
    CONSTRAINT fk_exercisePlan_id FOREIGN KEY (exerciseplan_id) REFERENCES exerciseplans(id)
);

