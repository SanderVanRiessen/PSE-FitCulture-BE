package app.controllers;

import app.dtos.MessageResponse;
import app.dtos.exercise.payload.UpdateExercise;
import app.models.JoinedExercise;
import app.models.JoinedExercisePlan;
import app.models.Status;
import app.models.User;
import app.repository.JoinedExercisePlanRepository;
import app.repository.JoinedExerciseRepository;
import app.repository.UserRepository;
import app.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Objects;

@RestController
public class JoinedExerciseController {

    JoinedExerciseRepository joinedExerciseRepository;
    JoinedExercisePlanRepository joinedExercisePlanRepository;
    UserRepository userRepository;

    @Autowired
    public JoinedExerciseController(JoinedExerciseRepository joinedExerciseRepository, JoinedExercisePlanRepository joinedExercisePlanRepository, UserRepository userRepository) {
        this.joinedExerciseRepository = joinedExerciseRepository;
        this.joinedExercisePlanRepository = joinedExercisePlanRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @PutMapping("/exercise")
    public ResponseEntity<?> updateExercise(@RequestBody UpdateExercise updateExercise, Authentication auth) {
        JoinedExercise exercise = joinedExerciseRepository.getById(updateExercise.getId());

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        User currentUser = userRepository.getById(userDetails.getId());
        if (!Objects.equals(currentUser.getId(), exercise.getExercisePlan().getUser().getId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("You are not the user of this exercise"));
        }

        exercise.setStatus(updateExercise.getStatus());
        joinedExerciseRepository.saveAndFlush(exercise);
        JoinedExercisePlan currentExercisePlan = joinedExercisePlanRepository.getById(exercise.getExercisePlan().getId());
        if (currentExercisePlan.firstExerciseStarted()) {
            currentExercisePlan.setStatus(Status.INPROGRESS);
            joinedExercisePlanRepository.saveAndFlush(currentExercisePlan);
        } else if (currentExercisePlan.lastExerciseFinished()) {
            currentExercisePlan.setStatus(Status.DONE);
            joinedExercisePlanRepository.saveAndFlush(currentExercisePlan);
        }
        return ResponseEntity.ok(new MessageResponse("Status of exercise is changed"));
    }
}
