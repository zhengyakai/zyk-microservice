package cn.zhengyk.core.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/27 11:22
 */
@Slf4j
public class LogUtil {

    private LogUtil(){}

    public static void logError(String appName, StackTraceElement traceElement, Throwable throwable) {
        String method = traceElement.getMethodName();
        log.error(appName + " fallback," + method + " is unusable:", throwable);
    }
}
