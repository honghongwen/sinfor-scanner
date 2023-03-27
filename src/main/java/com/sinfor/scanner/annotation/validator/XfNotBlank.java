package com.sinfor.scanner.annotation.validator;

import java.lang.annotation.*;

/**
 * @author fengwen
 * @date 11:27 2022/5/5
 * @description 校验字符串是否为空字符
 **/
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XfNotBlank {

    String message() default "";

}
