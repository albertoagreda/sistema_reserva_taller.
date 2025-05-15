-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-05-2025 a las 08:55:25
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `coches`
--

CREATE TABLE `coches` (
  `COCHE_ID` int(11) NOT NULL,
  `MODELO` varchar(50) DEFAULT NULL,
  `MARCA` varchar(50) DEFAULT NULL,
  `ANIO` int(11) DEFAULT NULL,
  `KILOMETRAJE` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `coches`
--

INSERT INTO `coches` (`COCHE_ID`, `MODELO`, `MARCA`, `ANIO`, `KILOMETRAJE`) VALUES
(1, 'Focus', 'Ford', 2018, 45000),
(2, 'Civic', 'Honda', 2019, 30000),
(3, 'Corolla', 'Toyota', 2020, 25000),
(4, 'Model 3', 'Tesla', 2021, 12000),
(5, 'Golf', 'Volkswagen', 2017, 60000),
(6, 'A3', 'Audi', 2019, 28000),
(7, 'Serie 3', 'BMW', 2018, 35000),
(8, 'Clio', 'Renault', 2016, 70000),
(9, '208', 'Peugeot', 2020, 20000),
(10, 'Ibiza', 'SEAT', 2019, 22000),
(11, 'Fiesta', 'Ford', 2020, 20000),
(12, 'S-Class', 'Mercedes', 2021, 15000),
(13, 'Mustang', 'Ford', 2022, 5000),
(14, 'Q7', 'Audi', 2019, 40000),
(15, 'i8', 'BMW', 2021, 10000),
(16, 'Cayenne', 'Porsche', 2021, 12000),
(17, 'Challenger', 'Dodge', 2020, 22000),
(18, 'A6', 'Audi', 2018, 45000),
(19, 'X6', 'BMW', 2020, 30000),
(20, 'Aston Martin DB11', 'Aston Martin', 2021, 8000),
(98, 'SEAT', 'Arona', 2001, 45),
(99, 'SEAT', 'Arona', 2001, 45);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `coches`
--
ALTER TABLE `coches`
  ADD PRIMARY KEY (`COCHE_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
