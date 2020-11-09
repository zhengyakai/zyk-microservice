package cn.zhengyk.es.service.impl;

import cn.zhengyk.es.service.ElasticsearchApi;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/11/9 14:42
 */
@Slf4j
@Service
public class ElasticsearchApiImpl implements ElasticsearchApi {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Override
    public String insertById(String index, String id, String documentJson, RequestOptions requestOptions) {
        IndexRequest indexRequest = new IndexRequest(index);
        try {
            indexRequest.id(id);
            indexRequest.source(documentJson, XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, requestOptions);
            return indexResponse.getId();
        } catch (Exception e) {
            log.error("failed to insert index: {}, document: {}, id: {}.", index, documentJson, id, e);
        }
        return null;
    }

    @Override
    public DeleteResponse deleteById(String index, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        try {
           return restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        }catch (Exception e) {
            log.error("failed to delete index:{}, id:{}.", index, id, e);
        }
        return null;
    }

    @Override
    public BulkResponse batchInsert(String index, List<Map<String, Object>> list) {
        try {
            BulkRequest bulkRequest = new BulkRequest();
            IndexRequest request;
            for (Map<String, Object> map : list) {
                request = new IndexRequest(index);
                if (map.get("id") != null) {
                    request.id(String.valueOf(map.get("id"))).source(map, XContentType.JSON);
                } else {
                    request.source(map, XContentType.JSON);
                }
                bulkRequest.add(request);
            }
            return restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("batchInsert failed:", e);
        }
        return null;
    }

    @Override
    public void deleteByQuery(DeleteByQueryRequest queryRequest, RequestOptions requestOptions) {
        try {
            restHighLevelClient.deleteByQuery(queryRequest, requestOptions);
        } catch (IOException e) {
            log.error("deleteByQuery ex:", e);
        }
    }
}
