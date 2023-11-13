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
  `userId` INT NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `infix` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`userId`),
  CONSTRAINT uk_userName
  UNIQUE (userName))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`Course` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`Course` (
  `courseId` INT NOT NULL AUTO_INCREMENT,
  `courseName` VARCHAR(45) NOT NULL,
  `level` VARCHAR(10) NOT NULL,
  `coordinator` INT NOT NULL,
  PRIMARY KEY (`courseId`),
  INDEX `fk_Cursus_Gebruiker1_idx` (`coordinator` ASC) VISIBLE,
  UNIQUE (`courseName`),
  CONSTRAINT `fk_Course_User1`
    FOREIGN KEY (`coordinator`)
    REFERENCES `QuizMaster`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`Group` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`Group` (
  `groupId` INT NOT NULL AUTO_INCREMENT,
  `course` INT NOT NULL,
  `groupName` VARCHAR(45) NOT NULL,
  `maxNumberStudents` INT NOT NULL,
  `teacher` INT NOT NULL,
  PRIMARY KEY (`groupId`),
  INDEX `fk_Groep_Cursus_idx` (`course` ASC) VISIBLE,
  INDEX `fk_Groep_Gebruiker1_idx` (`teacher` ASC) VISIBLE,
  CONSTRAINT `fk_Group_Course`
    FOREIGN KEY (`course`)
    REFERENCES `QuizMaster`.`Course` (`courseId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_User1`
    FOREIGN KEY (`teacher`)
    REFERENCES `QuizMaster`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Quiz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`Quiz` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`Quiz` (
  `quizId` INT NOT NULL AUTO_INCREMENT,
  `quizName` VARCHAR(45) NOT NULL,
  `level` VARCHAR(10) NOT NULL,
  `succesDefinition` INT NOT NULL,
  `course` INT NOT NULL,
  PRIMARY KEY (`quizId`),
  INDEX `fk_Quiz_Cursus1_idx` (`course` ASC) VISIBLE,
  UNIQUE (`quizName`),
  CONSTRAINT `fk_Quiz_Course1`
    FOREIGN KEY (`course`)
    REFERENCES `QuizMaster`.`Course` (`courseId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`Question` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`Question` (
  `questionId` INT NOT NULL AUTO_INCREMENT,
  `textQuestion` VARCHAR(250) NOT NULL,
  `correctAnswer` VARCHAR(250) NOT NULL,
  `answer2` VARCHAR(250) NOT NULL,
  `answer3` VARCHAR(250) NOT NULL,
  `answer4` VARCHAR(250) NOT NULL,
  `quiz` INT NOT NULL,
  PRIMARY KEY (`questionId`),
  INDEX `fk_Question_Quiz1_idx` (`quiz` ASC) VISIBLE,
  CONSTRAINT `fk_Question_Quiz1`
    FOREIGN KEY (`quiz`)
    REFERENCES `QuizMaster`.`Quiz` (`quizId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`studentRegistration`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`studentRegistration` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`studentRegistration` (
  `studentRegistrationId` INT NOT NULL AUTO_INCREMENT,
  `student` INT NOT NULL,
  `course` INT NOT NULL,
  INDEX `fk_Groep_has_Gebruiker_Gebruiker1_idx` (`student` ASC) VISIBLE,
  INDEX `fk_studentInschrijving_Cursus1_idx` (`course` ASC) VISIBLE,
  PRIMARY KEY (`studentRegistrationId`),
  CONSTRAINT `fk_Group_has_User_User1`
    FOREIGN KEY (`student`)
    REFERENCES `QuizMaster`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_studentRegistration_Course1`
    FOREIGN KEY (`course`)
    REFERENCES `QuizMaster`.`Course` (`courseId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`QuizResult`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`QuizResult` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`QuizResult` (
  `quizResultId` INT NOT NULL AUTO_INCREMENT,
  `user` INT NOT NULL,
  `quiz` INT NOT NULL,
  `numberCorrect` INT NOT NULL,
  `dateTime` DATETIME NOT NULL,
  INDEX `fk_User_has_Quiz_Quiz1_idx` (`quiz` ASC) VISIBLE,
  INDEX `fk_User_has_Quiz_Gebruiker1_idx` (`user` ASC) VISIBLE,
  PRIMARY KEY (`quizResultId`),
  CONSTRAINT `fk_User_has_Quiz_User1`
    FOREIGN KEY (`user`)
    REFERENCES `QuizMaster`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Quiz_Quiz1`
    FOREIGN KEY (`quiz`)
    REFERENCES `QuizMaster`.`Quiz` (`quizId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`GroupAssignment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `QuizMaster`.`GroupAssignment` ;

CREATE TABLE IF NOT EXISTS `QuizMaster`.`GroupAssignment` (
  `groupAssignmentId` INT NOT NULL AUTO_INCREMENT,
  `group` INT NOT NULL,
  `user` INT NOT NULL,
  INDEX `fk_Group_has_User_User2_idx` (`user` ASC) VISIBLE,
  INDEX `fk_Group_has_User_Group1_idx` (`group` ASC) VISIBLE,
  PRIMARY KEY (`groupAssignmentId`),
  CONSTRAINT `fk_Group_has_User_Group1`
    FOREIGN KEY (`group`)
    REFERENCES `QuizMaster`.`Group` (`groupId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_has_User_User2`
    FOREIGN KEY (`user`)
    REFERENCES `QuizMaster`.`User` (`userId`)
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
