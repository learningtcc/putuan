/*
Navicat MySQL Data Transfer

Source Server         : 152
Source Server Version : 50546
Source Host           : 120.24.16.152:3306
Source Database       : putuan

Target Server Type    : MYSQL
Target Server Version : 50546
File Encoding         : 65001

Date: 2017-02-10 09:38:59
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='--------------管理员账号表';

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', 'advanpro', '888888', '安润普', '1970-01-01 00:00:00', '1970-01-01 00:00:00');

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=COMPACT COMMENT='--------------基本跪拜表';

-- ----------------------------
-- Records of base_kneel_info
-- ----------------------------
INSERT INTO `base_kneel_info` VALUES ('1', '80', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-05 15:09:32', '2016-12-05 15:09:32', '2016-12-05 15:09:32');
INSERT INTO `base_kneel_info` VALUES ('3', '80', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-07 10:38:02', '2016-12-07 10:38:02', '2016-12-07 10:38:02');
INSERT INTO `base_kneel_info` VALUES ('4', '61', 'gh_34db4e9151de_c03c39cee7eec4f1', '54', '2016-12-07 17:17:52', '2016-12-07 17:17:52', '2016-12-07 17:40:18');
INSERT INTO `base_kneel_info` VALUES ('5', '61', 'gh_34db4e9151de_c03c39cee7eec4f1', '7', '2016-12-08 14:54:21', '2016-12-08 14:54:21', '2016-12-08 14:56:21');
INSERT INTO `base_kneel_info` VALUES ('6', '105', 'gh_34db4e9151de_c03c39cee7eec4f1', '14', '2016-12-14 09:42:24', '2016-12-14 09:42:24', '2016-12-14 09:42:24');
INSERT INTO `base_kneel_info` VALUES ('7', '80', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-14 09:59:39', '2016-12-14 09:59:39', '2016-12-14 09:59:39');
INSERT INTO `base_kneel_info` VALUES ('8', '61', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-14 10:19:26', '2016-12-14 10:19:26', '2016-12-14 10:19:26');
INSERT INTO `base_kneel_info` VALUES ('9', '80', 'gh_34db4e9151de_ec1c2def8681cbd7', '11', '2017-02-08 14:18:00', '2017-02-08 14:18:00', '2017-02-08 14:18:00');
INSERT INTO `base_kneel_info` VALUES ('10', '112', 'gh_34db4e9151de_ec1c2def8681cbd7', '12', '2017-02-08 17:36:58', '2017-02-08 17:36:59', '2017-02-08 17:42:00');

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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='--------------设备表';

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('12', '25388', 'PT', 'gh_34db4e9151de_0d76d5b85b67ae29', 'PT2016111403052432445', 'http://we.qq.com/d/AQBBHX-Au_I3tyqRevm-pb_Ka0AyaAgbQFaixIuB', 'C6:01:93:D1:47:E1', '3', '', '1', '5', '0', '0', '-1', '-2', null, '1970-01-01 00:00:00', '2016-11-14 15:06:30');
INSERT INTO `device` VALUES ('13', '25388', 'PT', 'gh_34db4e9151de_021e6037c20ec7af', 'PT2016111403354093918', 'http://we.qq.com/d/AQBBHX-A7GlMoacEb0VbbZdLohO5mTDWXpfUNVLz', '12:3C:33:3C:11:26', '3', '', '1', '5', '0', '0', '-1', '-2', null, '1970-01-01 00:00:00', '2016-11-14 15:35:40');
INSERT INTO `device` VALUES ('14', '25388', 'PT', 'gh_34db4e9151de_c03c39cee7eec4f1', 'PT2016111403430937089', 'http://we.qq.com/d/AQBBHX-ACAyh8g5ie--1GnTz4h73a2g-wsi_URSz', 'F2:3A:7A:89:72:8D', '3', '', '1', '5', '0', '0', '-1', '-2', null, '1970-01-01 00:00:00', '2016-11-14 15:43:09');
INSERT INTO `device` VALUES ('15', '25388', 'PT', 'gh_34db4e9151de_e5e8bf3b45a7426e', 'PT2016111404024548530', 'http://we.qq.com/d/AQBBHX-AOgea2XFDU0o16tSJREqBkMhoZ5qb06cm', 'CE:93:A5:9F:B4:FB', '3', '', '1', '5', '0', '0', '-1', '-2', null, '1970-01-01 00:00:00', '2016-11-14 16:02:45');
INSERT INTO `device` VALUES ('16', '25388', 'PT', 'gh_34db4e9151de_3c3684cb57466c5a', 'PT2016111404110456667', 'http://we.qq.com/d/AQBBHX-AHQJ8lHszku-sl7-Nj_GBeV50QJf1Vr14', 'FB:12:3C:55:E5:C6', '3', '', '1', '5', '0', '0', '-1', '-2', null, '1970-01-01 00:00:00', '2016-11-14 16:11:05');
INSERT INTO `device` VALUES ('17', '25388', 'PT', 'gh_34db4e9151de_c8c5301d0ea15882', 'PT2016111404191765210', 'http://we.qq.com/d/AQBBHX-ASVLUvxzH--Q7e8JT43oz9GBhAAnKX0Qx', '58:6E:82:66:58:9D', '3', '', '1', '5', '0', '0', '-1', '-2', null, '1970-01-01 00:00:00', '2016-11-14 16:19:17');
INSERT INTO `device` VALUES ('42', '25388', 'PT', 'gh_34db4e9151de_3ab33da5e132478c', 'PT2016121201095798818', 'http://we.qq.com/d/AQBBHX-AU0_RijSVUeb8kFyn6tmd0a8hjzfvHpR2', 'D8:9F:7C:D9:23:F4', '3', '', '1', '5', '0', '0', '-1', '-2', null, '1970-01-01 00:00:00', '2016-12-12 13:09:57');
INSERT INTO `device` VALUES ('43', '25388', 'PT', 'gh_34db4e9151de_e19758c5441a46f3', 'PT2016121204112670023', 'http://we.qq.com/d/AQBBHX-AedpKw7HvAPig7sYmILdwKipZY4ZF6E_d', 'DF:6C:89:88:A7:42', '3', '', '1', '5', '0', '0', '-1', '-2', null, '1970-01-01 00:00:00', '2016-12-12 16:11:27');
INSERT INTO `device` VALUES ('46', '25388', 'PT', 'gh_34db4e9151de_ecd832e3b642940c', 'PT2016122302362097907', 'http://we.qq.com/d/AQBBHX-ANa4wb-yrvynd2cebnaMDqLl56tQhCTrQ', 'FA:C7:0D:A5:BF:09', '3', '', '1', '5', '0', '0', '-1', '-2', null, '1970-01-01 00:00:00', '2016-12-23 14:36:21');
INSERT INTO `device` VALUES ('47', '25388', 'PT', 'gh_34db4e9151de_ec1c2def8681cbd7', 'PT2016122603420737500', 'http://we.qq.com/d/AQBBHX-Ayg1cOHVf9avYkxNwpGKbm1BhSnWLNW6F', 'C7:6A:A6:21:04:B3', '3', '', '1', '5', '0', '0', '-1', '-2', null, '1970-01-01 00:00:00', '2016-12-26 15:42:08');
INSERT INTO `device` VALUES ('48', '25388', 'PT', 'gh_34db4e9151de_d4cd442b97ef1775', 'PT2017010501403401291', 'http://we.qq.com/d/AQBBHX-A8jW0S7QWEZ5h_bgssritL-9b1cxhPnC-', 'C4:DF:0A:4D:9E:BF', '3', '', '1', '5', '0', '0', '-1', '-2', null, '1970-01-01 00:00:00', '2017-01-05 13:40:34');

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
-- Records of device_type
-- ----------------------------
INSERT INTO `device_type` VALUES ('1', '智能蒲团', 'PT', '1970-01-01 00:00:00', '2016-09-13 12:45:39');
INSERT INTO `device_type` VALUES ('2', '智能磕垫', 'KD', '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `device_type` VALUES ('3', '智能手套', 'ST', '1970-01-01 00:00:00', '1970-01-01 00:00:00');

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
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='--------------跪拜表';

-- ----------------------------
-- Records of kneel_info
-- ----------------------------
INSERT INTO `kneel_info` VALUES ('1', '3', '40', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-14 00:00:00', '2016-11-14 17:12:10', '2016-11-14 17:12:19');
INSERT INTO `kneel_info` VALUES ('2', '10', '42', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-14 00:00:00', '2016-11-14 17:13:25', '2016-11-14 17:13:55');
INSERT INTO `kneel_info` VALUES ('3', '5', '42', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-11-14 00:00:00', '2016-11-14 17:39:04', '2016-11-14 17:39:04');
INSERT INTO `kneel_info` VALUES ('4', '0', '42', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-15 00:00:00', '2016-11-15 09:21:44', '2016-11-15 09:21:44');
INSERT INTO `kneel_info` VALUES ('5', '69', '42', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-11-15 00:00:00', '2016-11-15 10:17:13', '2016-11-15 16:15:53');
INSERT INTO `kneel_info` VALUES ('6', '10', '59', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-15 00:00:00', '2016-11-15 13:20:03', '2016-11-15 13:21:43');
INSERT INTO `kneel_info` VALUES ('7', '2', '42', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-11-16 00:00:00', '2016-11-16 09:47:50', '2016-11-16 09:47:50');
INSERT INTO `kneel_info` VALUES ('8', '64', '40', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-11-16 00:00:00', '2016-11-16 10:40:37', '2016-11-16 13:41:27');
INSERT INTO `kneel_info` VALUES ('9', '37', '59', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-11-16 00:00:00', '2016-11-16 13:47:17', '2016-11-16 13:51:27');
INSERT INTO `kneel_info` VALUES ('10', '25', '59', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-16 00:00:00', '2016-11-16 13:55:06', '2016-11-16 13:59:36');
INSERT INTO `kneel_info` VALUES ('11', '0', '40', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-16 00:00:00', '2016-11-16 14:57:43', '2016-11-16 14:57:43');
INSERT INTO `kneel_info` VALUES ('12', '0', '40', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-17 00:00:00', '2016-11-17 09:03:23', '2016-11-17 09:03:23');
INSERT INTO `kneel_info` VALUES ('13', '0', '40', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-18 00:00:00', '2016-11-18 09:02:47', '2016-11-18 09:02:47');
INSERT INTO `kneel_info` VALUES ('14', '0', '40', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-21 00:00:00', '2016-11-21 13:32:32', '2016-11-21 13:32:32');
INSERT INTO `kneel_info` VALUES ('15', '84', '40', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-22 00:00:00', '2016-11-22 09:14:17', '2016-11-22 17:48:17');
INSERT INTO `kneel_info` VALUES ('16', '0', '59', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-22 00:00:00', '2016-11-22 09:23:25', '2016-11-22 09:23:25');
INSERT INTO `kneel_info` VALUES ('17', '378', '42', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-22 00:00:00', '2016-11-22 12:04:22', '2016-11-22 15:41:53');
INSERT INTO `kneel_info` VALUES ('18', '2', '65', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-22 00:00:00', '2016-11-22 12:24:43', '2016-11-22 12:24:43');
INSERT INTO `kneel_info` VALUES ('19', '8', '47', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-22 00:00:00', '2016-11-22 15:47:45', '2016-11-22 15:47:45');
INSERT INTO `kneel_info` VALUES ('20', '71', '40', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-23 00:00:00', '2016-11-23 08:43:50', '2016-11-23 15:17:47');
INSERT INTO `kneel_info` VALUES ('21', '15', '59', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-23 00:00:00', '2016-11-23 10:15:47', '2016-11-23 13:21:54');
INSERT INTO `kneel_info` VALUES ('22', '2', '40', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-11-23 00:00:00', '2016-11-23 15:30:57', '2016-11-23 15:30:57');
INSERT INTO `kneel_info` VALUES ('23', '0', '40', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-24 00:00:00', '2016-11-24 09:27:33', '2016-11-24 09:27:33');
INSERT INTO `kneel_info` VALUES ('24', '0', '40', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-11-24 00:00:00', '2016-11-24 15:39:28', '2016-11-24 15:39:28');
INSERT INTO `kneel_info` VALUES ('25', '134', '65', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-30 00:00:00', '2016-11-30 13:04:27', '2016-11-30 18:12:39');
INSERT INTO `kneel_info` VALUES ('26', '5', '40', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-01 00:00:00', '2016-12-01 11:48:19', '2016-12-01 14:50:26');
INSERT INTO `kneel_info` VALUES ('27', '55', '40', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-02 00:00:00', '2016-12-02 14:35:27', '2016-12-02 14:56:51');
INSERT INTO `kneel_info` VALUES ('28', '0', '80', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-05 15:09:32', '2016-12-05 15:09:32', '2016-12-05 15:09:32');
INSERT INTO `kneel_info` VALUES ('29', '0', '80', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-07 10:38:02', '2016-12-07 10:38:02', '2016-12-07 10:38:02');
INSERT INTO `kneel_info` VALUES ('30', '211', '65', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-07 00:00:00', '2016-12-07 13:26:19', '2016-12-07 17:03:13');
INSERT INTO `kneel_info` VALUES ('31', '54', '61', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-07 17:17:52', '2016-12-07 17:17:52', '2016-12-07 17:40:18');
INSERT INTO `kneel_info` VALUES ('32', '7', '61', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-08 14:54:21', '2016-12-08 14:54:21', '2016-12-08 14:56:21');
INSERT INTO `kneel_info` VALUES ('33', '12', '40', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-08 00:00:00', '2016-12-08 17:26:40', '2016-12-08 17:36:41');
INSERT INTO `kneel_info` VALUES ('34', '1', '65', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-08 00:00:00', '2016-12-08 18:18:59', '2016-12-08 18:18:59');
INSERT INTO `kneel_info` VALUES ('35', '2', '65', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-08 00:00:00', '2016-12-09 08:49:39', '2016-12-09 08:49:39');
INSERT INTO `kneel_info` VALUES ('36', '29', '65', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-09 00:00:00', '2016-12-09 08:49:39', '2016-12-09 16:06:58');
INSERT INTO `kneel_info` VALUES ('37', '8', '65', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-12 00:00:00', '2016-12-12 11:32:54', '2016-12-12 17:21:39');
INSERT INTO `kneel_info` VALUES ('38', '14', '59', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2016-12-12 00:00:00', '2016-12-12 13:17:02', '2016-12-12 14:04:29');
INSERT INTO `kneel_info` VALUES ('39', '9', '59', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2016-12-12 00:00:00', '2016-12-12 17:51:40', '2016-12-12 17:56:20');
INSERT INTO `kneel_info` VALUES ('40', '18', '65', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-13 00:00:00', '2016-12-13 14:50:11', '2016-12-13 15:50:07');
INSERT INTO `kneel_info` VALUES ('41', '14', '105', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-14 09:42:24', '2016-12-14 09:42:24', '2016-12-14 09:42:24');
INSERT INTO `kneel_info` VALUES ('42', '0', '80', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-14 09:59:39', '2016-12-14 09:59:39', '2016-12-14 09:59:39');
INSERT INTO `kneel_info` VALUES ('43', '0', '61', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-14 10:19:26', '2016-12-14 10:19:26', '2016-12-14 10:19:26');
INSERT INTO `kneel_info` VALUES ('44', '8', '40', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-14 00:00:00', '2016-12-14 10:36:59', '2016-12-14 11:05:22');
INSERT INTO `kneel_info` VALUES ('45', '0', '59', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2016-12-14 00:00:00', '2016-12-14 11:18:58', '2016-12-14 11:18:58');
INSERT INTO `kneel_info` VALUES ('46', '9', '59', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-14 00:00:00', '2016-12-14 11:23:15', '2016-12-14 11:23:52');
INSERT INTO `kneel_info` VALUES ('47', '0', '96', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2016-12-14 00:00:00', '2016-12-14 19:41:42', '2016-12-14 19:41:42');
INSERT INTO `kneel_info` VALUES ('48', '4', '96', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2016-12-14 00:00:00', '2016-12-14 19:49:01', '2016-12-14 19:49:01');
INSERT INTO `kneel_info` VALUES ('49', '3', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-15 00:00:00', '2016-12-15 13:45:05', '2016-12-15 14:09:42');
INSERT INTO `kneel_info` VALUES ('50', '6', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-16 00:00:00', '2016-12-16 09:04:30', '2016-12-16 12:49:05');
INSERT INTO `kneel_info` VALUES ('51', '35', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-19 00:00:00', '2016-12-19 08:51:50', '2016-12-19 21:21:11');
INSERT INTO `kneel_info` VALUES ('52', '32', '107', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-19 00:00:00', '2016-12-19 17:55:28', '2016-12-19 17:55:28');
INSERT INTO `kneel_info` VALUES ('53', '4', '96', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2016-12-19 00:00:00', '2016-12-19 18:00:50', '2016-12-19 18:00:50');
INSERT INTO `kneel_info` VALUES ('54', '2', '107', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-20 00:00:00', '2016-12-20 08:56:46', '2016-12-20 08:56:46');
INSERT INTO `kneel_info` VALUES ('55', '22', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-20 00:00:00', '2016-12-20 16:03:07', '2016-12-20 16:03:07');
INSERT INTO `kneel_info` VALUES ('56', '23', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-21 00:00:00', '2016-12-21 08:48:52', '2016-12-21 13:51:07');
INSERT INTO `kneel_info` VALUES ('57', '11', '107', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-21 00:00:00', '2016-12-21 14:19:10', '2016-12-21 16:02:50');
INSERT INTO `kneel_info` VALUES ('58', '12', '107', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-21 00:00:00', '2016-12-22 08:57:02', '2016-12-22 08:57:02');
INSERT INTO `kneel_info` VALUES ('59', '18', '107', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-22 00:00:00', '2016-12-22 08:57:06', '2016-12-22 17:44:49');
INSERT INTO `kneel_info` VALUES ('60', '2', '107', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-23 00:00:00', '2016-12-23 08:49:06', '2016-12-23 08:53:04');
INSERT INTO `kneel_info` VALUES ('61', '1', '107', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-26 00:00:00', '2016-12-26 10:18:48', '2016-12-26 10:18:48');
INSERT INTO `kneel_info` VALUES ('62', '408', '96', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2016-12-27 00:00:00', '2016-12-27 14:23:09', '2016-12-27 17:25:39');
INSERT INTO `kneel_info` VALUES ('63', '210', '96', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2016-12-28 00:00:00', '2016-12-28 10:40:47', '2016-12-28 18:00:04');
INSERT INTO `kneel_info` VALUES ('64', '6', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-21 00:00:00', '2016-12-28 15:58:14', '2016-12-28 15:58:14');
INSERT INTO `kneel_info` VALUES ('65', '11', '40', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-28 00:00:00', '2016-12-28 16:01:34', '2016-12-28 16:13:33');
INSERT INTO `kneel_info` VALUES ('66', '260', '96', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2016-12-29 00:00:00', '2016-12-29 08:53:56', '2016-12-29 14:51:43');
INSERT INTO `kneel_info` VALUES ('67', '12', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-29 00:00:00', '2016-12-29 13:43:33', '2016-12-29 13:43:33');
INSERT INTO `kneel_info` VALUES ('68', '0', '102', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2016-12-29 00:00:00', '2016-12-29 16:45:15', '2016-12-29 16:45:15');
INSERT INTO `kneel_info` VALUES ('69', '3', '102', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2016-12-29 00:00:00', '2017-01-05 09:20:35', '2017-01-05 09:20:35');
INSERT INTO `kneel_info` VALUES ('70', '32', '59', 'gh_34db4e9151de_d4cd442b97ef1775', '0', '2017-01-05 00:00:00', '2017-01-05 13:48:38', '2017-01-05 13:56:00');
INSERT INTO `kneel_info` VALUES ('71', '1096', '96', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2017-01-03 00:00:00', '2017-01-05 14:44:44', '2017-01-05 14:44:44');
INSERT INTO `kneel_info` VALUES ('72', '4698', '96', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2017-01-04 00:00:00', '2017-01-05 14:44:45', '2017-01-05 14:44:45');
INSERT INTO `kneel_info` VALUES ('73', '623', '96', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2017-01-05 00:00:00', '2017-01-05 14:44:45', '2017-01-05 18:20:28');
INSERT INTO `kneel_info` VALUES ('74', '55', '96', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2017-01-05 00:00:00', '2017-01-06 10:29:49', '2017-01-06 10:29:49');
INSERT INTO `kneel_info` VALUES ('75', '853', '96', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2017-01-06 00:00:00', '2017-01-06 10:29:49', '2017-01-06 14:30:57');
INSERT INTO `kneel_info` VALUES ('76', '16', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2017-01-10 00:00:00', '2017-01-10 12:37:54', '2017-01-10 12:37:54');
INSERT INTO `kneel_info` VALUES ('77', '71', '96', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2017-01-12 00:00:00', '2017-01-12 10:45:23', '2017-01-12 18:22:21');
INSERT INTO `kneel_info` VALUES ('78', '35', '102', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2017-01-12 00:00:00', '2017-01-12 14:02:58', '2017-01-12 15:38:07');
INSERT INTO `kneel_info` VALUES ('79', '65535', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2017-01-12 00:00:00', '2017-01-12 14:41:06', '2017-01-12 16:51:06');
INSERT INTO `kneel_info` VALUES ('80', '84', '96', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2017-01-12 00:00:00', '2017-01-13 09:44:14', '2017-01-13 09:44:14');
INSERT INTO `kneel_info` VALUES ('81', '191', '96', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2017-01-13 00:00:00', '2017-01-13 09:44:43', '2017-01-13 18:16:13');
INSERT INTO `kneel_info` VALUES ('82', '65537', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2017-01-12 00:00:00', '2017-01-13 16:55:35', '2017-01-13 16:55:35');
INSERT INTO `kneel_info` VALUES ('83', '0', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2017-01-11 00:00:00', '2017-01-13 16:55:36', '2017-01-13 16:55:36');
INSERT INTO `kneel_info` VALUES ('84', '0', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2017-01-13 00:00:00', '2017-01-13 16:55:36', '2017-01-13 16:55:36');
INSERT INTO `kneel_info` VALUES ('85', '65535', '107', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2017-01-16 00:00:00', '2017-01-16 09:38:20', '2017-01-16 09:38:21');
INSERT INTO `kneel_info` VALUES ('86', '5', '108', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2017-02-05 00:00:00', '2017-02-05 09:37:15', '2017-02-05 09:48:37');
INSERT INTO `kneel_info` VALUES ('87', '23', '96', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2017-02-05 00:00:00', '2017-02-05 10:23:36', '2017-02-05 10:23:36');
INSERT INTO `kneel_info` VALUES ('88', '31', '96', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2017-02-06 00:00:00', '2017-02-06 10:57:29', '2017-02-06 14:38:22');
INSERT INTO `kneel_info` VALUES ('89', '63', '59', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 00:00:00', '2017-02-08 13:54:33', '2017-02-08 17:13:54');
INSERT INTO `kneel_info` VALUES ('90', '11', '80', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 14:18:00', '2017-02-08 14:18:00', '2017-02-08 14:18:00');
INSERT INTO `kneel_info` VALUES ('91', '13', '40', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 00:00:00', '2017-02-08 15:12:09', '2017-02-08 17:12:40');
INSERT INTO `kneel_info` VALUES ('92', '4', '86', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 00:00:00', '2017-02-08 16:00:45', '2017-02-08 16:02:40');
INSERT INTO `kneel_info` VALUES ('93', '12', '107', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 00:00:00', '2017-02-08 16:05:16', '2017-02-08 16:08:29');
INSERT INTO `kneel_info` VALUES ('94', '12', '112', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 17:36:58', '2017-02-08 17:36:59', '2017-02-08 17:42:00');
INSERT INTO `kneel_info` VALUES ('95', '43', '96', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2017-02-06 00:00:00', '2017-02-09 10:24:51', '2017-02-09 10:24:51');
INSERT INTO `kneel_info` VALUES ('96', '57', '96', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2017-02-09 00:00:00', '2017-02-09 10:25:52', '2017-02-09 16:06:45');

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
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='--------------用户信息表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('20', 'o0obxvw1_2XEfRtBuOMl-tZQ4CQM', '', '', 'joinly', '', '1', '0', '0000-00-00 00:00:00', '中国', '广东', '广州', 'http://wx.qlogo.cn/mmopen/ajNVdqHZLLBr9RdsZmsI4vEGMiaKdhm3tK33LiaBbBhEm7DN9HgovEImIlhcK6ghtPiaDibnxFaTbuGnAw5ubiayvbicdf1OTaeeSfuDcNqjmfEfk/0', 'zh_CN', '', 'WX', '24', null, '1', '2016-07-11 13:50:59', '2016-07-26 09:51:24');
INSERT INTO `user` VALUES ('21', 'o0obxvw0dLALErAPxt-fcc6VGPbY', '', '', 'liron.net', '', '1', '0', '0000-00-00 00:00:00', '中国', '广东', '珠海', 'http://wx.qlogo.cn/mmopen/YzvQTqyiaI1ahYuGLThoPWRHFj2qicDNCgjiby0BF5td0WVSVtBycAsa226NdMEfdXULXGeQ2EtreicPaYp1WLOOw0Y0jw1Wf164/0', 'zh_CN', '', 'WX', '0', null, '1', '2016-07-11 14:10:18', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('22', 'o0obxvyIZW7cRUchh-53nPCCecJo', '', '', '一只野生狮猿', '', '1', '0', '0000-00-00 00:00:00', '中国', '广东', '珠海', 'http://wx.qlogo.cn/mmopen/vPjficwtgTJB7C3Ssmicxw9Ag3WrHAFdg12bMrtibZemZo2NodzrLRibSFxFmta8IseJzmicFVibqTu8UktlDnn4cCbrpahtm41k39/0', 'zh_CN', '', 'WX', '0', null, '0', '2016-07-11 14:11:18', '2016-09-27 14:56:29');
INSERT INTO `user` VALUES ('24', '', '13750024026', '21218cca77804d2ba1922c33e0151105', 'vance', '', '1', '26', '0000-00-00 00:00:00', '中国', '广东', '珠海', '', 'zh_CN', '', 'APP', '20', null, '1', '2016-09-02 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('25', '', '13750024089', '21218cca77804d2ba1922c33e0151105', 'xiao', '', '1', '25', '0000-00-00 00:00:00', '中国', '广东', '珠海', '', 'zh_CN', '', 'APP', '0', null, '1', '2016-09-08 04:00:00', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('27', '', '13750024088', '21218cca77804d2ba1922c33e0151105', 'test11', '', '2', '43', '0000-00-00 00:00:00', '中国', '广西', '南宁', '', 'zh_CN', '', 'APP', '0', null, '1', '2016-09-14 04:00:00', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('28', '', '13750024080', '21218cca77804d2ba1922c33e0151105', 'test43', '', '2', '34', '0000-00-00 00:00:00', '中国', '北京', '北京', '', 'zh_CN', '', 'APP', '0', null, '1', '2016-09-14 08:00:00', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('29', '', '13750024081', '21218cca77804d2ba1922c33e0151105', 'test111', '', '2', '21', '0000-00-00 00:00:00', '中国', '四川', '成都', '', 'zh_CN', '', 'APP', '0', null, '1', '2016-09-19 03:00:00', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('30', '', '13750024082', '21218cca77804d2ba1922c33e0151105', 'xiaozhiyu', '', '1', '12', '0000-00-00 00:00:00', '中国', '吉林', '', '', 'zh_CN', '', 'APP', '0', null, '1', '2016-09-19 07:00:00', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('31', '', '13750024083', '21218cca77804d2ba1922c33e0151105', 'test', '', '1', '14', '0000-00-00 00:00:00', '中国', '江苏', '南京', '', 'zh_CN', '', 'APP', '0', null, '1', '2016-09-19 12:00:00', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('32', '', '13750024086', '21218cca77804d2ba1922c33e0151105', 'test1112', '', '0', '0', '1970-01-01 08:00:00', '中国', '浙江', '杭州', 'http://futon.ansobuy.cn/futon/20161108/bda24e7dc743422fa009f71c91478257.PNG', 'zh_CN', '', 'APP', '0', '0', '1', '2016-09-19 04:00:00', '2016-11-09 10:13:10');
INSERT INTO `user` VALUES ('39', null, '13750024022', '21218cca77804d2ba1922c33e0151105', 'test1123', '13758824022', '1', '0', '1990-06-24 00:00:00', null, '广东', '珠海', 'http://api.putuan.com/putuan/20160926/312eac6ab37a4cb4850b321ab5fcbabb.PNG', null, null, 'APP', '0', null, '1', '2016-09-21 15:09:41', '2016-09-30 15:20:54');
INSERT INTO `user` VALUES ('40', null, '15989781572', 'e10adc3949ba59abbe56e057f20f883e', '爱情公寓', '15989781574', '2', '0', '1992-01-01 00:00:00', null, '广东', '', 'http://futon.ansobuy.cn/futon/20161124/4d7dae74ca014f66b433ada97e3466b9.jpg', null, null, 'APP', '0', '0', '1', '2016-09-23 17:49:13', '2016-11-28 13:08:06');
INSERT INTO `user` VALUES ('41', null, '15989897815', 'e10adc3949ba59abbe56e057f20f883e', '111111', '', '1', '0', '1930-01-01 00:00:00', null, '海南', '', null, null, null, 'APP', '0', null, '1', '2016-09-26 12:16:20', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('42', null, '15989781577', 'e10adc3949ba59abbe56e057f20f883e', '他们可口可乐了了可口可乐了了可口可乐了了', '15989781572', '2', '0', '1932-01-01 00:00:00', null, '广东', '', 'http://futon.ansobuy.cn/futon/20161116/9fd6e0fa41d541b793b5aa0df28769e9.jpg', null, null, 'APP', '0', '0', '1', '2016-09-27 09:41:37', '2016-11-22 15:36:00');
INSERT INTO `user` VALUES ('43', null, '15989781573', 'e10adc3949ba59abbe56e057f20f883e', '1573', '15989781573', '1', '0', '2008-01-01 00:00:00', null, '广东', '', null, null, null, 'APP', '0', '0', '1', '2016-09-27 10:11:05', '2016-10-18 17:11:55');
INSERT INTO `user` VALUES ('45', null, '15989781571', 'e10adc3949ba59abbe56e057f20f883e', 'gggggh', '', '1', '0', '1992-01-01 00:00:00', null, '广东', '', null, null, null, 'APP', '0', '0', '1', '2016-09-27 13:41:44', '2016-10-18 16:55:48');
INSERT INTO `user` VALUES ('46', null, '15989781570', 'e10adc3949ba59abbe56e057f20f883e', '1570', '15989781572', '1', '0', '1992-01-01 00:00:00', null, '广东', '', null, null, null, 'APP', '0', '1', '1', '2016-09-30 14:15:40', '2016-09-30 14:15:57');
INSERT INTO `user` VALUES ('47', null, '15989781574', 'e10adc3949ba59abbe56e057f20f883e', '1574好好', '15989781577', '2', '0', '1991-01-01 00:00:00', null, '广东', '', null, null, null, 'APP', '0', '0', '1', '2016-09-30 14:22:44', '2016-10-25 17:09:13');
INSERT INTO `user` VALUES ('48', null, '15916201675', 'e10adc3949ba59abbe56e057f20f883e', 'lr', '15916201675', '1', '0', '1985-01-01 00:00:00', null, '湖南', '', 'http://futon.ansobuy.cn/futon/20161203/939464bfed10486dad33e6bbbed5284f.jpg', null, null, 'APP', '66', '0', '1', '2016-09-30 17:43:21', '2016-12-03 15:59:09');
INSERT INTO `user` VALUES ('49', 'oRKRiwc-OFrJMdIjhY4QAp2L7VqY', null, null, '一只野生狮猿', '15521156658', '1', '26', null, '中国', '广东', '珠海', 'http://wx.qlogo.cn/mmopen/5K48YNcpF3Y1p17YUOkpa4jpluY3SZnKt1hhib3Rn1kSPZ8S6mCjE8ZqwNSg0k5J4tRII8JcPkAqBExYNRZUx7H5WmcGFCn6V/0', 'zh_CN', '', 'WX', '0', '1', '1', '2016-10-11 12:41:26', '2016-10-12 14:30:34');
INSERT INTO `user` VALUES ('54', 'o8EPzvvUqrZ-CEFW-N-jw6fMTOIw', null, null, '爱的蒲公英', '15989781572', '0', '0', null, null, null, null, 'http://wx.qlogo.cn/mmopen/zQFS4APNzdShHywGVntEkRFneu9NFiaZjphoebrj0v5YeOBhhJU5nc74icej6QL3sKGU6oEkfh7K75VSXjFVwOlR26AtQ7MM7H/0', null, null, 'WX', '40', '0', '1', '2016-10-14 16:06:47', '2016-11-09 10:04:58');
INSERT INTO `user` VALUES ('55', null, '13926925001', 'e10adc3949ba59abbe56e057f20f883e', 'lucky', '13926925001', '1', '0', '1982-01-01 00:00:00', null, '广东', '', null, null, null, 'APP', '56', '0', '1', '2016-11-03 14:50:33', '2016-11-03 15:03:10');
INSERT INTO `user` VALUES ('56', 'o8EPzvmmSylS92NQ-T_STpL3iHIs', null, null, 'lucky', '13926925001', '0', '0', null, null, null, null, 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM4yoRAMp61wIyddDycjNnJwrSiamtbYMAdBdsBFmWMClpodaWmUsic5GTlBnHXiba82X2HYFgQYgLcAic2UEntYElyxIz7eVAxVZ2A/0', null, null, 'WX', '55', '0', '1', '2016-11-03 14:51:11', '2016-11-03 14:51:11');
INSERT INTO `user` VALUES ('57', 'o8EPzvmmSylS92NQ-T_STpL3iHIs', null, null, 'lucky', '13926925001', '0', '0', null, null, null, null, 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM4yoRAMp61wIyddDycjNnJwrSiamtbYMAdBdsBFmWMClpodaWmUsic5GTlBnHXiba82X2HYFgQYgLcAic2UEntYElyxIz7eVAxVZ2A/0', null, null, 'WX', '0', '0', '1', '2016-11-03 14:51:11', '2016-11-03 14:59:27');
INSERT INTO `user` VALUES ('58', null, '18792669386', 'bab81c3af68d373328fdc6a31c6086f4', 'hardware', '18792669386', '1', '0', '1992-01-01 00:00:00', null, '安徽', '', null, null, null, 'APP', '0', '1', '1', '2016-11-03 14:53:11', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('59', null, '18700596614', 'e10adc3949ba59abbe56e057f20f883e', '惜！', '18700596614', '2', '0', '1992-01-01 00:00:00', null, '辽宁', '', null, null, null, 'APP', '0', '1', '1', '2016-11-03 14:57:18', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('60', null, '13926925028', 'e10adc3949ba59abbe56e057f20f883e', 'ddny', '13926925028', '1', '0', '1982-01-01 00:00:00', null, '广东', '', null, null, null, 'APP', '0', '1', '1', '2016-11-03 15:10:49', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('61', 'oQR1_wJHooZoYl8gMNEPCKJQx9Lo', null, null, '权二先生', null, '1', '26', null, '中国', '广东', '珠海', 'http://wx.qlogo.cn/mmopen/ojZnUD3ibL6KLWNR7Bc7CFKaibrhUEkIlnFyWbkXLQs6oicAzkWMNvuBzibAzedpemOntes3uibCiaMic7LVqX6ibw03icrgRxylfSAgf/0', 'zh_CN', '', 'WX', '0', '0', '1', '2016-11-08 11:10:12', '2016-12-14 16:57:46');
INSERT INTO `user` VALUES ('65', null, '15820592307', 'e10adc3949ba59abbe56e057f20f883e', '大', '15820592307', '2', '0', '1993-01-01 00:00:00', null, '甘肃', '', 'http://futon.ansobuy.cn/futon/20161201/aadf945fd1bd4bda9732390b15a67c31.jpeg', null, null, 'APP', '0', '0', '1', '2016-11-10 16:37:47', '2016-12-07 14:23:01');
INSERT INTO `user` VALUES ('66', 'o8EPzvpCL7c5GlG3itSQikBNS98A', null, null, 'liron.net', '15916201675', '0', '0', null, null, null, null, 'http://wx.qlogo.cn/mmopen/zQFS4APNzdQibs7OATNhuUDzlUcNesZwapyEmLFLUESMTc3kbISuZzY1HcIbBZQw96NlFIGI7uC1PMOHUibib6855sC5n94aUbr/0', null, null, 'WX', '48', '0', '1', '2016-11-23 09:29:23', '2016-11-23 09:29:23');
INSERT INTO `user` VALUES ('67', null, '15820592009', '123456', '123456', '15820592009', '1', '0', '1970-01-01 08:00:01', null, '台湾', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 14:02:25', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('68', null, '15820592398', '123456', 'gggg', '15820592398', '2', '0', '1970-01-01 08:00:01', null, '江苏', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 14:12:07', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('69', null, '15850592308', 'e10adc3949ba59abbe56e057f20f883e', '大象运动', '15850592308', '1', '0', '1970-01-01 08:00:01', null, '安徽', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 14:15:49', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('70', null, '15820592001', 'e10adc3949ba59abbe56e057f20f883e', 'h回哦哦哦哦', '15820592001', '2', '0', '1970-01-01 08:00:01', null, '吉林', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 14:18:25', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('71', null, '15820592002', 'e10adc3949ba59abbe56e057f20f883e', 'y以牙还牙', '15820592002', '2', '0', '1970-01-01 08:00:02', null, '安徽', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 14:22:57', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('72', null, '15850592003', 'e10adc3949ba59abbe56e057f20f883e', 'f发广告', '15850592003', '1', '0', '1970-01-01 08:00:02', null, '台湾', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 14:45:38', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('73', null, '15820592004', 'e10adc3949ba59abbe56e057f20f883e', '哦哦哦哦', '15820592004', '1', '0', '1970-01-01 08:00:02', null, '江苏', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 14:48:48', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('74', null, '15820592005', 'e10adc3949ba59abbe56e057f20f883e', '455', '15820592005', '1', '0', '1970-01-01 08:00:01', null, '北京', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 14:55:50', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('75', null, '15820592006', 'e10adc3949ba59abbe56e057f20f883e', '788888', '15820592006', '1', '0', '1970-01-01 08:00:01', null, '山西', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 15:03:56', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('76', null, '15820592010', 'e10adc3949ba59abbe56e057f20f883e', '规划好', '15820592010', '1', '0', '1970-01-01 08:00:01', null, '贵州', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 15:06:28', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('77', null, '15820592020', 'e10adc3949ba59abbe56e057f20f883e', 'd都刚刚好', '15820592020', '1', '0', '1970-01-01 08:00:01', null, '河南', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 15:08:53', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('78', null, '15820592330', 'e10adc3949ba59abbe56e057f20f883e', '斤斤计较', '15820592330', '1', '0', '1970-01-01 08:00:01', null, '海南', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 15:16:00', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('79', null, '15820592390', 'e10adc3949ba59abbe56e057f20f883e', '京津冀', '15820592390', '2', '0', '1970-01-01 08:00:01', null, '澳门', '', null, null, null, 'APP', '0', '1', '1', '2016-11-24 15:19:22', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('80', 'oQR1_wMaxGw28W35J66x0sol4_Jg', null, null, '舟公', null, '1', '0', null, '中国', '广东', '珠海', 'http://wx.qlogo.cn/mmopen/ojZnUD3ibL6J1CI533qlJdvB9hTCuicRzoULHOSLiaVV5Dju27ECNKFuarnJtVkSmeuicNoyLIaO2YK2zyuuceibUMfbmX88TJvqP/0', 'zh_CN', '', 'WX', '0', '0', '1', '2016-11-25 15:48:19', '2016-12-01 13:47:53');
INSERT INTO `user` VALUES ('81', 'oQR1_wCYQvewt9BowJEg4fOKRVjk', null, null, 'liron.net', null, '1', '0', null, '中国', '广东', '珠海', 'http://wx.qlogo.cn/mmopen/ojZnUD3ibL6KLWNR7Bc7CFBeeMPdAHic2UIniciaickJeSX8kLs9abjA3k4YP5AHLlrSe1GsHiayKX3UIKls9x7nibBSbac9EwqibRyG/0', 'zh_CN', '', 'WX', '0', '1', '1', '2016-11-25 16:07:22', '2016-12-03 15:50:40');
INSERT INTO `user` VALUES ('82', 'oRKRiwblyFcKIsn8isOO_h8JSyG8', null, null, '权二先生', null, '1', '0', null, '中国', '广东', '珠海', 'http://wx.qlogo.cn/mmopen/KmmNJxGYNhibhicwxSlMmUN4VtyZqrusgyxRwbF6EYic0K8MR1b1ia3FEE4dlX7AzaTz1z4Htp8EpO9675o9aySYwtQWC6dOm2hZ/0', 'zh_CN', '', 'WX', '0', '0', '1', '2016-11-25 16:31:27', '2016-11-25 18:10:25');
INSERT INTO `user` VALUES ('83', 'oQR1_wI356wcSFlzNKFtIP4Ib5aU', null, null, '一只野生狮猿', null, '1', '26', null, '中国', '广东', '珠海', 'http://wx.qlogo.cn/mmopen/fFgUJknhibCx4BGAZoZUTIXkSwohgrWX9bSl7yOIRzqAhDzNbyOdx88E3LQdMdPqh4o8Pt3RqHVBcO7MkQicbicWnkZG8uR0cjX/0', 'zh_CN', '', 'WX', '0', '0', '1', '2016-11-29 13:30:37', '2016-12-12 11:14:26');
INSERT INTO `user` VALUES ('84', null, '15820592000', 'e10adc3949ba59abbe56e057f20f883e', '大象运动', '15820592000', '1', '0', '1970-01-01 08:00:01', null, '台湾', '', null, null, null, 'APP', '0', '1', '1', '2016-11-29 16:59:31', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('85', null, '15023658953', 'e10adc3949ba59abbe56e057f20f883e', '在线指导书……这', '15023658953', '2', '0', '1970-01-01 08:00:01', null, '宁夏', '', null, null, null, 'APP', '0', '1', '1', '2016-11-29 17:10:01', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('86', null, '18578226703', 'e10adc3949ba59abbe56e057f20f883e', 'slientstorm', '18578226703', '1', '0', '2600-08-11 13:50:00', null, '广东', '', 'http://futon.ansobuy.cn/futon/20161228/961e1a7b31c7432b8252c9b31394c33c.jpeg', null, null, 'APP', '0', '0', '1', '2016-12-01 09:33:02', '2016-12-28 16:03:44');
INSERT INTO `user` VALUES ('87', null, '15824536523', 'e10adc3949ba59abbe56e057f20f883e', 't吞吞吐吐', '15824536523', '1', '0', '1970-01-01 08:00:02', null, '天津', '', null, null, null, 'APP', '0', '1', '1', '2016-12-01 10:57:20', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('88', null, '15823562541', 'e10adc3949ba59abbe56e057f20f883e', '这么大半', '15823562541', '2', '0', '1970-01-01 08:00:01', null, '北京', '', null, null, null, 'APP', '0', '1', '1', '2016-12-01 11:01:46', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('89', null, '15820592586', 'e10adc3949ba59abbe56e057f20f883e', 'f覆盖规划', '15820592586', '2', '0', '1970-01-01 08:00:01', null, '吉林', '', null, null, null, 'APP', '0', '1', '1', '2016-12-01 11:13:52', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('90', null, '15825236985', 'e10adc3949ba59abbe56e057f20f883e', 'h还斤斤计较', '15825236985', '1', '0', '1970-01-01 08:00:01', null, '北京', '', null, null, null, 'APP', '0', '1', '1', '2016-12-01 11:26:45', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('91', null, '15823651478', 'e10adc3949ba59abbe56e057f20f883e', '快快快快快快', '15823651478', '1', '0', '1970-01-01 08:00:02', null, '重庆', '', null, null, null, 'APP', '0', '1', '1', '2016-12-01 12:13:33', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('92', null, '15820592145', 'e10adc3949ba59abbe56e057f20f883e', '一切', '15820592145', '2', '0', '1970-01-01 08:00:01', null, '山东', '', 'http://futon.ansobuy.cn/futon/20161201/190f0b51b2464830ac07357705f96b80.jpeg', null, null, 'APP', '0', '0', '1', '2016-12-01 12:21:48', '2016-12-01 12:26:43');
INSERT INTO `user` VALUES ('93', null, '15820592365', 'e10adc3949ba59abbe56e057f20f883e', '不会太太可靠', '15820592365', '2', '0', '1970-01-01 08:00:02', null, '四川', '', null, null, null, 'APP', '0', '0', '1', '2016-12-01 13:17:51', '2016-12-01 13:19:24');
INSERT INTO `user` VALUES ('94', null, '15820592148', 'e10adc3949ba59abbe56e057f20f883e', 'g搞活经济', '15820592148', '1', '0', '2581-08-06 03:10:00', null, '青海', '', null, null, null, 'APP', '0', '1', '1', '2016-12-01 15:53:55', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('95', null, '15820592657', 'e10adc3949ba59abbe56e057f20f883e', 'g规划局', '15820592657', '1', '0', '2594-04-09 18:16:40', null, '浙江', '', null, null, null, 'APP', '0', '1', '1', '2016-12-01 16:02:17', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('96', null, '13316982970', 'b3c3c4ce14f112887e02105a095b6beb', '', '13316982970', '0', '0', '1970-01-01 08:00:00', null, '', '', 'http://futon.ansobuy.cn/futon/20170118/6cda075a2e1148b4ab2652b5aa6cd0b0.jpg', null, null, 'APP', '0', '0', '1', '2016-12-06 11:12:33', '2017-02-06 18:09:22');
INSERT INTO `user` VALUES ('97', null, '15602448883', 'e807f1fcf82d132f9bb018ca6738a19f', '佛曰', '15602448883', '1', '0', '2599-05-05 14:43:20', null, '湖北', '', 'http://futon.ansobuy.cn/futon/20161206/bd960d650a3c4c26a46f133d48ce1197.jpeg', null, null, 'APP', '0', '1', '1', '2016-12-06 16:49:36', '2017-01-13 08:55:25');
INSERT INTO `user` VALUES ('98', null, '15825412578', 'e10adc3949ba59abbe56e057f20f883e', '反反复复', '15825412578', '1', '0', '2602-07-07 00:30:00', null, '北京', '', null, null, null, 'APP', '0', '1', '1', '2016-12-06 17:50:11', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('99', 'oQR1_wHIPbRDlprYTL6G3GcmtFJ8', null, null, '飞鸽', '18926935114', '1', '0', null, '中国', '广东', '珠海', 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM6P5ModnEBrpJ0k9eibR1WSmiaqM2epplrPpPyc5lhA2zWiaz1GH56kbrlQV0qvrOCdclN4Qzeia3teTA/0', 'zh_CN', '', 'WX', '0', '0', '1', '2016-12-07 13:06:44', '2016-12-07 13:29:08');
INSERT INTO `user` VALUES ('100', null, '15820654321', 'e10adc3949ba59abbe56e057f20f883e', '发个广告', '15820654321', '1', '0', '2585-09-18 18:16:40', null, '上海', '', null, null, null, 'APP', '0', '1', '1', '2016-12-07 15:31:54', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('101', null, '15820654322', 'e3ceb5881a0a1fdaad01296d7554868d', '哥哥', '15820654322', '1', '0', '2583-07-01 13:50:00', null, '河南', '', null, null, null, 'APP', '0', '1', '1', '2016-12-07 15:53:01', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('102', null, '18617186753', 'e807f1fcf82d132f9bb018ca6738a19f', '菩萨', '18617186753', '1', '0', '2581-08-06 03:10:00', null, '安徽', '', 'http://futon.ansobuy.cn/futon/20161207/358214ff553e4edd9f723ce8de8dc4a3.jpeg', null, null, 'APP', '0', '0', '1', '2016-12-07 15:53:39', '2017-02-09 13:31:07');
INSERT INTO `user` VALUES ('103', null, '13247603938', 'e10adc3949ba59abbe56e057f20f883e', 'qqqqq', '13247603938', '1', '0', '1970-01-24 07:40:10', null, '湖北', '', null, null, null, 'APP', '0', '1', '1', '2016-12-08 15:12:26', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('104', null, '13631648402', 'f41d46e118604a573532ec554abdd290', '荷兰风车', '13631648402', '1', '0', '1981-01-01 00:00:00', null, '湖北', '', null, null, null, 'APP', '0', '1', '1', '2016-12-12 14:28:00', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('105', 'oQR1_wOU0hdsV0CFeCRRKCif4ZAQ', null, null, '刘东亮', null, '1', '0', null, '中国', '天津', '', 'http://wx.qlogo.cn/mmopen/dB8ibpYKr44hFCJQZzw6RibQU4ASvOAXaRMQxach5jEVoDmic7OrsZiblJPsicVj1ocApQpN3rqqQoMtkCkxtPVCxGkQkgyJP3KAF/0', 'zh_CN', '', 'WX', '0', '0', '1', '2016-12-14 09:39:01', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('106', 'oQR1_wBEa4K3M9-P3zRpo4wUPmNQ', null, null, '飞哥', null, '0', '0', null, '', '', '', '', 'zh_CN', '', 'WX', '0', '0', '1', '2016-12-14 10:14:08', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('107', null, '13824123129', 'e10adc3949ba59abbe56e057f20f883e', '林小欣', '13824123129', '1', '0', '2601-03-31 01:23:20', null, '广东', '', 'http://futon.ansobuy.cn/futon/20161221/da6e78a27e204a83be491a8ec7ad7f93.jpeg', null, null, 'APP', '0', '1', '1', '2016-12-19 16:59:54', '2016-12-21 14:58:33');
INSERT INTO `user` VALUES ('108', null, '18565665078', 'b3c3c4ce14f112887e02105a095b6beb', 'qaw', '18565665078', '1', '0', '1970-01-01 08:00:00', null, '浙江', '', 'http://futon.ansobuy.cn/futon/20170205/6dfe866aa05748d8a424ad3768dd8ecb.jpg', null, null, 'APP', '0', '0', '1', '2016-12-30 14:28:36', '2017-02-05 09:58:56');
INSERT INTO `user` VALUES ('109', null, '13510445020', 'e10adc3949ba59abbe56e057f20f883e', '123', '13510445020', '1', '0', '1992-01-01 00:00:00', null, '广东', '', 'http://futon.ansobuy.cn/futon/20170120/df98eb5a19314158aff6b2249562782f.jpg', null, null, 'APP', '0', '1', '1', '2017-01-20 16:16:41', '2017-01-20 16:26:05');
INSERT INTO `user` VALUES ('110', null, '13682475265', '86ac625e8a5580d3985b4748490d4db4', '污喂冥', '13682475265', '0', '0', '1970-01-01 08:00:00', null, '', '', 'http://futon.ansobuy.cn/futon/20170120/f7295886135c4ae5bae04917d750ff1c.jpg', null, null, 'APP', '0', '0', '1', '2017-01-20 16:31:40', '2017-02-05 14:47:24');
INSERT INTO `user` VALUES ('111', 'oQR1_wDYqdKKy3mW8rfmf_PQiZZw', null, null, '惜', null, '2', '0', null, '中国', '陕西', '西安', 'http://wx.qlogo.cn/mmopen/qcyhbDv9VYv5X4I0G0Erpfic7A5N2NELBniaWYvNakVc1R4WLO46wT6MYF4htic9HSB8QRsvrzoTnDuW0rOicqmWygd40d9mNgNC/0', 'zh_CN', '', 'WX', '0', '0', '1', '2017-02-08 14:04:57', '1970-01-01 00:00:00');
INSERT INTO `user` VALUES ('112', 'oQR1_wIujNcNdAEvIWkCmd3oIz2U', null, null, '于', null, '1', '0', null, '美国', '德州', '博蒙特', 'http://wx.qlogo.cn/mmopen/dB8ibpYKr44jiaibO8kqyVJqJh2icjaEmptyicgSibVO9WjCbZIs1yqZrBh2Czp81QklFqmgSYRcP0YvOnvjibGrA9OCQzF5vqOnibiat/0', 'zh_CN', '', 'WX', '0', '0', '1', '2017-02-08 17:20:53', '1970-01-01 00:00:00');

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
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='--------------用户设备关联表';

-- ----------------------------
-- Records of user_device
-- ----------------------------
INSERT INTO `user_device` VALUES ('14', '40', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-24 09:27:31', '2016-11-24 15:33:11');
INSERT INTO `user_device` VALUES ('15', '42', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-22 15:40:02', '2016-11-22 15:41:53');
INSERT INTO `user_device` VALUES ('16', '42', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-11-14 17:29:40', '2016-11-16 12:31:02');
INSERT INTO `user_device` VALUES ('17', '40', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-28 16:00:54', '2016-12-28 16:13:54');
INSERT INTO `user_device` VALUES ('18', '59', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-23 13:21:34', '2016-11-23 13:21:54');
INSERT INTO `user_device` VALUES ('19', '59', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-14 11:31:15', '2016-12-14 11:31:15');
INSERT INTO `user_device` VALUES ('20', '58', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-11-16 14:03:03', '2016-11-16 14:03:19');
INSERT INTO `user_device` VALUES ('21', '48', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-16 14:39:22', '2016-11-16 14:41:25');
INSERT INTO `user_device` VALUES ('22', '65', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-30 14:24:30', '2016-11-30 18:12:20');
INSERT INTO `user_device` VALUES ('23', '47', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-11-22 15:46:57', '1970-01-01 00:00:00');
INSERT INTO `user_device` VALUES ('24', '40', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-11-24 17:01:38', '2016-11-25 08:55:37');
INSERT INTO `user_device` VALUES ('25', '65', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-11-25 17:38:19', '2016-11-25 17:38:19');
INSERT INTO `user_device` VALUES ('27', '61', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-14 11:04:24', '2016-12-14 11:05:13');
INSERT INTO `user_device` VALUES ('28', '81', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-01 13:49:34', '2016-12-01 13:52:17');
INSERT INTO `user_device` VALUES ('30', '80', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-14 09:58:31', '2016-12-14 10:01:50');
INSERT INTO `user_device` VALUES ('35', '61', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-11-30 14:45:08', '2016-11-30 14:47:35');
INSERT INTO `user_device` VALUES ('37', '81', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-11-30 14:48:22', '2016-12-01 11:10:51');
INSERT INTO `user_device` VALUES ('38', '86', 'gh_34db4e9151de_e5e8bf3b45a7426e', '0', '2016-12-01 16:46:08', '2016-12-14 17:57:45');
INSERT INTO `user_device` VALUES ('39', '65', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-12 14:31:57', '2016-12-12 14:31:57');
INSERT INTO `user_device` VALUES ('41', '98', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2016-12-06 17:50:35', '1970-01-01 00:00:00');
INSERT INTO `user_device` VALUES ('42', '61', 'gh_34db4e9151de_0d76d5b85b67ae29', '0', '2016-12-07 15:46:14', '2016-12-07 15:46:31');
INSERT INTO `user_device` VALUES ('43', '99', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-14 10:04:55', '2016-12-14 10:05:42');
INSERT INTO `user_device` VALUES ('44', '83', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-14 10:11:35', '2016-12-14 10:11:57');
INSERT INTO `user_device` VALUES ('45', '59', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2016-12-12 13:15:41', '2016-12-12 14:05:04');
INSERT INTO `user_device` VALUES ('46', '59', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2016-12-12 17:55:53', '2016-12-14 11:21:14');
INSERT INTO `user_device` VALUES ('47', '105', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-14 10:07:52', '2016-12-14 10:08:20');
INSERT INTO `user_device` VALUES ('48', '106', 'gh_34db4e9151de_c03c39cee7eec4f1', '0', '2016-12-14 10:20:49', '2016-12-14 10:22:21');
INSERT INTO `user_device` VALUES ('49', '102', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2016-12-14 15:50:30', '1970-01-01 00:00:00');
INSERT INTO `user_device` VALUES ('50', '102', 'gh_34db4e9151de_3ab33da5e132478c', '1', '2017-02-09 16:42:39', '2017-02-09 16:42:39');
INSERT INTO `user_device` VALUES ('51', '96', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2017-02-09 16:06:04', '2017-02-09 16:06:04');
INSERT INTO `user_device` VALUES ('52', '96', 'gh_34db4e9151de_e19758c5441a46f3', '0', '2017-01-05 14:44:16', '2017-01-06 16:41:48');
INSERT INTO `user_device` VALUES ('53', '86', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2017-01-13 16:55:34', '2017-01-13 16:55:34');
INSERT INTO `user_device` VALUES ('54', '107', 'gh_34db4e9151de_3c3684cb57466c5a', '0', '2017-01-16 10:57:26', '2017-01-16 11:01:31');
INSERT INTO `user_device` VALUES ('55', '59', 'gh_34db4e9151de_d4cd442b97ef1775', '0', '2017-01-05 13:49:29', '2017-02-08 13:30:29');
INSERT INTO `user_device` VALUES ('56', '97', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2017-01-13 16:27:02', '2017-01-13 16:27:02');
INSERT INTO `user_device` VALUES ('57', '108', 'gh_34db4e9151de_3ab33da5e132478c', '0', '2017-02-05 09:36:09', '1970-01-01 00:00:00');
INSERT INTO `user_device` VALUES ('58', '59', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 17:17:05', '2017-02-08 17:26:37');
INSERT INTO `user_device` VALUES ('59', '111', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 14:26:44', '2017-02-08 14:26:44');
INSERT INTO `user_device` VALUES ('60', '99', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 14:24:51', '2017-02-08 14:25:09');
INSERT INTO `user_device` VALUES ('61', '61', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 14:14:31', '2017-02-08 14:14:56');
INSERT INTO `user_device` VALUES ('62', '80', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 14:22:20', '2017-02-08 14:23:22');
INSERT INTO `user_device` VALUES ('63', '40', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 17:11:27', '2017-02-08 17:14:32');
INSERT INTO `user_device` VALUES ('64', '86', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 16:00:32', '2017-02-08 16:02:41');
INSERT INTO `user_device` VALUES ('65', '107', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 16:06:56', '2017-02-08 16:08:30');
INSERT INTO `user_device` VALUES ('66', '112', 'gh_34db4e9151de_ec1c2def8681cbd7', '0', '2017-02-08 17:36:39', '2017-02-08 17:48:01');

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
  `device_type` varchar(200) COLLATE utf8mb4_bin DEFAULT '',
  `url` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `md5` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT 'yyyy-MM-dd hh:mm:ss',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT 'yyyy-MM-dd hh:mm:ss',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='--------------终端版本';

-- ----------------------------
-- Records of version
-- ----------------------------
INSERT INTO `version` VALUES ('29', '2.0.6', 'ADV', 'smartfuton', '', 'FIRMWARE', 'PT', 'http://futon.ansobuy.cn/futon/FIRMWARE/2.0.6/smartfuton-PT-2.0.6.bin', '9ace20b7f797024450f12cae4dcf36f5', '2016-12-07 13:15:16', '2016-12-07 13:15:16');
