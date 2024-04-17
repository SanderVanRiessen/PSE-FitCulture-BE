package app.dtos.exercise.response;

import app.models.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetJoinedExercisePlan extends GetExercisePlan {
    private Status status;
    private List<GetJoinedExercise> exercises;
}
