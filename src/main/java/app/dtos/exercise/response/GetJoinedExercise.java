package app.dtos.exercise.response;

import app.models.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetJoinedExercise extends GetExercise{
    private Status status;
}
