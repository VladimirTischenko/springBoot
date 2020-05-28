CREATE SEQUENCE hibernate_sequence START 1 INCREMENT 1;

CREATE TABLE messages (
  id       INT4         NOT NULL,
  filename VARCHAR(255),
  text     VARCHAR(2048)NOT NULL,
  user_id  INT4,
  PRIMARY KEY (id)
);

CREATE TABLE roles (
  user_id INT4 NOT NULL,
  roles   VARCHAR(255)
);

CREATE TABLE users (
  id              INT4          NOT NULL,
  activation_code VARCHAR(255),
  email           VARCHAR(255)  NOT NULL,
  enabled         BOOLEAN       NOT NULL,
  name            VARCHAR(255)  NOT NULL,
  password        VARCHAR(255)  NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS users
  ADD CONSTRAINT email_unique UNIQUE (email);

ALTER TABLE IF EXISTS messages
  ADD CONSTRAINT message_user_fk FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS roles
  ADD CONSTRAINT role_user_fk FOREIGN KEY (user_id) REFERENCES users;