package app.dtos.challenge.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptChallengeRequestDTO {
    private Long challengeId;
    private String username;
}
