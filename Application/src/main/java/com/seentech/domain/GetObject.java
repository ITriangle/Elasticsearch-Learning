package com.seentech.domain;

import com.google.gson.Gson;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.jupiter.api.Test;

/**
 * Created by seentech on 2017/2/10.
 */
public class GetObject {

    /**
     * @param esClient
     * @param index
     * @param type
     * @param id
     * @return
     */
    public String getMacLogStr(ESClient esClient, String index, String type, String id) {
        TransportClient client = esClient.getClient();

        if (client == null) {
            System.out.println("Client is null");

            return null;
        }

        GetResponse responseGet = client.prepareGet(index, type, id).execute().actionGet();

        //获取结果

        return responseGet.getSourceAsString();
    }

    public MacLog getMacLogObj(ESClient esClient) {
        TransportClient client = esClient.getClient();

        if (client == null) {
            System.out.println("Client is null");

            return null;
        }

        GetResponse responseGet = client.prepareGet("mac_2020_01_01_01", "type", "3815027").execute().actionGet();

        //输出结果
        Gson gson = new Gson();
        MacLog macLog = gson.fromJson(responseGet.getSourceAsString(), MacLog.class);

        System.out.println(macLog);

        String json = gson.toJson(macLog);
        System.out.println(json);

        return macLog;
    }


    /*public String scrollMacLog(ESClient esClient, String index, String type, QueryParamAreaCode queryParamAreaCode) {

        TransportClient client = esClient.getClient();

        if (client == null) {
            System.out.println("Client is null");

            return null;
        }

//        QueryBuilder qb = termQuery("area_code", "120105");

        QueryBuilder qb = termQuery("area_code", queryParamAreaCode.getQuery().getTerm().getArea_code());

        QueryBuilder qbAll = matchAllQuery();

        QueryBuilder qbBool = boolQuery().must(qbAll).must(qb);

        SearchResponse scrollResp = client.prepareSearch(index)
                .setTypes(type)
                .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(60000))
                .setQuery(qbBool)
                .setSize(10).execute().actionGet(); //100 hits per shard will be returned for each scroll

        while (true) {

            System.out.println("显示结果:");

            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //Handle the hit...

                System.out.println("once!");
                System.out.println(hit.getSourceAsString());


            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {

                System.out.println("显示完毕!");
                break;
            }

        }

        return null;

    }*/

    @Test
    public void test() {
        ESClient esClient = new ESClient();

        GetObject getObject = new GetObject();

        getObject.getMacLogStr(esClient, "mac_2020_01_01_01", "type", "3815027");

        getObject.getMacLogObj(esClient);

    }

    @Test
    public void test2(){

        ESClient esClient = new ESClient();

        GetObject getObject = new GetObject();

        Gson gson = new Gson();
        String json = "{query : {\"term\":{\"area_code\":\"120105\"}}}";
//        QueryParamAreaCode queryParamAreaCode = gson.fromJson(json, QueryParamAreaCode.class);
//
//        getObject.scrollMacLog(esClient, "mac_2020_01_01_01", "type", queryParamAreaCode);


    }

}
