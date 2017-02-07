package com.seentech;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.sort.SortParseElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Test
    public void contextLoads() {
    }

    /**
     * 测试创建数据
     */
    @Test
    public void testES() {
        TransportClient client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

            //创建数据
            String json = "{" +
                    "\"user\":\"Kiven\"," +
                    "\"postDate\":\"2013-01-29\"," +
                    "\"message\":\"trying in Elasticsearch\"" +
                    "}";
            IndexResponse responseIndex = client.prepareIndex("twitter", "tweet", "2")
                    .setSource(json)
                    .get();

            //搜索数据
            GetResponse responseGet = client.prepareGet("twitter", "tweet", "1").execute().actionGet();
            //输出结果
            System.out.println(responseGet.getSourceAsString());

            //关闭client
            client.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Catche UnknownHostException");
            return;
        } finally {
            System.out.println("finally UnknownHostException");
        }

        System.out.println("Still excute");
    }

    /**
     * 测试搜索
     */
    @Test
    public void testSearch(){
        TransportClient client = null;

        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        /**
         * 执行搜索
         */
        SearchResponse response = client.prepareSearch("twitter")
                .setTypes("tweet")
                .setFrom(0).setSize(60).setExplain(true)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("postDate", "2013-01-29"))
                .execute()
                .actionGet();

        /**
         * 获取搜索结果
         */
        for (SearchHit hit : response.getHits().getHits()) {
            //Handle the hit...

            System.out.println(hit.getId());
            System.out.println(hit.sourceAsString());


        }

        /**
         * 关闭连接
         */
        if (client != null) {
            client.close();
        }

    }

    /**
     * 测试 scroll
     */
    @Test
    public void testScroll() {

        TransportClient client = null;

        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        QueryBuilder qb = termQuery("user", "kimchy1");

        QueryBuilder qbAll = matchAllQuery();

        SearchResponse scrollResp = client.prepareSearch("twitter")
                .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(60000))
				.setQuery(qbAll)
                .setSize(100).execute().actionGet(); //100 hits per shard will be returned for each scroll

        //Scroll until no hits are returned
        while (true) {

            System.out.println("显示结果:");

            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //Handle the hit...

                System.out.println(hit.getSourceAsString());


            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {

                System.out.println("显示完毕!");
                break;
            }

        }

        if (client != null) {
            client.close();
        }
    }
}
