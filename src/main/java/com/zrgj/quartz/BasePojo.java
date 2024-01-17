package com.zrgj.quartz;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * @author 肘劉祁
 */

public class BasePojo {

    @Schema(description = "操作时间")
    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @Schema(description = "修改时间")
    @TableField(value = "UPDATE_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Schema(description = "删除标识")
    @TableField("IS_DELETED")
    @TableLogic
    private Integer isDeleted;
}
