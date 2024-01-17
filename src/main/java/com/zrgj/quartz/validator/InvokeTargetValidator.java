package com.zrgj.quartz.validator;

import cn.hutool.core.util.StrUtil;
import com.zrgj.quartz.util.ScheduleUtils;
import com.zrgj.quartz.validator.valid.InvokeTargetValid;
import com.zrgj.quartz.validator.valid.SysJobVoValid;
import com.zrgj.quartz.vo.SysJobVo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 肘劉祁
 * 调用目标字符串验证器
 */

public class InvokeTargetValidator implements ConstraintValidator<InvokeTargetValid, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        //禁用默认的提示信息
        context.disableDefaultConstraintViolation();
        //初始化字符串长度
        int size = 1000;
        //判断是否为空
        if (StrUtil.isBlank(s)) {
            build(context, "调用目标字符串不能为空");
            return false;
        }
        //判断长度是否超出1000个字符
        if (s.length() > size) {
            build(context, "调用目标字符串不能超过1000个字符");
            return false;
        }
        //验证调用目标字符串
        if (!ScheduleUtils.whiteList(s)) {
            build(context, "任务调用目标字符串不在白名单中");
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
}
