package com.sinfor.scanner.starter.scanner.param;

import com.sinfor.scanner.annotation.validator.*;
import com.sinfor.scanner.result.Result;
import com.sinfor.scanner.starter.scanner.Invoke;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

/**
 * @author fengwen
 * @date 2022/5/5
 * @description 参数校验解析器
 **/
@Slf4j
public class ParamValidate {

    private ParamValidate() {
    }

    public static ParamValidate getInstance() {
        return new ParamValidate();
    }

    public Result<String> valid(Invoke invoke, Object[] params) {
        Parameter[] parameters = invoke.getMethod().getParameters();
        for (int i = 0; i < params.length; i++) {
            // 参数值
            Object paramValue = params[i];
            // 参数定义
            Parameter paramDefine = parameters[i];

            Annotation[] paramAnnotation = paramDefine.getAnnotations();
            if (paramAnnotation.length == 0) {
                continue;
            }

            // 只取第一个注解作为校验，避免过于影响效率。所以参数上校验注解务必写在第一位
            Annotation annotation = paramAnnotation[0];

            if (annotation instanceof XfNotNull) {
                // 校验值是否为null
                if (paramValue == null) {
                    XfNotNull xf = (XfNotNull) annotation;
                    String msg = xf.message();
                    String finalMsg = "".equals(msg) ? paramDefine.getName() + "不能为null" : msg;
                    return Result.fail(finalMsg);
                }
            }

            if (annotation instanceof XfNotBlank) {
                if (paramValue == null || "".equals(paramValue)) {
                    XfNotBlank xf = (XfNotBlank) annotation;
                    String msg = xf.message();
                    String finalMsg = "".equals(msg) ? paramDefine.getName() + "不能为空字符" : msg;
                    return Result.fail(finalMsg);
                }
            }

            if (annotation instanceof XfMin) {
                int paramV;
                try {
                    paramV = Integer.parseInt(paramValue.toString());
                } catch (NumberFormatException e) {
                    log.warn("内部错误，解析参数校验注解失败", e);
                    // 出错时跳过校验
                    continue;
                }
                XfMin xf = (XfMin) annotation;
                int value = xf.value();
                if (paramV < value) {
                    String msg = xf.message();
                    String finalMsg = "".equals(msg) ? paramDefine.getName() + "最小值为" + value : msg;
                    return Result.fail(finalMsg);
                }
            }

            if (annotation instanceof XfMax) {
                int paramV;
                try {
                    paramV = Integer.parseInt(paramValue.toString());
                } catch (NumberFormatException e) {
                    log.warn("内部错误，解析参数校验注解失败", e);
                    // 出错时跳过校验
                    continue;
                }
                XfMax xf = (XfMax) annotation;
                int value = xf.value();
                if (paramV > value) {
                    String msg = xf.message();
                    String finalMsg = "".equals(msg) ? paramDefine.getName() + "最大值为" + value : msg;
                    return Result.fail(finalMsg);
                }
            }

            if (annotation instanceof XfBetween) {
                int paramV;
                try {
                    paramV = Integer.parseInt(paramValue.toString());
                } catch (NumberFormatException e) {
                    log.warn("内部错误，解析参数校验注解失败", e);
                    // 出错时跳过校验
                    continue;
                }
                XfBetween xf = (XfBetween) annotation;
                int min = xf.min();
                int max = xf.max();
                if (paramV < min || paramV > max) {
                    String msg = xf.message();
                    String finalMsg = "".equals(msg) ? paramDefine.getName() + "区间为" + min + "~" + max : msg;
                    return Result.fail(finalMsg);
                }
            }

            // bean的校验
            if (annotation instanceof XfValid) {
                Class<?> clazz = paramDefine.getType();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    try {
                        if (field.isAnnotationPresent(XfNotNull.class)) {
                            field.setAccessible(true);
                            Object value = field.get(paramValue);
                            if (value == null) {
                                XfNotNull xf = field.getAnnotation(XfNotNull.class);
                                String msg = xf.message();
                                String finalMsg = "".equals(msg) ? field.getName() + "不能为null" : msg;
                                return Result.fail(finalMsg);
                            }
                        }

                        if (field.isAnnotationPresent(XfNotBlank.class)) {
                            field.setAccessible(true);
                            Object value = field.get(paramValue);
                            if (value == null || "".equals(value)) {
                                XfNotBlank xf = field.getAnnotation(XfNotBlank.class);
                                String msg = xf.message();
                                String finalMsg = "".equals(msg) ? field.getName() + "不能为空字符串" : msg;
                                return Result.fail(finalMsg);
                            }
                        }

                        if (field.isAnnotationPresent(XfMin.class)) {
                            field.setAccessible(true);
                            Object o = field.get(paramValue);
                            int paramV = (int) o;
                            XfMin xf = field.getAnnotation(XfMin.class);
                            int value = xf.value();
                            if (paramV < value) {
                                String msg = xf.message();
                                String finalMsg = "".equals(msg) ? field.getName() + "最小值为" + value : msg;
                                return Result.fail(finalMsg);
                            }
                        }

                        if (field.isAnnotationPresent(XfMax.class)) {
                            field.setAccessible(true);
                            int paramV = field.getInt(paramValue);
                            XfMax xf = field.getAnnotation(XfMax.class);
                            int value = xf.value();
                            if (paramV > value) {
                                String msg = xf.message();
                                String finalMsg = "".equals(msg) ? field.getName() + "最大值为" + value : msg;
                                return Result.fail(finalMsg);
                            }
                        }

                        if (field.isAnnotationPresent(XfBetween.class)) {
                            field.setAccessible(true);
                            int paramV = field.getInt(paramValue);
                            XfBetween xf = field.getAnnotation(XfBetween.class);
                            int min = xf.min();
                            int max = xf.max();
                            if (paramV > max || paramV < min) {
                                String msg = xf.message();
                                String finalMsg = "".equals(msg) ? field.getName() + "区间为" + min + "~" + max : msg;
                                return Result.fail(finalMsg);
                            }
                        }
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        log.warn("内部错误，解析参数校验注解失败", e);
                        continue;
                    }
                }
            }
        }
        return null;
    }

}
