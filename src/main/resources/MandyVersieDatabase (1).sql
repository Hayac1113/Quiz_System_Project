CREATE DATABASE  IF NOT EXISTS `quizmaster` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `quizmaster`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: quizmaster
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `courseName` varchar(45) NOT NULL,
  `level` varchar(10) NOT NULL,
  `coordinator` varchar(12) NOT NULL,
  PRIMARY KEY (`courseName`),
  KEY `fk_Cursus_Gebruiker1_idx` (`coordinator`),
  CONSTRAINT `fk_Course_User1` FOREIGN KEY (`coordinator`) REFERENCES `user` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` (`courseName`, `level`, `coordinator`) VALUES ('Analyse','noorha','Gevorderd'),('Calculus 1','noorha','Medium'),('Engels A','dreujo','Beginner'),('Engels B','tilli','Medium'),('Engels C','dreujo','Gevorderd'),('Geschiedenis Middeleeuwen','koopala','Beginner'),('Harmonieleer 1','bruggdj','Beginner'),('Harmonieleer 2','verdaiz','Medium'),('Harmonieleer Jazz','leijtde','Gevorderd'),('Java Object Orientatie','lambrge','Medium'),('Java Programming','lambrge','Beginner'),('Java Spring Framework','lambrge','Gevorderd'),('Kunstgeschiedenis','koopala','Medium'),('Moderne Geschiedenis','koopala','Medium'),('Muziek Geschiedenis','verdaiz','Beginner'),('Muziek Theorie Basis','gilsiel','Beginner'),('Python Basis','knobbse','Beginner'),('Python++','knobbse','Medium'),('Spaans A','bruggdj','Beginner'),('Spaans B','bruggdj','Medium'),('Spaans C','bruggdj','Gevorderd');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group` (
  `courseName` varchar(45) NOT NULL,
  `groupName` varchar(45) NOT NULL,
  `maxAantalStudenten` int NOT NULL,
  `teacher` varchar(12) NOT NULL,
  PRIMARY KEY (`courseName`,`groupName`),
  KEY `fk_Groep_Cursus_idx` (`courseName`),
  KEY `fk_Groep_Gebruiker1_idx` (`teacher`),
  CONSTRAINT `fk_Group_Course` FOREIGN KEY (`courseName`) REFERENCES `course` (`courseName`),
  CONSTRAINT `fk_Group_User1` FOREIGN KEY (`teacher`) REFERENCES `user` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` (`courseName`, `groupName`, `maxAantalStudenten`, `teacher`) VALUES ('Engels A','Groep 1',25,'veldhye'),('Engels B','Groep 1',25,'otterma'),('Harmonieleer 1','Groep 1',25,'simonma'),('Harmonieleer 1','Groep 2',25,'simonma'),('Harmonieleer 1','Groep 3',25,'simonma'),('Harmonieleer Jazz','Groep 1',15,'ypmaze'),('Java Object Orientatie','Groep 1',25,'reiniro'),('Java Object Orientatie','Groep 2',25,'stegefa'),('Java Spring Framework','Groep 1',20,'schooza'),('Kunstgeschiedenis','Groep 1',20,'cornemi'),('Kunstgeschiedenis','Groep 2',20,'veltrli'),('Kunstgeschiedenis','Groep 3',20,'cornemi'),('Moderne Geschiedenis','Groep 1',25,'cornemi'),('Muziek Theorie Basis','Groep 1',25,'hovendo'),('Muziek Theorie Basis','Groep 2',25,'gubbeke'),('Python Basis','Groep 1',25,'tonkemi'),('Python Basis','Groep 2',25,'schooza'),('Python Basis','Groep 3',25,'schooza'),('Python++','Groep 1',25,'schooza'),('Python++','Groep 2',25,'stegefa'),('Python++','Groep 3',25,'stegefa'),('Spaans A','Groep 1',25,'otterma'),('Spaans B','Groep 1',25,'veldhye'),('Spaans B','Groep 2',25,'otterma');
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupassignment`
--

DROP TABLE IF EXISTS `groupassignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groupassignment` (
  `courseName` varchar(45) NOT NULL,
  `groupName` varchar(45) NOT NULL,
  `userName` varchar(12) NOT NULL,
  PRIMARY KEY (`courseName`,`groupName`,`userName`),
  KEY `fk_Group_has_User_User2_idx` (`userName`),
  KEY `fk_Group_has_User_Group1_idx` (`courseName`,`groupName`),
  CONSTRAINT `fk_Group_has_User_Group1` FOREIGN KEY (`courseName`, `groupName`) REFERENCES `group` (`courseName`, `groupName`),
  CONSTRAINT `fk_Group_has_User_User2` FOREIGN KEY (`userName`) REFERENCES `user` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupassignment`
--

LOCK TABLES `groupassignment` WRITE;
/*!40000 ALTER TABLE `groupassignment` DISABLE KEYS */;
/*!40000 ALTER TABLE `groupassignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `textQuestion` varchar(200) NOT NULL,
  `correctAnswer` varchar(45) NOT NULL,
  `answer2` varchar(45) NOT NULL,
  `answer3` varchar(45) NOT NULL,
  `answer4` varchar(45) NOT NULL,
  `quizName` varchar(45) NOT NULL,
  PRIMARY KEY (`textQuestion`,`quizName`),
  KEY `fk_Question_Quiz1_idx` (`quizName`),
  CONSTRAINT `fk_Question_Quiz1` FOREIGN KEY (`quizName`) REFERENCES `quiz` (`quizName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz` (
  `quizName` varchar(45) NOT NULL,
  `level` varchar(10) NOT NULL,
  `succesDefinition` int NOT NULL,
  `courseName` varchar(45) NOT NULL,
  PRIMARY KEY (`quizName`),
  KEY `fk_Quiz_Cursus1_idx` (`courseName`),
  CONSTRAINT `fk_Quiz_Course1` FOREIGN KEY (`courseName`) REFERENCES `course` (`courseName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
INSERT INTO `quiz` (`quizName`, `level`, `succesDefinition`, `courseName`) VALUES ('Engels A 1','Beginner',8,'Engels A'),('Engels A 2','Beginner',8,'Engels A'),('Engels A 3','Beginner',8,'Engels A'),('Geschiedenis Middeleeuwen 1','Beginner',6,'Geschiedenis Middeleeuwen'),('Geschiedenis Middeleeuwen 2','Beginner',6,'Geschiedenis Middeleeuwen'),('Geschiedenis Middeleeuwen 3','Beginner',6,'Geschiedenis Middeleeuwen'),('Java Basis','Beginner',7,'Java Programming'),('Python Basis','Beginner',5,'Python Basis');
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizresult`
--

DROP TABLE IF EXISTS `quizresult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizresult` (
  `dateTime` datetime NOT NULL,
  `user` varchar(12) NOT NULL,
  `quizName` varchar(45) NOT NULL,
  `numberCorrect` int NOT NULL,
  PRIMARY KEY (`dateTime`,`user`,`quizName`),
  KEY `fk_User_has_Quiz_Quiz1_idx` (`quizName`),
  KEY `fk_User_has_Quiz_Gebruiker1_idx` (`user`),
  CONSTRAINT `fk_User_has_Quiz_Quiz1` FOREIGN KEY (`quizName`) REFERENCES `quiz` (`quizName`),
  CONSTRAINT `fk_User_has_Quiz_User1` FOREIGN KEY (`user`) REFERENCES `user` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizresult`
--

LOCK TABLES `quizresult` WRITE;
/*!40000 ALTER TABLE `quizresult` DISABLE KEYS */;
/*!40000 ALTER TABLE `quizresult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentregistration`
--

DROP TABLE IF EXISTS `studentregistration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentregistration` (
  `student` varchar(12) NOT NULL,
  `courseName` varchar(45) NOT NULL,
  PRIMARY KEY (`student`,`courseName`),
  KEY `fk_Groep_has_Gebruiker_Gebruiker1_idx` (`student`),
  KEY `fk_studentInschrijving_Cursus1_idx` (`courseName`),
  CONSTRAINT `fk_Group_has_User_User1` FOREIGN KEY (`student`) REFERENCES `user` (`userName`),
  CONSTRAINT `fk_studentRegistration_Course1` FOREIGN KEY (`courseName`) REFERENCES `course` (`courseName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentregistration`
--

LOCK TABLES `studentregistration` WRITE;
/*!40000 ALTER TABLE `studentregistration` DISABLE KEYS */;
/*!40000 ALTER TABLE `studentregistration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userName` varchar(12) NOT NULL,
  `password` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `infix` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`userName`, `password`, `firstName`, `infix`, `lastName`, `role`) VALUES ('aanhodo','[LOGcWrxY.','Dorianne','van','Aanholt','Student'),('adriaja','GBJX]v^HvE','Jamy','','Adriaansen','Student'),('agemair','.j=h<APycX','Ireen','','Agema','Administrator'),('alberiw','S&g?5ZebCX','Iwan','','Alberto','Student'),('ammeral','dbkt&WwUMW','Alev','','Ammerlaan','Student'),('angelje','4qx+hB(Ah4','Jered','','Angela','Student'),('arasre','p;a\\=M[Wyi','Reintje','','Aras','Student'),('ariÃ«ndo','<gv5N+Do9n','Dolores','','AriÃ«ns','Student'),('Ã§obanma','K1moL:aYJ3','Margret','','Ã‡oban','Student'),('baarlye','be\\\"Nn4434','Yesim','van','Baarle','Student'),('bakenma','I\\Pf=8L__G','Maria','','Baken','Student'),('baltesa','6hs.8p`PI3','Sascha','','Baltes','Student'),('bekkeka','qCjkh/x0+\\','Kaitlin','','Bekkering','Docent'),('bloemca','IXkM[ijWLS','Cassandra','','Bloemers','Student'),('boerhfa',':9Y=;u.(QD','Faas','','Boerhof','Student'),('bongasa','A8`(B&Nvt_','SaÃ¯da','','Bongaerts','Student'),('boskman','C_FxMkfaR5','Anthonie','','Boskma','Student'),('bouwam','(*\\\"+o3QOVD','Amin','','Bouw','Student'),('brackha','2Qm4&G5xq;','Harmke','','Bracke','Student'),('brandlo','tZte)9dLc4','Lonne','','Brands','Student'),('brandwa','QF8LBFTYS!','Warner','van den','Brand','Student'),('bravemi','i5Y7%cM!2P','Mirna','','Bravenboer','Student'),('broekda','<C3Y5^X/t;','Darrel','van','Broekhoven','Docent'),('bruggdj','K0V:!A.(2h','Djura','','Bruggeling','CoÃ¶rdinator'),('brugma','YEPz0>u=7X','MarÃ¨l','van de','Brug','Student'),('bruijes','`\';q%*Aaua','Esmeralda','','Bruijns','Student'),('buinite','Y]j-4bT9j/','Teatske','','Buining','Student'),('bukkeye','%5dGX&SQM`','Yentle','','Bukkems','Student'),('bulkje','78:p6cDaEk','Jesse','van den','Bulk','Student'),('burgmsy','3u25H25?m\"','Syreeta','','Burgmans','Student'),('buschde','Odn*R=A]P[','Demi','','Buschman','Student'),('buskelu','.o;fHk_:Ma','Lucy','','Buskermolen','Docent'),('cornemi','QmP;r#\'*M&','Milouda','','Cornelisse','Docent'),('dijkeda','rm96(`3i=l','Dahlia','','Dijkers','Student'),('dikkexe','#P[En\\5*;j','Xena','','Dikker','Student'),('doessh','h\"QwC!Qxxt','Shane','van der','Does','Student'),('drentai','2:;Zwn<7(<','Aiden','','Drent','Student'),('dreujo','&[W?xt02\\e','Joury','de','Dreu','CoÃ¶rdinator'),('eijsico','*=<:_fB(H^','Coenraad','','Eijsink','Student'),('elzenra','Q6i]+!Qe+=','Rajni','','Elzenga','Student'),('engelya','jBY4mjzG07','Yaniek','van','Engelenburg','Student'),('faasssi','f&>zK`*AZo','Sietze','','Faassen','Student'),('fringjo','g585$DdgGe','JoÃ«l','','Frings','Student'),('fritzda','!6\'$0yIn84','Davinia','','Fritz','Student'),('gaaledo','2JzL]WR0$Q','Dora','van','Gaalen','Functioneel Beheerder'),('gijbene','<(k\\S1CNof','Nelis','','Gijbels','Student'),('gilsiel','\'Fq:iIqYPn','Elja','','Gilsing','CoÃ¶rdinator'),('goddina','\"<T84$br[O','Nathaly','','Goddijn','Administrator'),('gravedi','\\\"dg7Y326)h','Dimitrios','','Gravemaker','Student'),('grienan','X_9V\\Kl\'jf','AndrÃ©as','van','Griensven','Student'),('grosju','$U?8mYk+V<','Judy','','Gros','Student'),('gubbeke','On1yj8k&(g','Keziah','','Gubbels','Docent'),('haagseg','Tgq+:]&O=.','Egberdina','','Haagsma','Student'),('haakza',']9%1TVh1wF','Zafer','van den','Haak','Student'),('harteyi','1k13@iVW_2','Yinthe','','Hartevelt','Docent'),('hellead','(QDTR&K=9d','Adrie','','Helleman','Student'),('hessema','#Uae_OBWs/','Mazlum','','Hesselink','Student'),('hiddide','`O63@YLfVJ','Deon','','Hiddink','Student'),('holmaam','\"lsM3\\Vex+','Amra','','Holman','Student'),('hoogech','jvmj_O1+zq','Cherida','','Hoogendijk','Student'),('hoopeid','1^iE^;mu]A','Idil','ten','Hoopen','Student'),('houteli',';\\HvrCA:OB','Liedeke','','Houtepen','Student'),('hovelro','.*dv4c\"M<R','Romaissa','','Hoveling','Student'),('hovendo','0Zc_s3\'utE','Dogan','ten','Hoven','Docent'),('jankise','KK]V-$HUEZ','Selenay','','Janki','Student'),('jolinro','fvyf0OOEct','Romello','','Joling','Student'),('jonasku','[Qs08jZOd9','Kubilay','','Jonas','Student'),('kaagmra','2.r+&w4d/^','Raphael','','Kaagman','Docent'),('kaaja','xKjl6nT/#e','Jaromir','van der','Kaa','Student'),('kanky','TpJ<0\'!*z\'','Kylie','','Kan','Student'),('karelro',')#x!2#[>;!','Roelf','','Karels','Student'),('katme','9\"IuiBF!Wr','Mervyn','','Kat','Student'),('kÃ¶nigta','koM>3V9x5q','Tabitha','','KÃ¶nig','Student'),('keskima','&nkx`Y:Yfc','Marre','','Keskin','Student'),('keupean','i!SO[x6b?D','Annegien','','Keuper','Student'),('keusje','g3DE(BrK;n','Jessey','','Keus','Docent'),('kimenyo','&a>C-Y2*;o','Yordi','','Kimenai','Student'),('klopsji','`<SB#R9B9J','Jinke','','Klopstra','Student'),('knapesi','NvbK<]x=79','Siert','','Knape','Student'),('knippni','G.m+yjkT6e','Nimo','','Knippenberg','Student'),('knobbse','Xt$G?\'Rp%u','Selvi','','Knobben','CoÃ¶rdinator'),('knoefre','wT*Hd_/VMr','Reinie','','Knoef','Student'),('koopala','f;lyV-I;(X','LaÃ¯la','','Koopal','CoÃ¶rdinator'),('koppeni',';Gj5Hf_h1f','Nikay','','Koppelman','Student'),('kraaili','F5LL9B^b-e','Liza','','Kraaij','Student'),('laarre','7^\'(`O(tqK','Relinde','van de','Laar','Student'),('lambrge','Mkr<#uG97_','Gertjan','','Lambrechts','CoÃ¶rdinator'),('langeka','3\'x\'U\"3d`z','Kazimir','van de','Langenberg','Student'),('leijtde','sE$DB4:\"9-','Demy','','Leijtens','CoÃ¶rdinator'),('lentjco','0Ae*_zKR6y','Connor','','Lentjes','Student'),('leussel','MwwiMOZ<Z.','Elle','','Leussink','Student'),('leutsab','[XnE/QWQ%A','Abdirahman','','Leutscher','Student'),('liebrhe','z?/>/DvC;e','Henri','','Liebregts','Student'),('linnesa','G2n>47vXty','Sanjana','','Linnenbank','Student'),('litjeda','^SC)%%Xu)X','Dali','','Litjens','Student'),('looijem','FQFK)ccQ0\'','Emel','','Looij','Student'),('louweya','u<8.Uen0b:','Yalda','','Louwes','Student'),('luikca','BB@OMolWxK','Catelijne','','Luik','Student'),('maarssh','mzJwrpQW?Z','Shaima','van','Maarseveen','Student'),('makkiel','kfnA$Q)Els','Eliane','','Makkink','Student'),('mantero','>7pDfM.]ZV','Roanne','','Mantel','Student'),('marenan','pdq1[SaTLs','Andrei','van','Maren','Student'),('marthru','8#!3?cZG41','Ruth','','Martha','Student'),('matthsi','A4Dq;Kg`SQ','Sidar','','Matthijsse','Student'),('mekkisa','&ldB?g];k%','Sake','','Mekking','Student'),('molly','alSq=*A>)k','Lydia','de','Mol','Student'),('mooiwde','Bb%b<=@Ml[','Deniz','','Mooiweer','Student'),('mosseaa','0IgkDNCyI2','Aart','','Mosselman','Student'),('nelisre','v;[TM)kRt3','Remco','','Nelis','Student'),('nijdaar','%-^mw);lt5','Arsenio','','Nijdam','Functioneel Beheerder'),('noordro','EmKl7<TB`9','Romain','','Noordanus','Student'),('oostlna','%UU_XtE1Q0','Naoual','','Oostland','Student'),('ossewja','<DeztOIX$*','JaÃ¯ro','','Ossewaarde','Administrator'),('otterma','\'yzw\\\"s3+(8','Maj','van','Otterlo','Docent'),('oudenhe','rcuGzT::xl','Heba','','Oudendijk','Student'),('ouwerwi',']X`>\'+g?vU','Willibrordus','','Ouwerkerk','Student'),('paschan','A(O\\\'l3ptM','Anastasia','van der','Pasch','Student'),('pepermo','=CWvY%go2B','Morrison','','Pepers','Student'),('pietemi','i8R$w_LcRf','Milan','','Pieters','Student'),('pluijdj','RxVgvG>mr.','Djayden','','Pluijmen','Student'),('reiniro','J$x3a4s]Ui','Romay','','Reinink','Docent'),('remmeja','Qdw0B6*:lZ','Jai','','Remmelzwaal','Student'),('remmets','O8c6E*ql0T','Tsjerk','van','Remmerden','Student'),('roestol','qV.gbt#q\\F','Olivier','','Roest','Student'),('rorijfe','R_yHvu]5yQ','Ferrie','','Rorije','Student'),('rosenla','Za9\"!%K)+-','Laurenz','','Rosendahl','Student'),('ruiscan','t\"j9Y3&``l','Angenita','','Ruisch','Student'),('schaaki','bR865aQC7;','Kimo','','Schaareman','Student'),('schepja','^V^kvvLz4A','Jans','','Schep','Student'),('schipch','\"ro\\V^1#z-','Chantal','','Schiphorst','Student'),('schoema','8\"%6\\\'p-1k','Marko','','Schoen','Student'),('schooza','w]hYM8:WI-','Zarah','','School','Docent'),('schuiil','xc\':Gc5]&M','Ilias','','Schuijt','Student'),('simonma','iCT1@2VG62','Mana','','Simonis','Docent'),('slappma','n44z:rc/f8','Manasse','','Slappendel','Student'),('sneldfo',':r+;d-\\a9K','Fokko','','Snelder','Student'),('snijdla','n&E\\j3Pi(\\','Larry','','Snijder','Student'),('sourebu','/\'rO9PLho*','BuÄŸra','','Souren','Student'),('spierch','\'Gb7?7RGIv','Chris','','Spierenburg','Student'),('stapge','A6UHc=hNC1','Gerianne','','Stap','Student'),('stegefa','V<v\"&mBa!w','Fady','','Stegehuis','Docent'),('stratsh','U@bh-+sjlP','Sharan','van','Straten','Student'),('stuitti','w/+Xz;Ud;G','TimotheÃ¼s','','Stuit','Student'),('talmama','U#Df;h]$em','Mariella','','Talman','Student'),('thoolko','Ens9Zl/eM*','Koen','','Thoolen','Student'),('tiehuwi','j;5n\'lz3fF','Wijnanda','','Tiehuis','Docent'),('tilli','aZ9&6=g;LI','Lizet','van','Til','CoÃ¶rdinator'),('tuinedi','P4i\"jL9R*U','Dicle','van','Tuinen','Student'),('vechtma','V5Ch2b$:e5','Manouck','van der','Vecht','Student'),('veldhye',']$15RrJbxa','Yelina','','Veldhoen','Docent'),('veltrli','h)6A+y[inu','Lilian','','Veltrop','Docent'),('verbulu','^eG4k-X>C=','Lucille','','Verburgt','Student'),('verdaiz',')XSF@b<W+J','Izaak','','Verdaasdonk','CoÃ¶rdinator'),('verhema','A@]NG3>2w2','MathÃ©','','Verheijden','Student'),('vinkede','ajR4D?fgFr','Dewy','','Vinken','Student'),('vinkma','SQzt4mUmSy','Master','','Vink','Student'),('voorbap','/Ql[yE5Ka#','April','','Voorbraak','Student'),('vroegel','yO;#./(;5p','Elsa','','Vroegh','Student'),('wardtbr','4Q\\>1Ri+@Q','Brit','van der','Wardt','Student'),('widdeae',';wx;?$yt4*','Aemilius','','Widdershoven','Administrator'),('wijnhfa','rcn!5/=^@#','Farhad','','Wijnhoven','Student'),('worpor','y67>9?Fv\\z','Oriana','van der','Worp','Student'),('yeunglo','xFXT8emew.','Louise','','Yeung','Student'),('ypmaze','YR6g5a!&R?','Zev','','Ypma','Docent'),('zeegege','!P.>=Ei-Q*','Geurtje','','Zeegers','Student'),('zouwefl','pEK_!/IA;i','Florina','van der','Zouwen','Student'),('zuidear','2[[BG]UD>\\','Arij','','Zuidema','Student'),('zuijdre','`s<I$;PF>L','Reduan','','Zuijderduijn','Student'),('zwaanca','2=n;_Y\"+7Y','Calvin','van der','Zwaan','Student'),('zweerwa','t_^zxb57\\J','Wassim','','Zweers','Docent');
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

-- Dump completed on 2023-10-19 10:05:31
