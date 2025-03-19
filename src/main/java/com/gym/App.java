package com.gym;

import com.gym.config.AppConfig;
import com.gym.facade.GymFacade;
import com.gym.model.Trainee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        GymFacade facade = context.getBean("gymFacade", GymFacade.class);
        List<Trainee> trainees = facade.findAllTrainees("andrey123", "pass1");

        trainees.forEach(System.out::println);
    }
}
