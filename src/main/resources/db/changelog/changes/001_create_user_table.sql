-- liquibase formatted sql

-- changeset vishal:001
CREATE TABLE test (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL
);
