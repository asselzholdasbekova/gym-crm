package com.gym.facade;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.model.TrainingType;
import com.gym.service.TraineeService;
import com.gym.service.TrainerService;
import com.gym.service.TrainingService;
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

class GymFacadeTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private GymFacade gymFacade;

    private Trainee trainee;
    private Trainer trainer;
    private Training training;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainer = new Trainer("Алексей", "Петров", true, new TrainingType("Cardio"));
        trainee = new Trainee("Иван", "Иванов", true, LocalDate.parse("2000-01-01"), "Фурманова 2");
        training = new Training(trainer, trainee, "Кардио", new TrainingType("Cardio"), LocalDate.now(), 60);
    }

    @Test
    void testGetAllTrainees() {
        List<Trainee> trainees = Arrays.asList(trainee);
        when(traineeService.findAll()).thenReturn(trainees);
        assertEquals(1, gymFacade.findAllTrainees().size());
    }

    @Test
    void testGetAllTrainers() {
        List<Trainer> trainers = Arrays.asList(trainer);
        when(trainerService.findAll()).thenReturn(trainers);
        assertEquals(1, gymFacade.findAllTrainers().size());
    }

    @Test
    void testGetAllTrainings() {
        List<Training> trainings = Arrays.asList(training);
        when(trainingService.findAll()).thenReturn(trainings);
        assertEquals(1, gymFacade.findAllTrainings().size());
    }
}
