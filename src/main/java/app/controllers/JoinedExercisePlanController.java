package app.controllers;

import app.dtos.MessageResponse;
import app.dtos.exercise.payload.JoinExercisePlan;
import app.dtos.exercise.response.GetJoinedExercisePlan;
import app.dtos.exercise.response.MapperJoinedExercise;
import app.models.ExercisePlan;
import app.models.JoinedExercisePlan;
import app.models.User;
import app.repository.ExercisePlanRepository;
import app.repository.JoinedExercisePlanRepository;
import app.repository.JoinedExerciseRepository;
import app.repository.UserRepository;
import app.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class JoinedExercisePlanController {

    JoinedExercisePlanRepository joinedExercisePlanRepository;
    JoinedExerciseRepository joinedExerciseRepository;
    ExercisePlanRepository exercisePlanRepository;
    UserRepository userRepository;
    MapperJoinedExercise mapper;

    @Autowired
    public JoinedExercisePlanController(JoinedExercisePlanRepository joinedExercisePlanRepository, ExercisePlanRepository exercisePlanRepository, UserRepository userRepository, JoinedExerciseRepository joinedExerciseRepository, MapperJoinedExercise mapper) {
        this.joinedExercisePlanRepository = joinedExercisePlanRepository;
        this.exercisePlanRepository = exercisePlanRepository;
        this.userRepository = userRepository;
        this.joinedExerciseRepository = joinedExerciseRepository;
        this.mapper = mapper;
    }

    @Transactional
    @PostMapping("/joinexerciseplan")
    public ResponseEntity<?> joinExercisePlan(@RequestBody JoinExercisePlan joinExercisePlan, Authentication auth) {
        ExercisePlan exercisePlan = exercisePlanRepository.findById(joinExercisePlan.getExercisePlanId()) .orElseThrow(() -> new RuntimeException("Error: exercise plan is not found."));
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        User currentUser = userRepository.getById(userDetails.getId());
        JoinedExercisePlan newJoinedExercisePlan = new JoinedExercisePlan().joinExercisePlan(exercisePlan);
        newJoinedExercisePlan.setUser(currentUser);
        joinedExercisePlanRepository.save(newJoinedExercisePlan);
        joinedExerciseRepository.saveAll(newJoinedExercisePlan.getExercises());
        return ResponseEntity.ok(new MessageResponse("Created exercise plan"));
    }

    @Transactional
    @GetMapping("/yourexerciseplans")
    public ResponseEntity<?> yourExercisePlans(Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        User currentUser = userRepository.getById(userDetails.getId());
        List<JoinedExercisePlan> exercisePlans = joinedExercisePlanRepository.getAllByUserId(currentUser.getId());
        List<GetJoinedExercisePlan> serializedExercisePlans = exercisePlans.stream().map(mapper::getJoinedExercisePlan).collect(Collectors.toList());
        return ResponseEntity.ok(serializedExercisePlans);
    }

    @Transactional
    @GetMapping("/yourexerciseplans/{id}")
    public ResponseEntity<?> yourExercisePlans(Authentication auth, @PathVariable Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        User currentUser = userRepository.getById(userDetails.getId());
        JoinedExercisePlan exercisePlans = joinedExercisePlanRepository.getById(id);
        if (!Objects.equals(currentUser.getId(), exercisePlans.getUser().getId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("You are not the user of this exercise"));
        }
        GetJoinedExercisePlan serializedExercisePlans = mapper.getJoinedExercisePlan(exercisePlans);
        return ResponseEntity.ok(serializedExercisePlans);
    }
}
