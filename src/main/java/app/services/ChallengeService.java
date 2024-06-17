package app.services;

import app.dtos.challenge.mappers.ChallengeMapper;
import app.dtos.challenge.payload.*;
import app.dtos.challenge.response.*;
import app.models.*;
import app.repository.ChallengeRepository;
import app.repository.ExercisePlanRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExercisePlanRepository exercisePlanRepository;

    public ChallengeResponseDTO createChallenge(CreateChallengeRequestDTO request) {
        User challenger = userRepository.findByUsername(request.getChallengerUsername())
                .orElseThrow(() -> new RuntimeException("Challenger not found"));
        User challengedUser = userRepository.findByUsername(request.getChallengerUsername()) // Assume same for simplicity
                .orElseThrow(() -> new RuntimeException("Challenged user not found"));
        ExercisePlan exercisePlan = exercisePlanRepository.findById(request.getExercisePlanId())
                .orElseThrow(() -> new RuntimeException("Exercise plan not found"));

        Challenge challenge = ChallengeMapper.toEntity(request, challenger, challengedUser, exercisePlan);
        challenge = challengeRepository.save(challenge);
        return ChallengeMapper.toDTO(challenge);
    }

    public ChallengeResponseDTO acceptChallenge(AcceptChallengeRequestDTO request) {
        Challenge challenge = challengeRepository.findById(request.getChallengeId())
                .orElseThrow(() -> new RuntimeException("Challenge not found"));
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        challenge.addParticipant(user);
        challengeRepository.save(challenge);
        return ChallengeMapper.toDTO(challenge);
    }

    public ChallengeResponseDTO completeChallenge(CompleteChallengeRequestDTO request) {
        Challenge challenge = challengeRepository.findById(request.getChallengeId())
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        challenge.setStatus(Status.DONE);
        challengeRepository.save(challenge);
        return ChallengeMapper.toDTO(challenge);
    }

    public List<ChallengeResponseDTO> getAllChallenges() {
        List<Challenge> challenges = challengeRepository.findAll();
        return challenges.stream()
                .map(ChallengeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ChallengeResponseDTO getChallengeById(Long id) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));
        return ChallengeMapper.toDTO(challenge);
    }
}
