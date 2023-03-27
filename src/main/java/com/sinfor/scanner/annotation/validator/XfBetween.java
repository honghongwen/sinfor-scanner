package com.sinfor.scanner.annotation.validator;

import java.lang.annotation.*;

/**
 * @author fengwen
 * @date 11:28 2022/5/5
 * @description 校验数值区间
 **/
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XfBetween {

    String message() default "";

    int min();

    int max();
}
