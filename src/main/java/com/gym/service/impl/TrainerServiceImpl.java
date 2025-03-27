package com.gym.service.impl;

import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.model.TrainingType;
import com.gym.model.User;
import com.gym.repository.TrainerRepository;
import com.gym.repository.TrainingRepository;
import com.gym.service.TrainerService;
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
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, TrainingRepository trainingRepository) {
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;

    }

    @Override
    @Transactional
    public Trainer create(Trainer trainer) {
        log.info("Creating new trainer: Firstname = {}, Lastname = {}", trainer.getFirstname(), trainer.getLastname());

        trainer.setUsername(UserUtil.generateUsername(
                trainer.getFirstname(),
                trainer.getLastname(),
                trainerRepository::existsByUsername
        ));
        trainer.setPassword(PasswordEncoder.encode(UserUtil.generatePassword()));

        trainerRepository.create(trainer);
        log.info("Trainer created: Username = {}, ID = {}", trainer.getUsername(), trainer.getId());

        return trainer;
    }

    @Override
    @Transactional
    public Trainer update(Trainer trainer, String username, String password) {
        User user = trainerRepository.findByUsername(username).orElseThrow();
        UserUtil.authenticate(user, password);

        log.info("Updating trainer: ID = {}, Username = {}", trainer.getId(), trainer.getUsername());
        Trainer updatedTrainer = trainerRepository.update(trainer);
        log.info("Trainer updated: ID = {}, Username = {}", trainer.getId(), trainer.getUsername());
        return updatedTrainer;
    }

    @Override
    public Optional<Trainer> findById(Long id, String username, String password) {
        User user = trainerRepository.findByUsername(username).orElseThrow();
        UserUtil.authenticate(user, password);

        log.info("Finding trainer by ID: {}", id);
        Optional<Trainer> trainer = trainerRepository.findById(id);
        log.info("Trainer {} found by ID: {}", trainer.isPresent() ? "" : "not", id);
        return trainer;
    }

    @Override
    public Optional<Trainer> findByUsername(String targetUsername, String username, String password) {
        User user = trainerRepository.findByUsername(username).orElseThrow();
        UserUtil.authenticate(user, password);

        log.info("Finding trainer by username: {}", targetUsername);
        Optional<Trainer> trainer = trainerRepository.findByUsername(targetUsername);
        log.info("Trainer {} found by username: {}", trainer.isPresent() ? "" : "not", targetUsername);
        return trainer;
    }

    @Override
    public List<Trainer> findAll(String username, String password) {
        User user = trainerRepository.findByUsername(username).orElseThrow();
        UserUtil.authenticate(user, password);

        log.info("Fetching all trainers");
        List<Trainer> trainers = trainerRepository.findAll();
        log.info("Found {} trainers", trainers.size());
        return trainers;
    }

    @Override
    @Transactional
    public void deleteById(Long id, String username, String password) {
        User user = trainerRepository.findByUsername(username).orElseThrow();
        UserUtil.authenticate(user, password);

        log.info("Deleting trainer by ID: {}", id);
        trainerRepository.deleteById(id);
        log.info("Trainer deleted by ID: {}", id);
    }

    @Override
    @Transactional
    public void deleteByUsername(String targetUsername, String username, String password) {
        User user = trainerRepository.findByUsername(username).orElseThrow();
        UserUtil.authenticate(user, password);

        log.info("Deleting trainer by username: {}", targetUsername);
        trainerRepository.deleteByUsername(targetUsername);
        log.info("Trainer deleted by username: {}", targetUsername);
    }

    @Override
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = trainerRepository.findByUsername(username).orElseThrow();
        UserUtil.authenticate(user, oldPassword);

        log.info("Attempting to change password for username: {}", username);

        Trainer trainer = trainerRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        if (!PasswordEncoder.matches(oldPassword, trainer.getPassword())) {
            log.warn("Incorrect old password for username: {}", username);
            throw new IllegalArgumentException("Incorrect old password");
        }

        trainer.setPassword(PasswordEncoder.encode(newPassword));
        trainerRepository.update(trainer);

        log.info("Password changed successfully for username: {}", username);
    }

    @Override
    @Transactional
    public void updateStatus(String targetUsername, String username, String password) {
        User user = trainerRepository.findByUsername(username).orElseThrow();
        UserUtil.authenticate(user, password);

        log.info("Updating status for trainer: {}", targetUsername);

        Trainer trainer = trainerRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        trainer.setActive(!trainer.isActive());
        trainerRepository.update(trainer);

        log.info("Trainer status updated: Username = {}, New Status = {}", targetUsername, trainer.isActive());
    }

    @Override
    public List<Training> findTrainingsList(String targetUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername, TrainingType trainingType, String username, String password) {
        User user = trainerRepository.findByUsername(username).orElseThrow();
        UserUtil.authenticate(user, password);

        return trainingRepository.findByDto(targetUsername, fromDate, toDate, trainerUsername);
    }
}

