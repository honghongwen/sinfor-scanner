package com.sinfor.scanner.annotation.validator;

import java.lang.annotation.*;

/**
 * @author fengwen
 * @date 11:28 2022/5/5
 * @description 校验数值最大为
 **/
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XfMax {

    String message() default "";

    int value();

}
