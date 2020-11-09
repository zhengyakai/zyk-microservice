package cn.zhengyk.canal.listener;

import cn.zhengyk.canal.event.AbstractCanalEvent;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yakai.zheng
 * 事件监听器
 */
@Slf4j
public abstract class AbstractCanalListener<EVENT extends AbstractCanalEvent> implements ApplicationListener<EVENT> {

    protected static final String SCHEMA_TABLE_SEPARATOR = "." ;


    /**
     * 监听到相应的 binlog 后的同步操作
     */
    protected abstract void doSync(String schemaName, String table, RowData rowData);

    @Override
    public void onApplicationEvent(EVENT event) {
        
        Entry entry = event.getEntry();
        String schemaName = entry.getHeader().getSchemaName();
        String table = entry.getHeader().getTableName();
        RowChange change;
        try {
            change = RowChange.parseFrom(entry.getStoreValue());
        } catch (InvalidProtocolBufferException e) {
            log.error("根据 CanalEntry 获取 RowChange 失败！", e);
            return;
        }
        change.getRowDatasList().forEach(rowData -> doSync(schemaName, table, rowData));
    }

    /**
     * columns 转 map
     */
    protected Map<String,String> columnsToMap(List<Column> columns) {
        return columns.stream().collect(Collectors.toMap(Column::getName, Column::getValue));
    }

}
