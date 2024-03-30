package app.dtos.exercise.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetExercisePlan {
    private Long id;
    private String name;
    private String description;
}
