package app.dtos.exercise.payload;

import app.models.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateExercise {
    private Long id;
    private Status status;
}
