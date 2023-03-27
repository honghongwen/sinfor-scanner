package com.sinfor.scanner.starter.scanner;

import cn.hutool.core.date.StopWatch;
import com.sinfor.scanner.annotation.XfCommand;
import com.sinfor.scanner.annotation.XfModule;
import com.sinfor.scanner.exception.ModuleNotFoundException;
import com.sinfor.scanner.exception.ScanException;
import com.sinfor.scanner.starter.scanner.log.LogHelper;
import com.sinfor.scanner.starter.scanner.util.InvokeKeyGen;
import com.sinfor.stater.net.support.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author fengwen
 * @date 2022/4/29
 * @description 扫描指定包下自定义注解，将其加载到内存中
 **/
@Slf4j
public class Scanner {

    private Scanner() {
    }

    public static Scanner getInstance() {
        return new Scanner();
    }

    /**
     * @author fengwen
     * @date 9:43 2022/5/4
     * @description 扫描Module注解&Command注解，将其加入系统内存，配合ini内service使用。
     * @param 包全名
     **/
    public void scan(String packageName) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("扫描自定义注解");

        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(XfModule.class);
        for (Class<?> clazz : classes) {
            // 过滤掉实现类的Class
            if (!clazz.isAnnotationPresent(XfModule.class)) {
                continue;
            }
            // 通过spring获取到实现类
            Object bean = SpringUtil.getBean(clazz);
            if (bean == null) {
                throw new ModuleNotFoundException("未找到Module注解接口的实现类" + clazz.getName());
            }
            XfModule module = clazz.getAnnotation(XfModule.class);

            int moduleId = module.module();
            ScannerContext.moduleMap.put(moduleId, module);

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                XfCommand command = method.getAnnotation(XfCommand.class);
                if (command == null) {
                    // 过滤类下未注解的方法
                    continue;
                }
                Map<String, Invoke> invokeMap = ScannerContext.commandMap.get(moduleId);
                if (invokeMap == null) {
                    invokeMap = new HashMap<>();
                }
                String invokeKey = InvokeKeyGen.gen(command.cmd(), command.version());
                Invoke invoke = invokeMap.get(invokeKey);
                if (invoke != null) {
                    throw new ScanException("模块" + moduleId + "命令号重复");
                }
                invokeMap.put(invokeKey, Invoke.from(method, bean));
                ScannerContext.commandMap.put(moduleId, invokeMap);
            }
        }

        stopWatch.stop();
        log.info("自定义注解扫描完成" + stopWatch.prettyPrint());

        // 扫描module、command日志
        LogHelper.prettyPrint();
    }

}
