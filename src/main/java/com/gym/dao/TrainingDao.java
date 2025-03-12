package com.gym.dao;

import com.gym.model.Training;
import com.gym.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Repository
public class TrainingDao {
    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void save(Training training) {
        storage.getTrainings().put(training.getId(), training);
        log.info("Saved training: {}", training);
    }

    public Training update(Training training) {
        if (storage.getTrainings().containsKey(training.getId())) {
            storage.getTrainings().put(training.getId(), training);
            log.info("Updated training: {}", training);
            return training;
        } else {
            log.warn("Training with id {} not found for update", training.getId());
            throw new IllegalArgumentException("Training with id " + training.getId() + " not found");
        }
    }

    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(storage.getTrainings().get(id));
    }

    public Collection<Training> findAll() {
        return storage.getTrainings().values();
    }

    public void deleteById(Long id) {
        if (storage.getTrainings().remove(id) != null) {
            log.info("Deleted training with id: {}", id);
        } else {
            log.warn("Training with id {} not found for deletion", id);
        }
    }

    public boolean existsByName(String name) {
        return storage.getTrainings().values().stream()
                .anyMatch(training -> training.getName().equalsIgnoreCase(name));
    }
}
