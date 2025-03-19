package com.gym.service.impl;

import com.gym.model.Training;
import com.gym.repository.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private Training training;

    @BeforeEach
    void setUp() {
        training = new Training();
        training.setId(1L);
        training.setName("Strength Training");
    }

    @Test
    void create_ShouldSaveAndReturnTraining() {
        when(trainingRepository.create(training)).thenReturn(training);

        Training createdTraining = trainingService.create(training);

        assertNotNull(createdTraining);
        assertEquals(training.getId(), createdTraining.getId());
        assertEquals(training.getName(), createdTraining.getName());
        verify(trainingRepository, times(1)).create(training);
    }
}