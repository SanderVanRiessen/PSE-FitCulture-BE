package app.services;

import app.dtos.*;
import app.dtos.challenge.mappers.ChallengeMapper;
import app.dtos.challenge.payload.AcceptChallengeRequestDTO;
import app.dtos.challenge.payload.CompleteChallengeRequestDTO;
import app.dtos.challenge.payload.CreateChallengeRequestDTO;
import app.dtos.challenge.response.ChallengeResponseDTO;
import app.models.*;
import app.repository.ChallengeRepository;
import app.repository.ExercisePlanRepository;
import app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChallengeServiceTest {

    @Mock
    private ChallengeRepository challengeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExercisePlanRepository exercisePlanRepository;

    @InjectMocks
    private ChallengeService challengeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateChallenge() {
        CreateChallengeRequestDTO request = new CreateChallengeRequestDTO();
        request.setName("Challenge 1");
        request.setDescription("Description 1");
        request.setExercisePlanId(1L);
        request.setChallengerUsername("john");
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now().plusDays(7));

        User challenger = new User(1L, "John Doe", "john@example.com", "password", new Role(ERole.USER));
        User challengedUser = new User(2L, "Jane Smith", "jane@example.com", "password", new Role(ERole.USER));
        ExercisePlan exercisePlan = new ExercisePlan();
        exercisePlan.setId(1L);
        exercisePlan.setName("Plan 1");

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(challenger));
        when(userRepository.findByUsername("jane")).thenReturn(Optional.of(challengedUser)); // Adjust if necessary
        when(exercisePlanRepository.findById(1L)).thenReturn(Optional.of(exercisePlan));
        when(challengeRepository.save(any(Challenge.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ChallengeResponseDTO response = challengeService.createChallenge(request);

        assertNotNull(response);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getDescription(), response.getDescription());
        assertEquals(exercisePlan.getId(), response.getExercisePlanId());
        assertEquals(request.getStartDate(), response.getStartDate());
        assertEquals(request.getEndDate(), response.getEndDate());
        assertEquals(Status.TODO.name(), response.getStatus());

        verify(challengeRepository, times(1)).save(any(Challenge.class));
    }

    @Test
    public void testAcceptChallenge() {
        AcceptChallengeRequestDTO request = new AcceptChallengeRequestDTO();
        request.setChallengeId(1L);
        request.setUsername("john");

        User user = new User(1L, "John Doe", "john@example.com", "password", new Role(ERole.USER));
        Challenge challenge = new Challenge();
        challenge.setId(1L);
        challenge.setName("Challenge 1");
        challenge.setParticipants(Arrays.asList());

        when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(challengeRepository.save(any(Challenge.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ChallengeResponseDTO response = challengeService.acceptChallenge(request);

        assertNotNull(response);
        assertTrue(response.getParticipants().stream().anyMatch(u -> u.getId().equals(user.getId())));

        verify(challengeRepository, times(1)).save(any(Challenge.class));
    }

    @Test
    public void testCompleteChallenge() {
        CompleteChallengeRequestDTO request = new CompleteChallengeRequestDTO();
        request.setChallengeId(1L);

        Challenge challenge = new Challenge();
        challenge.setId(1L);
        challenge.setName("Challenge 1");
        challenge.setStatus(Status.TODO);

        when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
        when(challengeRepository.save(any(Challenge.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ChallengeResponseDTO response = challengeService.completeChallenge(request);

        assertNotNull(response);
        assertEquals(Status.DONE.name(), response.getStatus());

        verify(challengeRepository, times(1)).save(any(Challenge.class));
    }

    @Test
    public void testGetAllChallenges() {
        Challenge challenge1 = new Challenge();
        challenge1.setId(1L);
        challenge1.setName("Challenge 1");

        Challenge challenge2 = new Challenge();
        challenge2.setId(2L);
        challenge2.setName("Challenge 2");

        when(challengeRepository.findAll()).thenReturn(Arrays.asList(challenge1, challenge2));

        List<ChallengeResponseDTO> response = challengeService.getAllChallenges();

        assertNotNull(response);
        assertEquals(2, response.size());

        verify(challengeRepository, times(1)).findAll();
    }

    @Test
    public void testGetChallengeById() {
        Challenge challenge = new Challenge();
        challenge.setId(1L);
        challenge.setName("Challenge 1");

        when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

        ChallengeResponseDTO response = challengeService.getChallengeById(1L);

        assertNotNull(response);
        assertEquals(challenge.getId(), response.getId());
        assertEquals(challenge.getName(), response.getName());

        verify(challengeRepository, times(1)).findById(1L);
    }
}
