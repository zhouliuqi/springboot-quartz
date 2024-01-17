package com.zrgj.quartz.validator;

import cn.hutool.core.util.StrUtil;
import com.zrgj.quartz.util.CronUtils;
import com.zrgj.quartz.validator.valid.CronValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 肘劉祁
 * 时间表达式验证器
 */

public class CronValidator implements ConstraintValidator<CronValid, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        //禁用默认的提示信息
        context.disableDefaultConstraintViolation();
        //初始化字符串长度
        int size = 255;
        //判断是否为空
        if (StrUtil.isBlank(s)) {
            build(context, "时间表达式不能为空");
            return false;
        }
        //判断长度是否超出1000个字符
        if (s.length() > size) {
            build(context, "时间表达式不能超过255个字符");
            return false;
        }
        //验证时间表达式
        if (!CronUtils.isValid(s)){
            build(context, "时间表达式不正确");
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
