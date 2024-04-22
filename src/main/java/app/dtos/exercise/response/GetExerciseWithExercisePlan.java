package app.dtos.exercise.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetExerciseWithExercisePlan extends GetExercise {
    private GetExercisePlan exercisePlan;
}
