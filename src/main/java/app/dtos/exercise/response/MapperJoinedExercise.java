package app.dtos.exercise.response;

import app.models.JoinedExercisePlan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperJoinedExercise {

    public GetJoinedExercisePlan getJoinedExercisePlan(JoinedExercisePlan joinedExercisePlan){
        GetJoinedExercisePlan dto = new GetJoinedExercisePlan();
        dto.setId(joinedExercisePlan.getId());
        dto.setName(joinedExercisePlan.getExercisePlan().getName());
        dto.setDescription(joinedExercisePlan.getExercisePlan().getDescription());
        dto.setStatus(joinedExercisePlan.getStatus());
        List<GetJoinedExercise> exercises = new ArrayList<>();
        joinedExercisePlan.getExercises().forEach((e) -> {
            GetJoinedExercise exercise = new GetJoinedExercise();
            exercise.setName(e.getExercise().getName());
            exercise.setDescription(e.getExercise().getDescription());
            exercise.setId(e.getId());
            exercise.setStatus(e.getStatus());
            exercises.add(exercise);
        });
        dto.setExercises(exercises);
        return dto;
    }
}
