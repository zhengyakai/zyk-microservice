package cn.zhengyk.canal.schedule;

import cn.zhengyk.canal.config.BaseHolder;
import cn.zhengyk.canal.event.DeleteCanalEvent;
import cn.zhengyk.canal.event.InsertCanalEvent;
import cn.zhengyk.canal.event.UpdateCanalEvent;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author yakai.zheng
 */
@Slf4j
@Component
public class CanalSchedule implements DisposableBean {

    @Autowired
    private CanalConnector canalConnector;

    @Value("${canal.batchSize:1000}")
    private int batchSize;

    /**
     * 每200毫秒拉取一次数据
     */
    @Async("canal")
    @Scheduled(fixedDelay = 200)
    public void fetch() {
        try {
            Message message = canalConnector.getWithoutAck(batchSize);
            long batchId = message.getId();
            log.debug("batchId={}" , batchId);
            try {
                List<Entry> entries = message.getEntries();
                if (batchId != -1 && entries.size() > 0) {
                    entries.forEach(entry -> {
                        if (entry.getEntryType() == EntryType.ROWDATA) {
                            CanalEntry.EventType eventType = entry.getHeader().getEventType();
                            switch (eventType) {
                                case INSERT:
                                    BaseHolder.applicationContext.publishEvent(new InsertCanalEvent(entry));
                                    break;
                                case UPDATE:
                                    BaseHolder.applicationContext.publishEvent(new UpdateCanalEvent(entry));
                                    break;
                                case DELETE:
                                    BaseHolder.applicationContext.publishEvent(new DeleteCanalEvent(entry));
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                }
                canalConnector.ack(batchId);
            } catch (Exception e) {
                log.error("批量获取 mysql 同步信息失败，batchId回滚,batchId=" + batchId, e);
                // 发生异常时，调用 rollback，那么下次抓取 binlog 的时候还会把上次异常的 binlog 抓过来。
                // 如果异常一直存在那么会反复抓取，可能导致 binlog 消费阻塞，具体是 ack 还是 rollback 看业务而定
                // canalConnector.ack(batchId);
                canalConnector.rollback(batchId);
            }
        } catch (Exception e) {
            log.error("Canal 定时消费 binlog 异常:", e);
        }
    }

    @Override
    public void destroy() {
        Optional.ofNullable(canalConnector).ifPresent(CanalConnector::disconnect);
    }
}
