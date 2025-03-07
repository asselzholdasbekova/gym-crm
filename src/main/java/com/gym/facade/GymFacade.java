package com.gym.facade;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.service.TraineeService;
import com.gym.service.TrainerService;
import com.gym.service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Component
public class GymFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    @Autowired
    public GymFacade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    // --- Trainee Methods ---
    public void createTrainee(Trainee trainee) {
        log.info("Creating trainee: {}", trainee);
        traineeService.create(trainee);
    }

    public Trainee updateTrainee(Trainee trainee) {
        return traineeService.update(trainee);
    }

    public Optional<Trainee> findTraineeById(Long id) {
        return traineeService.findById(id);
    }

    public Collection<Trainee> findAllTrainees() {
        return traineeService.findAll();
    }

    public void deleteTraineeById(Long id) {
        traineeService.deleteById(id);
    }

    public void createTrainer(Trainer trainer) {
        log.info("Creating trainer: {}", trainer);
        trainerService.create(trainer);
    }

    public Trainer updateTrainer(Trainer trainer) {
        return trainerService.update(trainer);
    }

    public Optional<Trainer> findTrainerById(Long id) {
        return trainerService.findById(id);
    }

    public Collection<Trainer> findAllTrainers() {
        return trainerService.findAll();
    }

    public void deleteTrainerById(Long id) {
        trainerService.deleteById(id);
    }

    public void createTraining(Training training) {
        log.info("Creating training: {}", training);
        trainingService.create(training);
    }

    public Training updateTraining(Training training) {
        return trainingService.update(training);
    }

    public Optional<Training> findTrainingById(Long id) {
        return trainingService.findById(id);
    }

    public Collection<Training> findAllTrainings() {
        return trainingService.findAll();
    }

    public void deleteTrainingById(Long id) {
        trainingService.deleteById(id);
    }
}
