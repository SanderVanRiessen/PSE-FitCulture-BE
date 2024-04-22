package app.dtos.exercise.response;

import app.models.Exercise;
import app.models.ExercisePlan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperExercise {
    public GetExercise toPlainExercise(Exercise exercise) {
        GetExercise dto = new GetExercise();
        dto.setId(exercise.getId());
        dto.setName(exercise.getName());
        dto.setDescription(exercise.getDescription());
        return dto;
    }

    public GetExercisePlan toPlainExercisePlan(ExercisePlan exercisePlan) {
        GetExercisePlan dto = new GetExercisePlan();
        dto.setId(exercisePlan.getId());
        dto.setName(exercisePlan.getName());
        dto.setDescription(exercisePlan.getDescription());
        return dto;
    }

    public GetExerciseWithExercisePlan toExerciseWithExercisePlan(Exercise exercise) {
        GetExerciseWithExercisePlan dto = new GetExerciseWithExercisePlan();
        dto.setId(exercise.getId());
        dto.setName(exercise.getName());
        dto.setDescription(exercise.getDescription());
        GetExercisePlan exercisePlan = new GetExercisePlan();
        exercisePlan.setId(exercise.getExercisePlan().getId());
        exercisePlan.setDescription(exercise.getExercisePlan().getDescription());
        exercisePlan.setName(exercise.getExercisePlan().getName());
        dto.setExercisePlan(exercisePlan);
        return dto;
    }

    public GetExercisePlanWithExercises toExercisePlanWithExercises(ExercisePlan exercisePlan) {
        GetExercisePlanWithExercises dto = new GetExercisePlanWithExercises();
        dto.setId(exercisePlan.getId());
        dto.setName(exercisePlan.getName());
        dto.setDescription(exercisePlan.getDescription());
        List<GetExercise> exercises = new ArrayList<>();
        exercisePlan.getExercises().forEach((e) -> {
            GetExercise exercise = new GetExercise();
            exercise.setName(e.getName());
            exercise.setDescription(e.getDescription());
            exercise.setId(e.getId());
            exercises.add(exercise);
        });
        dto.setExercises(exercises);
        return dto;
    }
}
