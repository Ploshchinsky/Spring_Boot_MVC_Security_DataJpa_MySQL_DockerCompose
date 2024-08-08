CREATE TABLE users
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(80) NOT NULL,
    email VARCHAR(50) UNIQUE,
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_visit TIMESTAMP
);

CREATE TABLE todos
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    is_complete BOOLEAN,
    creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    complete_time TIMESTAMP,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE roles
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_MODERATOR');

insert into users (username, password, email, registration_date, last_visit)
values ('admin', '$2a$12$FKXNBq0YJpS2aAVLCf9acuV6IYOjxhw2nItmo7wON2CyJ6EGmcAqe', 'admin@yahoo,com', CURRENT_TIMESTAMP, null);

insert into users_roles (user_id, role_id)
values (1, 2);