package cn.zhengyk.canal.handler.impl;

import cn.zhengyk.canal.handler.DataDbHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author by yakai.zheng
 * @Description schema: test下 sys_user 表的 增删改 binlog 处理
 * @Date 2020/11/9 10:42
 */
@Component("test.sys_user")
public class SysUserHandler implements DataDbHandler {


    @Override
    public void update(Map<String, String> beforeDataMap, Map<String, String> dataMap) {
        // do your service
    }

    @Override
    public void insert(Map<String, String> afterDataMap) {

    }

    @Override
    public void delete(Map<String, String> beforeDataMap) {

    }
}
