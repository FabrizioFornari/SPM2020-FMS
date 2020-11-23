CREATE DATABASE  IF NOT EXISTS `smartparking_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `smartparking_db`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: smartparking_db
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car` (
  `LicensePlateNumber` varchar(20) NOT NULL,
  `Driver` int NOT NULL,
  `Model` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`LicensePlateNumber`,`Driver`),
  KEY `FKCar26252` (`Driver`),
  CONSTRAINT `FKCar26252` FOREIGN KEY (`Driver`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkingspace`
--

DROP TABLE IF EXISTS `parkingspace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parkingspace` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) DEFAULT NULL,
  `Address` varchar(100) NOT NULL,
  `Coordnates` varchar(255) NOT NULL,
  `Spots_capacity` int NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Coordnates` (`Coordnates`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkingspace`
--

LOCK TABLES `parkingspace` WRITE;
/*!40000 ALTER TABLE `parkingspace` DISABLE KEYS */;
/*!40000 ALTER TABLE `parkingspace` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkingspot`
--

DROP TABLE IF EXISTS `parkingspot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parkingspot` (
  `SpotNumber` int NOT NULL,
  `ParkingSpace` int NOT NULL,
  `IsReserved` tinyint(1) NOT NULL,
  `Restriction` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`SpotNumber`,`ParkingSpace`),
  KEY `FKParkingSpo748404` (`ParkingSpace`),
  CONSTRAINT `FKParkingSpo748404` FOREIGN KEY (`ParkingSpace`) REFERENCES `parkingspace` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkingspot`
--

LOCK TABLES `parkingspot` WRITE;
/*!40000 ALTER TABLE `parkingspot` DISABLE KEYS */;
/*!40000 ALTER TABLE `parkingspot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Id_driver` int NOT NULL,
  `LicensePlateNumber` varchar(20) NOT NULL,
  `Id_parkingSpot` int NOT NULL,
  `ParkingSpace` int NOT NULL,
  `Parking_start` datetime NOT NULL,
  `Parking_end` datetime NOT NULL,
  `Booking_time` int NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Id_parkingSpot` (`Id_parkingSpot`,`ParkingSpace`,`Parking_start`),
  UNIQUE KEY `Id_parkingSpot_2` (`Id_parkingSpot`,`ParkingSpace`,`Parking_end`),
  KEY `FKReservatio749584` (`LicensePlateNumber`,`Id_driver`),
  CONSTRAINT `FKReservatio749584` FOREIGN KEY (`LicensePlateNumber`, `Id_driver`) REFERENCES `car` (`LicensePlateNumber`, `Driver`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKReservatio915184` FOREIGN KEY (`Id_parkingSpot`, `ParkingSpace`) REFERENCES `parkingspot` (`SpotNumber`, `ParkingSpace`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL,
  `Surname` varchar(50) DEFAULT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Tax_code` varchar(20) NOT NULL,
  `Phone_number` varchar(20) NOT NULL,
  `User_type` varchar(15) NOT NULL,
  `Id_number` varchar(20) DEFAULT NULL,
  `Auth_number` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Email` (`Email`),
  UNIQUE KEY `Tax_code` (`Tax_code`),
  UNIQUE KEY `Id_number` (`Id_number`),
  UNIQUE KEY `Auth_number` (`Auth_number`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (21,'Mario','Lombardi','mario@hotmail.com','$2a$10$0rb9TtCwDQuPrmFeRZRqXeosfKg399RyXBkNykKvFrTX505566SI2','FDFDGDFGD','881','Driver',NULL,NULL),(26,'Mario','Lombardi','lombardi@hotmail.it','$2a$10$xFeUD1HXDq0BizsUn50UCuG671mJNiBlceFR/7Ylg/PDgTgQASnYu','756757','64646','Policeman','57657',NULL),(28,'Mario','Lombardi','lombardi@hotmail.com','$2a$10$sS.qhofh3yQFnF1pYvXunOalB2admiLWyBa0iRGRLBG5alDgdL2oi','giugigi','5353','Policeman','54453',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-21 19:06:25
