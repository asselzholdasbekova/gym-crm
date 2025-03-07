package com.gym.service;

import com.gym.dao.TrainingDao;
import com.gym.model.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class TrainingService {
    private TrainingDao trainingDao;

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    public void create(Training training) {
        log.info("Creating training: {}", training);
        trainingDao.save(training);
    }

    public Training update(Training training) {
        log.info("Updating training: {}", training);
        return trainingDao.update(training);
    }

    public Optional<Training> findById(Long id) {
        return trainingDao.findById(id);
    }

    public Collection<Training> findAll() {
        return trainingDao.findAll();
    }

    public void deleteById(Long id) {
        log.info("Deleting training with id: {}", id);
        trainingDao.deleteById(id);
    }
}
