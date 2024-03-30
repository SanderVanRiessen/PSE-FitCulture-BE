package app.controllers;

import app.dtos.exercise.payload.AddExercise;
import app.dtos.exercise.response.GetExercise;
import app.dtos.exercise.response.MapperExercise;
import app.models.Exercise;
import app.models.ExercisePlan;
import app.repository.ExercisePlanRepository;
import app.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ExerciseController {
    ExerciseRepository exerciseRepository;
    ExercisePlanRepository exercisePlanRepository;
    MapperExercise mapperExercise;

    @Autowired
    public ExerciseController(ExerciseRepository exerciseRepository, ExercisePlanRepository exercisePlanRepository, MapperExercise mapperExercise) {
        this.exerciseRepository = exerciseRepository;
        this.exercisePlanRepository = exercisePlanRepository;
        this.mapperExercise = mapperExercise;
    }

    @Transactional
    @GetMapping("/exercise/{id}")
    public ResponseEntity<?> getExercise(@PathVariable Long id){
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: no exercise found"));
        GetExercise serializedExercise = mapperExercise.toPlainExercise(exercise);
        return ResponseEntity.ok(serializedExercise);
    }

    @Transactional
    @GetMapping("/exercise")
    public ResponseEntity<?> getExercises(){
        List<Exercise> exercises = exerciseRepository.findAll();
        List<GetExercise> serializedExercises = exercises.stream().map(mapperExercise::toPlainExercise).collect(Collectors.toList());
        return ResponseEntity.ok(serializedExercises);
    }

    @Transactional
    @PostMapping("/exercise")
    public ResponseEntity<?> addExercise(@RequestBody AddExercise exercise){
        ExercisePlan exercisePlan = exercisePlanRepository.findById(exercise.getExercisePlanId()).orElseThrow(() -> new RuntimeException("Error: exercise plan not found"));
        Exercise newExercise = new Exercise();
        newExercise.setDescription(exercise.getDescription());
        newExercise.setName(exercise.getName());
        newExercise.setExercisePlan(exercisePlan);
        Exercise createdExercise = exerciseRepository.save(newExercise);
        GetExercise serializedExercise = mapperExercise.toPlainExercise(createdExercise);
        return ResponseEntity.ok(serializedExercise);
    }
}
