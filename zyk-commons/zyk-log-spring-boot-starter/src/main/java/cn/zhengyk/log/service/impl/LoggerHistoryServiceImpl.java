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
 * @Description 单纯用日志记录操作历史, 不管 log-type 是什么，都会执行此类
 * @Date 2020/11/9 18:16
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "history-log", name = "log-type", havingValue = "logger", matchIfMissing = true)
public class LoggerHistoryServiceImpl implements LogHistoryService {

    private static final String MSG_PATTERN = "{}|{}|{}|{}|{}|{}|{}";

    /**
     * 格式为：{时间}|{应用名}|{类名}|{方法名}|{用户id}|{用户名}|{操作信息}
     */
    @Override
    public void saveHistoryLog(LogHistory logHistory) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info(MSG_PATTERN,
                sdf.format(logHistory.getOperateTime()),
                logHistory.getApplicationName(),
                logHistory.getClassName(),
                logHistory.getMethodName(),
                logHistory.getUserId(),
                logHistory.getUserName(),
                logHistory.getOperation());
    }
}
