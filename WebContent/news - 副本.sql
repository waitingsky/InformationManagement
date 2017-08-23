
CREATE TABLE `sys_portalconfig` (
  `config_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `content` text,
  `options` text,
  `update_time` datetime,
  PRIMARY KEY (`configId`)

) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;




CREATE TABLE `t_news` (
  `newsId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(40) DEFAULT NULL,
  `content` text,
  `publishDate` datetime DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL,
  `click` int(11) DEFAULT NULL,
  `isHead` tinyint(1) DEFAULT NULL,
  `isImage` tinyint(4) DEFAULT NULL,
  `imageName` varchar(20) DEFAULT NULL,
  `isHot` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`newsId`),
  KEY `FK_t_news` (`typeId`),
  CONSTRAINT `FK_t_news` FOREIGN KEY (`typeId`) REFERENCES `t_newstype` (`newsTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;
