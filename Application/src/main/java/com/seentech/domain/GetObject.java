package com.seentech.domain;

import com.google.gson.Gson;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by seentech on 2017/2/10.
 */
public class GetObject {

    /**
     *
     * @param esClient
     * @param index
     * @param type
     * @param id
     * @return
     */
    public String getMacLogStr(ESClient esClient, String index, String type, String id)
    {
        TransportClient client = esClient.getClient();

        if(client == null){
            System.out.println("Client is null");

            return null;
        }

        GetResponse responseGet = client.prepareGet(index, type, id).execute().actionGet();

        //获取结果
        String indexTypeId = "{\"index\": {\"_type\": \""+ type + "\", \"_id\": \""+ id + "\"}}";
        String json = responseGet.getSourceAsString();

        System.out.println(indexTypeId);
        System.out.println(json);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indexTypeId + "\n");
        stringBuilder.append(json + "\n");

        String filePath ="./" + index + "_" + type + ".json";
        writeStringToFile(filePath, stringBuilder);

        return json;
    }

    public MacLog getMacLogObj(ESClient esClient)
    {
        TransportClient client = esClient.getClient();

        if(client == null){
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

    /**
     * 清空文件,并写入字符串到文件
     * @param filePath
     * @param stringBuilder
     */
    public void writeStringToFile(String filePath, StringBuilder stringBuilder) {
        FileWriter fileWriter = null;
        BufferedWriter bw = null;

        try {
            fileWriter = new FileWriter(filePath);// 向文件中写入刚才读取文件中的内容

            bw = new BufferedWriter(fileWriter);
            bw.write(stringBuilder.toString());

            bw.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test(){
        ESClient esClient = new ESClient();

        GetObject getObject = new GetObject();

        getObject.getMacLogStr(esClient,"mac_2020_01_01_01", "type", "3815027");

        getObject.getMacLogObj(esClient);

    }

}
