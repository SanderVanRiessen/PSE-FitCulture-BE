package app.controllers;

import app.dtos.exercise.payload.AddExercisePlan;
import app.dtos.exercise.response.GetExercisePlan;
import app.dtos.exercise.response.GetExercisePlanWithExercises;
import app.dtos.exercise.response.MapperExercise;
import app.models.ExercisePlan;
import app.repository.ExercisePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExercisePlanController {

    ExercisePlanRepository exercisePlanRepository;
    MapperExercise mapper;

    @Autowired
    public ExercisePlanController(ExercisePlanRepository exercisePlanRepository, MapperExercise mapper) {
        this.exercisePlanRepository = exercisePlanRepository;
        this.mapper = mapper;
    }

    @Transactional
    @GetMapping("/exerciseplan")
    public ResponseEntity<?>getExercisePlans() {
        List<ExercisePlan> exercisePlan = exercisePlanRepository.findAll();
        List<GetExercisePlanWithExercises> serializedExercisePlan = exercisePlan.stream().map(mapper::toExercisePlanWithExercises).collect(Collectors.toList());
        return ResponseEntity.ok(serializedExercisePlan);
    }

    @Transactional
    @GetMapping("/exerciseplan/{id}")
    public ResponseEntity<?>getExercisePlan(@PathVariable Long id) {
        ExercisePlan exercisePlan = exercisePlanRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: no exerciseplan found"));
        GetExercisePlanWithExercises serializedExercisePlan = mapper.toExercisePlanWithExercises(exercisePlan);
        return ResponseEntity.ok(serializedExercisePlan);
    }

    @Transactional
    @PostMapping("/exerciseplan")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addExercisePlan(@RequestBody AddExercisePlan exercisePlan) {
        ExercisePlan newExercisePlan = new ExercisePlan();
        newExercisePlan.setName(exercisePlan.getName());
        newExercisePlan.setDescription(exercisePlan.getDescription());

        ExercisePlan createdExercisePlan = exercisePlanRepository.save(newExercisePlan);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdExercisePlan.getId()).toUri();

        GetExercisePlan serializedExercisePlan = mapper.toPlainExercisePlan(createdExercisePlan);
        return ResponseEntity.created(location).body(serializedExercisePlan);
    }
}
