package app.repository;

import app.models.ExercisePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExercisePlanRepository extends JpaRepository<ExercisePlan, Long> {
}
