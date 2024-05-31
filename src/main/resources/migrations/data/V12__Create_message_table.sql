CREATE TABLE IF NOT EXISTS messages (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        message VARCHAR(255) NOT NULL,
    owner_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES users(id),
    CONSTRAINT fk_receiver_id FOREIGN KEY (receiver_id) REFERENCES users(id)
    );