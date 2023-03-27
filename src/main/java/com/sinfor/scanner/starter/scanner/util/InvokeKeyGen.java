package com.sinfor.scanner.starter.scanner.util;

/**
 * @author fengwen
 * @date 2022/5/4
 * @description scanner内存数据key
 **/
public class InvokeKeyGen {

    /**
     * 分隔符
     */
    public static String SPLITTER = ":";

    public static String gen(int cmd, String version) {
        return cmd + SPLITTER + version;
    }
}
