package com.gym.repository.impl;

import com.gym.model.Training;
import com.gym.repository.TrainingRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
class TrainingRepositoryImpl implements TrainingRepository {
    private final SessionFactory sessionFactory;

    public TrainingRepositoryImpl(@Qualifier("getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Training create(Training training) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(training);
            transaction.commit();
            return training;
        }
    }

    @Override
    public List<Training> findByDto(String traineeUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername) {
        String hql = """
            SELECT t FROM Training t 
            JOIN t.trainer tr 
            JOIN t.trainee tn
            WHERE (:trainerUsername IS NULL OR tr.username = :trainerUsername)
            AND (:traineeUsername IS NULL OR tn.username = :traineeUsername)
            AND (:fromDate IS NULL OR t.date >= :fromDate)
            AND (:toDate IS NULL OR t.date <= :toDate)
        """;

        try (Session session = sessionFactory.openSession()) {
            var query = session.createQuery(hql, Training.class);

            if (trainerUsername != null) {
                query.setParameter("trainerUsername", trainerUsername);
            }
            if (traineeUsername != null) {
                query.setParameter("traineeUsername", traineeUsername);
            }
            if (fromDate != null) {
                query.setParameter("fromDate", fromDate);
            }
            if (toDate != null) {
                query.setParameter("toDate", toDate);
            }

            return query.getResultList();
        }
    }
}
