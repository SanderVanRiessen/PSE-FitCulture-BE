package app.dtos.exercise.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddExercisePlan {
    private String name;
    private String description;
}
