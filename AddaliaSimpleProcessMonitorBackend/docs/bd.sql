DROP DATABASE IF EXISTS db_simpleprocessmonitor;
CREATE DATABASE db_simpleprocessmonitor CHARACTER SET utf8 COLLATE utf8_general_ci;
USE db_simpleprocessmonitor;

-- CREAR BASE DE DATOS Y TABLAS

-- USERS TABLE
CREATE TABLE `db_simpleprocessmonitor`.`users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  `last_access` datetime DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `phones` varchar(45) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USERS__EMAIL` (`email`),
  UNIQUE KEY `UK_USERS__USERNAME` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ROLES TABLE
CREATE TABLE `db_simpleprocessmonitor`.`roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ROLES__NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- USERS-ROLES - PIVOT TABLE
CREATE TABLE `db_simpleprocessmonitor`.`users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_USERS_ROLES__USER_ID__ROLE_ID` (`user_id`,`role_id`),
  KEY `FK_USERS_ROLES__ROLE_ID` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- admin user pass: admin    	>ROLES: ADMIN & USER
-- addalia user pass: addalia	>ROLES: USER
INSERT INTO `db_simpleprocessmonitor`.`users` VALUES (1,'2023-12-05 11:19:59','admin@addalia.com',1,'2023-12-05 11:19:59','Administrator','Addalia','$2a$10$Uo5W6BDwuBncmQVhxGiNA.DHE75neiYGA47gsZYSI4MRcythG1xUK','224234','2023-12-05 11:19:59','admin'),(2,'2023-12-05 11:19:59','usuario@addalia.com',1,'2023-12-05 11:19:59','User','Addalia','$2a$10$4.CX97hi3/pUgtxMf6xIVOYa.kRWzxm3hjwD/OSzeeMCG6TRjxiiO','234234','2023-12-05 11:19:59','usuario');
INSERT INTO `db_simpleprocessmonitor`.`roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
INSERT INTO `db_simpleprocessmonitor`.`users_roles` VALUES (1,1),(1,2),(2,2);