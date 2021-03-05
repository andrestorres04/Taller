-- PERSONA
INSERT INTO taller.persona (dni, nombre, apellidos, telefono) VALUES 
('12345678A', 'Pepe', 'Morales Cifuentes', '000111222'),
('12345678B', 'Rocio', 'Sanchez Mel', '222111000'),
('12345678C', 'Josue', 'Martin Boletus', '333222111'),
('12345678D', 'Alberto', 'Leal Contador', '000000000'),
('87654321A', 'Luis', 'Rodriguez Roedor', '332210000'),
('87654321B', 'Marisa', 'De los Ángeles', '123456789'),
('87654321C', 'Takumi', 'Reparte Tofu', '123456781'),
('12345679X', 'Dominic', 'Toreto', '993456991'),
('00056964R', 'Morty', 'Repara Ciclos', '993456991'),
('95175345H', 'Sofía', ' Maracuya Amarilla', '093456990'),
('74185296M', 'Rigoberto', 'Vende Motos', '223456991'),
('55566644R', 'Joselito', 'Sans Serif', '113456991');

-- CONCESIONARIO
INSERT INTO taller.concesionario (cod_conce, nombre) VALUES 
(1, 'Todo Ruedas'),
(2, 'H&N Customs');

-- EMPLEADO
INSERT INTO taller.empleado (cod_empleado, dni, cod_conce, usuario, contrasena) VALUES 
(1, '12345678A', 1, 'ventas', MD5('usuario')),
(2, '12345678B', 1, 'mecanico', MD5('usuario')),
(3, '12345678C', 1, 'mecanicojefe', MD5('usuario')),
(4, '12345678D', 1, 'jefe', MD5('usuario')),
(5, '12345679X', 1, 'dominic', MD5('usuario')),
(6, '0005694R', 1, 'mecanicociclo', MD5('usuario')),
(7, '95175345H', 1, 'ventas2', MD5('usuario')),
(8, '74185296M', 1, 'ventas3', MD5('usuario'));

-- CLIENTE
INSERT INTO taller.cliente (cod_cliente, dni) VALUES 
(1, '87654321A'),
(2, '87654321B'),
(3, '87654321C'),
(4, '55566644R');

-- JEFE
INSERT INTO taller.jefe (cod_jefe, cod_empleado) VALUES 
(1, 4);

-- ESPECIALIDAD
INSERT INTO taller.especialidad (cod_especialidad, nombre_esp) VALUES 
(1, 'coche'),
(2, 'motocicleta'),
(3, 'ciclomotor');

-- MECANICO 
INSERT INTO taller.mecanico (cod_mecanico, cod_mecanico_jefe, cod_especialidad, cod_empleado) VALUES 
(1, 1, 1, 3),
(2, 1, 2, 2),
(3, 1, 1, 5),
(4, 1, 3, 6);

-- VENTAS
INSERT INTO taller.ventas (cod_ventas, cod_empleado) VALUES 
(1, 1),
(2, 7),
(3, 8);

-- VEHÍCULO
INSERT INTO taller.vehiculo (num_bastidor, cod_ventas, cod_cliente, cod_conce, tipo_vehiculo, marca, modelo, combustible, precio, anno, kilometros) VALUES 
('1234567890', 1, 2, 1, 'Coche', 'Subaru', 'WRX STI', 'Gasolina', 37000, '2017', '1540'),
('9876543210', 2, 3, 1, 'Coche', 'Nissan', '370Z Nismo', 'Gasolina', 35000, '2017', '3000'),
('4328457431', 1, 1, 1, 'Motocicleta', 'Honda', 'CBR 1000', 'Gasolina', 7900, '2010', '1100'),
('5486079685', 3, 1, 1, 'Ciclomotor', 'Piaggio', 'SH', 'Gasolina', 2900, '2012', '5988'),
('0006079000', 3, 3, 1, 'Ciclomotor', 'Piaggio', 'ZIP', 'Gasolina', 800, '2009', '6888'),
('9996079999', 1, 2, 1, 'Motocicleta', 'Yamaha', 'SR', 'Gasolina', 4900, '2014', '5566'), -- Vehículo ya vendido (algunos en reparación)
('0123456789', null, null, 1, 'Coche', 'Seat', 'Ibiza', 'Diesel', 6700, '2011', '120000'),
('1112223334', null, null, 1, 'Motocicleta', 'Suzuki', 'GSX-1300R Hayabusa', 'Gasolina', 35000, '2019', '3999'),
('4443332221', null, null, 1, 'Ciclomotor', 'Hawk', '1800W/40AH L1e-B', 'Híbrido', 1900, '2020', '11'),
('1111112223', null, null, 1, 'Coche', 'Toyota', 'Prius', 'Híbrido', 20000, '2019', '50300'),
('3334445556', null, null, 1, 'Motocicleta', 'Benelli', 'BN 302', 'Gasolina', 4000, '2004', '15440'), -- Vehículo a la venta, sin dueño ni cod ventas
('0000000001', null, 4, 1, 'Coche', 'BMW', '335d', 'Diesel', null, '2015', '143540'); -- Vehículo en reparaciones, sin precio ni cod ventas

-- REPARACION
INSERT INTO taller.reparacion (cod_reparacion, cod_mecanico, num_bastidor, fecha_entrada, fecha_salida, piezas, precio) VALUES
(1, 1, '1234567890', '2021-10-9', '2021-10-11', 'Turbo K04 híbrido, downpipe, pintura blue rally', 500),
(2, 2, '9996079999', '2021-10-9', '2021-10-29', '4 Bujías, silenciador don silencioso, piezas caras saca la pasta', 320),
(3, 4, '0006079000', '2021-10-9', '2021-10-28', 'Tirar a la basura esta sh*t', 50),
(4, 3, '0000000001', '2021-10-16', '2021-12-18', 'Pastillas de freno, pinzas nuevas, 4 neumáticos continental sport, adblue, hierro pa los buques', 1200);

-- COCHE
INSERT INTO taller.coche (mat_coche, num_bastidor) VALUES
('1122ABC', '1234567890'),
('3322DEF', '9876543210'),
('9988PNR', '0123456789'),
('7766TTT', '1111112223'),
('8844PLR', '0000000001');

-- MOTOCICLETA
INSERT INTO taller.motocicleta (mat_moto, num_bastidor) VALUES
('4455ABC', '4328457431'),
('1122POL', '9996079999'),
('5588XXX', '1112223334'),
('2323ERX', '3334445556');

-- CICLOMOTOR
INSERT INTO taller.ciclomotor (mat_ciclo, num_bastidor) VALUES
('4455DEF', '5486079685'),
('0099SHT', '0006079000'),
('0101IER', '4443332221');

-- PROPUESTA
INSERT INTO taller.propuesta (cod_propuesta, cod_cliente, cod_ventas, num_bastidor, fecha_validez, precio) VALUES
(1, 1, 1, '0123456789', '2021-10-11', 5000),
(2, 2, 1, '1112223334', '2021-12-17', 49999),
(3, 3, 1, '1111112223', '2021-11-19', 12312);




