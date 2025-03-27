package com.gym.repository.impl;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.repository.TraineeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

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
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(trainee);
            transaction.commit();
            return trainee;
        }
    }

    @Override
    public Trainee update(Trainee trainee) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Trainee updatedTrainee = session.merge(trainee);
            transaction.commit();
            return updatedTrainee;
        }
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Trainee.class, id));
        }
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Trainee WHERE username = :username", Trainee.class)
                    .setParameter("username", username)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<Trainee> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Trainee", Trainee.class).getResultList();
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
            session.createMutationQuery("DELETE FROM Trainee WHERE username = :username")
                    .setParameter("username", username)
                    .executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public List<Trainer> findNotAssignedTrainers(String traineeUserName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT t FROM Trainer t WHERE t NOT IN " +
                                    "(SELECT tr FROM Trainee tr JOIN tr.trainers trainers WHERE tr.username = :traineeUserName)",
                            Trainer.class)
                    .setParameter("traineeUserName", traineeUserName)
                    .getResultList();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery("SELECT COUNT(t) FROM Trainee t WHERE t.username = :username", Long.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return count > 0;
        }
    }
}
