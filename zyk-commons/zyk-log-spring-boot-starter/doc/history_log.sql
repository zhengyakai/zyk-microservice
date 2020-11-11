
DROP TABLE IF EXISTS `history_log`;
CREATE TABLE `history_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operate_time` varchar(30)  NOT NULL COMMENT '创建时间',
  `application_name` varchar(32)  NULL DEFAULT NULL COMMENT '应用名',
  `business_module` varchar(32)  NULL DEFAULT NULL COMMENT '业务模块',
  `class_name` varchar(128)  NOT NULL COMMENT '类名',
  `method_name` varchar(64)  NOT NULL COMMENT '方法名',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(50)  NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(1024)  NOT NULL COMMENT '操作信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB ROW_FORMAT = Dynamic;