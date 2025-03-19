CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    firstname VARCHAR NOT NULL,
    lastname VARCHAR NOT NULL,
    username VARCHAR UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    is_active BOOLEAN
);

CREATE TABLE trainees (
    id SERIAL PRIMARY KEY,
    date_of_birth DATE NOT NULL,
    address VARCHAR NOT NULL,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE training_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE trainers (
    id SERIAL PRIMARY KEY,
    training_type_id INT NOT NULL,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (training_type_id) REFERENCES training_types(id)
);

CREATE TABLE training (
    id SERIAL PRIMARY KEY,
    trainer_id INT NOT NULL,
    trainee_id INT NOT NULL,
    name VARCHAR NOT NULL,
    training_type_id INT NOT NULL,
    date DATE NOT NULL,
    duration INT NOT NULL,
    FOREIGN KEY (trainer_id) REFERENCES trainers(id) ON DELETE CASCADE,
    FOREIGN KEY (trainee_id) REFERENCES trainees(id) ON DELETE CASCADE,
    FOREIGN KEY (training_type_id) REFERENCES training_types(id)
);

CREATE TABLE trainer_trainee (
    trainee_id INT NOT NULL,
    trainer_id INT NOT NULL,
    PRIMARY KEY (trainee_id, trainer_id),
    FOREIGN KEY (trainee_id) REFERENCES trainees(id) ON DELETE CASCADE,
    FOREIGN KEY (trainer_id) REFERENCES trainers(id) ON DELETE CASCADE
);

INSERT INTO users (firstname, lastname, username, password, is_active) VALUES
('Андрей', 'Андреев', 'andrey123', 'pass1', true),
('Иван', 'Иванов', 'ivan99', 'pass2', true),
('Мария', 'Сидорова', 'maria21', 'pass3', true),
('Алексей', 'Петров', 'alex_trainer', 'pass4', true),
('Елена', 'Смирнова', 'elena_trainer', 'pass5', true);

INSERT INTO trainees (id, date_of_birth, address) VALUES
(1, '2000-01-01', 'Фурманова 2'),
(2, '1998-05-15', 'Абая 10'),
(3, '2001-07-21', 'Достык 15');

INSERT INTO training_types (name) VALUES
('Фитнес'),
('Йога'),
('Бокс');

INSERT INTO trainers (id, training_type_id) VALUES
(4, 1),
(5, 2);

INSERT INTO training (trainer_id, trainee_id, name, training_type_id, date, duration) VALUES
(4, 1, 'Утренняя тренировка', 1, '2024-03-01', 60),
(4, 2, 'Интенсивная тренировка', 1, '2024-03-03', 90),
(5, 3, 'Йога на свежем воздухе', 2, '2024-03-05', 75);

INSERT INTO trainer_trainee (trainee_id, trainer_id) VALUES
(1, 4),
(2, 4),
(3, 5);

UPDATE users
SET password = encode(digest(password::text, 'sha256'), 'base64');

