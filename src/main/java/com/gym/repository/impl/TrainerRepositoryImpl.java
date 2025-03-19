package com.gym.repository.impl;

import com.gym.model.Trainer;
import com.gym.repository.TrainerRepository;
import org.hibernate.SessionFactory;
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
        sessionFactory.getCurrentSession().persist(trainer);
        return trainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        return sessionFactory.getCurrentSession().merge(trainer);
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(Trainer.class, id));
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Trainer WHERE username = :username", Trainer.class)
                .setParameter("username", username)
                .uniqueResultOptional();
    }

    @Override
    public List<Trainer> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Trainer", Trainer.class)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(trainer -> sessionFactory.getCurrentSession().remove(trainer));
    }

    @Override
    public void deleteByUsername(String username) {
        sessionFactory.getCurrentSession().createMutationQuery("DELETE FROM Trainer t WHERE t.username = :username")
                .setParameter("username", username)
                .executeUpdate();
    }

    @Override
    public boolean existsByUsername(String username) {
        Long count = sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(t) FROM Trainer t WHERE t.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }
}
