package com.gym.facade;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.model.TrainingType;
import com.gym.service.TraineeService;
import com.gym.service.TrainerService;
import com.gym.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    // Trainee operations
    public Trainee createTrainee(Trainee trainee) {
        return traineeService.create(trainee);
    }

    public Trainee updateTrainee(Trainee trainee, String username, String password) {
        return traineeService.update(trainee, username, password);
    }

    public Optional<Trainee> findTraineeById(Long id, String username, String password) {
        return traineeService.findById(id, username, password);
    }

    public Optional<Trainee> findTraineeByUsername(String targetUsername, String username, String password) {
        return traineeService.findByUsername(targetUsername, username, password);
    }

    public List<Trainee> findAllTrainees(String username, String password) {
        return traineeService.findAll(username, password);
    }

    public void deleteTraineeById(Long id, String username, String password) {
        traineeService.deleteById(id, username, password);
    }

    public void deleteTraineeByUsername(String targetUsername, String username, String password) {
        traineeService.deleteByUsername(targetUsername, username, password);
    }

    public void changeTraineePassword(String username, String oldPassword, String newPassword) {
        traineeService.changePassword(username, oldPassword, newPassword);
    }

    public void updateTraineeStatus(String targetUsername, String username, String password) {
        traineeService.updateStatus(targetUsername, username, password);
    }

    public List<Training> findTraineeTrainings(String targetUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername, TrainingType trainingType, String username, String password) {
        return traineeService.findTrainingsList(targetUsername, fromDate, toDate, trainerUsername, trainingType, username, password);
    }

    public List<Trainer> findNotAssignedTrainers(String targetUsername, String username, String password) {
        return traineeService.findNotAssignedTrainers(targetUsername, username, password);
    }

    public void updateTraineeTrainers(String targetUsername, List<Trainer> trainers, String username, String password) {
        traineeService.updateTrainersList(targetUsername, trainers, username, password);
    }

    // Trainer operations
    public Trainer createTrainer(Trainer trainer) {
        return trainerService.create(trainer);
    }

    public Trainer updateTrainer(Trainer trainer, String username, String password) {
        return trainerService.update(trainer, username, password);
    }

    public Optional<Trainer> findTrainerById(Long id, String username, String password) {
        return trainerService.findById(id, username, password);
    }

    public Optional<Trainer> findTrainerByUsername(String targetUsername, String username, String password) {
        return trainerService.findByUsername(targetUsername, username, password);
    }

    public List<Trainer> findAllTrainers(String username, String password) {
        return trainerService.findAll(username, password);
    }

    public void deleteTrainerById(Long id, String username, String password) {
        trainerService.deleteById(id, username, password);
    }

    public void deleteTrainerByUsername(String targetUsername, String username, String password) {
        trainerService.deleteByUsername(targetUsername, username, password);
    }

    public void changeTrainerPassword(String username, String oldPassword, String newPassword) {
        trainerService.changePassword(username, oldPassword, newPassword);
    }

    public void updateTrainerStatus(String targetUsername, String username, String password) {
        trainerService.updateStatus(targetUsername, username, password);
    }

    public List<Training> findTrainerTrainings(String targetUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername, TrainingType trainingType, String username, String password) {
        return trainerService.findTrainingsList(targetUsername, fromDate, toDate, trainerUsername, trainingType, username, password);
    }

    // Training operations
    public Training createTraining(Training training) {
        return trainingService.create(training);
    }
}
