package com.gym.repository.impl;

import com.gym.model.Training;
import com.gym.repository.TrainingRepository;
import org.hibernate.SessionFactory;
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
        sessionFactory.getCurrentSession().persist(training);
        return training;
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

        var query = sessionFactory.getCurrentSession().createQuery(hql, Training.class);

        query.setParameter("trainerUsername", trainerUsername);
        query.setParameter("traineeUsername", traineeUsername);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        return query.getResultList();
    }

}