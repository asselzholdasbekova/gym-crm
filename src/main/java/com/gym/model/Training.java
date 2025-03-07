package com.gym.model;

import java.time.LocalDate;

public class Training {
    private Long id;
    private Trainer trainer;
    private Trainee trainee;
    private String name;
    private TrainingType trainingType;
    private LocalDate date;
    private long duration;

    public Training() {}

    public Training(Trainer trainer, Trainee trainee, String name, TrainingType trainingType, LocalDate date, int duration) {
        this.trainer = trainer;
        this.trainee = trainee;
        this.name = name;
        this.trainingType = trainingType;
        this.date = date;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Trainee getTrainee() {
        return trainee;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", trainer=" + trainer.getUsername() +
                ", trainee=" + trainee.getUsername() +
                ", name='" + name + '\'' +
                ", trainingType=" + trainingType.getName() +
                ", date=" + date +
                ", duration=" + duration +
                '}';
    }
}
