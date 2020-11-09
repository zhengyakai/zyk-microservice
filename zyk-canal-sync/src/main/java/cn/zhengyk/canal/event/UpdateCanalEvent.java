package cn.zhengyk.canal.event;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;

/**
 * @author yakai.zheng
 * 更新事件
 */
public class UpdateCanalEvent extends AbstractCanalEvent {
    public UpdateCanalEvent(Entry source) {
        super(source);
    }
}
