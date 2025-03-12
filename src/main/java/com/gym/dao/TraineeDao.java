package com.gym.dao;

import com.gym.model.Trainee;
import com.gym.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Repository
public class TraineeDao {
    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void save(Trainee trainee) {
        storage.getTrainees().put(trainee.getId(), trainee);
        log.info("Saved trainee: {}", trainee);
    }

    public Trainee update(Trainee trainee) {
        if (storage.getTrainees().containsKey(trainee.getId())) {
            storage.getTrainees().put(trainee.getId(), trainee);
            log.info("Updated trainee: {}", trainee);
            return trainee;
        } else {
            log.warn("Trainee with id {} not found for update", trainee.getId());
            throw new IllegalArgumentException("Trainee with id " + trainee.getId() + " not found");
        }
    }

    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(storage.getTrainees().get(id));
    }

    public Collection<Trainee> findAll() {
        return storage.getTrainees().values();
    }

    public void deleteById(Long id) {
        if (storage.getTrainees().remove(id) != null) {
            log.info("Deleted trainee with id: {}", id);
        } else {
            log.warn("Trainee with id {} not found for deletion", id);
        }
    }

    public boolean existsByUsername(String username) {
        return storage.getTrainees().values().stream()
                .anyMatch(trainee -> trainee.getUsername().equals(username));
    }
}
