package com.sinfor.scanner.annotation.validator;

import java.lang.annotation.*;

/**
 * @author fengwen
 * @date 11:27 2022/5/5
 * @description 校验字段是否为null
 **/
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XfNotNull {

    String message() default "";

}
