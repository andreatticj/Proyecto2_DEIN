-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: libros
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Table structure for table `Alumno`

DROP TABLE IF EXISTS `Alumno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Alumno` (
  `dni` varchar(9) COLLATE latin1_spanish_ci NOT NULL,
  `nombre` varchar(150) COLLATE latin1_spanish_ci DEFAULT NULL,
  `apellido1` varchar(150) COLLATE latin1_spanish_ci DEFAULT NULL,
  `apellido2` varchar(150) COLLATE latin1_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dumping data for table `Alumno`

LOCK TABLES `Alumno` WRITE;
/*!40000 ALTER TABLE `Alumno` DISABLE KEYS */;
INSERT INTO `Alumno` VALUES 
('12345678A','Juan','Pérez','Gómez'),
('23456789B','María','López','Sánchez'),
('34567890C','Carlos','Rodríguez','Fernández'),
('45678901D','Ana','García','Martínez'),
('56789012E','Josefo','Hernández','González');
/*!40000 ALTER TABLE `Alumno` ENABLE KEYS */;
UNLOCK TABLES;

-- Table structure for table `Libro`

DROP TABLE IF EXISTS `Libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Libro` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(150) COLLATE latin1_spanish_ci DEFAULT NULL,
  `autor` varchar(200) COLLATE latin1_spanish_ci DEFAULT NULL,
  `editorial` varchar(150) COLLATE latin1_spanish_ci DEFAULT NULL,
  `estado` varchar(50) COLLATE latin1_spanish_ci DEFAULT NULL,
  `baja` int DEFAULT '0',
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dumping data for table `Libro`

LOCK TABLES `Libro` WRITE;
/*!40000 ALTER TABLE `Libro` DISABLE KEYS */;
INSERT INTO `Libro` VALUES 
(1,'El Quijote','Miguel de Cervantes','Editorial A','Nuevo',0),
(2,'Cien años de soledad','Gabriel García Márquez','Editorial B','Nuevo',0),
(3,'1984','George Orwell','Editorial C','Usado nuevo',0),
(4,'El Principito','Antoine de Saint-Exupéry','Editorial D','Nuevo',0),
(5,'Matar a un ruiseñor','Harper Lee','Editorial F','Usado estropeado',0);
/*!40000 ALTER TABLE `Libro` ENABLE KEYS */;
UNLOCK TABLES;

-- Table structure for table `Prestamo`

DROP TABLE IF EXISTS `Prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Prestamo` (
  `id_prestamo` int NOT NULL AUTO_INCREMENT,
  `dni_alumno` varchar(9) COLLATE latin1_spanish_ci NOT NULL,
  `codigo_libro` int NOT NULL,
  `fecha_prestamo` datetime DEFAULT NULL,
  PRIMARY KEY (`id_prestamo`),
  KEY `FK_Prestamo_Libro` (`codigo_libro`),
  KEY `FK_Prestamo_Alumno` (`dni_alumno`),
  CONSTRAINT `FK_Prestamo_Alumno` FOREIGN KEY (`dni_alumno`) REFERENCES `Alumno` (`dni`),
  CONSTRAINT `FK_Prestamo_Libro` FOREIGN KEY (`codigo_libro`) REFERENCES `Libro` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dumping data for table `Prestamo`

LOCK TABLES `Prestamo` WRITE;
/*!40000 ALTER TABLE `Prestamo` DISABLE KEYS */;
INSERT INTO `Prestamo` VALUES 
(2,'45678901D',4,'2025-01-23 00:00:00'),
(3,'34567890C',3,'2025-01-27 00:00:00'),
(4,'12345678A',1,'2025-01-30 00:00:00');
/*!40000 ALTER TABLE `Prestamo` ENABLE KEYS */;
UNLOCK TABLES;

-- Table structure for table `Historico_prestamo`

DROP TABLE IF EXISTS `Historico_prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Historico_prestamo` (
  `id_prestamo` int NOT NULL AUTO_INCREMENT,
  `dni_alumno` varchar(9) COLLATE latin1_spanish_ci NOT NULL,
  `codigo_libro` int NOT NULL,
  `fecha_prestamo` datetime DEFAULT NULL,
  `fecha_devolucion` datetime DEFAULT NULL,
  PRIMARY KEY (`id_prestamo`),
  KEY `FK_Historico_prestamo_Alumno` (`dni_alumno`),
  KEY `FK_Historico_prestamo_Libro` (`codigo_libro`),
  CONSTRAINT `FK_Historico_prestamo_Alumno` FOREIGN KEY (`dni_alumno`) REFERENCES `Alumno` (`dni`),
  CONSTRAINT `FK_Historico_prestamo_Libro` FOREIGN KEY (`codigo_libro`) REFERENCES `Libro` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dumping data for table `Historico_prestamo`

LOCK TABLES `Historico_prestamo` WRITE;
/*!40000 ALTER TABLE `Historico_prestamo` DISABLE KEYS */;
INSERT INTO `Historico_prestamo` VALUES 
(1,'12345678A',1,'2025-01-01 10:00:00','2025-01-10 14:00:00'),
(2,'23456789B',2,'2025-01-02 11:00:00','2025-01-11 16:00:00'),
(3,'34567890C',3,'2025-01-03 12:00:00','2025-01-12 15:00:00'),
(4,'12345678A',4,'2025-01-20 00:00:00','2025-01-22 00:00:00'),
(5,'56789012E',2,'2025-01-22 00:00:00','2025-01-22 00:00:00'),
(6,'12345678A',1,'2025-01-22 00:00:00','2025-01-28 00:00:00');
/*!40000 ALTER TABLE `Historico_prestamo` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
