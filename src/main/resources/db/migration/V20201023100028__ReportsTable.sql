CREATE TABLE reports
(
    id       SERIAL primary key,
    name     TEXT not null,
    submission_date date not null,
    text TEXT not null,
    approved bool,
    submitted_by integer not null,

    constraint fk_users2rooms_users foreign key (submitted_by) references users (id)
);
