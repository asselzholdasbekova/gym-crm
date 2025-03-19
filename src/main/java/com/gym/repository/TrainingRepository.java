package com.gym.repository;

import com.gym.model.Trainer;
import com.gym.model.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingRepository {
    Training create(Training training);
    List<Training> findByDto(String traineeUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername);
}
