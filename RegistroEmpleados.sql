
CREATE DATABASE empleados;


CREATE TABLE empleado (
    id                 SERIAL PRIMARY KEY,
    nombres            VARCHAR(100)  NOT NULL,
    apellidos          VARCHAR(100)  NOT NULL,
    cedula             VARCHAR(20)   NOT NULL UNIQUE,
    correo             VARCHAR(100)  UNIQUE,
    telefono           VARCHAR(15),
    cargo              VARCHAR(50)   NOT NULL,
    departamento       VARCHAR(50)   NOT NULL,
    salario            NUMERIC(10,2) NOT NULL,
    fecha_contratacion DATE          NOT NULL,
    estado             VARCHAR(10)   NOT NULL
);



INSERT INTO empleado (nombres, apellidos, cedula, correo, telefono, cargo, departamento, salario, fecha_contratacion, estado) VALUES
('Ana María', 'López Pérez',    '001-150390-0001A', 'ana.lopez@empresa.com',    '8888-1111', 'Contadora',     'Finanzas',          25000.00, '2022-03-15', 'Activo'),
('Carlos',    'Martínez Ruiz',  '001-220588-0002B', 'carlos.martinez@empresa.com', '8888-2222', 'Desarrollador', 'Tecnología',        32000.00, '2021-07-01', 'Activo'),
('María',     'González Díaz',  '001-100795-0003C', 'maria.gonzalez@empresa.com',  '8888-3333', 'Analista',      'Recursos Humanos',  22000.00, '2023-01-10', 'Activo'),
('José',      'Hernández Mora', '001-050285-0004D', 'jose.hernandez@empresa.com',  '8888-4444', 'Soporte',       'Tecnología',        18000.00, '2020-11-20', 'Inactivo'),
('Laura',     'Ramírez Soto',   '001-301292-0005E', 'laura.ramirez@empresa.com',   '8888-5555', 'Asistente',     'Finanzas',          15000.00, '2024-05-05', 'Activo');



SELECT *
FROM empleado;

-- 1. Mostrar todos los empleados
SELECT * FROM empleado;

-- 2. Solo nombres, apellidos y cargo
SELECT nombres, apellidos, cargo FROM empleado;

-- 3. Empleados de un departamento determinado
SELECT * FROM empleado WHERE departamento = 'Tecnología';

-- 4. Empleados con salario mayor a un valor
SELECT * FROM empleado WHERE salario > 20000;

-- 5. Ordenar por salario de mayor a menor
SELECT * FROM empleado ORDER BY salario DESC;

-- 6. Contar el total de empleados
SELECT COUNT(*) AS total_empleados FROM empleado;

-- 7. Salario promedio
SELECT AVG(salario) AS salario_promedio FROM empleado;

-- 8. Suma de todos los salarios
SELECT SUM(salario) AS total_salarios FROM empleado;

-- 9. Solo empleados activos
SELECT * FROM empleado WHERE estado = 'Activo';

-- 10. Cantidad de empleados por departamento
SELECT departamento, COUNT(*) AS cantidad
FROM empleado
GROUP BY departamento;






















