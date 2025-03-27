package com.gym.service;

import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.repository.TrainerRepository;
import com.gym.repository.TrainingRepository;
import com.gym.service.impl.TrainerServiceImpl;
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
class TrainerServiceTest {
    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstname("John");
        trainer.setLastname("Doe");
        trainer.setUsername("jdoe");
        trainer.setPassword(PasswordEncoder.encode("password"));
    }

    @Test
    void testCreateTrainer() {
        when(trainerRepository.existsByUsername(any())).thenReturn(false);
        when(trainerRepository.create(any())).thenReturn(trainer);

        Trainer created = trainerService.create(trainer);
        assertNotNull(created);
        assertEquals("john.doe", created.getUsername());

        verify(trainerRepository).create(any());
    }

    @Test
    void testUpdateTrainer() {
        when(trainerRepository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));
        when(trainerRepository.update(any())).thenReturn(trainer);

        Trainer updated = trainerService.update(trainer, "jdoe", "password");
        assertNotNull(updated);
        assertEquals(trainer.getId(), updated.getId());
    }

    @Test
    void testFindById() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(trainerRepository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));

        Optional<Trainer> found = trainerService.findById(1L, "jdoe", "password");
        assertTrue(found.isPresent());
    }

    @Test
    void testFindByUsername() {
        when(trainerRepository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));

        Optional<Trainer> found = trainerService.findByUsername("jdoe", "jdoe", "password");
        assertTrue(found.isPresent());
        assertEquals("jdoe", found.get().getUsername());
    }

    @Test
    void testFindAll() {
        when(trainerRepository.findAll()).thenReturn(List.of(trainer));
        when(trainerRepository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));

        List<Trainer> trainers = trainerService.findAll("jdoe", "password");
        assertEquals(1, trainers.size());
    }

    @Test
    void testDeleteById() {
        when(trainerRepository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));
        doNothing().when(trainerRepository).deleteById(1L);

        trainerService.deleteById(1L, "jdoe", "password");

        verify(trainerRepository).deleteById(1L);
    }

    @Test
    void testDeleteByUsername() {
        when(trainerRepository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));
        doNothing().when(trainerRepository).deleteByUsername("jdoe");

        trainerService.deleteByUsername("jdoe", "jdoe", "password");

        verify(trainerRepository).deleteByUsername("jdoe");
    }

    @Test
    void testChangePassword() {
        when(trainerRepository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));
        when(trainerRepository.update(any())).thenReturn(trainer);

        trainerService.changePassword("jdoe", "password", "newpass");

        verify(trainerRepository).update(any());
    }

    @Test
    void testUpdateStatus() {
        when(trainerRepository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));
        when(trainerRepository.update(any())).thenReturn(trainer);

        trainerService.updateStatus("jdoe", "jdoe", "password");

        verify(trainerRepository).update(any());
    }

    @Test
    void testFindTrainingsList() {
        LocalDate fromDate = LocalDate.now().minusDays(7);
        LocalDate toDate = LocalDate.now();

        when(trainerRepository.findByUsername("jdoe")).thenReturn(Optional.of(trainer));
        when(trainingRepository.findByDto("traineeUser", fromDate, toDate, "jdoe"))
                .thenReturn(List.of(new Training()));

        List<Training> trainings = trainerService.findTrainingsList("traineeUser", fromDate, toDate, "jdoe", null, "jdoe", "password");

        assertEquals(1, trainings.size());
    }
}