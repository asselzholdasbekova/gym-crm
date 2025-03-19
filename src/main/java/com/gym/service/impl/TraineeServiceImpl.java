package com.gym.service.impl;

import com.gym.model.*;
import com.gym.repository.TraineeRepository;
import com.gym.repository.TrainingRepository;
import com.gym.repository.UserRepository;
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
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    @Autowired
    public TraineeServiceImpl(TraineeRepository traineeRepository, TrainingRepository trainingRepository, UserRepository userRepository) {
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
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
    @Transactional
    public Trainee update(Trainee trainee, String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        UserUtil.authenticate(user, password);

        log.info("Updating trainee: ID = {}, Username = {}", trainee.getId(), trainee.getUsername());
        Trainee updatedTrainee = traineeRepository.update(trainee);
        log.info("Trainee updated: ID = {}, Username = {}", trainee.getId(), trainee.getUsername());
        return updatedTrainee;
    }

    @Override
    public Optional<Trainee> findById(Long id, String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        UserUtil.authenticate(user, password);

        log.info("Finding trainee by ID: {}", id);
        Optional<Trainee> trainee = traineeRepository.findById(id);
        log.info("Trainee {} found by ID: {}", trainee.isPresent() ? "" : "not", id);
        return trainee;
    }

    @Override
    public Optional<Trainee> findByUsername(String targetUsername, String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        UserUtil.authenticate(user, password);

        log.info("Finding trainee by username: {}", username);
        Optional<Trainee> trainee = traineeRepository.findByUsername(targetUsername);
        log.info("Trainee {} found by username: {}", trainee.isPresent() ? "" : "not", username);
        return trainee;
    }

    @Override
    public List<Trainee> findAll(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        UserUtil.authenticate(user, password);

        log.info("Fetching all trainees");
        List<Trainee> trainees = traineeRepository.findAll();
        log.info("Found {} trainees", trainees.size());
        return trainees;
    }

    @Override
    @Transactional
    public void deleteById(Long id, String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        UserUtil.authenticate(user, password);

        log.info("Deleting trainee by ID: {}", id);
        traineeRepository.deleteById(id);
        log.info("Trainee deleted by ID: {}", id);
    }

    @Override
    @Transactional
    public void deleteByUsername(String targetUsername, String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        UserUtil.authenticate(user, password);

        log.info("Deleting trainee by username: {}", targetUsername);
        traineeRepository.deleteByUsername(targetUsername);
        log.info("Trainee deleted by username: {}", targetUsername);
    }

    @Override
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findByUsername(username);
        UserUtil.authenticate(user, oldPassword);

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
    @Transactional
    public void updateStatus(String targetUsername, String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        UserUtil.authenticate(user, password);

        log.info("Updating status for trainee: {}", targetUsername);

        Trainee trainee = traineeRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));

        trainee.setActive(!trainee.isActive());
        traineeRepository.update(trainee);

        log.info("Trainee status updated: Username = {}, New Status = {}", targetUsername, trainee.isActive());
    }

    @Override
    public List<Training> findTrainingsList(String targetUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername, TrainingType trainingType, String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        UserUtil.authenticate(user, password);

        return trainingRepository.findByDto(targetUsername, fromDate, toDate, trainerUsername);
    }

    @Override
    public List<Trainer> findNotAssignedTrainers(String targetUsername, String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        UserUtil.authenticate(user, password);

        return traineeRepository.findNotAssignedTrainers(targetUsername);
    }

    @Override
    @Transactional
    public void updateTrainersList(String targetUsername, List<Trainer> trainers, String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        UserUtil.authenticate(user, password);

        Trainee trainee = traineeRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));

        trainee.setTrainers(trainers);
        traineeRepository.update(trainee);
    }
}
