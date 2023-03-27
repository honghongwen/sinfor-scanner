package com.sinfor.scanner.starter.scanner;


import com.sinfor.scanner.annotation.XfModule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fengwen
 * @date 2022/4/29
 * @description 内存map
 **/
public class ScannerContext {

    public static Map<Integer, XfModule> moduleMap = new ConcurrentHashMap<>();

    public static Map<Integer, Map<String, Invoke>> commandMap = new ConcurrentHashMap<>();
}
