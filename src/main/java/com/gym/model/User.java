package com.gym.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "username", unique = true, nullable = false)
    @NaturalId
    private String username;

    @Column(name = "password", nullable = false, length = 10)
    private String password;

    @Column(name = "is_active")
    private boolean isActive;
}

