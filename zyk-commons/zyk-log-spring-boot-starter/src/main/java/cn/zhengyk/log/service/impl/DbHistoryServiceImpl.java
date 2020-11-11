package cn.zhengyk.log.service.impl;

import cn.zhengyk.log.model.LogHistory;
import cn.zhengyk.log.service.LogHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * @author by yakai.zheng
 * @Description 将 日志 存数据库
 * @Date 2020/11/9 18:16
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "history-log", name = "log-type", havingValue = "db")
public class DbHistoryServiceImpl implements LogHistoryService {

    private static final String INSERT_HIS_LOG_SQL = "INSERT INTO `history_log` " +
            "( `operate_time`, `application_name`, `business_module`, `class_name`, `method_name`, `user_id`, `user_name`, `operation` )\n" +
            "VALUES( ?, ?, ?, ?, ?, ?, ?, ? )";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Async
    @Override
    public void saveHistoryLog(LogHistory logHistory) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.jdbcTemplate.update(INSERT_HIS_LOG_SQL,
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
