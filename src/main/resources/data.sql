INSERT INTO socio (username, password, first_name, last_name, email, register_date, active)
VALUES ('js', 'secret', 'Johann Sebastian', 'Bach', 'jsbach@gmail.com', '2019-08-12 09:50:45', true);
INSERT INTO socio (username, password, first_name, last_name, email, register_date, active)
VALUES ('rw', 'secret', 'Richard', 'Wagner', 'rwagner@gmail.com', '2019-08-12 09:50:45', true);
INSERT INTO socio (username, password, first_name, last_name, email, register_date, active)
VALUES ('bb', 'secret', 'Bela', 'Bartok', 'bbartok@gmail.com', '2019-08-12 09:50:45', true);

INSERT INTO socio_associated_socio (socio_id, associated_socio_id) VALUES ('1','2');
INSERT INTO socio_associated_socio (socio_id, associated_socio_id) VALUES ('1','3');