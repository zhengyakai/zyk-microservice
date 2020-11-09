package cn.zhengyk.es.service;

import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/11/9 14:42
 */
public interface ElasticsearchApi {

    String insertById(String index, String id, String documentJson, RequestOptions requestOptions);

    DeleteResponse deleteById(String index, String id);

    BulkResponse batchInsert(String index, List<Map<String, Object>> list);

    void deleteByQuery(DeleteByQueryRequest queryRequest, RequestOptions requestOptions);
}
