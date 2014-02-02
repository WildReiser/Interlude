CREATE TABLE IF NOT EXISTS `accounts` (
  `login` VARCHAR(45) NOT NULL DEFAULT '',
  `password` VARCHAR(45),
  `lastactive` DECIMAL(20),
  `access_level` INT(3) NOT NULL DEFAULT 0,
  `lastServer` INT(4) DEFAULT 1,
  `l2answer` varchar(100) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `l2question` varchar(100) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `l2email` varchar(100) NOT NULL DEFAULT 'null@null',
  PRIMARY KEY (`login`)
);
