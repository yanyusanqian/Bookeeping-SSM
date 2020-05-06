/*
 Navicat Premium Data Transfer

 Source Server         : bendi
 Source Server Type    : MySQL
 Source Server Version : 50045
 Source Host           : localhost:3306
 Source Schema         : bookeeping

 Target Server Type    : MySQL
 Target Server Version : 50045
 File Encoding         : 65001

 Date: 06/05/2020 20:03:31
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `bill_id` int(255) NOT NULL AUTO_INCREMENT,
  `user_id` int(255) NOT NULL DEFAULT '',
  `bill_count` float(255, 2) NOT NULL DEFAULT '',
  `bill_inexType` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
  `bill_detailType` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
  `bill_imgRes` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
  `bill_time` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
  `bill_note` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
  PRIMARY KEY USING BTREE (`bill_id`),
  INDEX `user_id` USING BTREE(`user_id`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = gbk COLLATE = gbk_chinese_ci COMMENT = 'InnoDB free: 10240 kB; (`user_id`) REFER `bookeeping/user`(`user_id`) ON UPDATE ' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `article_id` int(255) NOT NULL AUTO_INCREMENT,
  `user_id` int(255) NOT NULL DEFAULT '',
  `article_content` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL,
  `article_image` text CHARACTER SET gbk COLLATE gbk_chinese_ci NULL,
  `article_time` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
  PRIMARY KEY USING BTREE (`article_id`),
  INDEX `user_id` USING BTREE(`user_id`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = gbk COLLATE = gbk_chinese_ci COMMENT = 'InnoDB free: 10240 kB; (`user_id`) REFER `bookeeping/user`(`user_id`) ON UPDATE ' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for detailtype
-- ----------------------------
DROP TABLE IF EXISTS `detailtype`;
CREATE TABLE `detailtype`  (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '',
  `type_text` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
  `type_img` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
  PRIMARY KEY USING BTREE (`type_id`),
  INDEX `user_id` USING BTREE(`user_id`),
  CONSTRAINT `detailtype_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = gbk COLLATE = gbk_chinese_ci COMMENT = 'InnoDB free: 10240 kB; (`user_id`) REFER `bookeeping/user`(`user_id`) ON UPDATE ' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `reply_id` int(255) NOT NULL AUTO_INCREMENT,
  `article_id` int(255) NULL DEFAULT NULL,
  `reply_user_id` int(255) NOT NULL DEFAULT '',
  `reply_to_user_id` int(255) NOT NULL DEFAULT '',
  `reply_content` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
  PRIMARY KEY USING BTREE (`reply_id`),
  INDEX `reply_user_id` USING BTREE(`reply_user_id`),
  INDEX `article_id` USING BTREE(`article_id`),
  CONSTRAINT `reply_ibfk_3` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reply_ibfk_2` FOREIGN KEY (`reply_user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = gbk COLLATE = gbk_chinese_ci COMMENT = 'InnoDB free: 10240 kB; (`article_id`) REFER `bookeeping/article`(`article_id`) O' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(255) NOT NULL AUTO_INCREMENT,
  `user_phone` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
  `user_password` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '',
  `user_nikename` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL,
  `user_image` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL,
  PRIMARY KEY USING BTREE (`user_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
