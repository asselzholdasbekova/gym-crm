package com.gym;

import com.gym.config.AppConfig;
import com.gym.model.Trainee;
import com.gym.service.TraineeService;
import com.gym.service.TrainerService;
import com.gym.service.TrainingService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        TraineeService traineeService = context.getBean(TraineeService.class);
        TrainerService trainerService = context.getBean(TrainerService.class);
        TrainingService trainingService = context.getBean(TrainingService.class);

        System.out.println("Список тренирующихся: " + traineeService.findAll());
        System.out.println("Список тренеров: " + trainerService.findAll());
        System.out.println("Список тренировок: " + trainingService.findAll());

        Trainee trainee = new Trainee();
        trainee.setFirstname("Андрей");
        trainee.setLastname("Андреев");
        trainee.setActive(true);
        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainee.setAddress("Фурманова 2");
        traineeService.create(trainee);

        System.out.println("Тренирующийся с ID 5: " + traineeService.findById(5L));

        Trainee trainee2 = new Trainee("Иван", "Иванов",true,  LocalDate.parse("1998-05-15"), "Абая 10");
        traineeService.create(trainee2);

        Trainee trainee3 = new Trainee("Мария", "Сидорова",true, LocalDate.parse("2001-07-21"), "Достык 15");
        traineeService.create(trainee3);

        System.out.println("\nВсе тренирующиеся: " + traineeService.findAll());

        Trainee trainee1 = traineeService.findById(5L).orElse(null);
        if (trainee1 != null) {
            trainee1.setAddress("Фурманова 222");
            traineeService.update(trainee1);
            System.out.println("Обновленный адрес: " + traineeService.findById(5L).get().getAddress());
        }

        traineeService.deleteById(5L);
        System.out.println("Тренирующийся с ID 5 после удаления: " + traineeService.findById(5L));
    }
}
