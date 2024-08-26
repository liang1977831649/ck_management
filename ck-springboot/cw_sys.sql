/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : cw_sys

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 26/08/2024 15:37:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '货名',
  `storage_id` int(11) NULL DEFAULT NULL COMMENT '仓库',
  `goodstype_id` int(11) NULL DEFAULT NULL COMMENT '分类',
  `count` int(11) NULL DEFAULT NULL COMMENT '数量',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '辣条', 1, 1, 10, '');
INSERT INTO `goods` VALUES (4, '手机3', 4, 1, 10, '');
INSERT INTO `goods` VALUES (5, '耳机', 4, 3, 10, '');
INSERT INTO `goods` VALUES (6, '低音炮', 1, 3, 11, '');
INSERT INTO `goods` VALUES (7, '罐头', 1, 3, 9, '');
INSERT INTO `goods` VALUES (8, '阿胶囊', 1, 1, 10, '');
INSERT INTO `goods` VALUES (9, '法国口红', 1, 8, 999, '');
INSERT INTO `goods` VALUES (10, '小米手机', 5, 6, 1100, '');
INSERT INTO `goods` VALUES (11, '办公座椅', 5, 7, 34, '');
INSERT INTO `goods` VALUES (12, '安踏鞋', 6, 1, 223, '');
INSERT INTO `goods` VALUES (13, '辣了', 5, 6, 33, NULL);

-- ----------------------------
-- Table structure for goods_type
-- ----------------------------
DROP TABLE IF EXISTS `goods_type`;
CREATE TABLE `goods_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_type
-- ----------------------------
INSERT INTO `goods_type` VALUES (1, '衣物类', '');
INSERT INTO `goods_type` VALUES (3, '食品类', '');
INSERT INTO `goods_type` VALUES (5, '器械类', '');
INSERT INTO `goods_type` VALUES (6, '数码类', '');
INSERT INTO `goods_type` VALUES (7, '家居类', '');
INSERT INTO `goods_type` VALUES (8, '美妆类', '');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL,
  `menu_code` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_right` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_level` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_parent_code` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_index` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_component` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '001', '管理员管理', '1', '0', NULL, '/AdminManagement', 'user/AdminManagement.vue', 'UserFilled');
INSERT INTO `menu` VALUES (2, '002', '用户管理', '1,2', '1', NULL, '/UserManagement', 'user/UserManagement.vue', 'Avatar');
INSERT INTO `menu` VALUES (3, '003', '仓库管理', '1,2', '2', NULL, '/StorageManagement', 'storage/StorageManagement.vue', 'OfficeBuilding');
INSERT INTO `menu` VALUES (4, '004', '货物分类管理', '1,2', '3', NULL, '/GoodsTypeManagement', 'goodsType/GoodsTypeManagement', 'Menu');
INSERT INTO `menu` VALUES (5, '005', '货物管理', '1,2,3', '4', NULL, '/GoodsManagement', 'goods/GoodsManagement', 'Grid');
INSERT INTO `menu` VALUES (6, '006', '记录管理', '1,2,3', '5', NULL, '/RecordManagement', 'record/RecordManagement', 'View');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_id` int(11) NOT NULL COMMENT '货品id',
  `user_Id` int(11) NULL DEFAULT NULL COMMENT '取货人/补货人',
  `admin_id` int(11) NULL DEFAULT NULL COMMENT '操作人id',
  `count` int(11) NULL DEFAULT NULL COMMENT '数量',
  `createtime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `operation` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (1, 1, 20, 1, 5, '2024-08-24 21:19:38', NULL, 1);
INSERT INTO `record` VALUES (2, 4, 20, 1, 2, '2024-08-24 21:19:38', NULL, 2);
INSERT INTO `record` VALUES (3, 13, 20, 1, 2, '2024-08-24 21:19:38', NULL, 1);
INSERT INTO `record` VALUES (4, 6, 9, 1, 12, '2024-08-26 09:22:24', '', 1);
INSERT INTO `record` VALUES (5, 12, 4, 1, 11, '2024-08-26 09:24:35', '', 1);
INSERT INTO `record` VALUES (6, 11, 20, 1, 10, '2024-08-26 09:26:40', '', 1);
INSERT INTO `record` VALUES (7, 10, 6, 1, -2, '2024-08-26 09:27:07', '', 2);
INSERT INTO `record` VALUES (8, 1, 5, 1, 20, '2024-08-26 09:35:03', '', 1);
INSERT INTO `record` VALUES (9, 7, 4, 1, -1, '2024-08-26 09:47:39', '', 2);

-- ----------------------------
-- Table structure for storage
-- ----------------------------
DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '仓库名',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of storage
-- ----------------------------
INSERT INTO `storage` VALUES (1, '仓库1', '');
INSERT INTO `storage` VALUES (4, '仓库4', '');
INSERT INTO `storage` VALUES (5, '仓库5', '');
INSERT INTO `storage` VALUES (6, '仓库6', '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `sex` int(11) NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  `isvalid` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT ' Y ',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_no`(`no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '1110', 'admin', '123456', 22, 1, '13707845678', 1, 'Y');
INSERT INTO `user` VALUES (3, '3330', 'jack', '123456', 30, 1, '12345678901', 2, 'Y ');
INSERT INTO `user` VALUES (4, '4440', 'smith', '123456', 28, 1, '12345678765', 3, 'Y ');
INSERT INTO `user` VALUES (5, '5550', 'long', '9999999', 32, 0, '12345678765', 3, 'Y ');
INSERT INTO `user` VALUES (6, '6660', 'ajax', '123456', 22, 0, '12345678765', 3, 'N');
INSERT INTO `user` VALUES (7, '7770', 'mary', '123456', 23, 0, '12345678765', 2, 'N');
INSERT INTO `user` VALUES (8, '8880', 'alibaba', '123456', 44, 0, '12345678765', 2, 'N');
INSERT INTO `user` VALUES (9, '9990', 'tenxun', '123456', 36, 0, '12345678765', 3, 'Y');
INSERT INTO `user` VALUES (10, '1100', 'monika', '123456', 28, 1, '15224537598', 1, 'Y');
INSERT INTO `user` VALUES (14, '4563', 'agoodman', '123456', 53, 1, '18907866215', 3, 'Y');
INSERT INTO `user` VALUES (20, '5745', 'england', '123456', 43, 1, '19968161580', 3, 'Y');
INSERT INTO `user` VALUES (21, '6787', 'flash', '12345678', 33, 0, '18907866215', 2, 'Y');

SET FOREIGN_KEY_CHECKS = 1;
