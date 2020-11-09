package cn.zhengyk.canal.event;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;

/**
 * @author yakai.zheng
 * 删除事件
 */
public class DeleteCanalEvent extends AbstractCanalEvent {
    public DeleteCanalEvent(Entry source) {
        super(source);
    }
}
