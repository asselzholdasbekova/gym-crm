package com.gym.storage;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.model.TrainingType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Component
public class Storage {

    private final Map<Long, Trainee> trainees = new HashMap<>();
    private final Map<Long, Trainer> trainers = new HashMap<>();
    private final Map<Long, Training> trainings = new HashMap<>();

    public Map<Long, Trainee> getTrainees() {
        return trainees;
    }

    public Map<Long, Trainer> getTrainers() {
        return trainers;
    }

    public Map<Long, Training> getTrainings() {
        return trainings;
    }

    @PostConstruct
    public void initialize() {
        Properties prop = new Properties();
        try (InputStream input = Files.newInputStream(Paths.get("src/main/resources/application.properties"))) {
            prop.load(input);
            String storageFile = prop.getProperty("storage.file.path");

            try (BufferedReader reader = new BufferedReader(new FileReader(storageFile))) {
                log.info("{} file has been accessed", storageFile);
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");
                    switch (fields[0]) {
                        case "trainee" -> {
                            long id = Long.parseLong(fields[1]);
                            Trainee trainee = new Trainee(fields[2], fields[3], Boolean.parseBoolean(fields[4]),
                                    LocalDate.parse(fields[5]), fields[6]);
                            String username = fields[7];
                            String password = fields[8];
                            trainee.setUsername(username);
                            trainee.setPassword(password);
                            trainees.put(id, trainee);
                            log.info("Trainee added: {}", trainee);
                        }
                        case "trainer" -> {
                            long id = Long.parseLong(fields[1]);
                            Trainer trainer = new Trainer(fields[2], fields[3], Boolean.parseBoolean(fields[4]),
                                    new TrainingType(fields[5]));
                            String username = fields[6];
                            String password = fields[7];
                            trainer.setUsername(username);
                            trainer.setPassword(password);
                            trainers.put(id, trainer);
                            log.info("Trainer added: {}", trainer);
                        }
                        case "training" -> {
                            long id = Long.parseLong(fields[1]);
                            long traineeId = Long.parseLong(fields[2]);
                            long trainerId = Long.parseLong(fields[3]);

                            Trainee trainee = trainees.get(traineeId);
                            Trainer trainer = trainers.get(trainerId);

                            if (trainee == null) {
                                log.warn("Trainee with id {} not found", traineeId);
                                continue;
                            }

                            if (trainer == null) {
                                log.warn("Trainer with id {} not found", trainerId);
                                continue;
                            }

                            Training training = new Training(trainer, trainee, fields[4],
                                    new TrainingType(fields[5]),
                                    LocalDate.parse(fields[6]),
                                    Integer.parseInt(fields[7]));

                            trainings.put(id, training);
                            log.info("Training added: {}", training);
                        }
                        default -> log.warn("Unknown entry type: {}", fields[0]);
                    }
                }
            }
        } catch (IOException e) {
            log.error("IOException has been thrown while working with file {}", e.getMessage(), e);
            throw new RuntimeException("Error reading data file", e);
        }
    }
}
