CREATE DATABASE  IF NOT EXISTS `alumnos` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `alumnos`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: alumnos
-- ------------------------------------------------------
-- Server version	8.0.19

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

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) COLLATE utf8_spanish_ci NOT NULL,
  `imagen` varchar(150) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'default.png',
  `precio` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
INSERT INTO `curso` VALUES (1,'Curso Programador Python','pyn.png',255),(2,'Curso Conectividad y Redes ','redes.png',235),(3,'Curso Programador Android','android.png',375),(4,'Curso Programador Java 8','java.png',395);
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `avatar` varchar(250) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'avatar1.png',
  `sexo` varchar(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'h',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (1,'Arantxa','avatar1.png','m'),(11,'Markel','avatar2.png','h'),(37,'Juan','avatar13.png','h'),(51,'Titae','avatar11.png','h'),(52,'Álvaro','avatar9.png','h'),(54,'Charo ','avatar10.png','m'),(55,'Manolo Carabolo','avatar13.png','h'),(56,'Leticia ','avatar7.png','m'),(57,'Rodrigo ','avatar14.png','h'),(58,'Ramón ','avatar6.png','h'),(59,'Ron Damon','avatar8.png','h'),(60,'Angélica','avatar12.png','m'),(61,'Ximena','avatar16.png','m'),(62,'Estela','avatar15.png','m'),(63,'Lenny','avatar6.png','h'),(64,'Dr Watson','avatar14.png','h'),(65,'Mariela','avatar7.png','m'),(67,'Pedrototote','avatar14.png','h'),(70,'Aintzane','avatar15.png','m'),(74,'Jhon Doe','avatar7.png','h');
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona_has_curso`
--

DROP TABLE IF EXISTS `persona_has_curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona_has_curso` (
  `persona_id` int NOT NULL,
  `curso_id` int NOT NULL,
  PRIMARY KEY (`persona_id`,`curso_id`),
  KEY `fk_persona_has_curso_curso1_idx` (`curso_id`),
  KEY `fk_persona_has_curso_persona_idx` (`persona_id`),
  CONSTRAINT `fk_persona_has_curso_persona` FOREIGN KEY (`persona_id`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona_has_curso`
--

LOCK TABLES `persona_has_curso` WRITE;
/*!40000 ALTER TABLE `persona_has_curso` DISABLE KEYS */;
INSERT INTO `persona_has_curso` VALUES (1,1),(11,2),(37,3),(1,4);
/*!40000 ALTER TABLE `persona_has_curso` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-20 14:14:48

/*CUSTOM SQL*/
/*
SELECT
	p.id as persona_id,
    p.nombre as persona_nombre,
    p.avatar as persona_avatar,
    p.sexo as persona_sexo,
    c.id as curso_id,
    c.nombre as curso_nombre,
    c.imagen as curso_imagen,
    c.precio as curso_precio
FROM (persona p LEFT JOIN persona_has_curso pc ON p.id = pc.persona_id)
LEFT JOIN curso c ON pc.curso_id = c.id;
*/
