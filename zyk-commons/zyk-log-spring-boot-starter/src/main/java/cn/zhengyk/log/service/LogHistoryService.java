package cn.zhengyk.log.service;

import cn.zhengyk.log.model.LogHistory;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/11/9 18:10
 */
public interface LogHistoryService {

    void saveHistoryLog(LogHistory logHistory);
}
