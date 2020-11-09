package cn.zhengyk.canal.event;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import org.springframework.context.ApplicationEvent;

public abstract class AbstractCanalEvent extends ApplicationEvent {


    public AbstractCanalEvent(Entry source) {
        super(source);
    }

    public Entry getEntry() {
        return (Entry) source;
    }
}
