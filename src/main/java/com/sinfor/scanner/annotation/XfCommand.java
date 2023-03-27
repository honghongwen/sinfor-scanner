package com.sinfor.scanner.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XfCommand {
    /**
     * 命令号
     *
     * @return
     */
    short cmd();

    /**
     * 命令名称
     *
     * @return
     */
    String cmdName();

    /**
     * 登陆检测
     *
     * @return
     */
    boolean loginCheck() default true;

    /**
     * 余额检测
     *
     * @return
     */
    boolean moneyCheck() default true;

    /**
     * 是否添加权限按扭
     *
     * @return
     */
    boolean addRights() default false;

    /**
     * 命令序号
     *
     * @return
     */
    int posIndex() default 0;

    boolean webApi() default false;
    /**
     * 命令号版本
     * @return
     */
    String version() default "1.0";
}
