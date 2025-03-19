package com.gym.service.impl;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.model.TrainingType;
import com.gym.repository.TraineeRepository;
import com.gym.repository.TrainingRepository;
import com.gym.service.TraineeService;
import com.gym.util.PasswordEncoder;
import com.gym.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final TrainingRepository trainingRepository;

    @Autowired
    public TraineeServiceImpl(TraineeRepository traineeRepository, TrainingRepository trainingRepository) {
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;

    }

    private void authenticate(String username, String password) {
        Optional<Trainee> trainee = traineeRepository.findByUsername(username);
        if (trainee.isEmpty() || !PasswordEncoder.matches(password, trainee.get().getPassword())) {
            log.warn("Authentication failed for username: {}", username);
            throw new SecurityException("Invalid username or password");
        }
    }

    @Override
    public Trainee create(Trainee trainee) {
        log.info("Creating new trainee: Firstname = {}, Lastname = {}", trainee.getFirstname(), trainee.getLastname());

        trainee.setUsername(UserUtil.generateUsername(
                trainee.getFirstname(),
                trainee.getLastname(),
                traineeRepository::existsByUsername
        ));
        trainee.setPassword(PasswordEncoder.encode(UserUtil.generatePassword()));

        traineeRepository.create(trainee);
        log.info("Trainee created: Username = {}, ID = {}", trainee.getUsername(), trainee.getId());

        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee, String username, String password) {
        authenticate(username, password);

        log.info("Updating trainee: ID = {}, Username = {}", trainee.getId(), trainee.getUsername());
        Trainee updatedTrainee = traineeRepository.update(trainee);
        log.info("Trainee updated: ID = {}, Username = {}", trainee.getId(), trainee.getUsername());
        return updatedTrainee;
    }

    @Override
    public Optional<Trainee> findById(Long id, String username, String password) {
        authenticate(username, password);

        log.info("Finding trainee by ID: {}", id);
        Optional<Trainee> trainee = traineeRepository.findById(id);
        log.info("Trainee {} found by ID: {}", trainee.isPresent() ? "" : "not", id);
        return trainee;
    }

    @Override
    public Optional<Trainee> findByUsername(String targetUsername, String username, String password) {
        authenticate(username, password);

        log.info("Finding trainee by username: {}", username);
        Optional<Trainee> trainee = traineeRepository.findByUsername(username);
        log.info("Trainee {} found by username: {}", trainee.isPresent() ? "" : "not", username);
        return trainee;
    }

    @Override
    public List<Trainee> findAll(String username, String password) {
        authenticate(username, password);

        log.info("Fetching all trainees");
        List<Trainee> trainees = traineeRepository.findAll();
        log.info("Found {} trainees", trainees.size());
        return trainees;
    }

    @Override
    public void deleteById(Long id, String username, String password) {
        authenticate(username, password);

        log.info("Deleting trainee by ID: {}", id);
        traineeRepository.deleteById(id);
        log.info("Trainee deleted by ID: {}", id);
    }

    @Override
    public void deleteByUsername(String targetUsername, String username, String password) {
        authenticate(username, password);

        log.info("Deleting trainee by username: {}", username);
        traineeRepository.deleteByUsername(username);
        log.info("Trainee deleted by username: {}", username);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        authenticate(username, oldPassword);

        log.info("Attempting to change password for username: {}", username);

        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));

        if (!PasswordEncoder.matches(oldPassword, trainee.getPassword())) {
            log.warn("Incorrect old password for username: {}", username);
            throw new IllegalArgumentException("Incorrect old password");
        }

        trainee.setPassword(PasswordEncoder.encode(newPassword));
        traineeRepository.update(trainee);

        log.info("Password changed successfully for username: {}", username);
    }

    @Override
    public void updateStatus(String targetUsername, String username, String password) {
        authenticate(username, password);
        log.info("Updating status for trainee: {}", username);

        Trainee trainee = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));

        trainee.setActive(!trainee.isActive());
        traineeRepository.update(trainee);

        log.info("Trainee status updated: Username = {}, New Status = {}", username, trainee.isActive());
    }

    @Override
    public List<Training> findTrainingsList(String targetUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername, TrainingType trainingType, String username, String password) {
        authenticate(username, password);

        return trainingRepository.findByDto(targetUsername, fromDate, toDate, trainerUsername);
    }

    @Override
    public List<Trainer> findNotAssignedTrainers(String targetUsername, String username, String password) {
        authenticate(username, password);

        return traineeRepository.findNotAssignedTrainers(targetUsername);
    }

    @Override
    public void updateTrainersList(String targetUsername, List<Trainer> trainers, String username, String password) {
        authenticate(username, password);

        Trainee trainee = traineeRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));

        trainee.setTrainers(trainers);
        traineeRepository.update(trainee);
    }
}
