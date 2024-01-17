package com.zrgj.quartz.validator.valid;
import com.zrgj.quartz.validator.SysJobVoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 肘劉祁
 * 任务对象验证器
 *
 * */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SysJobVoValidator.class)
public @interface SysJobVoValid {

    String message() default "xxx";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
