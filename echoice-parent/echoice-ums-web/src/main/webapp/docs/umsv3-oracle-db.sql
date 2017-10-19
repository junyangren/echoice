----------------------------------------------------
-- Export file for user GXCCM                     --
-- Created by Administrator on 2016/5/16, 8:38:54 --
----------------------------------------------------

spool ums-oracle-db_.log

prompt
prompt Creating table EC_ACCSS_MODE
prompt ============================
prompt
create table EC_ACCSS_MODE
(
  ACCSS_ID INTEGER not null,
  NAME     VARCHAR2(64),
  ALIAS    VARCHAR2(64),
  STATUS   VARCHAR2(4),
  NOTE     VARCHAR2(256),
  TAXIS    INTEGER default 99999
)
;
alter table EC_ACCSS_MODE
  add constraint PK_EC_ACCSS_MODE primary key (ACCSS_ID);
create index INDEX_ACCSS_MODE on EC_ACCSS_MODE (ALIAS);

prompt
prompt Creating table EC_COMMON_SEQ
prompt ============================
prompt
create table EC_COMMON_SEQ
(
  PK_KEY   VARCHAR2(32) not null,
  PK_VALUE NUMBER(11) default 1
)
;
alter table EC_COMMON_SEQ
  add constraint PK_EC_COMMON_SEQ primary key (PK_KEY);

prompt
prompt Creating table EC_GROUP
prompt =======================
prompt
create table EC_GROUP
(
  GROUP_ID   INTEGER not null,
  NAME       VARCHAR2(128),
  ALIAS      VARCHAR2(128),
  PARENT_ID  INTEGER,
  NOTE       VARCHAR2(256),
  TAXIS      INTEGER default 99999,
  OP_TIME    DATE,
  FULL_NAME  VARCHAR2(512),
  TYPE       VARCHAR2(4),
  GROUP_PATH VARCHAR2(512),
  NOTE2      VARCHAR2(256),
  NOTE3      VARCHAR2(256)
)
;
alter table EC_GROUP
  add constraint PK_EC_GROUP primary key (GROUP_ID);
create index INDEX_EC_GROUP on EC_GROUP (ALIAS);

prompt
prompt Creating table EC_GROUP_AREA
prompt ============================
prompt
create table EC_GROUP_AREA
(
  AREA             VARCHAR2(4) not null,
  NAME             VARCHAR2(64),
  GROUP_ID         INTEGER not null,
  ORDERNO          NUMBER(5) default 0,
  AREA_JM          VARCHAR2(10),
  UQCOMPANYID      VARCHAR2(64),
  CRM_AREA         VARCHAR2(4),
  REQUEST_IP       VARCHAR2(20),
  APPLICANT        VARCHAR2(20),
  STAFFID          VARCHAR2(20),
  DELIVERY_ADDRES  VARCHAR2(512),
  DELIVERY_RECEIVE VARCHAR2(64),
  DELIVERY_PHONE   VARCHAR2(20),
  POST_CODE        VARCHAR2(6),
  TEL_NUMBER       VARCHAR2(15)
)
;
comment on column EC_GROUP_AREA.AREA
  is '区号';
comment on column EC_GROUP_AREA.NAME
  is '地区名';
comment on column EC_GROUP_AREA.GROUP_ID
  is '组织架构ID，关联EC_GROUP表GROUP_ID';
comment on column EC_GROUP_AREA.ORDERNO
  is '排序';
comment on column EC_GROUP_AREA.AREA_JM
  is '地区简码';
comment on column EC_GROUP_AREA.UQCOMPANYID
  is '公司ID（凭证用）';
comment on column EC_GROUP_AREA.CRM_AREA
  is 'CRM地区编码';
comment on column EC_GROUP_AREA.REQUEST_IP
  is '请求IP';
comment on column EC_GROUP_AREA.APPLICANT
  is '申请人';
comment on column EC_GROUP_AREA.STAFFID
  is '省卡管工号，由集团全国卡管平台统一分配';
comment on column EC_GROUP_AREA.DELIVERY_ADDRES
  is '收货地址';
comment on column EC_GROUP_AREA.DELIVERY_RECEIVE
  is '收货人';
comment on column EC_GROUP_AREA.DELIVERY_PHONE
  is '收货人联系电话';
comment on column EC_GROUP_AREA.POST_CODE
  is '邮政编码';

prompt
prompt Creating table EC_ROLE
prompt ======================
prompt
create table EC_ROLE
(
  ROLE_ID   INTEGER not null,
  NAME      VARCHAR2(64),
  ALIAS     VARCHAR2(64),
  STATUS    VARCHAR2(4),
  PARENT_ID INTEGER,
  NOTE      VARCHAR2(256),
  TAXIS     INTEGER default 99999,
  OP_TIME   DATE
)
;
alter table EC_ROLE
  add constraint PK_EC_ROLE primary key (ROLE_ID);

prompt
prompt Creating table EC_GROUP_ASSIGNMENT
prompt ==================================
prompt
create table EC_GROUP_ASSIGNMENT
(
  GA_ID    INTEGER not null,
  GROUP_ID INTEGER,
  ROLE_ID  INTEGER,
  OP_TIME  DATE
)
;
alter table EC_GROUP_ASSIGNMENT
  add constraint PK_EC_GROUP_ASSIGNMENT primary key (GA_ID);
alter table EC_GROUP_ASSIGNMENT
  add constraint FK_EC_GROUP_RELATIONS_EC_ROLE foreign key (ROLE_ID)
  references EC_ROLE (ROLE_ID);
create index RELATIONSHIP_12_FK on EC_GROUP_ASSIGNMENT (ROLE_ID);
create index RELATIONSHIP_13_FK on EC_GROUP_ASSIGNMENT (GROUP_ID);

prompt
prompt Creating table EC_LOG4J_LOG
prompt ===========================
prompt
create table EC_LOG4J_LOG
(
  LOG_ID  NUMBER(9) not null,
  MSG     CLOB,
  OP_TIME DATE
)
;
alter table EC_LOG4J_LOG
  add constraint PK_LOGID primary key (LOG_ID);

prompt
prompt Creating table EC_OBJECTS
prompt =========================
prompt
create table EC_OBJECTS
(
  OBJ_ID    INTEGER not null,
  NAME      VARCHAR2(64),
  ALIAS     VARCHAR2(64),
  TYPE      VARCHAR2(30),
  ICON      VARCHAR2(512),
  STATUS    VARCHAR2(4),
  PARENT_ID INTEGER,
  NOTE      VARCHAR2(512),
  TAXIS     INTEGER default 99999,
  OP_TIME   DATE,
  NOTE2     VARCHAR2(512),
  NOTE3     VARCHAR2(512)
)
;
alter table EC_OBJECTS
  add constraint PK_EC_OBJECTS primary key (OBJ_ID);
create index INDEX_OBJECTS on EC_OBJECTS (ALIAS, PARENT_ID);
create index INDEX_OBJECTS_TYPE on EC_OBJECTS (TYPE);

prompt
prompt Creating table EC_OPERATOR
prompt ==========================
prompt
create table EC_OPERATOR
(
  OPER_ID  INTEGER not null,
  OBJ_ID   INTEGER,
  ACCSS_ID INTEGER
)
;
alter table EC_OPERATOR
  add constraint PK_EC_OPERATOR primary key (OPER_ID);
alter table EC_OPERATOR
  add constraint FK_EC_OPERA_RELATIONS_EC_ACCSS foreign key (ACCSS_ID)
  references EC_ACCSS_MODE (ACCSS_ID);
create index RELATIONSHIP_1_FK on EC_OPERATOR (OBJ_ID);
create index RELATIONSHIP_2_FK on EC_OPERATOR (ACCSS_ID);

prompt
prompt Creating table EC_PERMISSION
prompt ============================
prompt
create table EC_PERMISSION
(
  PA_ID   INTEGER not null,
  ROLE_ID INTEGER,
  OPER_ID INTEGER,
  OP_TIME DATE
)
;
alter table EC_PERMISSION
  add constraint PK_EC_PERMISSION primary key (PA_ID);
alter table EC_PERMISSION
  add constraint FK_EC_PERMI_RELATIONS_EC_ROLE foreign key (ROLE_ID)
  references EC_ROLE (ROLE_ID);
create index RELATIONSHIP_11_FK on EC_PERMISSION (OPER_ID);
create index RELATIONSHIP_3_FK on EC_PERMISSION (ROLE_ID);

prompt
prompt Creating table EC_RESOURCE
prompt ==========================
prompt
create table EC_RESOURCE
(
  RESOURCE_ID INTEGER not null,
  NAME        VARCHAR2(64),
  ALIAS       VARCHAR2(64),
  TYPE        VARCHAR2(30),
  STATUS      VARCHAR2(4),
  PARENT_ID   INTEGER,
  TAXIS       INTEGER default 99999,
  OP_TIME     DATE
)
;
alter table EC_RESOURCE
  add constraint PK_EC_RESOURCE primary key (RESOURCE_ID);

prompt
prompt Creating table EC_USER
prompt ======================
prompt
create table EC_USER
(
  USER_ID    INTEGER not null,
  NAME       VARCHAR2(64),
  ALIAS      VARCHAR2(64),
  PASSWORD   VARCHAR2(100),
  EMAIL      VARCHAR2(50),
  STATUS     VARCHAR2(4),
  NOTE       VARCHAR2(256),
  TAXIS      INTEGER default 99999,
  JOB_NUMBER VARCHAR2(64),
  OP_TIME    DATE
)
;
create index INDEX_EC_USER on EC_USER (ALIAS);
create unique index PK_EC_USER on EC_USER (USER_ID);

prompt
prompt Creating table EC_USERS_ASSIGNMEN
prompt =================================
prompt
create table EC_USERS_ASSIGNMEN
(
  UA_ID   INTEGER not null,
  USER_ID INTEGER,
  ROLE_ID INTEGER,
  OP_TIME DATE,
  NOTE    VARCHAR2(256)
)
;
alter table EC_USERS_ASSIGNMEN
  add constraint PK_EC_USERS_ASSIGNMEN primary key (UA_ID);
create index RELATIONSHIP_5_FK on EC_USERS_ASSIGNMEN (USER_ID);
create index RELATIONSHIP_6_FK on EC_USERS_ASSIGNMEN (ROLE_ID);

prompt
prompt Creating table EC_USER_EXTEND
prompt =============================
prompt
create table EC_USER_EXTEND
(
  USER_ID   INTEGER,
  MOBILE    VARCHAR2(64),
  TEL       VARCHAR2(64),
  ADDRESS   VARCHAR2(256),
  QQ        VARCHAR2(64),
  MSN       VARCHAR2(64),
  DUTY      VARCHAR2(256),
  LEADER_ID INTEGER,
  NOTE      VARCHAR2(512)
)
;
create index RELATIONSHIP_9_FK on EC_USER_EXTEND (USER_ID);

prompt
prompt Creating table EC_USER_GROUP
prompt ============================
prompt
create table EC_USER_GROUP
(
  UG_ID    INTEGER not null,
  GROUP_ID INTEGER,
  USER_ID  INTEGER,
  NOTE     VARCHAR2(64)
)
;
alter table EC_USER_GROUP
  add constraint PK_EC_USER_GROUP primary key (UG_ID);
create index RELATIONSHIP_10_FK on EC_USER_GROUP (GROUP_ID);
create index RELATIONSHIP_8_FK on EC_USER_GROUP (USER_ID);


spool off
