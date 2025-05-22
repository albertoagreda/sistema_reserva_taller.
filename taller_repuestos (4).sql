-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-05-2025 a las 12:38:59
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `taller_repuestos`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `RegistrarCita` (IN `p_cliente_id` INT, IN `p_coche_id` INT, IN `p_fecha` DATE)   BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al registrar la cita';
    END;

    START TRANSACTION;
    -- Validar fecha ocupada
    IF EXISTS (SELECT 1 FROM citas WHERE fecha = p_fecha) THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Fecha ocupada';
    END IF;
    -- Validar existencia de cliente y coche
    IF NOT EXISTS (SELECT 1 FROM clientes WHERE id = p_cliente_id) THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cliente no existe';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM coches WHERE COCHE_ID = p_coche_id) THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Coche no existe';
    END IF;
    -- Validar año de la cita
    IF YEAR(p_fecha) > 2025 THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Fecha futura no permitida';
    END IF;
    -- Insertar cita
    INSERT INTO citas (cliente_id, coche_id, fecha)
    VALUES (p_cliente_id, p_coche_id, p_fecha);
    COMMIT;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `RegistrarMensajeSoporte` (IN `p_cliente_id` INT, IN `p_mensaje` TEXT)   BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al registrar el mensaje';
    END;

    START TRANSACTION;
    -- Validar mensaje no vacío
    IF p_mensaje IS NULL OR p_mensaje = '' THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Mensaje vacío';
    END IF;
    -- Validar cliente
    IF NOT EXISTS (SELECT 1 FROM clientes WHERE id = p_cliente_id) THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cliente no existe';
    END IF;
    -- Insertar mensaje
    INSERT INTO soporte (cliente_id, mensaje)
    VALUES (p_cliente_id, p_mensaje);
    COMMIT;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `citas`
--

CREATE TABLE `citas` (
  `id` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `coche_id` int(11) NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `citas`
--

INSERT INTO `citas` (`id`, `cliente_id`, `coche_id`, `fecha`) VALUES
(2, 2, 2, '2025-05-24'),
(3, 3, 3, '2025-06-01');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `correo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `nombre`, `apellidos`, `correo`) VALUES
(1, 'Juan', 'Pérez García', 'juan.perez@gmail.com'),
(2, 'María', 'López Fernández', 'maria.lopez@gmail.com'),
(3, 'Carlos', 'Gómez Ruiz', 'carlos.gomez@gmail.com'),
(4, 'Ana', 'Martínez Díaz', 'ana.martinez@gmail.com'),
(5, 'Luis', 'Rodríguez Sánchez', 'luis.rodriguez@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `coches`
--

CREATE TABLE `coches` (
  `COCHE_ID` int(11) NOT NULL,
  `MODELO` varchar(50) NOT NULL,
  `MARCA` varchar(50) NOT NULL,
  `ANIO` int(11) NOT NULL,
  `KILOMETRAJE` int(11) NOT NULL,
  `detalles` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`detalles`))
) ;

--
-- Volcado de datos para la tabla `coches`
--

INSERT INTO `coches` (`COCHE_ID`, `MODELO`, `MARCA`, `ANIO`, `KILOMETRAJE`, `detalles`) VALUES
(2, 'Corolla', 'Toyota', 2020, 25000, '{\"color\": \"Blanco\", \"combustible\": \"Híbrido\"}'),
(3, 'Golf', 'Volkswagen', 2017, 60000, '{\"color\": \"Negro\", \"combustible\": \"Diésel\"}'),
(4, 'Model 3', 'Tesla', 2021, 12000, '{\"color\": \"Rojo\", \"combustible\": \"Eléctrico\"}'),
(5, 'Ibiza', 'SEAT', 2019, 22000, '{\"color\": \"Gris\", \"combustible\": \"Gasolina\"}');

--
-- Disparadores `coches`
--
DELIMITER $$
CREATE TRIGGER `after_coche_delete` AFTER DELETE ON `coches` FOR EACH ROW BEGIN
    INSERT INTO log_coches (coche_id, accion, fecha)
    VALUES (OLD.COCHE_ID, 'Eliminado', NOW());
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `log_coches`
--

CREATE TABLE `log_coches` (
  `id` int(11) NOT NULL,
  `coche_id` int(11) NOT NULL,
  `accion` varchar(50) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `log_coches`
--

INSERT INTO `log_coches` (`id`, `coche_id`, `accion`, `fecha`) VALUES
(1, 1, 'Eliminado', '2025-05-19 10:56:56');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `soporte`
--

CREATE TABLE `soporte` (
  `id` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `mensaje` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `soporte`
--

INSERT INTO `soporte` (`id`, `cliente_id`, `mensaje`) VALUES
(1, 1, 'El coche no arranca después de la última reparación.'),
(2, 2, 'Necesito una cita urgente para revisar frenos.'),
(3, 3, 'Consulta sobre garantía de piezas.');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `citas`
--
ALTER TABLE `citas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cliente_id` (`cliente_id`),
  ADD KEY `coche_id` (`coche_id`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_correo` (`correo`);

--
-- Indices de la tabla `coches`
--
ALTER TABLE `coches`
  ADD PRIMARY KEY (`COCHE_ID`);

--
-- Indices de la tabla `log_coches`
--
ALTER TABLE `log_coches`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `soporte`
--
ALTER TABLE `soporte`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cliente_id` (`cliente_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `citas`
--
ALTER TABLE `citas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `log_coches`
--
ALTER TABLE `log_coches`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `soporte`
--
ALTER TABLE `soporte`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `citas`
--
ALTER TABLE `citas`
  ADD CONSTRAINT `citas_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `citas_ibfk_2` FOREIGN KEY (`coche_id`) REFERENCES `coches` (`COCHE_ID`) ON DELETE CASCADE;

--
-- Filtros para la tabla `soporte`
--
ALTER TABLE `soporte`
  ADD CONSTRAINT `soporte_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
