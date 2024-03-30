package app.repository;

import app.models.JoinedExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinedExerciseRepository extends JpaRepository<JoinedExercise, Long> {
}
