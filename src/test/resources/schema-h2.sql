

CREATE TABLE IF NOT EXISTS socio (
    id INT(10) PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(250) NOT NULL,
    first_name VARCHAR(250) NULL,
    last_name VARCHAR(250) NULL,
    email VARCHAR(250) NOT NULL,
    register_date DATETIME NOT NULL,
    active TINYINT(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS socio_associated_socio (
   associated_socio_id INT(10) NULL,
   socio_id INT(10) NULL,
   FOREIGN KEY (associated_socio_id) REFERENCES socio(id),
   FOREIGN KEY (socio_id) REFERENCES socio(id)
);
