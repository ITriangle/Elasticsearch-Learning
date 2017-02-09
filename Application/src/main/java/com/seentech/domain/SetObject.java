package com.seentech.domain;

import com.google.gson.Gson;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;

/**
 * Created by seentech on 2017/2/9.
 */
public class SetObject {

    @Test
    public void testES() {
        TransportClient client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.4.84"), 9300));

            GetResponse responseGet = client.prepareGet("mac_2020_01_01_01", "type", "3815027").execute().actionGet();
            //输出结果
            System.out.println(responseGet.getSourceAsString());

//            Gson gson = new GsonBuilder().serializeNulls().create();
            Gson gson = new Gson();
            MacLog macLog = gson.fromJson(responseGet.getSourceAsString(), MacLog.class);

            System.out.println(macLog);

            String json = gson.toJson(macLog);
            System.out.println(json);

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
}
