package com.gym.model;

public class Trainer extends User {
    private TrainingType specialization;
    private Long userId;

    public Trainer() {}

    public Trainer(String firstname, String lastname, boolean isActive, TrainingType specialization) {
        super(firstname, lastname, isActive);
        this.specialization = specialization;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "specialization=" + specialization +
                '}';
    }
}
