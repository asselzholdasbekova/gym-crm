package com.gym.service;

import com.gym.dao.TraineeDao;
import com.gym.model.Trainee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class TraineeService {
    private TraineeDao traineeDao;

    @Autowired
    public void setTraineeDao(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    public void create(Trainee trainee) {
        if (traineeDao.existsByUsername(trainee.getUsername())) {
            log.warn("Trainee with username {} already exists", trainee.getUsername());
            throw new IllegalArgumentException("Trainee with this username already exists");
        }
        traineeDao.save(trainee);
        log.info("Created new trainee: {}", trainee);
    }

    public Trainee update(Trainee trainee) {
        Trainee updatedTrainee = traineeDao.update(trainee);
        if (updatedTrainee != null) {
            log.info("Updated trainee: {}", updatedTrainee);
        } else {
            log.warn("Trainee with id {} not found", trainee.getId());
        }
        return updatedTrainee;
    }

    public Optional<Trainee> findById(Long id) {
        return traineeDao.findById(id);
    }

    public Collection<Trainee> findAll() {
        return traineeDao.findAll();
    }

    public void deleteById(Long id) {
        if (traineeDao.findById(id).isPresent()) {
            traineeDao.deleteById(id);
            log.info("Deleted trainee with id: {}", id);
        } else {
            log.warn("Trainee with id {} not found", id);
            throw new IllegalArgumentException("Trainee not found");
        }
    }
}
