package com.gym.service.impl;

import com.gym.model.Training;
import com.gym.repository.TrainingRepository;
import com.gym.service.TrainingService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    @Transactional
    public Training create(Training training) {
        log.info("Creating new training: {}", training);

        trainingRepository.create(training);
        log.info("Training created: {}", training);

        return training;
    }
}
