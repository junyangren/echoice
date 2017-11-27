﻿# SQL Manager 2007 for MySQL 4.4.0.5
# ---------------------------------------
# Host     : 192.168.21.45
# Port     : 3306
# Database : zxpay_db


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE `zxpay_db`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

#
# Structure for the `cfg_result_code` table : 
#

CREATE TABLE `cfg_result_code` (
  `code` VARCHAR(6) COLLATE gbk_chinese_ci NOT NULL DEFAULT '' COMMENT '错误代码',
  `note` VARCHAR(100) COLLATE gbk_chinese_ci DEFAULT NULL COMMENT '错误原因',
  `errmessages` VARCHAR(128) COLLATE gbk_chinese_ci DEFAULT NULL COMMENT '错误原因',
  `type` VARCHAR(6) COLLATE gbk_chinese_ci DEFAULT NULL COMMENT '代码类型：1-集团定义结果码 2-各省自定义结果码',
  PRIMARY KEY (`code`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_accss_mode` table : 
#

CREATE TABLE `ec_accss_mode` (
  `accss_id` BIGINT(20) NOT NULL,
  `name` VARCHAR(128) COLLATE utf8_general_ci DEFAULT NULL,
  `alias` VARCHAR(128) COLLATE utf8_general_ci DEFAULT NULL,
  `status` VARCHAR(4) COLLATE utf8_general_ci DEFAULT NULL,
  `note` VARCHAR(1024) COLLATE utf8_general_ci DEFAULT NULL,
  `taxis` BIGINT(20) DEFAULT '99999',
  PRIMARY KEY (`accss_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_common_seq` table : 
#

CREATE TABLE `ec_common_seq` (
  `pk_key` VARCHAR(32) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `pk_value` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`pk_key`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_group` table : 
#

CREATE TABLE `ec_group` (
  `group_id` BIGINT(20) NOT NULL,
  `name` VARCHAR(128) COLLATE utf8_general_ci DEFAULT NULL,
  `alias` VARCHAR(128) COLLATE utf8_general_ci DEFAULT NULL,
  `parent_id` BIGINT(20) DEFAULT NULL,
  `note` VARCHAR(1024) COLLATE utf8_general_ci DEFAULT NULL,
  `taxis` BIGINT(20) DEFAULT '99999',
  `type` VARCHAR(8) COLLATE utf8_general_ci DEFAULT NULL,
  `op_time` DATE DEFAULT NULL,
  `full_name` VARCHAR(1024) COLLATE utf8_general_ci DEFAULT NULL,
  `group_path` VARCHAR(1024) COLLATE utf8_general_ci DEFAULT NULL,
  `note2` VARCHAR(512) COLLATE utf8_general_ci DEFAULT NULL,
  `note3` VARCHAR(512) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`group_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_group_assignment` table : 
#

CREATE TABLE `ec_group_assignment` (
  `ga_id` BIGINT(20) NOT NULL,
  `group_id` BIGINT(20) DEFAULT NULL,
  `role_id` BIGINT(20) DEFAULT NULL,
  `op_time` DATE DEFAULT NULL,
  PRIMARY KEY (`ga_id`),
  KEY `FK_Relationship_12` (`role_id`),
  KEY `FK_Relationship_13` (`group_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_objects` table : 
#

CREATE TABLE `ec_objects` (
  `obj_id` BIGINT(20) NOT NULL,
  `name` VARCHAR(128) COLLATE utf8_general_ci DEFAULT NULL,
  `alias` VARCHAR(128) COLLATE utf8_general_ci DEFAULT NULL,
  `type` VARCHAR(30) COLLATE utf8_general_ci DEFAULT NULL,
  `icon` VARCHAR(512) COLLATE utf8_general_ci DEFAULT NULL,
  `status` VARCHAR(4) COLLATE utf8_general_ci DEFAULT NULL,
  `parent_id` BIGINT(20) DEFAULT NULL,
  `note` VARCHAR(1024) COLLATE utf8_general_ci DEFAULT NULL,
  `taxis` BIGINT(20) DEFAULT '99999',
  `op_time` DATE DEFAULT NULL,
  `note2` VARCHAR(512) COLLATE utf8_general_ci DEFAULT NULL,
  `note3` VARCHAR(512) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`obj_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_operator` table : 
#

CREATE TABLE `ec_operator` (
  `oper_id` BIGINT(20) NOT NULL,
  `obj_id` BIGINT(20) DEFAULT NULL,
  `accss_id` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`oper_id`),
  KEY `FK_Relationship_1` (`obj_id`),
  KEY `FK_Relationship_2` (`accss_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_permission` table : 
#

CREATE TABLE `ec_permission` (
  `pa_id` BIGINT(20) NOT NULL,
  `role_id` BIGINT(20) DEFAULT NULL,
  `oper_id` BIGINT(20) DEFAULT NULL,
  `op_time` DATE DEFAULT NULL,
  PRIMARY KEY (`pa_id`),
  KEY `FK_Relationship_11` (`oper_id`),
  KEY `FK_Relationship_3` (`role_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_resource` table : 
#

CREATE TABLE `ec_resource` (
  `resource_id` BIGINT(20) NOT NULL,
  `name` VARCHAR(128) COLLATE utf8_general_ci DEFAULT NULL,
  `alias` VARCHAR(128) COLLATE utf8_general_ci DEFAULT NULL,
  `type` VARCHAR(30) COLLATE utf8_general_ci DEFAULT NULL,
  `status` VARCHAR(4) COLLATE utf8_general_ci DEFAULT NULL,
  `parent_id` BIGINT(20) DEFAULT NULL,
  `taxis` BIGINT(20) DEFAULT '99999',
  `op_time` DATE DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_role` table : 
#

CREATE TABLE `ec_role` (
  `role_id` BIGINT(20) NOT NULL,
  `name` VARCHAR(128) COLLATE utf8_general_ci DEFAULT NULL,
  `alias` VARCHAR(128) COLLATE utf8_general_ci DEFAULT NULL,
  `status` VARCHAR(4) COLLATE utf8_general_ci DEFAULT NULL,
  `parent_id` BIGINT(20) DEFAULT NULL,
  `note` VARCHAR(512) COLLATE utf8_general_ci DEFAULT NULL,
  `taxis` BIGINT(20) DEFAULT '99999',
  `op_time` DATE DEFAULT NULL,
  PRIMARY KEY (`role_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_user` table : 
#

CREATE TABLE `ec_user` (
  `user_id` BIGINT(20) NOT NULL,
  `name` VARCHAR(64) COLLATE utf8_general_ci DEFAULT NULL,
  `alias` VARCHAR(64) COLLATE utf8_general_ci DEFAULT NULL,
  `job_number` VARCHAR(64) COLLATE utf8_general_ci DEFAULT NULL,
  `password` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL,
  `email` VARCHAR(128) COLLATE utf8_general_ci DEFAULT NULL,
  `status` VARCHAR(4) COLLATE utf8_general_ci DEFAULT NULL,
  `note` VARCHAR(512) COLLATE utf8_general_ci DEFAULT NULL,
  `taxis` BIGINT(20) DEFAULT '99999',
  `op_time` DATE DEFAULT NULL,
  PRIMARY KEY (`user_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_user_extend` table : 
#

CREATE TABLE `ec_user_extend` (
  `user_id` BIGINT(20) NOT NULL,
  `mobile` VARCHAR(64) COLLATE utf8_general_ci DEFAULT NULL,
  `tel` VARCHAR(64) COLLATE utf8_general_ci DEFAULT NULL,
  `address` VARCHAR(256) COLLATE utf8_general_ci DEFAULT NULL,
  `qq` VARCHAR(64) COLLATE utf8_general_ci DEFAULT NULL,
  `msn` VARCHAR(64) COLLATE utf8_general_ci DEFAULT NULL,
  `duty` VARCHAR(256) COLLATE utf8_general_ci DEFAULT NULL,
  `leader_id` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_user_group` table : 
#

CREATE TABLE `ec_user_group` (
  `ug_id` BIGINT(20) NOT NULL,
  `group_id` BIGINT(20) DEFAULT NULL,
  `user_id` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`ug_id`),
  KEY `FK_Relationship_10` (`group_id`),
  KEY `FK_Relationship_8` (`user_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `ec_users_assignmen` table : 
#

CREATE TABLE `ec_users_assignmen` (
  `ua_id` BIGINT(20) NOT NULL,
  `user_id` BIGINT(20) DEFAULT NULL,
  `role_id` BIGINT(20) DEFAULT NULL,
  `op_time` DATE DEFAULT NULL,
  PRIMARY KEY (`ua_id`),
  KEY `FK_Relationship_5` (`user_id`),
  KEY `FK_Relationship_6` (`role_id`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Data for the `cfg_result_code` table  (LIMIT 0,500)
#

INSERT INTO `cfg_result_code` (`code`, `note`, `errmessages`, `type`) VALUES 
  ('0','成功',NULL,'0'),
  ('1000','参数错误','尊敬的客户，对不起，充值失败，您所编辑的指令格式不正确','3001'),
  ('10000','未知','','10000'),
  ('10001',NULL,NULL,'10001'),
  ('10002',NULL,NULL,'10002'),
  ('10003',NULL,NULL,'10003'),
  ('10004',NULL,NULL,'10004'),
  ('10005',NULL,NULL,'10005'),
  ('10006',NULL,NULL,'10006'),
  ('10007',NULL,NULL,'10007'),
  ('10008',NULL,NULL,'10008'),
  ('10009',NULL,NULL,'10009'),
  ('10010',NULL,NULL,'10010'),
  ('10011','调用的业务能力未激活',NULL,'10011'),
  ('10012','路由不可达',NULL,'10012'),
  ('10013','目标网元还没有激活',NULL,'10013'),
  ('10014',NULL,NULL,'10014'),
  ('10015',NULL,NULL,'10015'),
  ('10016',NULL,NULL,'10016'),
  ('10017',NULL,NULL,'10017'),
  ('10018',NULL,NULL,'10018'),
  ('10019',NULL,NULL,'10019'),
  ('1002','流水号重复','对不起，操作失败，请咨询10000号','1002'),
  ('10020',NULL,NULL,'10020'),
  ('10021',NULL,NULL,'10021'),
  ('1003','接入省参数格式错误','接入省参数格式错误','1003'),
  ('1004','卡归属省参数格式错误','卡归属省参数格式错误','1004'),
  ('2001','卡密码错误','尊敬的客户，对不起，充值失败，您输入的充值卡密码错误，请重新输入正确的卡密码充值','2001'),
  ('2002','卡归属地区不匹配','充值卡归属地区不匹配','2002'),
  ('2003','卡号不存在','尊敬的客户，对不起，充值失败，您输入的充值卡不存在，请咨询10000号','2003'),
  ('2101','卡未激活','尊敬的客户，对不起，充值失败，您输入的充值卡未激活，请咨询10000号','2001'),
  ('2102','卡被锁定','尊敬的客户，对不起，充值失败，您输入的充值卡被锁定，请咨询10000号','2102'),
  ('2103','卡已被使用','尊敬的客户，对不起，充值失败，该充值卡已经被使用，无法再次充值。','2103'),
  ('2104','卡已过期','尊敬的客户，对不起，充值失败，您输入的充值卡已超过使用期限','2104'),
  ('2105','卡为已注销','尊敬的客户，对不起，充值失败，您输入的卡已被注销','2105'),
  ('2106','在途卡','尊敬的客户，对不起，充值失败，您输入的充值卡状态是在途卡，不能充值','2101'),
  ('2201','专卡专用不提供跨省充值','专卡专用不提供跨省充值','2201'),
  ('2202','卡业务类型受限','对不起，网络故障，请稍候再试','2202'),
  ('2203','卡面额与拆分金额总额不一致','尊敬的客户，对不起，充值失败，您输入的卡面额与拆分金额总额不一致','2203'),
  ('2204','卡使用区域范围受限','尊敬的客户您刚才充值一笔失败，原因是尊敬的客户您刚才充值一笔失败，原因是充值卡使用区域范围受限','2204'),
  ('2205','卡已被解锁','卡已被解锁','2205'),
  ('2301','流水号错误导致卡冲正失败','对不起，系统故障，请稍候再试','2301'),
  ('2302','卡号不符导致卡冲正失败 ','对不起，系统故障，请稍候再试','2302'),
  ('2303','卡信息不符合业务管理规定','对不起，操作失败，请咨询10000号','2303'),
  ('2304','跨省卡鉴权超','尊敬的客户，对不起，充值失败，跨省卡鉴权超时，请咨询10000号','2304'),
  ('2305','跨省卡鉴权失败','尊敬的客户，对不起，充值失败，跨省卡鉴权失败，请咨询10000号','2305'),
  ('2306','跨省卡鉴权冲正失败','对不起，操作失败，请咨询10000号','2306'),
  ('2307','找不到卡归属地','尊敬的客户，对不起，充值失败，操作失败，请咨询10000号','2001'),
  ('3001','用户不存在','尊敬的客户您充值失败，原因是被充值号码不存在！详询10000','3001'),
  ('3002','用户处于冷冻期','尊敬的客户，对不起，充值失败，用户被冷冻','3002'),
  ('3003','用户未使用','对不起，用户未使用','3003'),
  ('3004','用户处于挂失状态','对不起，该用户处于挂失状态','3004'),
  ('3005','用户服务密码不正确','服务密码不正确','3005'),
  ('3006','流水号错误导致帐户冲正失败','对不起，系统故障，请稍候再试','3006'),
  ('3007','跨省后付费用户暂时不提供余额查询','对不起，系统暂时不提供跨省后付费用户余额查询','3007'),
  ('3008','用户进入风险控制名单','对不起，操作失败','3008'),
  ('3009','用户归属地区不匹配','对不起，您输入的被充值号码前没有加区号','3009'),
  ('3010','非中国电信用户','对不起，操作失败','3010'),
  ('3011','找不到用户归属地','对不起，操作失败','3001'),
  ('3012','用户资料错误','用户已存在','3012'),
  ('3013','充值用户受限，不允许充值','充值用户受限，不允许充值（如充值号码是公话、外省电话等或者一个自然月内同一个号码充值3G流量促销卡超过1次等）。','3013'),
  ('3014','用户状态异常','对不起，您所充值的用户状态异常','3001'),
  ('3015','宽带无业务账号','宽带无业务账号','3001'),
  ('3020','公免用户充值受限','尊敬的客户：充值受限，公免用户不能使用11888卡充值。详询10000！','3013'),
  ('3021','租赁用户充值受限','尊敬的客户：充值受限，租赁用户不能使用11888卡充值。详询10000！','3013'),
  ('3022','代办点用户充值受限','尊敬的客户：充值受限，代办点用户不能使用11888卡充值。详询10000！','3013'),
  ('3023','智能网用户充值受限','尊敬的客户：充值受限，智能网用户不能使用11888卡充值。详询10000！','3013'),
  ('3024','业务配置产品规格充值受限','尊敬的客户：充值受限，原因是业务配置产品规格充值受限。详询10000！','3013'),
  ('3025','业务配置帐户标识充值受限','尊敬的客户：充值受限，原因是业务配置帐户标识充值受限。详询10000！','3013'),
  ('3026','业务配置销售品规格充值受限','尊敬的客户：充值受限，原因是业务配置销售品规格充值受限。详询10000！','3013'),
  ('3028','用户号码与产品属性不匹配','用户号码与产品属性不匹配','3028'),
  ('3029','翼支付账户不存在','翼支付账户不存在','3029'),
  ('3030','用户状态锁定','尊敬的客户，对不起，充值失败，用户状态锁定','3030'),
  ('3100','促销流量卡充值限制','尊敬的客户：充值受限，原因是每个自然月只允许充值一次促销类流量卡。详询10000！','3101'),
  ('3101','流量卡业务受限','尊敬的客户：充值受限，原因是当前套餐与流量卡业务互斥等原因。详询10000！','3013'),
  ('3102','停机用户不能使用流量卡充值','尊敬的客户：充值受限，原因是停机用户不能使用流量卡充值。详询10000！','3013'),
  ('3682','小灵通充值受限','你好本次充值失败，小灵通不能使用11888卡充值，请于就近的电信营业厅缴费谢谢','3013'),
  ('3683','对不起，您查询的充值记录笔数过多，请提需求单到IT部查询','对不起，您查询的充值记录笔数过多，请提需求单到IT部查询','3013'),
  ('3914','用户状态异常','用户不存在','3001'),
  ('3915','宽带无业务账号','用户不存在','3001'),
  ('3916','用户状态不可用','用户不存在','3001'),
  ('3917','用户已存在','用户已存在','3012'),
  ('3918','用户与用户属性不匹配','用户不存在','3001'),
  ('4001','VC平台内部操作超时','尊敬的客户，对不起，充值失败，系统繁忙，余额查询失败','4001'),
  ('4002','VC平台内部错误，操作失败','尊敬的客户，对不起，充值失败，系统故障，请稍候再试','4002'),
  ('4003','系统繁忙','尊敬的客户，对不起，充值失败，系统繁忙','3013'),
  ('4004','VC平台数据库操作失败','尊敬的客户，对不起，充值失败，系统繁忙','4004'),
  ('4005','VC平台写文件错误','对不起，操作失败，请咨询10000号','4005'),
  ('4006','VC平台处于升级或维护期，暂时不能充值 ','对不起，系统正在升级，详情请咨询10000号','4006'),
  ('4007','VC卡归属平台内部操作超时','对不起，系统繁忙，余额查询失败','4007'),
  ('4008','VC卡归属平台内部错误，操作失败','对不起，系统故障，请稍候再试','4008'),
  ('4009','卡归属系统繁忙','对不起，系统繁忙','4009'),
  ('4010','VC卡归属平台数据库操作失败','系统内部错误','4010'),
  ('4011','VC卡归属平台写文件错误','对不起，操作失败，请咨询10000号','4011'),
  ('4012','VC卡归属平台处于升级或维护期，暂时不能充值 ','对不起，系统正在升级，详情请咨询10000号','4012'),
  ('4013','接入超时','对不起，系统繁忙','4013'),
  ('4101','余额归属系统超时','尊敬的客户，对不起，充值失败，系统繁忙，请咨询10000号','3013'),
  ('4102','计费系统数据库错误','尊敬的客户，对不起，充值失败，系统繁忙，请咨询10000号','4102'),
  ('4103','计费系统内部错误','尊敬的客户，对不起，充值失败，系统繁忙，请咨询10000号','3013'),
  ('4104','VC与计费系统连接异常','尊敬的客户，对不起，充值失败，系统繁忙，请咨询10000号','3013'),
  ('4105','充值失败','尊敬的客户，对不起，充值失败，系统繁忙，请咨询10000号','3013'),
  ('4106','充值失败，不支持客户级充值','尊敬的客户，对不起，充值失败，系统繁忙，请咨询10000号','4106'),
  ('4107','冲正失败','尊敬的客户，对不起，充值失败，系统繁忙，请咨询10000号','4107'),
  ('4108','查帐户信息失败','尊敬的客户，对不起，充值失败，系统繁忙，请咨询10000号','4108'),
  ('4109','锁帐户失败','尊敬的客户，对不起，充值失败，系统繁忙，请咨询10000号','3013'),
  ('4110','无法新增账本','对不起，操作失败，请咨询10000号','4110'),
  ('4111','充值失败，对应的余额类型不存在','对不起，操作失败，请咨询10000号','4111'),
  ('4112','计费系统处于出帐或维护期，暂时不能充值','对不起，操作失败，请咨询10000号','4112'),
  ('4113','账户下付费用户过多，不能充值','对不起，操作失败，请咨询10000号','4113'),
  ('4201','用户鉴权系统超时','对不起，操作失败，请咨询10000号','4201'),
  ('4202','VC与用户鉴权系统连接异常','对不起，操作失败，请咨询10000号','4202'),
  ('4203','用户鉴权系统数据库错误','尊敬的客户，对不起，充值失败，系统繁忙，请咨询10000号','3013'),
  ('4204','用户鉴权系统内部错误','尊敬的客户，对不起，充值失败，系统繁忙，请咨询10000号','4204'),
  ('4205','帐号找不到相应的余额归属系统','尊敬的客户，对不起，充值失败，系统繁忙，请咨询10000号','4205'),
  ('4304','DCC接口异常','对不起，系统繁忙，余额查询失败','4001'),
  ('5001','超过被充值用户的最大限额','对不起，您所充值的用户超过被充值用户的最大限额','5001'),
  ('5002','批量充值不可拆分充值','对不起，批量充值不可拆分充值','5002'),
  ('5003','拆分充值中某笔失败导致整体回滚','对不起，操作失败','5003'),
  ('5004','除了ivr其他接入方式不支持被充帐号是外省的','除了IVR其他接入方式不支持被充帐号是外省的','3013'),
  ('5005','拆分充值时充值失败没有处理余下的充值','对不起，系统故障，请稍候再试','2203'),
  ('5007','帐号没有相应的付费系统','对不起，系统故障，请使用其他方式充值','3001'),
  ('5008','外省用户不支持充值记录查询','对不起，系统暂不提供外省用户对充值记录查询','3013'),
  ('5009','外省用户不可通过自服务平台进行余额查询','对不起，系统暂不提供外省用户通过自服务平台进行余额查询','3013'),
  ('5011','充值记录不存在','对不起，没有查到相应的充值记','3013'),
  ('5012','ivr跨省不能给附属产品充值','对不起，系统暂不提供IVR跨省对附属产品充值','3013'),
  ('5013','卡号不存在','对不起，卡号无效','3013'),
  ('5014 ',' ivr sms ussd接入方式不支持批量和拆分充值','对不起，系统不支持批量和拆分充值','3013'),
  ('5015','充值卡没有余额','充值卡无效或已使用','2303'),
  ('5016','ivr/crm不支持附属产品充值','对不起，系统不支持附属产品充值','3013'),
  ('5017','该接入号不在黑名单','该接入号不在黑名单中','3013'),
  ('5018','卡的有效时间异','卡的有效时间异','2303'),
  ('5019','卡的面额异常','卡的面额异常','2303'),
  ('5021','分拆卡不支持网厅以外接入方式充值','对不起，操作失败','2203'),
  ('5022','卡余额小于充值总金额','对不起，操作失败','2203'),
  ('5023','充值金额不等于卡面额，卡不支持多次充值，需一次充完','对不起，操作失败','2203'),
  ('5025','不能为外省卡进行密码重置','对不起，操作失败','5025'),
  ('5026','设置的密码前缀与平台编码不符,设置无效','对不起，操作失败','5026'),
  ('5027','设置的密码已存在,请重新设置','对不起，操作失败','5027'),
  ('5028','计费超时用户存在的充值成功','对不起，操作失败','5028'),
  ('5029','密码更新失败,请重新设置','对不起，操作失败','5029'),
  ('5030','外省卡不是全国卡不支持拆分和批量充值','对不起，操作失败','3013'),
  ('5031','充值失败已回滚','充值失败已回滚','3013'),
  ('5032','省外卡信息查询','卡使用区域范围受限','2204'),
  ('5033','没有配置相应的资料更新信息','对不起，您所编辑的指令格式不正确','1000'),
  ('5034','请求包为空','对不起，您所编辑的指令格式不正确','1000'),
  ('5038','不在冲正有效期内','对不起，操作失败，请咨询10000号。','4107'),
  ('5501','统一支付接口超时','统一支付接口超时','5501'),
  ('5502','统一支付数据校验错误','统一支付数据校验错误','5502'),
  ('5503','统一支付其他错误','统一支付其他错误','5503'),
  ('5504','统一支付接口异常','统一支付接口异常','5504'),
  ('5505','不支持外省用户银行卡充值','不支持外省用户银行卡充值','5505'),
  ('6000','卡鉴权被冲正成功改变数据库中的结果','卡鉴权被冲正成功改变数据库中的结果','0'),
  ('6001','翼支付冲正超时或异常充值为成功','翼支付冲正超时或异常充值为成功','0'),
  ('6002','重单判断出错','重单判断出错','3013'),
  ('6003','重单判断返回有误','重单判断返回有误','3013'),
  ('6004','开通失败','开通失败','3013'),
  ('6005','存入限制','存入限制','3013'),
  ('6006','查帐户失败','查帐户失败','3013'),
  ('6007','锁帐户失败','锁帐户失败','3013'),
  ('6008','存入失败','存入失败','3013'),
  ('6009','存入成功，但查询余额失败','存入成功，但查询余额失败','0'),
  ('6010','租赁电话判断失败','租赁电话判断失败','3013'),
  ('6011','冲正初始化失败','冲正初始化失败','3013'),
  ('6012','冲正失败','冲正失败','3013'),
  ('6013','写日志失败','写日志失败','3013'),
  ('6014','读日志失败','读日志失败','3013'),
  ('6015','冲正金额不一致','对不起，操作失败','3013'),
  ('6016','充值开通用户','充值付费已成功,您的被充值号码将在24小时内开通','0'),
  ('7001','找不到本地余额类型','找不到本地余额类型','3013'),
  ('7002','流水重复','流水重复','3013'),
  ('7003','已经反销帐过','已经反销帐过','3013'),
  ('7004','反消失败找不到原流水','反消失败找不到原流水','3013'),
  ('7005','不是当日交易的不能冲正','不是当日交易的不能冲正','3013'),
  ('7006','反消失败与原来费用不合','反消失败与原来费用不合','3013'),
  ('7007','反消失败现在金额不足反消','反消失败现在金额不足反消','3013'),
  ('7008','充值失败','充值失败','3013'),
  ('8000','请求包为空','对不起，系统故障，请稍候再试','3013'),
  ('8001','冲值流水号重复','冲值流水号重复','3013'),
  ('8002','用于返销的流水异常','用于返销的流水异常','3013'),
  ('8003','返销帐失败，找无相关记录','返销帐失败，找无相关记录','3013'),
  ('8004','余额不足以反销帐','余额不足以反销帐','3013'),
  ('8005','电话号码不存在','电话号码不存在','3001'),
  ('9000','请求包为空','请求包为空','1000'),
  ('9001','没有配置相应的资料更新信息','没有配置相应的资料更新信息','3001'),
  ('91','数据格式错误','数据格式错误','91'),
  ('91000','交易失败','交易失败','91000'),
  ('91001','消费失败','消费失败','91001'),
  ('91002','转账失败','转账失败','91002'),
  ('91003','授权失败','授权失败','91003'),
  ('91004','帐户查询失败','帐户查询失败','91004'),
  ('91005','冲正失败','冲正失败','91005'),
  ('91006','挂失失败','挂失失败','91006'),
  ('91007','绑定失败','绑定失败','91007'),
  ('91008','签到失败','签到失败','91008'),
  ('91009','综合业务查询失败','综合业务查询失败','91009'),
  ('91010','缴费失败','缴费失败','91010'),
  ('91011','充值失败','充值失败','91011'),
  ('91012','因冲正引起的交易失败','因冲正引起的交易失败','91012'),
  ('91013','金额超出限制','金额超出限制','91013'),
  ('91014','支付记录非法','支付记录非法','91014'),
  ('91015','非当前应用系统支付记录','非当前应用系统支付记录','91015'),
  ('91016','非当前应用用户系统支付记录','非当前应用用户系统支付记录','91016'),
  ('91017','用户取消交易','用户取消交易','91017'),
  ('91018','交易记录不存在','交易记录不存在','91018'),
  ('91200','消费接口超时','消费接口超时','91200'),
  ('91201','转账接口超时','转账接口超时','91201'),
  ('91202','授权接口超时','授权接口超时','91202'),
  ('91203','帐户查询接口超时','帐户查询接口超时','91203'),
  ('91204','冲正接口超时','冲正接口超时','91204'),
  ('91205','挂失接口超时','挂失接口超时','91205'),
  ('91206','绑定接口操作','绑定接口操作','91206'),
  ('91207','签到接口超时','签到接口超时','91207'),
  ('91208','综合业务查询超时','综合业务查询超时','91208'),
  ('91209','缴费接口超时','缴费接口超时','91209'),
  ('91210','充值接口超时','充值接口超时','91210'),
  ('91211','通知接口超时','通知接口超时','91211'),
  ('91212','适配层接口超时','适配层接口超时','91212'),
  ('91400','无效交易','无效交易','91400'),
  ('91401','无效金额','无效金额','91401'),
  ('91402','无效卡号（无此账号）','无效卡号（无此账号）','91402'),
  ('91403','无此发卡方','无此发卡方','91403'),
  ('91404','非正常的关联交易','非正常的关联交易','91404'),
  ('91405','找不到原始交易','找不到原始交易','91405'),
  ('91406','交换中心不支持的银行','交换中心不支持的银行','91406'),
  ('91407','过期的卡','过期的卡','91407'),
  ('91408','受限制的卡','受限制的卡','91408'),
  ('91409','请求的功能尚不支持','请求的功能尚不支持','91409'),
  ('91410','挂失卡','挂失卡','91410'),
  ('91411','无此账户','无此账户','91411'),
  ('91412','被窃卡','被窃卡','91412'),
  ('91413','无此投资账户','无此投资账户','91413'),
  ('91414','资金不足','资金不足','91414'),
  ('91415','无此支票账户','无此支票账户','91415'),
  ('91416','无此储蓄卡账户','无此储蓄卡账户','91416'),
  ('91417','过期的卡','过期的卡','91417'),
  ('91418','密码错误','密码错误','91418'),
  ('91419','不允许持卡人进行的交易','不允许持卡人进行的交易','91419'),
  ('91420','超出金额限制','超出金额限制','91420'),
  ('91421','受限制的卡','受限制的卡','91421'),
  ('91422','无效账户','无效账户','91422'),
  ('91423','转入行无此账户','能力类型不存在转入行无此账户','91423'),
  ('91424','用户密码错误','用户密码错误','91424'),
  ('91425','转入卡无效','转入卡无效','91425'),
  ('91426','行业后台超时','行业后台超时','91426'),
  ('91427','公免公纳用户不允许扣费','公免公纳用户不允许扣费','91427'),
  ('91428','销帐金额大于透支额度，销帐失败','销帐金额大于透支额度，销帐失败','91428'),
  ('91429','明细超长须柜面缴纳','明细超长须柜面缴纳','91429'),
  ('91430','用户未欠费，无法缴纳','用户未欠费，无法缴纳','91430'),
  ('91431','该户正在代缴中（账户锁定），不能进行自助缴费','该户正在代缴中（账户锁定），不能进行自助缴费','91431'),
  ('91432','失败（行业系统出错）','失败（行业系统出错）','91432'),
  ('91433','该用户缴纳金额已变动，需重新查询缴纳','该用户缴纳金额已变动，需重新查询缴纳','91433'),
  ('91434','该用户缴费方式为上门申报，需按月缴税','该用户缴费方式为上门申报，需按月缴税','91434'),
  ('91435','终端密钥已过期，请重新签到','终端密钥已过期，请重新签到','91435'),
  ('91436','交易违法，不能完成','交易违法，不能完成','91436'),
  ('91437','失败（行业系统出错）','失败（行业系统出错）','91437'),
  ('91438','失败（行业系统出错）','失败（行业系统出错）','91438'),
  ('91439','不予承兑','不予承兑','91439'),
  ('91440','允许的输入PIN 次数超限','允许的输入PIN 次数超限','91440'),
  ('91441','转出卡不支持（贷记卡）','转出卡不支持（贷记卡）','91441'),
  ('91442','该转入卡交易不支持（非还贷卡等）','该转入卡交易不支持（非还贷卡等）','91442'),
  ('91443','超过交易次数','超过交易次数','91443'),
  ('91444','转出卡无权限','转出卡无权限','91444'),
  ('91445','操作员不存在','操作员不存在','91445'),
  ('91446','资金已结算或冻结，无法撤销','资金已结算或冻结，无法撤销','91446'),
  ('91447','交易超时，请联系转入机构核实交易是否成功','交易超时，请联系转入机构核实交易是否成功','91447'),
  ('91448','信息不一致，请重新交易','信息不一致，请重新交易','91448'),
  ('91449','数量不足，无法交易','数量不足，无法交易','91449'),
  ('91450','终端不支持该行业缴费交易','终端不支持该行业缴费交易','91450'),
  ('91900','无效冲正交易号','无效冲正交易号','91900'),
  ('91901','无需冲正的交易','无需冲正的交易','91901'),
  ('91902','无效冲正时限','无效冲正时限','91902'),
  ('91903','待冲正的记录非法','待冲正的记录非法','91903'),
  ('92','能力类型不存在','能力类型不存在','92'),
  ('92000','服务商不存在','服务商不存在','92000'),
  ('92001','服务商状态不正常','服务商状态不正常','92001'),
  ('92002','服务商未生效','服务商未生效','92002'),
  ('92003','服务商接口地址无效','服务商接口地址无效','92003'),
  ('92004','服务不存在','服务不存在','92004'),
  ('92005','服务状态不正常','服务状态不正常','92005'),
  ('92006','服务未生效','服务未生效','92006'),
  ('92007','服务商已经存在','服务商已经存在','92007'),
  ('92008','服务已经存在','服务已经存在','92008'),
  ('92500','无此商品类型','无此商品类型','92500'),
  ('92501','商品不存在','商品不存在','92501'),
  ('92502','商品过期','商品过期','92502'),
  ('92503','商品库存不足','商品库存不足','92503'),
  ('92700','应用平台不存在','应用平台不存在','92700'),
  ('92701','应用平台状态不正常','应用平台状态不正常','92701'),
  ('92702','应用平台IP受限','应用平台IP受限','92702'),
  ('92900','无此受理单','无此受理单','92900'),
  ('92901','受理单已结束','受理单已结束','92901'),
  ('92902','非当前服务商受理单','非当前服务商受理单','92902'),
  ('92903','非当前服务商受理单','非当前服务商受理单','92903'),
  ('92904','非当前应用系统用户受理单','非当前应用系统用户受理单','92904'),
  ('92905','非当前服务受理单','非当前服务受理单','92905'),
  ('92906','受理单校验失败','受理单校验失败','92906'),
  ('93','密钥验证失败','密钥验证失败','93'),
  ('93000','黑名单用户','黑名单用户','93000'),
  ('93001','达到单次支付上限','达到单次支付上限','93001'),
  ('93002','达到日支付上限','达到日支付上限','93002'),
  ('93003','达到月支付上限','达到月支付上限','93003'),
  ('93004','未绑定帐户','未绑定帐户','93004'),
  ('93005','未开通帐户','未开通帐户','93005'),
  ('93006','用户不支持该交易','用户不支持该交易','93006'),
  ('93007','用户状态异常','用户状态异常','93007'),
  ('93008','用户已绑定无法重新绑定','用户已绑定无法重新绑定','93008'),
  ('93009','用户绑定多张卡异常','用户绑定多张卡异常','93009'),
  ('93010','同步失败','同步失败','93010'),
  ('93500','路由地址错误','路由地址错误','93500'),
  ('93501','路由地址不存在','路由地址不存在','93501'),
  ('93502','帐户无对应路由地址','帐户无对应路由地址','93502'),
  ('93511','订单未支付','订单未支付','93511'),
  ('94','IP、SPID绑定校验失败','IP、SPID绑定校验失败','94'),
  ('95','数据加密失败','数据加密失败','95'),
  ('9500','订单格式错误','订单格式错误','9500'),
  ('9501','订单过期','订单过期','9501'),
  ('9502','订单已支付','订单已支付','9502'),
  ('9503','订单已存在','订单已存在','9503'),
  ('9504','订单不存在','订单不存在','9504'),
  ('9505','支付成功通知应用平台失败','支付成功通知应用平台失败','9505'),
  ('9506','订单下发失败','订单下发失败','9506'),
  ('9507','订单下发接口超时','订单下发接口超时','9507'),
  ('9508','订单支付通知接口超时','订单支付通知接口超时','9508'),
  ('9509','订单支付金额不足','订单支付金额不足','9509'),
  ('9510','超出订单支付金额','超出订单支付金额','9510'),
  ('9511','订单未支付','订单未支付','9511'),
  ('9512','订单已达最大重发次数','订单已达最大重发次数','9512'),
  ('9513','订单信息错误','订单信息错误','9513'),
  ('9514','订单待支付','订单待支付','9514'),
  ('96','数据解密失败','数据解密失败','96'),
  ('97','数据校验失败','数据校验失败','97'),
  ('97000','数据库操作失败','数据库操作失败','97000'),
  ('97001','系统退出','系统退出','97001'),
  ('97002','系统初始化失败','系统初始化失败','97002'),
  ('97003','系统异常','系统异常','97003'),
  ('97004','系统繁忙','系统繁忙','97004'),
  ('97005','系统服务地址配置异常','系统服务地址配置异常','97005'),
  ('97006','系统内部模块服务异常','系统内部模块服务异常','97006'),
  ('98','校验码错误','校验码错误','98'),
  ('99','无校验码','无校验码','99'),
  ('999','其他错误','其他错误','999'),
  ('99998','链路异常','链路异常','99998');
COMMIT;

#
# Data for the `ec_accss_mode` table  (LIMIT 0,500)
#

INSERT INTO `ec_accss_mode` (`accss_id`, `name`, `alias`, `status`, `note`, `taxis`) VALUES 
  (1,'查看','see','y','99999',NULL),
  (2,'可视','view','y','99999',NULL);
COMMIT;

#
# Data for the `ec_common_seq` table  (LIMIT 0,500)
#

INSERT INTO `ec_common_seq` (`pk_key`, `pk_value`) VALUES 
  ('aq_flower_order',19),
  ('aq_mail_notices',34),
  ('aq_order',86),
  ('aq_order_delivery',290),
  ('aq_order_trackingId',68),
  ('aq_user_account_tranfer',8),
  ('aq_user_consignee',16),
  ('aq_wechat_notices',15),
  ('ums_ec_accss_mode',115),
  ('ums_ec_group',116),
  ('ums_ec_group_assignment',110),
  ('ums_ec_objects',171),
  ('ums_ec_operator',136),
  ('ums_ec_permission',128),
  ('ums_ec_resource',110),
  ('ums_ec_role',116),
  ('ums_ec_user',120),
  ('ums_ec_users_assignmen',121),
  ('ums_ec_user_group',116);
COMMIT;

#
# Data for the `ec_group` table  (LIMIT 0,500)
#

INSERT INTO `ec_group` (`group_id`, `name`, `alias`, `parent_id`, `note`, `taxis`, `type`, `op_time`, `full_name`, `group_path`, `note2`, `note3`) VALUES 
  (1,'UMS系统用户组','1',-1,'',1,'01','2015-04-30','UMS系统用户组','1','',''),
  (110,'智信公司用户组','110',-1,'',2,'01','2015-08-06','智信公司用户组','110','',''),
  (112,'开发组','110-112',110,'',2,'01','2015-04-30','VC用户组/开发组','110-112','',''),
  (113,'维护组','110-113',110,'',1,'01','2015-04-30','VC用户组/维护组','110-113','',''),
  (114,'商务组','110-114',110,'',3,'01','2015-08-06','智信公司用户组/商务组','110-114','',''),
  (115,'代理商用户组','115',-1,'',3,'01','2015-08-06','代理商用户组','115','','');
COMMIT;

#
# Data for the `ec_objects` table  (LIMIT 0,500)
#

INSERT INTO `ec_objects` (`obj_id`, `name`, `alias`, `type`, `icon`, `status`, `parent_id`, `note`, `taxis`, `op_time`, `note2`, `note3`) VALUES 
  (1,'UMS管理系统','ec-ums','ecums',NULL,'y',-1,'ec-ums权限',1,'2015-04-30','',''),
  (2,'对象操作管理','obj_accordion','ecums',NULL,'y',1,'Ext.get(''obj_accordion'')?Ext.get(''obj_accordion'').dom.innerHTML:''''',99999,'2011-04-19',NULL,NULL),
  (3,'用户组织管理','user_accordion','ecums',NULL,'y',1,'Ext.get(''user_accordion'')?Ext.get(''user_accordion'').dom.innerHTML:''''',99999,'2011-04-19',NULL,NULL),
  (4,'权限分配管理','per_accordion','ecums',NULL,'y',1,'Ext.get(''per_accordion'')?Ext.get(''per_accordion'').dom.innerHTML:''''',99999,'2011-04-19',NULL,NULL),
  (5,'资源对象管理','resource_menu','ecums',NULL,'y',2,'createObjectsMainPanel();',0,'2015-02-26','',''),
  (6,'操作管理','opt_menu','ecums',NULL,'y',2,'createAccssModeMainPanel();',99999,'2011-04-19',NULL,NULL),
  (7,'用户组管理','group_menu','ecums',NULL,'y',3,'createGroupsMainPanel();',99999,'2011-04-19',NULL,NULL),
  (8,'用户管理','user_menu','ecums',NULL,'y',3,'createUserMainPanel();',99999,'2011-04-19',NULL,NULL),
  (9,'角色管理','role_menu','ecums',NULL,'y',4,'createRoleMainPanel();',99999,'2011-04-19',NULL,NULL),
  (10,'权限检索','role_search','ecums',NULL,'y',4,'createSearchMainPanel()',99999,'2011-04-19',NULL,NULL),
  (110,'后台菜单管理','console-menu','ecums',NULL,'y',-1,'',2,'2015-08-06','',''),
  (111,'代理商管理','c-business-agent-menu','ecums',NULL,'y',113,'{url:''console/opAgent.do?action=index'',reload:true}',1,'2015-08-06','',''),
  (113,'业务管理','c-business-menu','ecums',NULL,'y',110,'',1,'2015-08-06','',''),
  (114,'业务参数管理','bs-param-cfg','ecums',NULL,'y',-1,'',3,'2015-08-06','',''),
  (146,'统计报表','c-report-menu','ecums',NULL,'y',110,'',2,'2015-08-06','',''),
  (147,'充值记录查询','c-report-recharegsearch-menu','ecums',NULL,'y',146,'{url:''console/opChargeSearch.do?action=index'',reload:true}',99999,'2015-08-06','',''),
  (167,'参数配置','c-param-menu','ecums',NULL,'y',110,'',99999,'2015-08-06','',''),
  (168,'错误码配置','vc-param-resultcode-menu','ecums',NULL,'y',167,'{url:''console/resultCode/''}',11,'2015-05-05','',''),
  (170,'代理商加款','c-business-agent-savemoney-menu','ecums',NULL,'y',113,'',99999,'2015-08-06','','');
COMMIT;

#
# Data for the `ec_operator` table  (LIMIT 0,500)
#

INSERT INTO `ec_operator` (`oper_id`, `obj_id`, `accss_id`) VALUES 
  (2,1,2),
  (3,2,2),
  (4,5,2),
  (5,6,2),
  (6,3,2),
  (7,7,2),
  (8,8,2),
  (9,4,2),
  (10,9,2),
  (11,10,2),
  (110,110,2),
  (111,111,2),
  (113,113,2),
  (117,146,2),
  (118,147,2),
  (132,168,2),
  (133,167,2),
  (135,170,2);
COMMIT;

#
# Data for the `ec_permission` table  (LIMIT 0,500)
#

INSERT INTO `ec_permission` (`pa_id`, `role_id`, `oper_id`, `op_time`) VALUES 
  (1,1,1,'2009-07-27'),
  (2,1,3,'2011-04-19'),
  (3,1,4,'2011-04-19'),
  (4,1,5,'2011-04-19'),
  (5,1,6,'2011-04-19'),
  (6,1,7,'2011-04-19'),
  (7,1,8,'2011-04-19'),
  (8,1,10,'2011-04-19'),
  (9,1,11,'2011-04-19'),
  (10,1,9,'2011-04-19'),
  (110,111,110,'2014-07-10'),
  (111,111,111,'2014-07-10'),
  (113,111,113,'2014-07-10'),
  (117,111,117,'2015-02-05'),
  (118,111,118,'2015-02-05'),
  (124,111,133,'2015-05-04'),
  (125,111,132,'2015-05-04'),
  (127,111,135,'2015-08-06');
COMMIT;

#
# Data for the `ec_role` table  (LIMIT 0,500)
#

INSERT INTO `ec_role` (`role_id`, `name`, `alias`, `status`, `parent_id`, `note`, `taxis`, `op_time`) VALUES 
  (1,'ums系统管理员角色','ums-admin','y',110,'',99999,'2015-05-04'),
  (110,'ums系统角色','ums-role','y',-1,'',99999,'2015-05-04'),
  (111,'系统管理员角色','zxpay-admin-role','y',114,'',99999,'2015-08-06'),
  (114,'智信话充系统角色','zxpay-role','y',-1,'',99999,'2015-08-06'),
  (115,'代理商角色','zxpay-agent-role','y',114,'',99999,'2015-08-06');
COMMIT;

#
# Data for the `ec_user` table  (LIMIT 0,500)
#

INSERT INTO `ec_user` (`user_id`, `name`, `alias`, `job_number`, `password`, `email`, `status`, `note`, `taxis`, `op_time`) VALUES 
  (1,'超级管理员','root',NULL,'C6285F69B6765A5CDD3B09556D84C5BE','root@echoice.kmip.net','y','',99999,'2009-07-27'),
  (110,'admin','admin','','9116392DC24B7B84483BA00B0D72B80C','','y','',99999,'2015-03-12'),
  (111,'大侠1','testdx1','','64669F7FE1DA1C484F67E1A3A7BFE9D9','47583048@qq.com','y','',99999,'2015-02-15'),
  (112,'大侠2','testdx2','','BC0F398D455CFFD013B67ADA5513D79F','junyangren@qq.com','y','',99999,'2015-03-12');
COMMIT;

#
# Data for the `ec_user_extend` table  (LIMIT 0,500)
#

INSERT INTO `ec_user_extend` (`user_id`, `mobile`, `tel`, `address`, `qq`, `msn`, `duty`, `leader_id`) VALUES 
  (1,'','',NULL,'','','',NULL),
  (110,'','',NULL,'','','',NULL),
  (111,'','',NULL,'','','',NULL),
  (112,'','',NULL,'','','',NULL),
  (117,'','',NULL,'','','',NULL),
  (119,'','',NULL,'','','',NULL);
COMMIT;

#
# Data for the `ec_user_group` table  (LIMIT 0,500)
#

INSERT INTO `ec_user_group` (`ug_id`, `group_id`, `user_id`) VALUES 
  (1,1,1),
  (110,110,110),
  (111,111,111),
  (112,111,112);
COMMIT;

#
# Data for the `ec_users_assignmen` table  (LIMIT 0,500)
#

INSERT INTO `ec_users_assignmen` (`ua_id`, `user_id`, `role_id`, `op_time`) VALUES 
  (1,1,1,NULL),
  (110,110,1,'2014-07-10'),
  (111,110,111,'2014-07-10');
COMMIT;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;