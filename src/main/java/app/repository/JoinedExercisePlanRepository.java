package app.repository;

import app.models.JoinedExercisePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinedExercisePlanRepository extends JpaRepository<JoinedExercisePlan, Long> {
    List<JoinedExercisePlan> getAllByUserId(long Id);
}
