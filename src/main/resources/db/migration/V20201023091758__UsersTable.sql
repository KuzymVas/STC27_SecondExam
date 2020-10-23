CREATE TABLE users
(
    id       SERIAL primary key,
    name     TEXT unique not null ,
    password TEXT not null
);

INSERT INTO users (name, password)
VALUES ('user', '$2a$10$NYsVpGQuIajs6L5XyvWQX.ejBSlJtpqFR.hjUxE/OoaEwmPVTgUYq'),
       ('abc', '$2a$10$DdDukxpgsBgl8st9TcfeC.Vmn3NLMGojt6MZ2J.RUcv1zH7XbO7S.');