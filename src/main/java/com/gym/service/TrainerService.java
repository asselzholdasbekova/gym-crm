package com.gym.service;

import com.gym.dao.TrainerDao;
import com.gym.model.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class TrainerService {
    private TrainerDao trainerDao;

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    public void create(Trainer trainer) {
        trainerDao.save(trainer);
        log.info("Trainer created: {}", trainer);
    }

    public Trainer update(Trainer trainer) {
        log.info("Updating trainer: {}", trainer);
        return trainerDao.update(trainer);
    }

    public Optional<Trainer> findById(Long id) {
        return trainerDao.findById(id);
    }

    public Collection<Trainer> findAll() {
        return trainerDao.findAll();
    }

    public void deleteById(Long id) {
        trainerDao.deleteById(id);
        log.info("Trainer deleted with id: {}", id);
    }
}
