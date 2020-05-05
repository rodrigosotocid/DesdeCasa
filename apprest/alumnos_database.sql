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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
INSERT INTO `curso` VALUES (1,'Python','pyn.png',255),(2,'Networking ','redes.png',235),(3,'Android','android.png',375),(4,'Java 8','java.png',395),(5,'Angular JS','angular.png',300),(6,'React JS','react.png',300);
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `noticia`
--

DROP TABLE IF EXISTS `noticia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `noticia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(300) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `contenido` longtext COLLATE utf8_spanish2_ci,
  `imagen` varchar(150) COLLATE utf8_spanish2_ci DEFAULT 'noticia(1).jpg',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `noticia`
--

LOCK TABLES `noticia` WRITE;
/*!40000 ALTER TABLE `noticia` DISABLE KEYS */;
INSERT INTO `noticia` VALUES (1,'Cientos de plazas disponibles en el 2020!!','2020-02-02 00:00:00','Tras la creciente demanda en el área tecnológica el 90% de los centros del país estan ofreciendo...Como viene siendo habitual durante los últimos años, los empleadores siguen requiriendo a desarrolladores de software que puedan mantener bases de datos con SQL y desarrollar distintos proyectos en Java.','noticia(1).jpg'),(2,'¿Qué se le pide a un programador en 2020?','2020-05-05 00:00:00','Con el comienzo de un nuevo año, aquellos aspirantes a programador o programadores que quieran buscar un nuevo empleo deben ser conscientes de todo lo que se le pide a un programador en 2020 para poder entrar en las principales empresas del mercado.','noticia(2).jpg'),(3,'Pyhton en el mundo Científico.','2020-01-28 00:00:00','No cabe la menor duda de que en muchas áreas de carácter científico-técnico la adecuada elección del software y/o lenguaje de programación empleado es determinante, de cara a la potencia, versatilidad, facilidad de uso y acceso por parte de todos los usuarios en sus propios dispositivos, de manera generalizada y gratuita.','noticia(3).jpg');
/*!40000 ALTER TABLE `noticia` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (1,'Arantxa','avatar1.png','m'),(37,'Juan','avatar13.png','h'),(52,'Álvaro','avatar9.png','h'),(54,'Charo','avatar10.png','m'),(55,'Joseba','avatar6.png','h'),(57,'Rodrigo','avatar14.png','h'),(61,'Eneritz','avatar16.png','m'),(62,'Janire','avatar15.png','m'),(63,'Lenny','avatar6.png','h'),(65,'Mariela','avatar7.png','m'),(88,'Ander','avatar3.png','h');
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
INSERT INTO `persona_has_curso` VALUES (1,1),(37,1),(54,1),(57,1),(54,2),(57,2),(37,3),(55,3),(57,3),(37,4),(57,4),(88,4),(1,5),(65,5),(1,6),(65,6);
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

-- Dump completed on 2020-05-05 16:35:28



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
