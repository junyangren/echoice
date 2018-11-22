alter table ec_user add   `mobile` VARCHAR(64) COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机';
alter table ec_user add   `tel` VARCHAR(32) COLLATE utf8_general_ci DEFAULT NULL COMMENT '电话';
alter table ec_user add   `address` VARCHAR(256) COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址';
alter table ec_user add   `qq` VARCHAR(32) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'QQ';
alter table ec_user add   `wechat` VARCHAR(32) COLLATE utf8_general_ci DEFAULT NULL COMMENT '微信';
alter table ec_user add   `duty` VARCHAR(512) COLLATE utf8_general_ci DEFAULT NULL COMMENT '职责';
alter table ec_user add   `leader_id` INTEGER(11) DEFAULT NULL;
alter table ec_user add   `idcard` VARCHAR(32) COLLATE utf8_general_ci DEFAULT NULL COMMENT '身份证号';   