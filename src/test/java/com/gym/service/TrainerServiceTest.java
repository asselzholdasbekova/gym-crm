package com.gym.service;

import com.gym.dao.TrainerDao;
import com.gym.model.Trainer;
import com.gym.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    @Mock
    private TrainerDao trainerDao;

    @InjectMocks
    private TrainerService trainerService;

    private Trainer trainer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainer = new Trainer("Алексей", "Петров", true, new TrainingType("Cardio"));
    }

    @Test
    void testCreateTrainer() {
        trainerService.create(trainer);
        verify(trainerDao, times(1)).save(trainer);
    }

    @Test
    void testFindTrainerById() {
        when(trainerDao.findById(1L)).thenReturn(Optional.of(trainer));
        Optional<Trainer> foundTrainer = trainerService.findById(1L);
        assertTrue(foundTrainer.isPresent());
        assertEquals(trainer.getFirstname(), foundTrainer.get().getFirstname());
    }

    @Test
    void testFindAllTrainers() {
        List<Trainer> trainers = Arrays.asList(trainer);
        when(trainerDao.findAll()).thenReturn(trainers);
        assertEquals(1, trainerService.findAll().size());
    }

    @Test
    void testDeleteTrainer() {
        trainerService.deleteById(1L);
        verify(trainerDao, times(1)).deleteById(1L);
    }
}
