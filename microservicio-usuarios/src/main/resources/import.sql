INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('ghernandez', '12345', 1, 'Giovanni', 'Hernandez', 'ghernandez@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('usuario1', '123456', 1, 'user', 'test', 'usertest@gmail.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);