package com.sinfor.scanner.annotation;


import com.sinfor.scanner.eun.XfModuleType;
import com.sinfor.scanner.eun.XfSiteTypeDefine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XfModule {
    /**
     * 模块号
     *
     * @return
     */
    int module();

    /**
     * 模块名称
     *
     * @return
     */
    String moduleName();

    /**
     * 上级菜单
     *
     * @return
     */
    int parentId();

    /**
     * 序号
     *
     * @return
     */
    int posIndex() default 0;

    XfModuleType moduleType() default XfModuleType.xmtModule;

    /**
     * 访问级别
     *
     * @return
     */
    int accessLevel() default XfSiteTypeDefine.XF_ALL_RIGHTS;
}
