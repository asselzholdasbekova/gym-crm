package com.gym.service;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.model.TrainingType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TraineeService {
    Trainee create(Trainee trainee);
    Trainee update(Trainee trainee, String username, String password);
    Optional<Trainee> findById(Long id, String username, String password);
    Optional<Trainee> findByUsername(String targetUsername, String username, String password);
    List<Trainee> findAll( String username, String password);
    void deleteById(Long id,  String username, String password);
    void deleteByUsername(String targetUsername,  String username, String password);
    void changePassword(String username, String oldPassword, String newPassword);
    void updateStatus(String targetUsername,  String username, String password);
    List<Training> findTrainingsList(String targetUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername, TrainingType trainingType, String username, String password);
    List<Trainer> findNotAssignedTrainers(String targetUsername, String username, String password);
    void updateTrainersList(String targetUsername, List<Trainer> trainers, String username, String password);
}
