package app.dtos.challenge.mapper;

import app.dtos.challenge.mappers.ChallengeMapper;
import app.dtos.challenge.payload.CreateChallengeRequestDTO;
import app.dtos.challenge.response.ChallengeResponseDTO;
import app.dtos.user.payload.UserDTO;
import app.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChallengeMapperTest {

    @Test
    public void testToDTO() {
        User user1 = new User(1L, "John Doe", "john@example.com", "password", new Role(ERole.USER));
        User user2 = new User(2L, "Jane Smith", "jane@example.com", "password", new Role(ERole.USER));

        ExercisePlan exercisePlan = new ExercisePlan();
        exercisePlan.setId(1L);
        exercisePlan.setName("Plan 1");

        Challenge challenge = new Challenge();
        challenge.setId(1L);
        challenge.setName("Challenge 1");
        challenge.setDescription("Description 1");
        challenge.setExercisePlan(exercisePlan);
        challenge.setParticipants(Arrays.asList(user1, user2));
        challenge.setStartDate(LocalDate.now());
        challenge.setEndDate(LocalDate.now().plusDays(7));
        challenge.setStatus(Status.TODO);

        ChallengeResponseDTO dto = ChallengeMapper.toDTO(challenge);

        assertNotNull(dto);
        assertEquals(challenge.getId(), dto.getId());
        assertEquals(challenge.getName(), dto.getName());
        assertEquals(challenge.getDescription(), dto.getDescription());
        assertEquals(challenge.getExercisePlan().getId(), dto.getExercisePlanId());
        assertEquals(challenge.getStartDate(), dto.getStartDate());
        assertEquals(challenge.getEndDate(), dto.getEndDate());
        assertEquals(challenge.getStatus().name(), dto.getStatus());

        List<UserDTO> participants = dto.getParticipants();
        assertNotNull(participants);
        assertEquals(2, participants.size());
        assertEquals(user1.getId(), participants.get(0).getId());
        assertEquals(user2.getId(), participants.get(1).getId());
    }

    @Test
    public void testToEntity() {
        CreateChallengeRequestDTO request = new CreateChallengeRequestDTO();
        request.setName("Challenge 1");
        request.setDescription("Description 1");
        request.setExercisePlanId(1L);
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now().plusDays(7));

        User challenger = new User(1L, "John Doe", "john@example.com", "password", new Role(ERole.USER));
        User challengedUser = new User(2L, "Jane Smith", "jane@example.com", "password", new Role(ERole.USER));
        ExercisePlan exercisePlan = new ExercisePlan();
        exercisePlan.setId(1L);
        exercisePlan.setName("Plan 1");

        Challenge challenge = ChallengeMapper.toEntity(request, challenger, challengedUser, exercisePlan);

        assertNotNull(challenge);
        assertEquals(request.getName(), challenge.getName());
        assertEquals(request.getDescription(), challenge.getDescription());
        assertEquals(exercisePlan.getId(), challenge.getExercisePlan().getId());
        assertEquals(request.getStartDate(), challenge.getStartDate());
        assertEquals(request.getEndDate(), challenge.getEndDate());
        assertEquals(Status.TODO, challenge.getStatus());

        List<User> participants = challenge.getParticipants();
        assertNotNull(participants);
        assertEquals(2, participants.size());
        assertEquals(challenger.getId(), participants.get(0).getId());
        assertEquals(challengedUser.getId(), participants.get(1).getId());
    }
}
