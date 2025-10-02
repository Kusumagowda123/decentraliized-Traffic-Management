/*
MySQL Data Transfer
Source Host: localhost
Source Database: trustdb
Target Host: localhost
Target Database: trustdb
Date: 09-02-2025 12:07:56
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for accounts
-- ----------------------------
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
  `user` varchar(20) NOT NULL,
  `acnum` varchar(100) NOT NULL,
  `ackey` varchar(100) NOT NULL,
  PRIMARY KEY  (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for trust_count
-- ----------------------------
DROP TABLE IF EXISTS `trust_count`;
CREATE TABLE `trust_count` (
  `user` varchar(20) NOT NULL,
  `tcount` int(5) NOT NULL,
  PRIMARY KEY  (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_data
-- ----------------------------
DROP TABLE IF EXISTS `user_data`;
CREATE TABLE `user_data` (
  `id` int(5) NOT NULL auto_increment,
  `mob` varchar(11) default NULL,
  `service` varchar(100) default NULL,
  `data` varchar(200) default NULL,
  `lat` varchar(20) default NULL,
  `lon` varchar(20) default NULL,
  `udate` varchar(40) default NULL,
  `status` varchar(20) default NULL,
  `vote_count` int(5) default NULL,
  `agree_count` int(5) default NULL,
  `deny_count` int(5) default NULL,
  `area` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_details
-- ----------------------------
DROP TABLE IF EXISTS `user_details`;
CREATE TABLE `user_details` (
  `name` varchar(30) default NULL,
  `password` varchar(20) default NULL,
  `gender` varchar(10) default NULL,
  `email` varchar(40) default NULL,
  `mob` varchar(11) NOT NULL,
  `profession` varchar(20) default NULL,
  `tdate` varchar(30) default NULL,
  PRIMARY KEY  (`mob`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_exp
-- ----------------------------
DROP TABLE IF EXISTS `user_exp`;
CREATE TABLE `user_exp` (
  `user` varchar(20) NOT NULL,
  `exp` int(5) NOT NULL,
  PRIMARY KEY  (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_interests
-- ----------------------------
DROP TABLE IF EXISTS `user_interests`;
CREATE TABLE `user_interests` (
  `mob` varchar(11) default NULL,
  `interests` varchar(100) default NULL,
  `status` varchar(5) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_location
-- ----------------------------
DROP TABLE IF EXISTS `user_location`;
CREATE TABLE `user_location` (
  `mob` varchar(11) NOT NULL,
  `lat` varchar(20) default NULL,
  `lon` varchar(20) default NULL,
  `udate` varchar(40) default NULL,
  PRIMARY KEY  (`mob`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_opinion
-- ----------------------------
DROP TABLE IF EXISTS `user_opinion`;
CREATE TABLE `user_opinion` (
  `vid` int(10) NOT NULL auto_increment,
  `user` varchar(20) default NULL,
  `id` varchar(10) default NULL,
  `opinion` varchar(20) default NULL,
  `lat` varchar(20) default NULL,
  `lon` varchar(20) default NULL,
  `vdate` varchar(40) default NULL,
  PRIMARY KEY  (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_rep
-- ----------------------------
DROP TABLE IF EXISTS `user_rep`;
CREATE TABLE `user_rep` (
  `user` varchar(20) NOT NULL,
  `pos_rep` int(5) NOT NULL,
  `neg_rep` int(5) NOT NULL,
  PRIMARY KEY  (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_reputation
-- ----------------------------
DROP TABLE IF EXISTS `user_reputation`;
CREATE TABLE `user_reputation` (
  `user` varchar(40) NOT NULL,
  `reputation` int(5) NOT NULL,
  PRIMARY KEY  (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_rewards
-- ----------------------------
DROP TABLE IF EXISTS `user_rewards`;
CREATE TABLE `user_rewards` (
  `user` varchar(20) NOT NULL,
  `rewards` int(10) NOT NULL,
  PRIMARY KEY  (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `accounts` VALUES ('9886832434', '0xBEab361b96e39AA4d5DB7E2879571FFBE234675E', '2bb6319f39a422ab857bf22fc0e6cbff33e9a663fd7c7e1665368a0d7c549985');
INSERT INTO `accounts` VALUES ('9988776644', '0xB708ac89224C9Be0E765946104A9Ad27Cf4b60bd', '9398eae5086c5e4617a13c816c986d1120bd9d50b6f02b4e2a7ae1dbc174aa6f');
INSERT INTO `trust_count` VALUES ('7892044926', '10');
INSERT INTO `trust_count` VALUES ('8095286693', '0');
INSERT INTO `trust_count` VALUES ('8800880088', '0');
INSERT INTO `trust_count` VALUES ('8899665544', '0');
INSERT INTO `trust_count` VALUES ('9481976437', '0');
INSERT INTO `trust_count` VALUES ('9886832434', '0');
INSERT INTO `trust_count` VALUES ('9988776633', '0');
INSERT INTO `trust_count` VALUES ('9988776644', '0');
INSERT INTO `trust_count` VALUES ('9988776655', '0');
INSERT INTO `user_data` VALUES ('1', '7892044926', 'Traffic', 'huge traffic', '12.8635534', '77.6217104', '2025-02-07 08:25:22', 'Pending', '0', '0', '0', null);
INSERT INTO `user_data` VALUES ('2', '7892044926', 'Traffic', 'huge traffic', '12.916724', '77.6007061', '2025-02-07 13:52:03', 'Approved', '1', '1', '0', null);
INSERT INTO `user_data` VALUES ('3', '7892044926', 'Traffic', 'okay traffic', '12.9167575', '77.6007069', '2025-02-07 18:39:08', 'Approved', '5', '5', '0', null);
INSERT INTO `user_data` VALUES ('4', '7892044926', 'Transport', 'huge', '12.8635563', '77.6217187', '2025-02-09 09:11:06', 'Pending', '0', '0', '0', 'Jayadeva');
INSERT INTO `user_data` VALUES ('5', '7892044926', 'Transport', 'huge', '12.8635563', '77.6217187', '2025-02-09 09:11:06', 'Pending', '0', '0', '0', 'Jayadeva');
INSERT INTO `user_data` VALUES ('6', '7892044926', 'Transport', 'huge', '12.8635563', '77.6217187', '2025-02-09 09:11:52', 'Approved', '0', '0', '0', 'Jayadeva');
INSERT INTO `user_data` VALUES ('7', '8095286693', 'Traffic', 'less', '13.00917564646786', '77.55150178320086', '2025-02-09 09:26:17', 'Pending', '0', '0', '0', 'JP Nagar');
INSERT INTO `user_details` VALUES ('sumit', '123456', 'Male', 'sumit@gmail.com', '7892044926', 'IT Professional', '04-09-2020');
INSERT INTO `user_details` VALUES ('sam', '123456', 'Male', 'sam@gmail.com', '8095286693', 'Tourist Guide', '11-09-2020');
INSERT INTO `user_details` VALUES ('jones', 'jones1', 'Male', 'www.joneslejo', '8310179931', 'IT Professional', '07-02-2025');
INSERT INTO `user_details` VALUES ('shravya', '123456', 'Female', 'shravya@gmail.com', '8800880088', 'Doctor', '27-07-2021');
INSERT INTO `user_details` VALUES ('anushree', '123456', 'Female', 'anu@gmail.com', '8899665544', 'Teacher', '15-09-2020');
INSERT INTO `user_details` VALUES ('aarohi', '123456', 'Female', 'aarohi@gmail.com', '9481976437', 'Doctor', '24-10-2020');
INSERT INTO `user_details` VALUES ('hrishi', '123456', 'Male', 'hrishi@gmail.com', '9886832434', 'IT Professional', '11-09-2020');
INSERT INTO `user_details` VALUES ('roja', '123456', 'Female', 'roja123@gmail.com', '9988776633', 'IT Professional', '15-09-2020');
INSERT INTO `user_details` VALUES ('trupti', '123456', 'Female', 'trupti@gmail.com', '9988776644', 'Tourist Guide', '12-09-2020');
INSERT INTO `user_details` VALUES ('avi', '123456', 'Male', 'avi@gmail.com', '9988776655', 'Teacher', '11-09-2020');
INSERT INTO `user_exp` VALUES ('7892044926', '0');
INSERT INTO `user_exp` VALUES ('8095286693', '0');
INSERT INTO `user_exp` VALUES ('8310179931', '0');
INSERT INTO `user_exp` VALUES ('8800880088', '0');
INSERT INTO `user_exp` VALUES ('8899665544', '6');
INSERT INTO `user_exp` VALUES ('9481976437', '0');
INSERT INTO `user_exp` VALUES ('9886832434', '0');
INSERT INTO `user_exp` VALUES ('9988776633', '0');
INSERT INTO `user_exp` VALUES ('9988776644', '0');
INSERT INTO `user_exp` VALUES ('9988776655', '0');
INSERT INTO `user_interests` VALUES ('7892044926', 'Transport', '1');
INSERT INTO `user_interests` VALUES ('9988776633', 'Healthcare', '1');
INSERT INTO `user_interests` VALUES ('8899665544', 'Environment', '1');
INSERT INTO `user_interests` VALUES ('8899665544', 'Healthcare', '1');
INSERT INTO `user_interests` VALUES ('7892044926', 'Traffic', '1');
INSERT INTO `user_interests` VALUES ('7892044926', 'Healthcare', '1');
INSERT INTO `user_interests` VALUES ('8095286693', 'Traffic', '1');
INSERT INTO `user_interests` VALUES ('8095286693', 'Select', '1');
INSERT INTO `user_interests` VALUES ('8095286693', 'Job', '1');
INSERT INTO `user_interests` VALUES ('7892044926', 'Select', '1');
INSERT INTO `user_interests` VALUES ('9481916434', 'Traffic', '0');
INSERT INTO `user_interests` VALUES ('7892044926', 'Job', '1');
INSERT INTO `user_interests` VALUES ('9481916434', 'Job', '1');
INSERT INTO `user_interests` VALUES ('9481916434', 'Healthcare', '1');
INSERT INTO `user_interests` VALUES ('9988776644', 'Traffic', '1');
INSERT INTO `user_interests` VALUES ('9988776644', 'Healthcare', '1');
INSERT INTO `user_interests` VALUES ('9988776644', 'Select', '0');
INSERT INTO `user_interests` VALUES ('8800880088', 'Select', '1');
INSERT INTO `user_interests` VALUES ('8800880088', 'Traffic', '1');
INSERT INTO `user_interests` VALUES ('9886832434', 'Select', '1');
INSERT INTO `user_interests` VALUES ('9886832434', 'Traffic', '1');
INSERT INTO `user_location` VALUES ('7892044926', '12.8635473', '77.6217188', '09-02-2025 00:50:04');
INSERT INTO `user_location` VALUES ('8095286693', '12.916630031345406', '77.60052897650245', '07-02-2025 18:42:59');
INSERT INTO `user_location` VALUES ('8310179931', '12.916648555235579', '77.60061862813231', '07-02-2025 18:41:39');
INSERT INTO `user_location` VALUES ('8800880088', '12.916608824492249', '77.60054988713614', '07-02-2025 17:39:25');
INSERT INTO `user_location` VALUES ('8899665544', '12.91647552538455', '77.60044060999529', '07-02-2025 17:45:37');
INSERT INTO `user_location` VALUES ('9481976437', '12.91647789077759', '77.60052131222537', '07-02-2025 17:46:40');
INSERT INTO `user_location` VALUES ('9886832434', '12.916458507041483', '77.60059215376026', '07-02-2025 19:38:11');
INSERT INTO `user_location` VALUES ('9988776644', '12.916718807841809', '77.60064336648409', '07-02-2025 18:53:13');
INSERT INTO `user_location` VALUES ('9988776655', '12.916728765978077', '77.60064034809061', '07-02-2025 18:45:13');
INSERT INTO `user_opinion` VALUES ('1', '8310179931', '2', 'Agree', '12.916760253875703', '77.60020098722117', '2025-02-07 18:35:04');
INSERT INTO `user_opinion` VALUES ('2', '8310179931', '3', 'Agree', '12.9167575', '77.6007069', '2025-02-07 18:40:42');
INSERT INTO `user_opinion` VALUES ('3', '8095286693', '3', 'Deny', '12.9167344', '77.6007097', '2025-02-07 18:42:53');
INSERT INTO `user_opinion` VALUES ('5', '9988776655', '3', 'Agree', '12.9167344', '77.6007097', '2025-02-07 18:44:15');
INSERT INTO `user_opinion` VALUES ('6', '9988776644', '3', 'Agree', '12.9167344', '77.6007097', '2025-02-07 18:45:36');
INSERT INTO `user_opinion` VALUES ('12', '9886832434', '3', 'Agree', '12.9167604', '77.6007104', '2025-02-07 19:37:25');
INSERT INTO `user_rep` VALUES ('7892044926', '0', '0');
INSERT INTO `user_rep` VALUES ('8095286693', '0', '0');
INSERT INTO `user_rep` VALUES ('8310179931', '7', '7');
INSERT INTO `user_rep` VALUES ('8800880088', '0', '0');
INSERT INTO `user_rep` VALUES ('8899665544', '0', '0');
INSERT INTO `user_rep` VALUES ('9481976437', '0', '0');
INSERT INTO `user_rep` VALUES ('9886832434', '7', '0');
INSERT INTO `user_rep` VALUES ('9988776633', '0', '0');
INSERT INTO `user_rep` VALUES ('9988776644', '7', '0');
INSERT INTO `user_rep` VALUES ('9988776655', '7', '0');
INSERT INTO `user_reputation` VALUES ('7892044926', '2');
INSERT INTO `user_reputation` VALUES ('8095286693', '2');
INSERT INTO `user_rewards` VALUES ('7892044926', '0');
INSERT INTO `user_rewards` VALUES ('8095286693', '-6');
INSERT INTO `user_rewards` VALUES ('8310179931', '7');
INSERT INTO `user_rewards` VALUES ('8800880088', '1');
INSERT INTO `user_rewards` VALUES ('8899665544', '1');
INSERT INTO `user_rewards` VALUES ('9481976437', '-1');
INSERT INTO `user_rewards` VALUES ('9886832434', '8');
INSERT INTO `user_rewards` VALUES ('9988776633', '0');
INSERT INTO `user_rewards` VALUES ('9988776644', '7');
INSERT INTO `user_rewards` VALUES ('9988776655', '7');
