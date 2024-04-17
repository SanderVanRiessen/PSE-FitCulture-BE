CREATE TABLE IF NOT EXISTS joinedexerciseplans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    linkedexerciseplan_id BIGINT,
    user_id BIGINT,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_linkedexerciseplan_id FOREIGN KEY (linkedexerciseplan_id) REFERENCES exerciseplans(id)
);

CREATE TABLE joinedexercises (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(255) NOT NULL,
    joinedexerciseplan_id BIGINT,
    linkedexercise_id BIGINT,
    CONSTRAINT fk_linkedExercise_id FOREIGN KEY (linkedexercise_id) REFERENCES exercises(id),
    CONSTRAINT fk_joinedexercisePlan_id FOREIGN KEY (joinedexerciseplan_id) REFERENCES joinedexerciseplans(id)
);

