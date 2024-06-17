CREATE TABLE IF NOT EXISTS friends (
    my_user_id BIGINT,
    other_user_id BIGINT,
    status VARCHAR(255) NOT NULL,
    primary key (my_user_id, other_user_id),
    CONSTRAINT fk_my_user_id FOREIGN KEY (my_user_id) REFERENCES users(id),
    CONSTRAINT fk_other_user_id  FOREIGN KEY (other_user_id) REFERENCES users(id)
    );