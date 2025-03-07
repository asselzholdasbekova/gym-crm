package com.gym.service;

import com.gym.dao.TraineeDao;
import com.gym.model.Trainee;
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

class TraineeServiceTest {

    @Mock
    private TraineeDao traineeDao;

    @InjectMocks
    private TraineeService traineeService;

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainee = new Trainee("Иван", "Иванов", true, LocalDate.parse("2000-01-01"), "Фурманова 2");
    }

    @Test
    void testCreateTrainee() {
        traineeService.create(trainee);
        verify(traineeDao, times(1)).save(trainee);
    }

    @Test
    void testFindTraineeById() {
        when(traineeDao.findById(1L)).thenReturn(Optional.of(trainee));
        Optional<Trainee> foundTrainee = traineeService.findById(1L);
        assertTrue(foundTrainee.isPresent());
        assertEquals(trainee.getFirstname(), foundTrainee.get().getFirstname());
    }

    @Test
    void testFindAllTrainees() {
        List<Trainee> trainees = Arrays.asList(trainee);
        when(traineeDao.findAll()).thenReturn(trainees);
        assertEquals(1, traineeService.findAll().size());
    }

    @Test
    void testDeleteTrainee() {
        traineeService.deleteById(1L);
        verify(traineeDao, times(1)).deleteById(1L);
    }
}
