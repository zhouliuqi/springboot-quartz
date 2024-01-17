package com.zrgj.quartz.validator.valid;

import com.zrgj.quartz.validator.InvokeTargetValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 肘劉祁
 * 调用目标字符串验证器
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InvokeTargetValidator.class)
public @interface InvokeTargetValid {

    String message() default "xxx";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
