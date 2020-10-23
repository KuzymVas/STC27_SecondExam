CREATE TABLE reports
(
    id       SERIAL primary key,
    name     TEXT not null,
    submission_date date not null,
    text TEXT not null,
    approved bool not null,
    submitted_by integer not null,

    constraint fk_users2rooms_users foreign key (submitted_by) references users (id)
);

INSERT INTO reports (name, submission_date, text, approved, submitted_by)
VALUES ('First exam grades', '20200618 10:34:09 AM', 'Grades are posted on moodle as usual', false, 1),
       ('New REST-service', '20190718 10:34:21 AM', 'REST-service is up and running', true, 2);
