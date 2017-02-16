package com.seentech.domain;

import com.google.gson.Gson;
import com.seentech.service.FileOperation;
import com.seentech.web.GsonTest.ExpressionObject;
import com.seentech.web.GsonTest.RangeObject;
import com.seentech.web.GsonTest.SearchObject;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.sort.SortParseElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by seentech on 2017/2/15.
 */
public class ScrollGet {

    /**
     * 从 ES 中获取数据
     *
     * @param esClient
     * @param index
     * @param type
     * @param searchObject
     * @return
     */
    public List<String> scrollMacLog(ESClient esClient, String index, String type, SearchObject searchObject) {

        TransportClient client = esClient.getClient();

        if (client == null) {
            System.out.println("Client is null");

            return null;
        }

        List<String> stringList = new ArrayList<>();


        /**
         * 获取条件
         */
        Gson gson = new Gson();

        System.out.println(searchObject.toString());

        Map<String, Object> must = searchObject.getQuery().getMust();


        BoolQueryBuilder boolQueryBuilder = boolQuery().must(matchAllQuery());

        QueryBuilder qb = null;

        for (String key : must.keySet()) {
            System.out.println("key: " + key);
            System.out.println(must.get(key).toString());

            if (key.equals("range")) {
                RangeObject rangeObject = gson.fromJson(must.get(key).toString(), RangeObject.class);
                System.out.println(rangeObject.toString());


                qb = rangeQuery(rangeObject.getField())
                        .from(rangeObject.getGt())
                        .to(rangeObject.getLt())
                        .includeLower(Boolean.valueOf(rangeObject.getGe()))
                        .includeUpper(Boolean.valueOf(rangeObject.getLe()));

            } else {
                ExpressionObject expressionObject = gson.fromJson(must.get(key).toString(), ExpressionObject.class);
                System.out.println(expressionObject.toString());

                qb = termQuery(expressionObject.getField(), expressionObject.getValue());
            }

            if (qb != null) {

                boolQueryBuilder = boolQueryBuilder.must(qb);

            }

        }


        QueryBuilder qbMust = boolQueryBuilder;

        SearchResponse scrollResp = null;
        if (type != null) {
            scrollResp = client.prepareSearch(index)
                    .setTypes(type)
                    .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
                    .setScroll(new TimeValue(60000))
                    .setQuery(qbMust)
                    .setSize(10).execute().actionGet(); //100 hits per shard will be returned for each scroll
        } else {
            scrollResp = client.prepareSearch(index)
                    .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
                    .setScroll(new TimeValue(60000))
                    .setQuery(qbMust)
                    .setSize(10).execute().actionGet(); //100 hits per shard will be returned for each scroll
        }


        while (true) {

            System.out.println("显示结果:");

            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //Handle the hit...

                /**
                 * 在这里获取返回的结果
                 */
                //直接返回结果
//                stringList.add(hit.getSourceAsString());

                //拼装后返回
                stringList.add(FileOperation.structeBulkStr(hit.getType(), hit.getId(), hit.getSourceAsString()));

            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {

                System.out.println("显示完毕!");
                break;
            }

        }

        return stringList;
    }

}
