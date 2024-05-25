package app.dtos.challenge.response;

import app.dtos.user.payload.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ChallengeResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Long exercisePlanId;
    private List<UserDTO> participants;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
