package com.gym.service;

import com.gym.dao.TrainingDao;
import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceTest {

    @Mock
    private TrainingDao trainingDao;

    @InjectMocks
    private TrainingService trainingService;

    private Training training;
    private Trainer trainer;
    private Trainee trainee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainer = new Trainer("Алексей", "Петров", true, new TrainingType("Cardio"));
        trainee = new Trainee("Иван", "Иванов", true, LocalDate.parse("2000-01-01"), "Фурманова 2");
        training = new Training(trainer, trainee, "Кардио", new TrainingType("Cardio"), LocalDate.now(), 60);
    }

    @Test
    void testCreateTraining() {
        trainingService.create(training);
        verify(trainingDao, times(1)).save(training);
    }

    @Test
    void testFindTrainingById() {
        when(trainingDao.findById(1L)).thenReturn(Optional.of(training));
        Optional<Training> foundTraining = trainingService.findById(1L);
        assertTrue(foundTraining.isPresent());
        assertEquals(training.getName(), foundTraining.get().getName());
    }

    @Test
    void testFindAllTrainings() {
        List<Training> trainings = Arrays.asList(training);
        when(trainingDao.findAll()).thenReturn(trainings);
        assertEquals(1, trainingService.findAll().size());
    }

    @Test
    void testDeleteTraining() {
        trainingService.deleteById(1L);
        verify(trainingDao, times(1)).deleteById(1L);
    }
}
