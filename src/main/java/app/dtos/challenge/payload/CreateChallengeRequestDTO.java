package app.dtos.challenge.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateChallengeRequestDTO {
    private String name;
    private String description;
    private Long exercisePlanId;
    private String challengerUsername;
    private LocalDate startDate;
    private LocalDate endDate;
}
