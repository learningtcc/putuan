/*
Navicat MySQL Data Transfer

Source Server         : 152
Source Server Version : 50546
Source Host           : 120.24.16.152:3306
Source Database       : putuan

Target Server Type    : MYSQL
Target Server Version : 50546
File Encoding         : 65001

Date: 2016-11-15 10:50:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `nick_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='--------------管理员账号表';

-- ----------------------------
-- Table structure for base_kneel_info
-- ----------------------------
DROP TABLE IF EXISTS `base_kneel_info`;
CREATE TABLE `base_kneel_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `device_id` varchar(55) COLLATE utf8mb4_bin DEFAULT '',
  `count` int(11) DEFAULT NULL,
  `time` datetime DEFAULT '1970-01-01 00:00:00',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(11) COLLATE utf8mb4_bin DEFAULT NULL,
  `type_code` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '设备类型',
  `device_id` varchar(50) COLLATE utf8mb4_bin DEFAULT '',
  `device_number` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '设备编号',
  `qr_ticket` varchar(200) COLLATE utf8mb4_bin DEFAULT '',
  `mac` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `connect_protocol` varchar(10) COLLATE utf8mb4_bin DEFAULT '',
  `auth_key` varchar(50) COLLATE utf8mb4_bin DEFAULT '',
  `close_strategy` varchar(10) COLLATE utf8mb4_bin DEFAULT '',
  `conn_strategy` varchar(10) COLLATE utf8mb4_bin DEFAULT '',
  `crypt_method` varchar(10) COLLATE utf8mb4_bin DEFAULT '',
  `auth_ver` varchar(10) COLLATE utf8mb4_bin DEFAULT '',
  `manu_mac_pos` varchar(10) COLLATE utf8mb4_bin DEFAULT '',
  `ser_mac_pos` varchar(10) COLLATE utf8mb4_bin DEFAULT '',
  `ble_simple_protocol` varchar(10) COLLATE utf8mb4_bin DEFAULT '',
  `update_time` datetime DEFAULT '1970-01-01 00:00:00',
  `create_time` datetime DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_mac` (`mac`) USING BTREE,
  UNIQUE KEY `un_deviceid` (`device_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='--------------设备表';

-- ----------------------------
-- Table structure for device_type
-- ----------------------------
DROP TABLE IF EXISTS `device_type`;
CREATE TABLE `device_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `type_code` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
  `create_time` datetime DEFAULT '1970-01-01 00:00:00',
  `update_time` datetime DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='--------------设备类型表';

-- ----------------------------
-- Table structure for kneel_info
-- ----------------------------
DROP TABLE IF EXISTS `kneel_info`;
CREATE TABLE `kneel_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kneel_count` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `device_id` varchar(50) COLLATE utf8mb4_bin DEFAULT '',
  `freeze` int(11) NOT NULL DEFAULT '0',
  `start_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='--------------跪拜表';

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `product_id` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `name` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `access_type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `type` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `connect_type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `detail` varchar(1000) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `configure_type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `configure` varchar(1000) COLLATE utf8mb4_bin DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='--------------产品表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(50) COLLATE utf8mb4_bin DEFAULT '',
  `account` varchar(50) COLLATE utf8mb4_bin DEFAULT '',
  `password` varchar(32) COLLATE utf8mb4_bin DEFAULT '',
  `nick_name` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `phone` varchar(20) COLLATE utf8mb4_bin DEFAULT '',
  `sex` tinyint(4) DEFAULT '0',
  `age` int(11) DEFAULT NULL,
  `birthday` datetime DEFAULT '1970-01-01 00:00:00',
  `country` varchar(20) COLLATE utf8mb4_bin DEFAULT '',
  `province` varchar(20) COLLATE utf8mb4_bin DEFAULT '',
  `city` varchar(20) COLLATE utf8mb4_bin DEFAULT '',
  `headimg_url` varchar(1000) COLLATE utf8mb4_bin DEFAULT '',
  `language` varchar(10) COLLATE utf8mb4_bin DEFAULT '',
  `remark` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  `user_type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL,
  `historied` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='--------------用户信息表';

-- ----------------------------
-- Table structure for user_device
-- ----------------------------
DROP TABLE IF EXISTS `user_device`;
CREATE TABLE `user_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0',
  `device_id` varchar(50) COLLATE utf8mb4_bin DEFAULT '0',
  `device_using` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `company` varchar(50) COLLATE utf8mb4_bin DEFAULT '',
  `product` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `description` varchar(200) COLLATE utf8mb4_bin DEFAULT '',
  `type` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `url` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `md5` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT 'yyyy-MM-dd hh:mm:ss',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT 'yyyy-MM-dd hh:mm:ss',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='------终端版本';
