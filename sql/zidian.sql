CREATE DATABASE `zidian` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
-- zidian.`data` definition

CREATE TABLE `data` (
                        `english` varchar(255) NOT NULL,
                        `chinese` varchar(255) NOT NULL,
                        `liju` varchar(6000) DEFAULT NULL,
                        PRIMARY KEY (`english`,`chinese`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;