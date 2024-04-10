package app.dtos.exercise.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddExercise {
    private String name;
    private String description;
    private Long exercisePlanId;
}
