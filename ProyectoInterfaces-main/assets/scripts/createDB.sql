SET SESSION FOREIGN_KEY_CHECKS=0;
CREATE DATABASE IF NOT EXISTS taller;
USE taller;
/* Drop Tables */

DROP TABLE IF EXISTS Ciclomotor;
DROP TABLE IF EXISTS Propuesta;
DROP TABLE IF EXISTS Coche;
DROP TABLE IF EXISTS Motocicleta;
DROP TABLE IF EXISTS Reparacion;
DROP TABLE IF EXISTS Vehiculo;
DROP TABLE IF EXISTS Cliente;
DROP TABLE IF EXISTS Jefe;
DROP TABLE IF EXISTS Mecanico;
DROP TABLE IF EXISTS Ventas;
DROP TABLE IF EXISTS Empleado;
DROP TABLE IF EXISTS Concesionario;
DROP TABLE IF EXISTS Especialidad;
DROP TABLE IF EXISTS Persona;




/* Create Tables */

CREATE TABLE Ciclomotor
(
	mat_ciclo varchar(7) NOT NULL UNIQUE,
	num_bastidor varchar(10) NOT NULL,
	PRIMARY KEY (mat_ciclo)
);


CREATE TABLE Cliente
(
	cod_cliente int NOT NULL AUTO_INCREMENT,
	dni varchar(9) NOT NULL,
	PRIMARY KEY (cod_cliente)
);


CREATE TABLE Coche
(
	mat_coche varchar(7) NOT NULL UNIQUE,
	num_bastidor varchar(10) NOT NULL,
	PRIMARY KEY (mat_coche)
);


CREATE TABLE Concesionario
(
	cod_conce int NOT NULL AUTO_INCREMENT,
	nombre varchar(15),
	PRIMARY KEY (cod_conce)
);


CREATE TABLE Empleado
(
	cod_empleado int NOT NULL AUTO_INCREMENT,
	dni varchar(9) NOT NULL,
	cod_conce int NOT NULL,
    usuario varchar(15) UNIQUE,
    contrasena varchar(150),
	PRIMARY KEY (cod_empleado)
);


CREATE TABLE Especialidad
(
	cod_especialidad int NOT NULL AUTO_INCREMENT,
	nombre_esp varchar(15),
	PRIMARY KEY (cod_especialidad)
);


CREATE TABLE Jefe
(
	cod_jefe int NOT NULL AUTO_INCREMENT,
	cod_empleado int NOT NULL,
	PRIMARY KEY (cod_jefe)
);


CREATE TABLE Mecanico
(
	cod_mecanico int NOT NULL AUTO_INCREMENT,
	cod_mecanico_jefe int NOT NULL,
	cod_especialidad int NOT NULL,
	cod_empleado int NOT NULL,
	PRIMARY KEY (cod_mecanico)
);


CREATE TABLE Motocicleta
(
	mat_moto varchar(7) NOT NULL UNIQUE,
	num_bastidor varchar(10) NOT NULL,
	PRIMARY KEY (mat_moto)
);


CREATE TABLE Persona
(
	dni varchar(9) NOT NULL UNIQUE,
	nombre varchar(15),
	apellidos varchar(25),
	telefono varchar(9),
	PRIMARY KEY (dni)
);


CREATE TABLE Propuesta
(
	cod_propuesta int NOT NULL AUTO_INCREMENT,
	cod_cliente int NOT NULL,
	cod_ventas int NOT NULL,
	num_bastidor varchar(10) NOT NULL,
    fecha_validez date,
    precio varchar(6),
	PRIMARY KEY (cod_propuesta)
);


CREATE TABLE Reparacion
(
	cod_reparacion int NOT NULL AUTO_INCREMENT,
	cod_mecanico int NOT NULL,
	num_bastidor varchar(10) NOT NULL,
    fecha_entrada date,
    fecha_salida date,
	piezas varchar(500),
    precio varchar(6),
	PRIMARY KEY (cod_reparacion)
);


CREATE TABLE Vehiculo
(
	num_bastidor varchar(10) NOT NULL UNIQUE,
	cod_ventas int,
	cod_cliente int,
	cod_conce int,
    tipo_vehiculo varchar(15),
	marca varchar(25),
	modelo varchar(35),
	combustible varchar(15),
	precio varchar(6),
    anno varchar(4),
    kilometros varchar(6),
	PRIMARY KEY (num_bastidor)
);


CREATE TABLE Ventas
(
	cod_ventas int NOT NULL AUTO_INCREMENT,
	cod_empleado int NOT NULL,
	PRIMARY KEY (cod_ventas)
);



/* Create Foreign Keys */

ALTER TABLE Propuesta
	ADD FOREIGN KEY (cod_cliente) REFERENCES Cliente (cod_cliente),
    ADD FOREIGN KEY (num_bastidor) REFERENCES Vehiculo (num_bastidor),
    ADD FOREIGN KEY (cod_ventas) REFERENCES Ventas (cod_ventas)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Vehiculo
	ADD FOREIGN KEY (cod_cliente) REFERENCES Cliente (cod_cliente),
    ADD FOREIGN KEY (cod_conce) REFERENCES Concesionario (cod_conce),
    ADD FOREIGN KEY (cod_ventas) REFERENCES Ventas (cod_ventas)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Empleado
	ADD FOREIGN KEY (cod_conce) REFERENCES Concesionario (cod_conce),
    ADD FOREIGN KEY (dni) REFERENCES Persona (dni)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Jefe
	ADD FOREIGN KEY (cod_empleado) REFERENCES Empleado (cod_empleado)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Mecanico
	ADD FOREIGN KEY (cod_empleado) REFERENCES Empleado (cod_empleado),
    ADD FOREIGN KEY (cod_especialidad) REFERENCES Especialidad (cod_especialidad),
    ADD FOREIGN KEY (cod_mecanico_jefe) REFERENCES Mecanico (cod_mecanico)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Ventas
	ADD FOREIGN KEY (cod_empleado) REFERENCES Empleado (cod_empleado)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Reparacion
	ADD FOREIGN KEY (cod_mecanico) REFERENCES Mecanico (cod_mecanico),
    ADD FOREIGN KEY (num_bastidor) REFERENCES Vehiculo (num_bastidor)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Cliente
	ADD FOREIGN KEY (dni)
	REFERENCES Persona (dni)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Ciclomotor
	ADD FOREIGN KEY (num_bastidor) REFERENCES Vehiculo (num_bastidor)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Coche
	ADD FOREIGN KEY (num_bastidor) REFERENCES Vehiculo (num_bastidor)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Motocicleta
	ADD FOREIGN KEY (num_bastidor) REFERENCES Vehiculo (num_bastidor)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;