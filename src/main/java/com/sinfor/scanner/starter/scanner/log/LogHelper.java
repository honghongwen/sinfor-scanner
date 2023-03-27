package com.sinfor.scanner.starter.scanner.log;

import com.sinfor.scanner.annotation.XfModule;
import com.sinfor.scanner.starter.scanner.Invoke;
import com.sinfor.scanner.starter.scanner.ScannerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.sinfor.scanner.starter.scanner.util.InvokeKeyGen.SPLITTER;


/**
 * @author fengwen
 * @date 2022/5/3
 * @description 扫描日志
 **/
@Slf4j
public class LogHelper {

    public static void prettyPrint() {
        log.info(templateModule());
        log.info(templateCmd());
    }

    private static String templateCmd() {
        String format = "|%1$-20s|%2$-20s|%3$-20s|\n";

        StringBuilder builder = new StringBuilder("Command注解如下\n");
        builder.append("----------------------------------------------------------------------------------------------------------------------\n");
        builder.append(String.format(format, LogHelper.center("module", 20),
                LogHelper.center("cmd", 20),
                LogHelper.center("version", 20)));
        builder.append("----------------------------------------------------------------------------------------------------------------------\n");
        for (Map.Entry<Integer, Map<String, Invoke>> entry : ScannerContext.commandMap.entrySet()) {
            Integer moduleId = entry.getKey();
            Map<String, Invoke> value = entry.getValue();
            for (Map.Entry<String, Invoke> iEntry : value.entrySet()) {
                String cmdVersion = iEntry.getKey();
                String[] cmdVersionArr = cmdVersion.split(SPLITTER);
                String cmd = cmdVersionArr[0];
                String version = cmdVersionArr[1];
                builder.append(String.format(format, LogHelper.center(moduleId + "", 20),
                        LogHelper.center(cmd, 20),
                        LogHelper.center(version, 20)));
            }
        }
        return builder.toString();
    }

    public static String templateModule() {
        String format = "|%1$-20s|%2$-20s|%3$-20s|\n";

        StringBuilder builder = new StringBuilder("Module注解如下\n");
        builder.append("----------------------------------------------------------------------------------------\n");
        builder.append(String.format(format, LogHelper.center("module", 20),
                LogHelper.center("moduleName", 21),
                LogHelper.center("parentId", 20)));
        builder.append("----------------------------------------------------------------------------------------\n");
        for (Map.Entry<Integer, XfModule> entry : ScannerContext.moduleMap.entrySet()) {
            XfModule module = entry.getValue();
            builder.append(String.format(format, LogHelper.center(module.module() + "", 20),
                    LogHelper.center(module.moduleName(), 20),
                    LogHelper.center(module.parentId() + "", 20)));
        }
        return builder.toString();
    }

    public static String center(String s, int size) {
        return center(s, size, ' ');
    }

    public static String center(String s, int size, char pad) {
        if (s == null || size <= s.length()) {
            return s;
        }

        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < (size - s.length()) / 2; i++) {
            sb.append(pad);
        }
        sb.append(s);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }
}
