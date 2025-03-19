package com.gym.repository.impl;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.repository.TraineeRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
class TraineeRepositoryImpl implements TraineeRepository {
    private final SessionFactory sessionFactory;

    public TraineeRepositoryImpl(@Qualifier("getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Trainee create(Trainee trainee) {
        sessionFactory.getCurrentSession().persist(trainee);
        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        return sessionFactory.getCurrentSession().merge(trainee);
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Trainee.class, id));
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Trainee WHERE username = :username", Trainee.class)
                .setParameter("username", username)
                .uniqueResultOptional();
    }

    @Override
    public List<Trainee> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Trainee", Trainee.class)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(trainee -> sessionFactory.getCurrentSession().remove(trainee));
    }

    @Override
    public void deleteByUsername(String username) {
        sessionFactory.getCurrentSession()
                .createMutationQuery("DELETE FROM Trainee WHERE username = :username")
                .setParameter("username", username)
                .executeUpdate();
    }

    @Override
    public List<Trainer> findNotAssignedTrainers(String traineeUserName) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT t FROM Trainer t WHERE t NOT IN (SELECT tr FROM Trainee tr JOIN tr.trainers trainers WHERE tr.username = :traineeUserName)", Trainer.class)
                .setParameter("traineeUserName", traineeUserName)
                .getResultList();
    }

    @Override
    public boolean existsByUsername(String username) {
        Long count = sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(t) FROM Trainee t WHERE t.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }
}
