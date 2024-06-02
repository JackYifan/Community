/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : community

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 02/06/2024 23:54:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ad
-- ----------------------------
DROP TABLE IF EXISTS `ad`;
CREATE TABLE `ad`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `image` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gmt_start` bigint NULL DEFAULT NULL,
  `gmt_end` bigint NULL DEFAULT NULL,
  `gmt_create` bigint NULL DEFAULT NULL,
  `gmt_modified` bigint NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `pos` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ad
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NOT NULL,
  `type` int NOT NULL,
  `commentator` bigint NOT NULL,
  `gmt_create` bigint NOT NULL,
  `gmt_modified` bigint NOT NULL,
  `like_count` bigint NULL DEFAULT 0,
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `comment_count` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1689881095757717506 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1688413495471194113, 1, 1, 1688413227916541954, 1691384121187, 1691384121187, 0, 'comment test', 0);
INSERT INTO `comment` VALUES (1688414136532811778, 1, 1, 1688413227916541954, 1691384274028, 1691384274028, 0, 'test', 2);
INSERT INTO `comment` VALUES (1688960026108616706, 1688414136532811778, 2, 1688959945129189378, 1691514424253, 1691514424253, 0, 'test', 0);
INSERT INTO `comment` VALUES (1688960045511467010, 1, 1, 1688959945129189378, 1691514428878, 1691514428878, 0, 'test', 0);
INSERT INTO `comment` VALUES (1689113816824557569, 1, 1, 1688959945129189378, 1691551090686, 1691551090686, 0, '1', 0);
INSERT INTO `comment` VALUES (1689113838114844673, 1688414136532811778, 2, 1688959945129189378, 1691551095892, 1691551095892, 0, '2', 0);
INSERT INTO `comment` VALUES (1689114596520493058, 1, 1, 1688959945129189378, 1691551276583, 1691551276583, 0, '1', 0);
INSERT INTO `comment` VALUES (1689114677889990657, 1689114657425981442, 1, 1688959945129189378, 1691551296102, 1691551296102, 0, '1', 1);
INSERT INTO `comment` VALUES (1689881095757717506, 1689114677889990657, 2, 1688959945129189378, 1691734024214, 1691734024214, 0, '2', 0);

-- ----------------------------
-- Table structure for nav
-- ----------------------------
DROP TABLE IF EXISTS `nav`;
CREATE TABLE `nav`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `priority` int NULL DEFAULT 0,
  `gmt_create` bigint NULL DEFAULT NULL,
  `gmt_modified` bigint NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of nav
-- ----------------------------

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `notifier` bigint NOT NULL,
  `receiver` bigint NOT NULL,
  `outerid` bigint NOT NULL,
  `type` int NOT NULL,
  `gmt_create` bigint NOT NULL,
  `status` int NOT NULL DEFAULT 0,
  `notifier_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `outer_title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1689881095757717507 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES (1688413495534108673, 1688413227916541954, 1688413227916541954, 1, 1, 1691384121202, 0, 'Jack', 'Test');
INSERT INTO `notification` VALUES (1688414136532811779, 1688413227916541954, 1688413227916541954, 1, 1, 1691384274031, 1, 'Jack', 'Test');
INSERT INTO `notification` VALUES (1688960026175725569, 1688959945129189378, 1688413227916541954, 1, 2, 1691514424270, 0, 'user', 'Test');
INSERT INTO `notification` VALUES (1688960045574381569, 1688959945129189378, 1688413227916541954, 1, 1, 1691514428883, 0, 'user', 'Test');
INSERT INTO `notification` VALUES (1689113816824557570, 1688959945129189378, 1688413227916541954, 1, 1, 1691551090809, 0, 'user', 'Test');
INSERT INTO `notification` VALUES (1689113838177759233, 1688959945129189378, 1688413227916541954, 1, 2, 1691551095898, 0, 'user', 'Test');
INSERT INTO `notification` VALUES (1689114596520493059, 1688959945129189378, 1688413227916541954, 1, 1, 1691551276703, 0, 'user', 'Test');
INSERT INTO `notification` VALUES (1689114677889990658, 1688959945129189378, 1688959945129189378, 1689114657425981442, 1, 1691551296107, 0, 'user', 'Hello');
INSERT INTO `notification` VALUES (1689118049548431361, 1688959945129189378, 1688959945129189378, 1689114657425981442, 3, 1691552099964, 1, 'user', 'Hello');
INSERT INTO `notification` VALUES (1689881095757717507, 1688959945129189378, 1688959945129189378, 1689114657425981442, 2, 1691734024361, 0, 'user', 'Hello');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `gmt_create` bigint NULL DEFAULT NULL,
  `gmt_modified` bigint NULL DEFAULT NULL,
  `creator` bigint NOT NULL,
  `comment_count` int NULL DEFAULT 0,
  `view_count` int NULL DEFAULT 0,
  `like_count` int NULL DEFAULT 0,
  `tag` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sticky` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1689114657425981442 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (1, 'Test', '# Test\r\n', 1691384104240, 1691384104240, 1688413227916541954, 5, 18, 0, 'java', 0);
INSERT INTO `question` VALUES (1688961227877347330, 'test', 'test', 1691514710642, 1691514710642, 1688959945129189378, 0, 0, 0, 'javascript', 0);
INSERT INTO `question` VALUES (1689114657425981442, 'Hello', 'Hello', 1691551291226, 1691551291226, 1688959945129189378, 1, 11, 0, 'css,javascript', 0);

-- ----------------------------
-- Table structure for thumb
-- ----------------------------
DROP TABLE IF EXISTS `thumb`;
CREATE TABLE `thumb`  (
  `id` bigint NOT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `comment_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of thumb
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `token` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gmt_create` bigint NULL DEFAULT NULL,
  `gmt_modified` bigint NULL DEFAULT NULL,
  `bio` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatar_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1688959945129189378 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1688413227916541954, '57058925', 'Jack', '8376b57b-9b84-4e46-a812-d1e314d1f908', 1691384057294, 1691384057294, NULL, 'https://avatars.githubusercontent.com/u/57058925?v=4', NULL);
INSERT INTO `user` VALUES (1688959945129189378, NULL, 'user', 'b8e12fd5-cbfe-4650-8898-f72d48e046a6', 1691514404939, 1691734010295, NULL, 'https://avatarfiles.alphacoders.com/314/314155.jpg', '123456');

SET FOREIGN_KEY_CHECKS = 1;
