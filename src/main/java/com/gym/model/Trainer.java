package com.gym.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "trainers")
@PrimaryKeyJoinColumn(name = "id")
public class Trainer extends User {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_type_id", nullable = false)
    private TrainingType specialization;

    @ManyToMany(mappedBy = "trainers")
    private List<Trainee> trainees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainer", orphanRemoval = true)
    private List<Training> trainings;
}


