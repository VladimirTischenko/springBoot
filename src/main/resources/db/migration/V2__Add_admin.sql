INSERT INTO users (id, email, enabled, name, password)
VALUES (1, 'vladimirtischenko82@gmail.com', TRUE, 'admin', 'admin');

INSERT INTO roles (user_id, roles)
VALUES (1, 'USER'),
  (1, 'ADMIN')
