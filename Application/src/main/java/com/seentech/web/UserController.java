package com.seentech.web;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by seentech on 2017/1/19.
 */
@RestController
@RequestMapping(value = "/users") // 通过这里配置使下面的映射都在/users下，可去除
public class UserController {

    /**
     * http://localhost:8080/users/hello
     * @return
     */
    @RequestMapping("/hello")
    public String index() {
        return "Hello User";
    }

    /**
     * 处理"/users/id"的Get请求,通过id获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable Long id) {

        TransportClient client = null;
        GetResponse responseGet;

        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

            //搜索数据
//            responseGet = client.prepareGet("twitter", "tweet", "1").execute().actionGet();
            responseGet = client.prepareGet("twitter", "tweet", id.toString()).execute().actionGet();
            //输出结果
            System.out.println(responseGet.getSourceAsString());

            //关闭client
            client.close();

            return responseGet.getSourceAsString();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "ID :" + id;

    }

    @RequestMapping(value = "/user")
    public String putUserTest(){

        TransportClient client = null;

        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

            //创建数据
            String json = "{" +
                    "\"user\":\"wangl\"," +
                    "\"postDate\":\"2016-01-30\"," +
                    "\"message\":\"trying out Elasticsearch\"" +
                    "}";

            IndexResponse responseIndex = client.prepareIndex("user", "seentech","1")
                    .setSource(json)
                    .get();

            return "ok";

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "NULL";
    }

    /**
     * 测试 JSON Put 更新数据
     * http://localhost:8080/users/user/seentech
     * @param json
     * @return
     */
    @RequestMapping(value = "/user/seentech", method = RequestMethod.PUT)
    public String putUserSeentech(@RequestBody String json){
        TransportClient client = null;

        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

            //创建数据
//            String json = "{" +
//                    "\"user\":\"wangl\"," +
//                    "\"postDate\":\"2016-01-30\"," +
//                    "\"message\":\"trying out Elasticsearch\"" +
//                    "}";

            IndexResponse responseIndex = client.prepareIndex("user", "seentech","3")
                    .setSource(json)
                    .get();

            return json;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "NULL";
    }
}
