package cn.zhengyk.log.service.impl;

import cn.zhengyk.log.model.LogHistory;
import cn.zhengyk.log.service.LogHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * @author by yakai.zheng
 * @Description 单纯用日志记录操作历史
 * @Date 2020/11/9 18:16
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "history-log", name = "log-type", havingValue = "logger", matchIfMissing = true)
public class LoggerHistoryServiceImpl implements LogHistoryService {

    private static final String MSG_PATTERN = "\r\n操作时间:{}\r\n" +
            "应用名:{}\r\n" +
            "业务模块:{}\r\n" +
            "类名:{}\r\n" +
            "方法名:{}\r\n" +
            "用户id:{}\r\n" +
            "用户名:{}\r\n" +
            "操作信息:{}\r\n";

    /**
     *
     */
    @Override
    public void saveHistoryLog(LogHistory logHistory) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info(MSG_PATTERN,
                sdf.format(logHistory.getOperateTime()),
                logHistory.getApplicationName(),
                logHistory.getBusinessModule(),
                logHistory.getClassName(),
                logHistory.getMethodName(),
                logHistory.getUserId(),
                logHistory.getUserName(),
                logHistory.getOperation());
    }
}
