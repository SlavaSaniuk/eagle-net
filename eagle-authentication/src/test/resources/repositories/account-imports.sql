-- MySQL dump 10.13  Distrib 5.5.62, for debian-linux-gnu (x86_64)
--
-- Host: 10.8.8.110    Database: eagle_users
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP TABLE IF EXISTS `account_status`;
DROP TABLE IF EXISTS `user_detail`;
DROP TABLE IF EXISTS `account`;
--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
                        `user_id` int(9) NOT NULL AUTO_INCREMENT,
                        `user_id_alias` varchar(30) DEFAULT NULL,
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `user_id_alias` (`user_id_alias`),
                        UNIQUE KEY `UK_43cdpnxsq4fjwf81ejpr3ktjx` (`user_id_alias`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL),(2,NULL),(3,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `account_id` int(9) NOT NULL,
  `account_email` varchar(255) NOT NULL,
  `account_password_hash` varchar(128) NOT NULL,
  `account_password_salt` varchar(128) NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_email` (`account_email`),
  UNIQUE KEY `UK_o72icrlyfe9jcc61hbgsnmkgr` (`account_email`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'test1@mail','D38917CCD71A23DFFF2C902E5A05F098762E9709928B340698920C9B126E34FB328C46686F11AC6C8CC52D32484852D82644D3753B25F65529C25FC61BDB409E','3E3B36A321DA05D219BDC816B56775339063D3C994C42321FE34FBBB9C8C30932292EBEECBF9019E5DA7BE7351175076B2F0047B9F4B35F6F59361C6A4F6A952'),(2,'test2@mail','5B57B974A22904C0C0AED25EA7525B8EDE330A949245FE80123CF1C5AC5679DCFDA6A2E7893E35D6068ABB6BCFACF36D2BE3D91D0DC77352C64D39257705185E','99975FEFFD3B6318F866122A5864B54E482897DF7BD4DD995586962CD0BB63AA1EA26E70882CE477CAE36C2A66ACE89FA08B4F5B28E00F4F7795299FA051D245'),(3,'test3@mail','8FFEE2E521656300E43C4B0B97623BC78A0A4F3CA20A60D56112A5836395D747C685279587BC8C9979AFF57F1417CEC733EABD27E575322BA06846C966B3CE51','63A3CF86660D0B6B65FFD29032F006306A7BBDC02386D93C055BAC5B441B5434EA3FFB8A9753B13089B86B8F01C73A033922B60AAE6448FE44BFC68954F65BCD');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_status`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_status` (
  `status_id` int(9) NOT NULL AUTO_INCREMENT,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`status_id`),
  CONSTRAINT `account_status_ibfk_1` FOREIGN KEY (`status_id`) REFERENCES `account` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_status`
--

LOCK TABLES `account_status` WRITE;
/*!40000 ALTER TABLE `account_status` DISABLE KEYS */;
INSERT INTO `account_status` VALUES (1,'CREATED'),(2,'CREATED'),(3,'CREATED');
/*!40000 ALTER TABLE `account_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_detail`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_detail` (
  `detail_id` int(9) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  PRIMARY KEY (`detail_id`),
  CONSTRAINT `user_detail_ibfk_1` FOREIGN KEY (`detail_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_detail`
--

LOCK TABLES `user_detail` WRITE;
/*!40000 ALTER TABLE `user_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-06  2:44:40
