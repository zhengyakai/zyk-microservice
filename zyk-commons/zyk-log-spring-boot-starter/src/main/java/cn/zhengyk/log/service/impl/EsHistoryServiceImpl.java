package cn.zhengyk.log.service.impl;

import cn.zhengyk.core.utils.JsonUtil;
import cn.zhengyk.es.service.ElasticsearchApi;
import cn.zhengyk.log.model.LogHistory;
import cn.zhengyk.log.service.LogHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * @author by yakai.zheng
 * @Description 将 日志 存 es
 * @Date 2020/11/9 18:16
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "history-log", name = "log-type", havingValue = "es")
public class EsHistoryServiceImpl implements LogHistoryService {

    @Autowired
    private ElasticsearchApi elasticsearchApi;

    @Async
    @Override
    public void saveHistoryLog(LogHistory logHistory) {
        String id = UUID.randomUUID().toString();
        elasticsearchApi.insertById("history_log", id, JsonUtil.toJson(logHistory), RequestOptions.DEFAULT);
    }
}
