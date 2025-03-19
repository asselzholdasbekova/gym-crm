package com.gym.repository;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;

import java.util.List;
import java.util.Optional;

public interface TraineeRepository {
    Trainee create(Trainee trainee);
    Trainee update(Trainee trainee);
    Optional<Trainee> findById(Long id);
    Optional<Trainee>  findByUsername(String username);
    List<Trainee> findAll();
    void deleteById(Long id);
    void deleteByUsername(String userName);
    List<Trainer> findNotAssignedTrainers(String traineeUserName);
    boolean existsByUsername(String username);
}