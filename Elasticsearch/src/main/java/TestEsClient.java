import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by seentech on 2017/1/17.
 */
public class TestEsClient {

    public static void main(String[] args){
        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        //创建client
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //创建数据
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        IndexResponse responseIndex = client.prepareIndex("twitter", "tweet", "1")
                .setSource(json)
                .get();

        //搜索数据
        GetResponse responseGet = client.prepareGet("twitter", "tweet", "1").execute().actionGet();
        //输出结果
        System.out.println(responseGet.getSourceAsString());
        //关闭client
        client.close();
    }

}
