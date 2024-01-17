package com.zrgj.quartz.validator;

import com.zrgj.quartz.constants.JobTypeConstants;
import com.zrgj.quartz.validator.valid.SysJobVoValid;
import com.zrgj.quartz.vo.SysJobVo;

import javax.validation.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 肘劉祁
 * 任务对象验证器
 */

public class SysJobVoValidator implements ConstraintValidator<SysJobVoValid, SysJobVo> {

    @Override
    public boolean isValid(SysJobVo jobVo, ConstraintValidatorContext context) {
        //禁用默认的提示信息
        context.disableDefaultConstraintViolation();
        //验证类型
        if (!typeValid(jobVo.getType(), context)) {
            return false;
        }
        // 获取 Validator 实例
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //初始化验证结果存储对象
        Set<ConstraintViolation<Object>> validate = new HashSet<>();
        switch (jobVo.getType()) {
            case JobTypeConstants.SIMPLE:
                //判断是否为空
                if (jobVo.getSimpleJob() == null) {
                    build(context, "任务类型为普通任务时,普通任务对象不能为空");
                    return false;
                }
                //验证对象
                validate = validator.validate(jobVo.getSimpleJob());
                //判断是否为空
                if (!validate.isEmpty()) {
                    build(context, validate.iterator().next().getMessage());
                    return false;
                }
                break;
            case JobTypeConstants.CRON:
                //判断是否为空
                if (jobVo.getCronJob() == null) {
                    build(context, "任务类型为时间表达式任务时,时间表达式任务对象不能为空");
                    return false;
                }
                //验证对象
                validate = validator.validate(jobVo.getCronJob());
                //判断是否为空
                if (!validate.isEmpty()) {
                    build(context, validate.iterator().next().getMessage());
                    return false;
                }
                break;
            default:
                build(context, "任务类型不存在");
                return false;
        }
        return true;
    }

    /**
     * 添加提示信息
     *
     * @param context 上下文
     * @param message 提示信息
     */
    void build(ConstraintValidatorContext context, String message) {
        //添加提示信息
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }


    /***
     * 验证类型
     * @param type 类型
     * @param context 上下文
     * @return 结果
     */
    boolean typeValid(Integer type, ConstraintValidatorContext context) {
        //验证类型
        if (type == null) {
            build(context, "任务类型不能为空");
            return false;
        }
        //验证有效值
        if (JobTypeConstants.SIMPLE > type || JobTypeConstants.CRON < type) {
            build(context, "错误的任务类型");
            return false;
        }
        return true;
    }

}
