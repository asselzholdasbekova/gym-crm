package com.gym.repository;

import com.gym.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository {
    Trainer create(Trainer trainer);
    Trainer update(Trainer trainer);
    Optional<Trainer> findById(Long id);
    Optional<Trainer> findByUsername(String username);
    List<Trainer> findAll();
    void deleteById(Long id);
    void deleteByUsername(String username);
    boolean existsByUsername(String username);
}
