package cn.zhengyk.canal.handler;

import java.util.Map;

/**
 * @author yakai.zheng
 * DataDbHandler 实现类 bean 命名用 schema.table 格式命名
 * 如果多数据源下存在 schema,table 都重名的需要单独处理
 */
public interface DataDbHandler {

    void update(Map<String, String> beforeDataMap, Map<String, String> dataMap);

    void insert(Map<String, String> afterDataMap);

    void delete(Map<String, String> beforeDataMap);

}