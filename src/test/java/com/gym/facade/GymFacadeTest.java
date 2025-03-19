package com.gym.facade;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
import java.util.List;
import java.util.Optional;

class GymFacadeTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private GymFacade gymFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainee() {
        Trainee trainee = new Trainee();
        when(traineeService.create(trainee)).thenReturn(trainee);

        Trainee result = gymFacade.createTrainee(trainee);
        assertEquals(trainee, result);
    }

    @Test
    void testFindTraineeById() {
        Long id = 1L;
        Trainee trainee = new Trainee();
        when(traineeService.findById(id, "user", "pass")).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = gymFacade.findTraineeById(id, "user", "pass");
        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void testFindAllTrainees() {
        List<Trainee> trainees = List.of(new Trainee(), new Trainee());
        when(traineeService.findAll("user", "pass")).thenReturn(trainees);

        List<Trainee> result = gymFacade.findAllTrainees("user", "pass");
        assertEquals(2, result.size());
    }

    @Test
    void testCreateTrainer() {
        Trainer trainer = new Trainer();
        when(trainerService.create(trainer)).thenReturn(trainer);

        Trainer result = gymFacade.createTrainer(trainer);
        assertEquals(trainer, result);
    }

    @Test
    void testFindTrainerById() {
        Long id = 1L;
        Trainer trainer = new Trainer();
        when(trainerService.findById(id, "user", "pass")).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = gymFacade.findTrainerById(id, "user", "pass");
        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void testFindAllTrainers() {
        List<Trainer> trainers = List.of(new Trainer(), new Trainer());
        when(trainerService.findAll("user", "pass")).thenReturn(trainers);

        List<Trainer> result = gymFacade.findAllTrainers("user", "pass");
        assertEquals(2, result.size());
    }

    @Test
    void testCreateTraining() {
        Training training = new Training();
        when(trainingService.create(training)).thenReturn(training);

        Training result = gymFacade.createTraining(training);
        assertEquals(training, result);
    }

    @Test
    void testFindTrainingsList() {
        List<Training> trainings = List.of(new Training(), new Training());

        when(traineeService.findTrainingsList(
                eq("trainee"),
                any(LocalDate.class),
                any(LocalDate.class),
                eq("trainer"),
                any(TrainingType.class),
                eq("user"),
                eq("pass")
        )).thenReturn(trainings);

        List<Training> result = gymFacade.findTraineeTrainings(
                "trainee", LocalDate.now(), LocalDate.now(), "trainer", new TrainingType(), "user", "pass"
        );

        assertEquals(2, result.size());
    }

}
