package com.sinfor.scanner.starter.scanner;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author fengwen
 * @date 2022/4/29
 * @description TODO
 **/
@Data
@Slf4j
public class Invoke {

    private Method method;

    private Object target;

    public static Invoke from(Method method, Object target) {
        Invoke invoke = new Invoke();
        invoke.setMethod(method);
        invoke.setTarget(target);
        return invoke;
    }

    public Object invoke(Object... objs) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(target, objs);
    }
}
