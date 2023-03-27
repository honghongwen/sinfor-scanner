package com.sinfor.scanner.annotation.validator;

import java.lang.annotation.*;

/**
 * @author fengwen
 * @date 11:28 2022/5/5
 * @description 校验数值最小为
 **/
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XfMin {

    String message() default "";

    int value();
}
