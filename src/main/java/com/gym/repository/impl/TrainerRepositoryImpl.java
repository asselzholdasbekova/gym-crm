package com.gym.repository.impl;

import com.gym.model.Trainer;
import com.gym.repository.TrainerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerRepositoryImpl implements TrainerRepository {
    private final SessionFactory sessionFactory;

    public TrainerRepositoryImpl(@Qualifier("getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Trainer create(Trainer trainer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(trainer);
            transaction.commit();
            return trainer;
        }
    }

    @Override
    public Trainer update(Trainer trainer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Trainer updatedTrainer = session.merge(trainer);
            transaction.commit();
            return updatedTrainer;
        }
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Trainer.class, id));
        }
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Trainer WHERE username = :username", Trainer.class)
                    .setParameter("username", username)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<Trainer> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Trainer", Trainer.class).getResultList();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            findById(id).ifPresent(session::remove);
            transaction.commit();
        }
    }

    @Override
    public void deleteByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createMutationQuery("DELETE FROM Trainer t WHERE t.username = :username")
                    .setParameter("username", username)
                    .executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery("SELECT COUNT(t) FROM Trainer t WHERE t.username = :username", Long.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return count > 0;
        }
    }
}
