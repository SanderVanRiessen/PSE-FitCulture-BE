package app.dtos.exercise.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetExercisePlanWithExercises extends GetExercisePlan {
    private List<GetExercise> exercises;
}
