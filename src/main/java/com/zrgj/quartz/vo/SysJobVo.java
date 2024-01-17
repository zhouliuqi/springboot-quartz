package com.zrgj.quartz.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zrgj.quartz.validator.valid.InvokeTargetValid;
import com.zrgj.quartz.validator.valid.SysJobVoValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 肘劉祁
 * 任务参数类
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@SysJobVoValid
public class SysJobVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "任务编号")
    private Integer jobId;

    @Schema(description = "唯一标识",hidden = true)
    private String uuid;

    @Schema(description = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    @Size(min = 0,max = 64,message = "任务名称不能超过64个字符")
    private String jobName;

    @Schema(description = "任务组名称")
    @NotBlank(message = "任务组名称不能为空")
    @Size(min = 0,max = 25,message = "任务组名称不能超过25个字符")
    private String jobGroup;

    @Schema(description = "开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @Schema(description = "结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不为空")
    private Date endTime;

    @Schema(description = "任务类型 0-普通 1-时间表达式",required = true)
    private Integer type;

    @Schema(description = "任务调用目标字符串",required = true)
    @InvokeTargetValid
    private String invokeTarget;

    @Schema(description = "任务是否允许并发 0-否(默认) 1-是")
    private Integer isCurrent=0;

    @Schema(description = "任务是否暂停 0-否(默认) 1-是")
    private Integer isPause=0;

    @Schema(description = "普通工作对象")
    private SysSimpleJobVo simpleJob;

    @Schema(description = "时间表达式工作对象")
    private SysCronJobVo cronJob;




}

