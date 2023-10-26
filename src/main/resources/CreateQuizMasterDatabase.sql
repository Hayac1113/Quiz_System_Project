-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema QuizMaster
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema QuizMaster
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `QuizMaster` DEFAULT CHARACTER SET utf8 ;
USE `QuizMaster` ;

-- -----------------------------------------------------
-- Table `QuizMaster`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`User` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`User` (
  `userName` VARCHAR(12) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `infix` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`userName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`Course` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`Course` (
  `courseName` VARCHAR(45) NOT NULL,
  `level` VARCHAR(10) NOT NULL,
  `coordinator` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`courseName`),
  INDEX `fk_Cursus_Gebruiker1_idx` (`coordinator` ASC) VISIBLE,
  CONSTRAINT `fk_Course_User1`
    FOREIGN KEY (`coordinator`)
    REFERENCES `QuizMaster`.`User` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`Group` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`Group` (
  `courseName` VARCHAR(45) NOT NULL,
  `groupName` VARCHAR(45) NOT NULL,
  `maxNumberStudents` INT NOT NULL,
  `teacher` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`courseName`, `groupName`),
  INDEX `fk_Groep_Cursus_idx` (`courseName` ASC) VISIBLE,
  INDEX `fk_Groep_Gebruiker1_idx` (`teacher` ASC) VISIBLE,
  CONSTRAINT `fk_Group_Course`
    FOREIGN KEY (`courseName`)
    REFERENCES `QuizMaster`.`Course` (`courseName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_User1`
    FOREIGN KEY (`teacher`)
    REFERENCES `QuizMaster`.`User` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Quiz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`Quiz` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`Quiz` (
  `quizName` VARCHAR(45) NOT NULL,
  `level` VARCHAR(10) NOT NULL,
  `succesDefinition` INT NOT NULL,
  `courseName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`quizName`),
  INDEX `fk_Quiz_Cursus1_idx` (`courseName` ASC) VISIBLE,
  CONSTRAINT `fk_Quiz_Course1`
    FOREIGN KEY (`courseName`)
    REFERENCES `QuizMaster`.`Course` (`courseName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`Question` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`Question` (
  `textQuestion` VARCHAR(200) NOT NULL,
  `correctAnswer` VARCHAR(45) NOT NULL,
  `answer2` VARCHAR(45) NOT NULL,
  `answer3` VARCHAR(45) NOT NULL,
  `answer4` VARCHAR(45) NOT NULL,
  `quizName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`textQuestion`, `quizName`),
  INDEX `fk_Question_Quiz1_idx` (`quizName` ASC) VISIBLE,
  CONSTRAINT `fk_Question_Quiz1`
    FOREIGN KEY (`quizName`)
    REFERENCES `QuizMaster`.`Quiz` (`quizName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`studentRegistration`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`studentRegistration` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`studentRegistration` (
  `student` VARCHAR(12) NOT NULL,
  `courseName` VARCHAR(45) NOT NULL,
  INDEX `fk_Groep_has_Gebruiker_Gebruiker1_idx` (`student` ASC) VISIBLE,
  INDEX `fk_studentInschrijving_Cursus1_idx` (`courseName` ASC) VISIBLE,
  PRIMARY KEY (`student`, `courseName`),
  CONSTRAINT `fk_Group_has_User_User1`
    FOREIGN KEY (`student`)
    REFERENCES `QuizMaster`.`User` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_studentRegistration_Course1`
    FOREIGN KEY (`courseName`)
    REFERENCES `QuizMaster`.`Course` (`courseName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`QuizResult`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`QuizResult` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`QuizResult` (
  `dateTime` DATETIME NOT NULL,
  `user` VARCHAR(12) NOT NULL,
  `quizName` VARCHAR(45) NOT NULL,
  `numberCorrect` INT NOT NULL,
  INDEX `fk_User_has_Quiz_Quiz1_idx` (`quizName` ASC) VISIBLE,
  INDEX `fk_User_has_Quiz_Gebruiker1_idx` (`user` ASC) VISIBLE,
  PRIMARY KEY (`dateTime`, `user`, `quizName`),
  CONSTRAINT `fk_User_has_Quiz_User1`
    FOREIGN KEY (`user`)
    REFERENCES `QuizMaster`.`User` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Quiz_Quiz1`
    FOREIGN KEY (`quizName`)
    REFERENCES `QuizMaster`.`Quiz` (`quizName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`GroupAssignment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`GroupAssignment` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`GroupAssignment` (
  `courseName` VARCHAR(45) NOT NULL,
  `groupName` VARCHAR(45) NOT NULL,
  `userName` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`courseName`, `groupName`, `userName`),
  INDEX `fk_Group_has_User_User2_idx` (`userName` ASC) VISIBLE,
  INDEX `fk_Group_has_User_Group1_idx` (`courseName` ASC, `groupName` ASC) VISIBLE,
  CONSTRAINT `fk_Group_has_User_Group1`
    FOREIGN KEY (`courseName` , `groupName`)
    REFERENCES `QuizMaster`.`Group` (`courseName` , `groupName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_has_User_User2`
    FOREIGN KEY (`userName`)
    REFERENCES `QuizMaster`.`User` (`userName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

--
-- User voor de database te gebruiken in QuizMaster connectie
--
CREATE USER 'userQuizMaster'@'localhost' IDENTIFIED BY 'userQuizMasterPW';
GRANT ALL PRIVILEGES ON QuizMaster . * TO 'userQuizMaster'@'localhost';
