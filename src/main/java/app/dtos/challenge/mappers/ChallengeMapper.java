package app.dtos.challenge.mappers;

import app.dtos.challenge.response.ChallengeResponseDTO;
import app.dtos.challenge.payload.CreateChallengeRequestDTO;
import app.dtos.user.payload.UserDTO;
import app.models.Challenge;
import app.models.ExercisePlan;
import app.models.Status;
import app.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChallengeMapper {

    public static ChallengeResponseDTO toDTO(Challenge challenge) {
        if (challenge == null) {
            return null;
        }

        ChallengeResponseDTO dto = new ChallengeResponseDTO();
        dto.setId(challenge.getId());
        dto.setName(challenge.getName());
        dto.setDescription(challenge.getDescription());
        dto.setExercisePlanId(challenge.getExercisePlan().getId());
        dto.setParticipants(toUserDTOList(challenge.getParticipants()));
        dto.setStartDate(challenge.getStartDate());
        dto.setEndDate(challenge.getEndDate());
        dto.setStatus(challenge.getStatus().name());

        return dto;
    }

    public static Challenge toEntity(CreateChallengeRequestDTO dto, User challenger, User challengedUser, ExercisePlan exercisePlan) {
        Challenge challenge = new Challenge();
        challenge.setName(dto.getName());
        challenge.setDescription(dto.getDescription());
        challenge.setExercisePlan(exercisePlan);
        List<User> participants = new ArrayList<>();
        participants.add(challenger);
        participants.add(challengedUser);
        challenge.setParticipants(participants);
        challenge.setStartDate(dto.getStartDate());
        challenge.setEndDate(dto.getEndDate());
        challenge.setStatus(Status.TODO);
        return challenge;
    }

    private static List<UserDTO> toUserDTOList(List<User> users) {
        return users.stream()
                .map(ChallengeMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    private static UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }
}
