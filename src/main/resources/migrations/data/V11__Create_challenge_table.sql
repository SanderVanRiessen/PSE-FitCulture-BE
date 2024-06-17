CREATE TABLE challenges (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            description TEXT,
                            exercise_plan_id BIGINT,
                            start_date DATE,
                            end_date DATE,
                            status VARCHAR(255),
                            CONSTRAINT fk_exercise_plan FOREIGN KEY (exercise_plan_id) REFERENCES exerciseplans(id)
);

CREATE TABLE challenge_participants (
                                        challenge_id BIGINT,
                                        user_id BIGINT,
                                        PRIMARY KEY (challenge_id, user_id),
                                        CONSTRAINT fk_challenge FOREIGN KEY (challenge_id) REFERENCES challenges(id) ON DELETE CASCADE,
                                        CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
