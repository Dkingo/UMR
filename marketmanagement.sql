/*
 Navicat Premium Data Transfer

 Source Server         : D.k_mysql
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : marketmanagement

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 18/12/2019 16:06:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for buying
-- ----------------------------
DROP TABLE IF EXISTS `buying`;
CREATE TABLE `buying`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `saleid` int(11) NOT NULL,
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `saleprice` float NULL DEFAULT NULL,
  `quantity` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cashier
-- ----------------------------
DROP TABLE IF EXISTS `cashier`;
CREATE TABLE `cashier`  (
  `cash_userId` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收银人员账号名',
  `cash_password` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收银人员密码',
  `cash_username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收银人员姓名',
  `cash_sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收银人员性别',
  `cash_age` int(11) NOT NULL COMMENT '收银人员的年龄',
  `cash_tele` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收银人员的手机号码',
  `cash_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收银人员的家庭住址',
  PRIMARY KEY (`cash_userId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cashier
-- ----------------------------
INSERT INTO `cashier` VALUES ('abc', '456123', '黄少林', '男', 25, '12345645841', '广东省潮州市');
INSERT INTO `cashier` VALUES ('adefbc', '123456', '大鱼', '男', 26, '23564521565', '湖南');
INSERT INTO `cashier` VALUES ('defgs', '456789', '认识他', '男', 25, '12', '广东省潮州市');
INSERT INTO `cashier` VALUES ('dgeh', '123456', '浩锋', '男', 23, '1215151', '广东');

-- ----------------------------
-- Table structure for commodityinformation
-- ----------------------------
DROP TABLE IF EXISTS `commodityinformation`;
CREATE TABLE `commodityinformation`  (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '商品编号',
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `measurement` float NOT NULL COMMENT '商品库存',
  `category` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品类别',
  `sdescribe` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品描述',
  `manufacturer` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '生产厂商',
  `buyingprice` float NOT NULL COMMENT '进货价',
  `saleprice` float NOT NULL COMMENT '销售价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodityinformation
-- ----------------------------
INSERT INTO `commodityinformation` VALUES (1, '水杯', 24, '日用品', '塑料材质', '广东', 5, 15);
INSERT INTO `commodityinformation` VALUES (2, '手撕面包', 24, '零食', '三只松鼠', '湖南', 2, 5);
INSERT INTO `commodityinformation` VALUES (3, '冰红茶', 15, '饮品', '冰力十足', '湖北', 4, 8);
INSERT INTO `commodityinformation` VALUES (4, '猪肉干', 20, '零食', '麻辣', '北京', 3, 5);
INSERT INTO `commodityinformation` VALUES (5, '牛肉干', 31, '零食', '辛辣', '广东', 4, 6);
INSERT INTO `commodityinformation` VALUES (6, '纸巾', 27, '日用品', '餐巾', '上海', 3, 6);
INSERT INTO `commodityinformation` VALUES (7, '牙膏', 46, '日用品', '佳洁士', '甘肃', 12, 20);
INSERT INTO `commodityinformation` VALUES (8, '钢笔', 30, '文具', '派克', '广东', 20, 50);
INSERT INTO `commodityinformation` VALUES (9, '王老吉', 47, '饮品', '解毒', '广东', 5, 8);
INSERT INTO `commodityinformation` VALUES (10, '红牛', 32, '饮品', '提神', '广东', 5, 9);
INSERT INTO `commodityinformation` VALUES (11, '毛笔', 24, '文具', '小字头', '海南', 10, 20);
INSERT INTO `commodityinformation` VALUES (12, '剃须刀', 28, '日用品', '男士专用', '青海', 30, 50);
INSERT INTO `commodityinformation` VALUES (13, '洗衣液', 26, '日用品', '蓝月亮', '浙江', 30, 50);
INSERT INTO `commodityinformation` VALUES (14, '咖啡', 13, '饮品', '雀巢', '四川', 12, 20);
INSERT INTO `commodityinformation` VALUES (15, '橡皮擦', 18, '文具', '晨光', '广东', 2, 4);
INSERT INTO `commodityinformation` VALUES (16, '洗面奶', 55, '日用品', '欧莱雅', '江苏', 30, 50);
INSERT INTO `commodityinformation` VALUES (24, '剪刀', 22, '日用品', '具有多种用途', '北京', 10, 16);
INSERT INTO `commodityinformation` VALUES (25, '拖把', 18, '日用品', '清洁工具', '广东', 15, 12);
INSERT INTO `commodityinformation` VALUES (26, '纸巾', 29, '日用品', '清风', '广东', 10, 9);
INSERT INTO `commodityinformation` VALUES (27, '辣条', 1, '零食', '威龙', '广东', 5, 10);
INSERT INTO `commodityinformation` VALUES (29, '煮面锅', 4, '日用品', '奥林个', '广东', 12, 50);

-- ----------------------------
-- Table structure for management
-- ----------------------------
DROP TABLE IF EXISTS `management`;
CREATE TABLE `management`  (
  `mag_username` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理人员用户名',
  `mag_password` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理人员密码',
  PRIMARY KEY (`mag_username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of management
-- ----------------------------
INSERT INTO `management` VALUES ('manager', '123456');

-- ----------------------------
-- Table structure for outofstock
-- ----------------------------
DROP TABLE IF EXISTS `outofstock`;
CREATE TABLE `outofstock`  (
  `id` int(4) NOT NULL COMMENT '商品编号',
  `no` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品流水号',
  `exchange` int(8) NOT NULL COMMENT '商品交易号',
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `saleprice` float NOT NULL COMMENT '商品价格',
  `sale` int(8) NOT NULL COMMENT '商品出库数量',
  `date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品出库日期',
  PRIMARY KEY (`no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of outofstock
-- ----------------------------
INSERT INTO `outofstock` VALUES (1, 1, 1000, '水杯', 15, 2, '2019-11-13 08:45:58');
INSERT INTO `outofstock` VALUES (3, 2, 1000, '冰红茶', 8, 1, '2019-11-13 08:53:40');
INSERT INTO `outofstock` VALUES (4, 3, 1000, '猪肉干', 5, 1, '2019-11-13 08:53:45');
INSERT INTO `outofstock` VALUES (7, 4, 1000, '牙膏', 20, 1, '2019-11-13 08:53:50');
INSERT INTO `outofstock` VALUES (10, 5, 1000, '红牛', 9, 2, '2019-11-13 08:55:38');
INSERT INTO `outofstock` VALUES (11, 6, 1000, '毛笔', 20, 1, '2019-11-13 08:55:48');
INSERT INTO `outofstock` VALUES (12, 7, 1000, '剃须刀', 50, 1, '2019-11-13 08:55:52');
INSERT INTO `outofstock` VALUES (1, 8, 1000, '水杯', 15, 1, '2019-11-14 11:43:33');
INSERT INTO `outofstock` VALUES (2, 9, 1000, '手撕面包', 5, 1, '2019-11-14 11:43:40');
INSERT INTO `outofstock` VALUES (3, 10, 1000, '冰红茶', 7.6, 1, '2019-11-14 11:44:06');
INSERT INTO `outofstock` VALUES (4, 11, 1000, '猪肉干', 4.75, 5, '2019-11-14 11:44:13');
INSERT INTO `outofstock` VALUES (14, 12, 1000, '咖啡', 20, 1, '2019-12-01 04:49:41');
INSERT INTO `outofstock` VALUES (10, 13, 1000, '红牛', 9, 1, '2019-12-03 08:10:14');
INSERT INTO `outofstock` VALUES (12, 14, 1000, '剃须刀', 50, 1, '2019-12-03 08:10:18');
INSERT INTO `outofstock` VALUES (6, 15, 1000, '纸巾', 6, 1, '2019-12-03 08:10:28');
INSERT INTO `outofstock` VALUES (14, 16, 1000, '咖啡', 20, 1, '2019-12-03 08:10:37');
INSERT INTO `outofstock` VALUES (11, 17, 1000, '毛笔', 20, 1, '2019-12-03 08:10:42');
INSERT INTO `outofstock` VALUES (9, 18, 1000, '王老吉', 7.6, 1, '2019-12-03 08:14:33');
INSERT INTO `outofstock` VALUES (7, 19, 1000, '牙膏', 19, 1, '2019-12-03 08:19:58');
INSERT INTO `outofstock` VALUES (6, 20, 1000, '纸巾', 5.7, 1, '2019-12-03 08:21:59');
INSERT INTO `outofstock` VALUES (5, 21, 1000, '牛肉干', 5.7, 1, '2019-12-03 08:22:07');
INSERT INTO `outofstock` VALUES (5, 22, 1000, '牛肉干', 5.7, 1, '2019-12-03 08:22:21');
INSERT INTO `outofstock` VALUES (9, 23, 1000, '王老吉', 7.6, 1, '2019-12-03 08:22:28');
INSERT INTO `outofstock` VALUES (13, 24, 1000, '洗衣液', 47.5, 1, '2019-12-03 08:24:10');
INSERT INTO `outofstock` VALUES (14, 25, 1000, '咖啡', 19, 1, '2019-12-03 08:24:18');
INSERT INTO `outofstock` VALUES (24, 26, 1000, '剪刀', 15.2, 1, '2019-12-04 05:52:41');
INSERT INTO `outofstock` VALUES (16, 27, 1000, '洗面奶', 47.5, 1, '2019-12-04 05:52:50');
INSERT INTO `outofstock` VALUES (24, 28, 1000, '剪刀', 16, 1, '2019-12-04 05:57:52');
INSERT INTO `outofstock` VALUES (24, 29, 1000, '剪刀', 15.2, 1, '2019-12-04 05:58:13');
INSERT INTO `outofstock` VALUES (15, 30, 1000, '橡皮擦', 3.8, 1, '2019-12-04 05:58:18');
INSERT INTO `outofstock` VALUES (11, 31, 1000, '毛笔', 19, 1, '2019-12-04 05:59:10');
INSERT INTO `outofstock` VALUES (2, 32, 1000, '手撕面包', 5, 5, '2019-12-04 09:08:54');
INSERT INTO `outofstock` VALUES (3, 33, 1000, '冰红茶', 8, 2, '2019-12-04 09:11:19');
INSERT INTO `outofstock` VALUES (4, 34, 1000, '猪肉干', 5, 3, '2019-12-04 09:11:29');
INSERT INTO `outofstock` VALUES (2, 35, 1000, '手撕面包', 5, 2, '2019-12-04 11:02:54');
INSERT INTO `outofstock` VALUES (3, 36, 1000, '冰红茶', 8, 2, '2019-12-04 11:06:21');
INSERT INTO `outofstock` VALUES (4, 37, 1000, '猪肉干', 5, 2, '2019-12-04 11:12:12');
INSERT INTO `outofstock` VALUES (5, 38, 1000, '牛肉干', 6, 2, '2019-12-04 11:12:16');
INSERT INTO `outofstock` VALUES (24, 39, 1000, '剪刀', 16, 1, '2019-12-05 10:30:14');
INSERT INTO `outofstock` VALUES (16, 40, 1000, '洗面奶', 50, 1, '2019-12-05 10:47:20');
INSERT INTO `outofstock` VALUES (13, 41, 1000, '洗衣液', 50, 1, '2019-12-05 10:47:25');
INSERT INTO `outofstock` VALUES (5, 42, 1000, '牛肉干', 5.7, 1, '2019-12-05 10:47:58');
INSERT INTO `outofstock` VALUES (7, 43, 1000, '牙膏', 19, 2, '2019-12-05 10:48:01');
INSERT INTO `outofstock` VALUES (10, 44, 1000, '红牛', 8.55, 1, '2019-12-05 10:48:05');
INSERT INTO `outofstock` VALUES (25, 45, 1001, '拖把', 12, 1, '2019-12-05 12:55:56');
INSERT INTO `outofstock` VALUES (26, 46, 1002, '纸巾', 9, 1, '2019-12-08 08:26:38');
INSERT INTO `outofstock` VALUES (24, 47, 1003, '剪刀', 16, 1, '2019-12-08 09:23:28');
INSERT INTO `outofstock` VALUES (6, 48, 1004, '纸巾', 6, 1, '2019-12-08 09:24:33');
INSERT INTO `outofstock` VALUES (9, 49, 1004, '王老吉', 8, 1, '2019-12-08 09:24:36');
INSERT INTO `outofstock` VALUES (1, 50, 1005, '水杯', 15, 2, '2019-12-10 11:38:27');
INSERT INTO `outofstock` VALUES (7, 51, 1006, '牙膏', 20, 1, '2019-12-11 11:27:33');
INSERT INTO `outofstock` VALUES (14, 52, 1007, '咖啡', 20, 4, '2019-12-12 10:14:30');
INSERT INTO `outofstock` VALUES (11, 53, 1008, '毛笔', 20, 1, '2019-12-18 09:22:19');
INSERT INTO `outofstock` VALUES (24, 54, 1008, '剪刀', 16, 1, '2019-12-18 09:22:28');
INSERT INTO `outofstock` VALUES (13, 55, 1008, '洗衣液', 50, 2, '2019-12-18 09:22:35');
INSERT INTO `outofstock` VALUES (16, 56, 1009, '洗面奶', 47.5, 1, '2019-12-18 09:23:56');

-- ----------------------------
-- Table structure for vip
-- ----------------------------
DROP TABLE IF EXISTS `vip`;
CREATE TABLE `vip`  (
  `vip_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员卡卡号',
  `vip_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员卡姓名',
  PRIMARY KEY (`vip_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vip
-- ----------------------------
INSERT INTO `vip` VALUES ('123456', '新时代');
INSERT INTO `vip` VALUES ('123456789', '小时');
INSERT INTO `vip` VALUES ('456123', '小红');

-- ----------------------------
-- Table structure for warehousing
-- ----------------------------
DROP TABLE IF EXISTS `warehousing`;
CREATE TABLE `warehousing`  (
  `id` int(4) NOT NULL COMMENT '商品编号',
  `id_serial` int(4) NOT NULL AUTO_INCREMENT COMMENT '商品入库流水号',
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `input` int(8) NOT NULL COMMENT '商品进库数量',
  `buyingprice` float NOT NULL COMMENT '商品进货价',
  `sellprice` float NOT NULL COMMENT '商品销售价',
  `date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品入库日期',
  PRIMARY KEY (`id_serial`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehousing
-- ----------------------------
INSERT INTO `warehousing` VALUES (1, 1, '水杯', 30, 5, 15, '2019-11-13 08:15:15');
INSERT INTO `warehousing` VALUES (2, 2, '手撕面包', 35, 2, 5, '2019-11-13 08:16:01');
INSERT INTO `warehousing` VALUES (3, 3, '冰红茶', 20, 4, 8, '2019-11-13 08:17:05');
INSERT INTO `warehousing` VALUES (4, 4, '猪肉干', 30, 3, 5, '2019-11-13 08:17:43');
INSERT INTO `warehousing` VALUES (5, 5, '牛肉干', 36, 4, 6, '2019-11-13 08:18:15');
INSERT INTO `warehousing` VALUES (6, 6, '纸巾', 30, 3, 6, '2019-11-13 08:18:52');
INSERT INTO `warehousing` VALUES (7, 7, '牙膏', 50, 12, 20, '2019-11-13 08:19:35');
INSERT INTO `warehousing` VALUES (8, 8, '钢笔', 30, 20, 50, '2019-11-13 08:20:41');
INSERT INTO `warehousing` VALUES (9, 9, '王老吉', 50, 5, 8, '2019-11-13 08:21:32');
INSERT INTO `warehousing` VALUES (10, 10, '红牛', 36, 5, 9, '2019-11-13 08:22:03');
INSERT INTO `warehousing` VALUES (11, 11, '毛笔', 30, 10, 20, '2019-11-13 08:23:12');
INSERT INTO `warehousing` VALUES (12, 12, '剃须刀', 30, 30, 50, '2019-11-13 08:23:56');
INSERT INTO `warehousing` VALUES (13, 13, '洗衣液', 30, 30, 50, '2019-11-13 08:24:29');
INSERT INTO `warehousing` VALUES (14, 14, '咖啡', 20, 12, 20, '2019-11-13 08:25:18');
INSERT INTO `warehousing` VALUES (15, 15, '橡皮擦', 20, 2, 4, '2019-11-13 08:26:27');
INSERT INTO `warehousing` VALUES (16, 16, '洗面奶', 60, 30, 50, '2019-11-13 08:27:14');
INSERT INTO `warehousing` VALUES (23, 23, '剪刀', 20, 5, 11, '2019-12-03 08:47:47');
INSERT INTO `warehousing` VALUES (24, 24, '剪刀', 30, 10, 16, '2019-12-03 08:50:04');
INSERT INTO `warehousing` VALUES (25, 25, '拖把', 20, 15, 20, '2019-12-05 12:32:40');
INSERT INTO `warehousing` VALUES (26, 26, '纸巾', 30, 10, 15, '2019-12-08 08:25:55');
INSERT INTO `warehousing` VALUES (27, 27, '辣条', 2, 5, 10, '2019-12-11 11:17:12');
INSERT INTO `warehousing` VALUES (28, 28, '煮面锅', 20, 50, 100, '2019-12-18 09:27:41');
INSERT INTO `warehousing` VALUES (29, 29, '煮面锅', 20, 12, 50, '2019-12-18 09:35:49');

-- ----------------------------
-- Table structure for wastage
-- ----------------------------
DROP TABLE IF EXISTS `wastage`;
CREATE TABLE `wastage`  (
  `id` int(11) NOT NULL COMMENT '商品编号',
  `no` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品流水号',
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `loss` int(11) NOT NULL COMMENT '商品损耗数量',
  `loss_describe` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品损耗原因',
  `date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品损耗日期',
  PRIMARY KEY (`no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wastage
-- ----------------------------
INSERT INTO `wastage` VALUES (1, 1, '水杯', 1, '打碎', '2019-11-13 08:45:58');
INSERT INTO `wastage` VALUES (16, 4, '洗面奶', 2, '过期', '2019-11-13 08:45:58');
INSERT INTO `wastage` VALUES (15, 5, '橡皮擦', 1, '过期', '2019-11-13 08:45:58');
INSERT INTO `wastage` VALUES (27, 7, '辣条', 1, '过期', '2019-12-15 07:46:14');

SET FOREIGN_KEY_CHECKS = 1;
