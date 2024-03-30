package app.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "joinedexerciseplans")
@Getter
@Setter
public class JoinedExercisePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exercisePlan")
    private List<JoinedExercise> exercises;

    @OneToOne
    @JoinColumn(name = "linkedexerciseplan_id")
    private ExercisePlan exercisePlan;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

    public JoinedExercisePlan joinExercisePlan(ExercisePlan exercisePlan) {
        JoinedExercisePlan newJoinedExercisePlan = new JoinedExercisePlan();
        List<JoinedExercise> newJoinedExercises = new ArrayList<>();
        exercisePlan.getExercises().forEach((e) -> {
            JoinedExercise newJoinedExercise = new JoinedExercise();
            newJoinedExercise.setExercisePlan(newJoinedExercisePlan);
            newJoinedExercise.setExercise(e);
            newJoinedExercise.setStatus(Status.TODO);
            newJoinedExercises.add(newJoinedExercise);
        });
        newJoinedExercisePlan.setExercises(newJoinedExercises);
        newJoinedExercisePlan.setExercisePlan(exercisePlan);
        newJoinedExercisePlan.setStatus(Status.TODO);
        return newJoinedExercisePlan;
    }

    public boolean firstExerciseStarted(){
        return exercises.stream().anyMatch(e -> e.getStatus().equals(Status.INPROGRESS));
    }

    public boolean lastExerciseFinished(){
        return exercises.stream().allMatch(e -> e.getStatus().equals(Status.DONE));
    }
}
