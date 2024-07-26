CREATE DATABASE aplication_phonebook_javaspringboot;

use aplication_phonebook_javaspringboot;

CREATE TABLE users
(
    username         VARCHAR(100) NOT NULL,
    password         VARCHAR(100) NOT NULL,
    name             VARCHAR(100) NOT NULL,
    token            VARCHAR(100),
    token_expired_at BIGINT,
    PRIMARY KEY (username),
    UNIQUE (token)
) ENGINE InnoDB;

SELECT *
FROM users;

DESC users;

CREATE TABLE contacts
(
    id         VARCHAR(100) NOT NULL,
    username   VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100),
    phone      VARCHAR(100),
    email      VARCHAR(100),
    PRIMARY KEY (id),
    FOREIGN KEY fk_users_contacts (username) REFERENCES users (username)
) ENGINE InnoDB;

drop table contacts;
delete from contacts;
delete from users;

SELECT *
FROM contacts;

DESC contacts;