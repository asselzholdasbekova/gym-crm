package com.gym.service;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.model.TrainingType;
import com.gym.repository.TraineeRepository;
import com.gym.repository.TrainingRepository;
import com.gym.service.impl.TraineeServiceImpl;
import com.gym.util.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private Trainee trainee;
    private Trainer trainer;
    private Training training;
    private TrainingType trainingType;

    @BeforeEach
    void setUp() {
        trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUsername("testUser");
        trainee.setPassword(PasswordEncoder.encode("password"));

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUsername("trainerUser");

        trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setName("Yoga");

        training = new Training();
        training.setId(1L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        training.setDate(LocalDate.now());
        training.setDuration(60);
    }

    @Test
    void testCreate() {
        when(traineeRepository.create(any(Trainee.class))).thenReturn(trainee);
        Trainee createdTrainee = traineeService.create(new Trainee());
        assertNotNull(createdTrainee);
        verify(traineeRepository, times(1)).create(any(Trainee.class));
    }

    @Test
    void testUpdate() {
        when(traineeRepository.findByUsername("testUser")).thenReturn(Optional.of(trainee));
        when(traineeRepository.update(any(Trainee.class))).thenReturn(trainee);
        Trainee updatedTrainee = traineeService.update(trainee, "testUser", "password");
        assertNotNull(updatedTrainee);
        verify(traineeRepository, times(1)).update(any(Trainee.class));
    }

    @Test
    void testFindById() {
        when(traineeRepository.findByUsername("testUser")).thenReturn(Optional.of(trainee));
        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));
        Optional<Trainee> foundTrainee = traineeService.findById(1L, "testUser", "password");
        assertTrue(foundTrainee.isPresent());
    }

    @Test
    void testFindByUsername() {
        when(traineeRepository.findByUsername("testUser")).thenReturn(Optional.of(trainee));
        Optional<Trainee> foundTrainee = traineeService.findByUsername("testUser", "testUser", "password");
        assertTrue(foundTrainee.isPresent());
    }

    @Test
    void testFindAll() {
        when(traineeRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(trainee));
        when(traineeRepository.findAll()).thenReturn(List.of(trainee));
        List<Trainee> trainees = traineeService.findAll("testUser", "password");
        assertFalse(trainees.isEmpty());
    }

    @Test
    void testDeleteById() {
        when(traineeRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(trainee));
        doNothing().when(traineeRepository).deleteById(1L);
        traineeService.deleteById(1L, "testUser", "password");
        verify(traineeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByUsername() {
        when(traineeRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(trainee));
        doNothing().when(traineeRepository).deleteByUsername("testUser");
        traineeService.deleteByUsername("testUser", "testUser", "password");
        verify(traineeRepository, times(1)).deleteByUsername("testUser");
    }

    @Test
    void testChangePassword() {
        when(traineeRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(trainee));
        when(traineeRepository.update(any(Trainee.class))).thenReturn(trainee);
        traineeService.changePassword("testUser", "password", "newPassword");
        verify(traineeRepository, times(1)).update(any(Trainee.class));
    }

    @Test
    void testFindTrainingsList() {
        when(trainingRepository.findByDto("testUser", LocalDate.now().minusDays(7), LocalDate.now(), "trainerUser"))
                .thenReturn(List.of(training));
        List<Training> trainings = traineeService.findTrainingsList("testUser", LocalDate.now().minusDays(7), LocalDate.now(), "trainerUser", trainingType, "testUser", "password");
        assertFalse(trainings.isEmpty());
    }

    @Test
    void testFindNotAssignedTrainers() {
        when(traineeRepository.findByUsername("testUser")).thenReturn(Optional.of(trainee));
        when(traineeRepository.findNotAssignedTrainers("testUser")).thenReturn(List.of(trainer));
        List<Trainer> trainers = traineeService.findNotAssignedTrainers("testUser", "testUser", "password");
        assertFalse(trainers.isEmpty());
    }

    @Test
    void testUpdateTrainersList() {
        when(traineeRepository.findByUsername("testUser")).thenReturn(Optional.of(trainee));
        when(traineeRepository.update(any(Trainee.class))).thenReturn(trainee);
        traineeService.updateTrainersList("testUser", List.of(trainer), "testUser", "password");
        verify(traineeRepository, times(1)).update(any(Trainee.class));
    }
}
