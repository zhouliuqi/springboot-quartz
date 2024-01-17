
-- 删除库
DROP DATABASE IF EXISTS QUARTZ;

-- 创建库
CREATE DATABASE QUARTZ;

-- 使用库
use QUARTZ;



DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

-- ----------------------------
-- 1、存储每一个已配置的 JOBDETAIL 的详细信息
-- ----------------------------
CREATE TABLE QRTZ_JOB_DETAILS (
                                  SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
                                  JOB_NAME             VARCHAR(200)    NOT NULL            COMMENT '任务名称',
                                  JOB_GROUP            VARCHAR(200)    NOT NULL            COMMENT '任务组名',
                                  DESCRIPTION          VARCHAR(250)    NULL                COMMENT '相关介绍',
                                  JOB_CLASS_NAME       VARCHAR(250)    NOT NULL            COMMENT '执行任务类名称',
                                  IS_DURABLE           VARCHAR(1)      NOT NULL            COMMENT '是否持久化',
                                  IS_NONCONCURRENT     VARCHAR(1)      NOT NULL            COMMENT '是否并发',
                                  IS_UPDATE_DATA       VARCHAR(1)      NOT NULL            COMMENT '是否更新数据',
                                  REQUESTS_RECOVERY    VARCHAR(1)      NOT NULL            COMMENT '是否接受恢复执行',
                                  JOB_DATA             BLOB            NULL                COMMENT '存放持久化JOB对象',
                                  PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
) ENGINE=INNODB COMMENT = '任务详细信息表';

-- ----------------------------
-- 2、 存储已配置的 TRIGGER 的信息
-- ----------------------------
CREATE TABLE QRTZ_TRIGGERS (
                               SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
                               TRIGGER_NAME         VARCHAR(200)    NOT NULL            COMMENT '触发器的名字',
                               TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT '触发器所属组的名字',
                               JOB_NAME             VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_JOB_DETAILS表JOB_NAME的外键',
                               JOB_GROUP            VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_JOB_DETAILS表JOB_GROUP的外键',
                               DESCRIPTION          VARCHAR(250)    NULL                COMMENT '相关介绍',
                               NEXT_FIRE_TIME       BIGINT(13)      NULL                COMMENT '上一次触发时间（毫秒）',
                               PREV_FIRE_TIME       BIGINT(13)      NULL                COMMENT '下一次触发时间（默认为-1表示不触发）',
                               PRIORITY             INTEGER         NULL                COMMENT '优先级',
                               TRIGGER_STATE        VARCHAR(16)     NOT NULL            COMMENT '触发器状态',
                               TRIGGER_TYPE         VARCHAR(8)      NOT NULL            COMMENT '触发器的类型',
                               START_TIME           BIGINT(13)      NOT NULL            COMMENT '开始时间',
                               END_TIME             BIGINT(13)      NULL                COMMENT '结束时间',
                               CALENDAR_NAME        VARCHAR(200)    NULL                COMMENT '日程表名称',
                               MISFIRE_INSTR        SMALLINT(2)     NULL                COMMENT '补偿执行的策略',
                               JOB_DATA             BLOB            NULL                COMMENT '存放持久化JOB对象',
                               PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
                               FOREIGN KEY (SCHED_NAME, JOB_NAME, JOB_GROUP) REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME, JOB_NAME, JOB_GROUP)
) ENGINE=INNODB COMMENT = '触发器详细信息表';

-- ----------------------------
-- 3、 存储简单的 TRIGGER，包括重复次数，间隔，以及已触发的次数
-- ----------------------------
CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
                                      SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
                                      TRIGGER_NAME         VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_TRIGGERS表TRIGGER_NAME的外键',
                                      TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_TRIGGERS表TRIGGER_GROUP的外键',
                                      REPEAT_COUNT         BIGINT(7)       NOT NULL            COMMENT '重复的次数统计',
                                      REPEAT_INTERVAL      BIGINT(12)      NOT NULL            COMMENT '重复的间隔时间',
                                      TIMES_TRIGGERED      BIGINT(10)      NOT NULL            COMMENT '已经触发的次数',
                                      PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
                                      FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) ENGINE=INNODB COMMENT = '简单触发器的信息表';

-- ----------------------------
-- 4、 存储 CRON TRIGGER，包括 CRON 表达式和时区信息
-- ----------------------------
CREATE TABLE QRTZ_CRON_TRIGGERS (
                                    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
                                    TRIGGER_NAME         VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_TRIGGERS表TRIGGER_NAME的外键',
                                    TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_TRIGGERS表TRIGGER_GROUP的外键',
                                    CRON_EXPRESSION      VARCHAR(200)    NOT NULL            COMMENT 'CRON表达式',
                                    TIME_ZONE_ID         VARCHAR(80)                         COMMENT '时区',
                                    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
                                    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) ENGINE=INNODB COMMENT = 'CRON类型的触发器表';

-- ----------------------------
-- 5、 TRIGGER 作为 BLOB 类型存储(用于 QUARTZ 用户用 JDBC 创建他们自己定制的 TRIGGER 类型，JOBSTORE 并不知道如何存储实例的时候)
-- ----------------------------
CREATE TABLE QRTZ_BLOB_TRIGGERS (
                                    SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
                                    TRIGGER_NAME         VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_TRIGGERS表TRIGGER_NAME的外键',
                                    TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_TRIGGERS表TRIGGER_GROUP的外键',
                                    BLOB_DATA            BLOB            NULL                COMMENT '存放持久化TRIGGER对象',
                                    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
                                    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) ENGINE=INNODB COMMENT = 'BLOB类型的触发器表';

-- ----------------------------
-- 6、 以 BLOB 类型存储存放日历信息， QUARTZ可配置一个日历来指定一个时间范围
-- ----------------------------
CREATE TABLE QRTZ_CALENDARS (
                                SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
                                CALENDAR_NAME        VARCHAR(200)    NOT NULL            COMMENT '日历名称',
                                CALENDAR             BLOB            NOT NULL            COMMENT '存放持久化CALENDAR对象',
                                PRIMARY KEY (SCHED_NAME, CALENDAR_NAME)
) ENGINE=INNODB COMMENT = '日历信息表';

-- ----------------------------
-- 7、 存储已暂停的 TRIGGER 组的信息
-- ----------------------------
CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
                                          SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
                                          TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_TRIGGERS表TRIGGER_GROUP的外键',
                                          PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP)
) ENGINE=INNODB COMMENT = '暂停的触发器表';

-- ----------------------------
-- 8、 存储与已触发的 TRIGGER 相关的状态信息，以及相联 JOB 的执行信息
-- ----------------------------
CREATE TABLE QRTZ_FIRED_TRIGGERS (
                                     SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
                                     ENTRY_ID             VARCHAR(95)     NOT NULL            COMMENT '调度器实例ID',
                                     TRIGGER_NAME         VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_TRIGGERS表TRIGGER_NAME的外键',
                                     TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_TRIGGERS表TRIGGER_GROUP的外键',
                                     INSTANCE_NAME        VARCHAR(200)    NOT NULL            COMMENT '调度器实例名',
                                     FIRED_TIME           BIGINT(13)      NOT NULL            COMMENT '触发的时间',
                                     SCHED_TIME           BIGINT(13)      NOT NULL            COMMENT '定时器制定的时间',
                                     PRIORITY             INTEGER         NOT NULL            COMMENT '优先级',
                                     STATE                VARCHAR(16)     NOT NULL            COMMENT '状态',
                                     JOB_NAME             VARCHAR(200)    NULL                COMMENT '任务名称',
                                     JOB_GROUP            VARCHAR(200)    NULL                COMMENT '任务组名',
                                     IS_NONCONCURRENT     VARCHAR(1)      NULL                COMMENT '是否并发',
                                     REQUESTS_RECOVERY    VARCHAR(1)      NULL                COMMENT '是否接受恢复执行',
                                     PRIMARY KEY (SCHED_NAME, ENTRY_ID)
) ENGINE=INNODB COMMENT = '已触发的触发器表';

-- ----------------------------
-- 9、 存储少量的有关 SCHEDULER 的状态信息，假如是用于集群中，可以看到其他的 SCHEDULER 实例
-- ----------------------------
CREATE TABLE QRTZ_SCHEDULER_STATE (
                                      SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
                                      INSTANCE_NAME        VARCHAR(200)    NOT NULL            COMMENT '实例名称',
                                      LAST_CHECKIN_TIME    BIGINT(13)      NOT NULL            COMMENT '上次检查时间',
                                      CHECKIN_INTERVAL     BIGINT(13)      NOT NULL            COMMENT '检查间隔时间',
                                      PRIMARY KEY (SCHED_NAME, INSTANCE_NAME)
) ENGINE=INNODB COMMENT = '调度器状态表';

-- ----------------------------
-- 10、 存储程序的悲观锁的信息(假如使用了悲观锁)
-- ----------------------------
CREATE TABLE QRTZ_LOCKS (
                            SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
                            LOCK_NAME            VARCHAR(40)     NOT NULL            COMMENT '悲观锁名称',
                            PRIMARY KEY (SCHED_NAME, LOCK_NAME)
) ENGINE=INNODB COMMENT = '存储的悲观锁信息表';

-- ----------------------------
-- 11、 QUARTZ集群实现同步机制的行锁表
-- ----------------------------
CREATE TABLE QRTZ_SIMPROP_TRIGGERS (
                                       SCHED_NAME           VARCHAR(120)    NOT NULL            COMMENT '调度名称',
                                       TRIGGER_NAME         VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_TRIGGERS表TRIGGER_NAME的外键',
                                       TRIGGER_GROUP        VARCHAR(200)    NOT NULL            COMMENT 'QRTZ_TRIGGERS表TRIGGER_GROUP的外键',
                                       STR_PROP_1           VARCHAR(512)    NULL                COMMENT 'STRING类型的TRIGGER的第一个参数',
                                       STR_PROP_2           VARCHAR(512)    NULL                COMMENT 'STRING类型的TRIGGER的第二个参数',
                                       STR_PROP_3           VARCHAR(512)    NULL                COMMENT 'STRING类型的TRIGGER的第三个参数',
                                       INT_PROP_1           INT             NULL                COMMENT 'INT类型的TRIGGER的第一个参数',
                                       INT_PROP_2           INT             NULL                COMMENT 'INT类型的TRIGGER的第二个参数',
                                       LONG_PROP_1          BIGINT          NULL                COMMENT 'LONG类型的TRIGGER的第一个参数',
                                       LONG_PROP_2          BIGINT          NULL                COMMENT 'LONG类型的TRIGGER的第二个参数',
                                       DEC_PROP_1           NUMERIC(13,4)   NULL                COMMENT 'DECIMAL类型的TRIGGER的第一个参数',
                                       DEC_PROP_2           NUMERIC(13,4)   NULL                COMMENT 'DECIMAL类型的TRIGGER的第二个参数',
                                       BOOL_PROP_1          VARCHAR(1)      NULL                COMMENT 'BOOLEAN类型的TRIGGER的第一个参数',
                                       BOOL_PROP_2          VARCHAR(1)      NULL                COMMENT 'BOOLEAN类型的TRIGGER的第二个参数',
                                       PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
                                       FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
) ENGINE=INNODB COMMENT = '同步机制的行锁表';



#删除任务表
DROP TABLE IF EXISTS SYS_JOB;
#创建任务表
CREATE TABLE SYS_JOB
(
    ID            INT PRIMARY KEY AUTO_INCREMENT COMMENT '任务编号',
    UUID         VARCHAR(50)  NOT NULL COMMENT '任务唯一标识',
    JOB_NAME          VARCHAR(100) NOT NULL COMMENT '任务名称',
    JOB_GROUP         VARCHAR(50)  NOT NULL COMMENT '任务组名称',
    START_TIME     DATETIME    NOT NULL COMMENT '开始时间',
    END_TIME       DATETIME    NOT NULL COMMENT '结束时间',
    TYPE          TINYINT(1)   NOT NULL COMMENT '任务类型 0-普通 1-时间表达式',
    INVOKE_TARGET VARCHAR(2000) NOT NULL COMMENT '任务调用目标字符串',
    IS_CURRENT    BIT DEFAULT 0 COMMENT '任务是否允许并发 0-否(默认) 1-是',
    IS_PAUSE     BIT DEFAULT 0 COMMENT '任务是否暂停 0-否(默认) 1-是',
    CREATE_TIME       DATETIME   DEFAULT NOW() COMMENT '创建时间',
    UPDATE_TIME       DATETIME   DEFAULT NOW() COMMENT '修改时间',
    IS_DELETED         BIT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除'
) COMMENT '任务表';

#删除任务表
DROP TABLE IF EXISTS SYS_JOB_LOG;
#创建任务表
CREATE TABLE SYS_JOB_LOG
(
    ID   INT PRIMARY KEY AUTO_INCREMENT COMMENT '任务日志编号',
    JOB_NAME VARCHAR(100) NOT NULL COMMENT '任务名称',
    JOB_GROUP VARCHAR(50) NOT NULL COMMENT '任务组名称',
    LOG_INFO VARCHAR(1000) DEFAULT '' COMMENT '日志信息',
    ERROR_INFO   VARCHAR(3000) DEFAULT '' COMMENT '错误信息',
    START_TIME   DATETIME NOT NULL COMMENT '开始时间',
    END_TIME     DATETIME NOT NULL COMMENT '结束时间',
    IS_SUCCESS    BIT    DEFAULT 1 COMMENT '任务执行是否成功 0-否 1-是(默认)',
    CREATE_TIME  DATETIME      DEFAULT NOW() COMMENT '创建时间',
    UPDATE_TIME  DATETIME      DEFAULT NOW() COMMENT '修改时间',
    IS_DELETED    BIT    DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除'
) COMMENT '任务日志表';

#删除任务普通表
DROP TABLE IF EXISTS SYS_SIMPLE_JOB;
#创建普通任务表
CREATE TABLE SYS_SIMPLE_JOB
(
    ID  INT PRIMARY KEY AUTO_INCREMENT COMMENT '普通任务编号',
    JOB_ID         INT        NOT NULL COMMENT '任务编号',
    COUNT          INT DEFAULT 1 COMMENT '执行次数 -1-无限制 1-默认',
    TIME_UNIT      TINYINT(1) DEFAULT 0 COMMENT '时间单位 0-毫秒(默认) 1-秒 2-分 3-时',
    TIME_INTERVAL INT NOT NULL COMMENT '时间间隔',
    MISFIRE_POLICY TINYINT(1) DEFAULT 0 COMMENT '计划策略 0-默认
1-立即触发执行,追赶上错失的次数
2-无论什么情况一次性执行
3-不触发立即执行,保持触发器的重复次数不变
4-不触发立即执行,重复次数修改为原本应该触发的总次数
5-立即触发执行,保持触发器的重复次数不变
6-立即触发执行,,重复次数修改为原本应该触发的总次数',
    CREATE_TIME    DATETIME   DEFAULT NOW() COMMENT '创建时间',
    UPDATE_TIME    DATETIME   DEFAULT NOW() COMMENT '修改时间',
    IS_DELETED      BIT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除'
) COMMENT '普通任务表';

#删除时间表达式任务表
DROP TABLE IF EXISTS SYS_CRON_JOB;
#创建时间表达式任务表
CREATE TABLE SYS_CRON_JOB
(
    ID    INT PRIMARY KEY AUTO_INCREMENT COMMENT '时间表达式任务编号',
    JOB_ID         INT         NOT NULL COMMENT '任务编号',
    CRON           VARCHAR(50) NOT NULL COMMENT '时间表达式',
    MISFIRE_POLICY TINYINT(1) DEFAULT 0 COMMENT '计划策略 0-默认 " +
            "1-立即触发执行,追赶上错失的次数 " +
            "2-不触发立即执行" +
            "3-触发立即执行',
    CREATE_TIME    DATETIME   DEFAULT NOW() COMMENT '创建时间',
    UPDATE_TIME    DATETIME   DEFAULT NOW() COMMENT '修改时间',
    IS_DELETED      BIT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除'
) COMMENT '时间表达式任务表';

COMMIT;