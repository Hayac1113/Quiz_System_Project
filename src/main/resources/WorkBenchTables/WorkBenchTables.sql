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
-- Table `QuizMaster`.`Gebruiker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`Gebruiker` (
  `gebruikerId` VARCHAR(12) NOT NULL,
  `gebruikersnaam` VARCHAR(45) NOT NULL,
  `wachtwoord` VARCHAR(45) NOT NULL,
  `rol` VARCHAR(45) NOT NULL,
  `voornaam` VARCHAR(45) NOT NULL,
  `tussenvoegsel` VARCHAR(45) NULL,
  `achternaam` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`gebruikerId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Cursus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`Cursus` (
  `cursusId` INT NOT NULL AUTO_INCREMENT,
  `naam` VARCHAR(45) NOT NULL,
  `coordinator` VARCHAR(12) NOT NULL,
  `niveau` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`cursusId`),
  INDEX `fk_Cursus_Gebruiker1_idx` (`coordinator` ASC) VISIBLE,
  CONSTRAINT `fk_Cursus_Gebruiker1`
    FOREIGN KEY (`coordinator`)
    REFERENCES `QuizMaster`.`Gebruiker` (`gebruikerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Groep`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`Groep` (
  `groepId` INT NOT NULL AUTO_INCREMENT,
  `groepsNaam` VARCHAR(45) NOT NULL,
  `groepsGrootte` INT NOT NULL,
  `cursusId` INT NOT NULL,
  `maxAantalStudenten` INT NOT NULL,
  `docent` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`groepId`),
  INDEX `fk_Groep_Cursus_idx` (`cursusId` ASC) VISIBLE,
  INDEX `fk_Groep_Gebruiker1_idx` (`docent` ASC) VISIBLE,
  CONSTRAINT `fk_Groep_Cursus`
    FOREIGN KEY (`cursusId`)
    REFERENCES `QuizMaster`.`Cursus` (`cursusId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Groep_Gebruiker1`
    FOREIGN KEY (`docent`)
    REFERENCES `QuizMaster`.`Gebruiker` (`gebruikerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Quiz`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`Quiz` (
  `quizId` INT NOT NULL AUTO_INCREMENT,
  `niveau` VARCHAR(10) NOT NULL,
  `succesDefinitie` INT NOT NULL,
  `cursusId` INT NOT NULL,
  `quizNaam` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`quizId`),
  INDEX `fk_Quiz_Cursus1_idx` (`cursusId` ASC) VISIBLE,
  CONSTRAINT `fk_Quiz_Cursus1`
    FOREIGN KEY (`cursusId`)
    REFERENCES `QuizMaster`.`Cursus` (`cursusId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Vraag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`Vraag` (
  `vraagId` INT NOT NULL AUTO_INCREMENT,
  `juistAntwoord` VARCHAR(45) NOT NULL,
  `antwoord2` VARCHAR(45) NOT NULL,
  `antwoord3` VARCHAR(45) NOT NULL,
  `antwoord4` VARCHAR(45) NOT NULL,
  `quizId` INT NOT NULL,
  `tekstVraag` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`vraagId`),
  INDEX `fk_Vraag_Quiz1_idx` (`quizId` ASC) VISIBLE,
  CONSTRAINT `fk_Vraag_Quiz1`
    FOREIGN KEY (`quizId`)
    REFERENCES `QuizMaster`.`Quiz` (`quizId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`studentInschrijving`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`studentInschrijving` (
  `studentId` VARCHAR(12) NOT NULL,
  `cursusId` INT NOT NULL,
  INDEX `fk_Groep_has_Gebruiker_Gebruiker1_idx` (`studentId` ASC) VISIBLE,
  INDEX `fk_studentInschrijving_Cursus1_idx` (`cursusId` ASC) VISIBLE,
  PRIMARY KEY (`studentId`, `cursusId`),
  CONSTRAINT `fk_Groep_has_Gebruiker_Gebruiker1`
    FOREIGN KEY (`studentId`)
    REFERENCES `QuizMaster`.`Gebruiker` (`gebruikerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_studentInschrijving_Cursus1`
    FOREIGN KEY (`cursusId`)
    REFERENCES `QuizMaster`.`Cursus` (`cursusId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`QuizPoging`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`QuizPoging` (
  `datumTime` DATETIME NOT NULL,
  `gebruikerId` VARCHAR(12) NOT NULL,
  `quizId` INT NOT NULL,
  `resultaat` DECIMAL(3,1) NOT NULL,
  INDEX `fk_Gebruiker_has_Quiz_Quiz1_idx` (`quizId` ASC) VISIBLE,
  INDEX `fk_Gebruiker_has_Quiz_Gebruiker1_idx` (`gebruikerId` ASC) VISIBLE,
  PRIMARY KEY (`datumTime`, `gebruikerId`, `quizId`),
  CONSTRAINT `fk_Gebruiker_has_Quiz_Gebruiker1`
    FOREIGN KEY (`gebruikerId`)
    REFERENCES `QuizMaster`.`Gebruiker` (`gebruikerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Gebruiker_has_Quiz_Quiz1`
    FOREIGN KEY (`quizId`)
    REFERENCES `QuizMaster`.`Quiz` (`quizId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QuizMaster`.`Groepsindeling`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizMaster`.`Groepsindeling` (
  `groepId` INT NOT NULL,
  `gebruikerId` VARCHAR(12) NOT NULL,
  INDEX `fk_Groep_has_Gebruiker_Gebruiker2_idx` (`gebruikerId` ASC) VISIBLE,
  INDEX `fk_Groep_has_Gebruiker_Groep1_idx` (`groepId` ASC) VISIBLE,
  PRIMARY KEY (`groepId`, `gebruikerId`),
  CONSTRAINT `fk_Groep_has_Gebruiker_Groep1`
    FOREIGN KEY (`groepId`)
    REFERENCES `QuizMaster`.`Groep` (`groepId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Groep_has_Gebruiker_Gebruiker2`
    FOREIGN KEY (`gebruikerId`)
    REFERENCES `QuizMaster`.`Gebruiker` (`gebruikerId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
