package app.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "joinedexercises")
@Getter
@Setter
public class JoinedExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "joinedexerciseplan_id")
    private JoinedExercisePlan exercisePlan;

    @OneToOne
    @JoinColumn(name = "linkedexercise_id")
    private Exercise exercise;

    @Enumerated(EnumType.STRING)
    private Status status;
}
