package com.zrgj.quartz.mybatis.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zrgj.quartz.BasePojo;
import com.zrgj.quartz.map.SysJobMap;
import com.zrgj.quartz.vo.SysJobVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 任务表
 * </p>
 *
 * @author 周刘奇
 * @since 2023-10-11
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_job")
@Schema(name = "SysJob", description = "$!{table.comment}")
@Data
public class SysJob extends BasePojo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "任务编号")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "唯一标识")
    @TableField("UUID")
    private String uuid;

    @Schema(description = "任务名称")
    @TableField("JOB_NAME")
    private String jobName;

    @Schema(description = "任务组名称")
    @TableField("JOB_GROUP")
    private String jobGroup;

    @Schema(description = "开始时间")
    @TableField("START_TIME")
    private Date startTime;

    @Schema(description = "结束时间")
    @TableField("END_TIME")
    private Date endTime;

    @Schema(description = "任务类型 0-普通 1-时间表达式")
    @TableField("TYPE")
    private Integer type;

    @Schema(description = "任务调用目标字符串")
    @TableField("INVOKE_TARGET")
    private String invokeTarget;

    @Schema(description = "任务是否允许并发 0-否(默认) 1-是")
    @TableField("IS_CURRENT")
    private Integer isCurrent;

    @Schema(description = "任务是否暂停 0-否(默认) 1-是")
    @TableField("IS_PAUSE")
    private Integer isPause;

}
