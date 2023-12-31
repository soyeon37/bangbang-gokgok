-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ssafyweb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafyweb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `enjoytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `enjoytrip` ;

-- -----------------------------------------------------
-- Table `ssafyweb`.`members`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enjoytrip`.`members` ;

CREATE TABLE IF NOT EXISTS `enjoytrip`.`members` (
  `user_id` VARCHAR(16) NOT NULL,
  `user_name` VARCHAR(20) NOT NULL,
  `user_password` VARCHAR(100) NOT NULL,
  `email_id` VARCHAR(20) NULL DEFAULT NULL,
  `email_domain` VARCHAR(45) NULL DEFAULT NULL,
  `join_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `trip_cnt` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

insert into `enjoytrip`.`members` (user_id, user_name, user_password, email_id, email_domain, join_date)
values 	('ssafy', '김싸피', '1234', 'ssafy', 'ssafy.com', now()), 
		('admin', '관리자', '1234', 'admin', 'google.com', now());
	
commit;

-- -----------------------------------------------------
-- Table `ssafyweb`.`trip`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enjoytrip`.`trip` ;

CREATE TABLE IF NOT EXISTS `enjoytrip`.`trip` (
  `trip_id` INT NOT NULL AUTO_INCREMENT,
  `trip_no` INT NOT NULL DEFAULT 0,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `content_id` INT NOT NULL,
  `memo` VARCHAR(2000) NULL DEFAULT NULL,
  PRIMARY KEY (`trip_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS enjoytrip.notice (
  article_no INT NOT NULL AUTO_INCREMENT,
  user_id VARCHAR(16) NULL DEFAULT NULL,
  subject VARCHAR(100) NULL DEFAULT NULL,
  content VARCHAR(2000) NULL DEFAULT NULL,
  hit INT NULL DEFAULT 0,
  register_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (article_no),
  INDEX notice_to_members_user_id_fk (user_id ASC) VISIBLE,
  CONSTRAINT notice_to_members_user_id_fk
    FOREIGN KEY (user_id)
    REFERENCES enjoytrip.members (user_id))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS enjoytrip.board (
  article_no INT NOT NULL AUTO_INCREMENT,
  user_id VARCHAR(16) NULL DEFAULT NULL,
  subject VARCHAR(100) NULL DEFAULT NULL,
  content VARCHAR(2000) NULL DEFAULT NULL,
  hit INT NULL DEFAULT 0,
  register_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (article_no),
  INDEX board_to_members_user_id_fk (user_id ASC) VISIBLE,
  CONSTRAINT board_to_members_user_id_fk
    FOREIGN KEY (user_id)
    REFERENCES enjoytrip.members (user_id))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
