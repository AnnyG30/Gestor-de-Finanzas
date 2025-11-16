CREATE DATABASE gestor_finanzas;
USE gestor_finanzas;

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT,
    nombre VARCHAR(100),
    correo VARCHAR(255),
    PRIMARY KEY (id_usuario)
);


-- Crear UN usuario que servirá para TODOS los gastos e ingresos
INSERT INTO usuarios (id_usuario, nombre, correo) 
VALUES (1, 'Maria', 'maria@gestor.com');

-- Verificar
SELECT * FROM usuarios;

select * from ingresos;

CREATE TABLE ingresos (
    id_ingreso INT AUTO_INCREMENT,
    id_usuario INT,
    fecha_ingreso DATETIME,
    categoria VARCHAR(255),
    monto DECIMAL(10,2),
    descripcion VARCHAR(255),
    PRIMARY KEY (id_ingreso),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE gastos (
    id_gasto INT AUTO_INCREMENT,
    id_usuario INT,
    fecha_gasto DATETIME,
    categoria VARCHAR(255),
    monto DECIMAL(10,2),
    descripcion VARCHAR(255),
    PRIMARY KEY (id_gasto),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE ahorro (
    id_ahorro INT AUTO_INCREMENT,
    id_usuario INT,
    monto DECIMAL(10,2),
    fecha_ahorro DATETIME,
    PRIMARY KEY (id_ahorro),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);
