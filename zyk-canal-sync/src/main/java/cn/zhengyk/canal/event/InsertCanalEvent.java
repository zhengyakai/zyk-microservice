package cn.zhengyk.canal.event;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;

/**
 * @author yakai.zheng
 * 插入事件
 */
public class InsertCanalEvent extends AbstractCanalEvent {
    public InsertCanalEvent(Entry source) {
        super(source);
    }
}
