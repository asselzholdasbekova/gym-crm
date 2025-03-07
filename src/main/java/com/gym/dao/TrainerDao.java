package com.gym.dao;

import com.gym.model.Trainer;
import com.gym.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Repository
public class TrainerDao {
    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void save(Trainer trainer) {
        storage.getTrainers().put(trainer.getId(), trainer);
        log.info("Saved trainer: {}", trainer);
    }

    public Trainer update(Trainer trainer) {
        if (storage.getTrainers().containsKey(trainer.getId())) {
            storage.getTrainers().put(trainer.getId(), trainer);
            log.info("Updated trainer: {}", trainer);
            return trainer;
        } else {
            log.warn("Trainer with id {} not found for update", trainer.getId());
            return null;
        }
    }

    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(storage.getTrainers().get(id));
    }

    public Collection<Trainer> findAll() {
        return storage.getTrainers().values();
    }

    public void deleteById(Long id) {
        if (storage.getTrainers().remove(id) != null) {
            log.info("Deleted trainer with id: {}", id);
        } else {
            log.warn("Trainer with id {} not found for deletion", id);
        }
    }

    public boolean existsByName(String firstName, String lastName) {
        return storage.getTrainers().values().stream()
                .anyMatch(trainer -> trainer.getFirstname().equals(firstName) && trainer.getLastname().equals(lastName));
    }
}
