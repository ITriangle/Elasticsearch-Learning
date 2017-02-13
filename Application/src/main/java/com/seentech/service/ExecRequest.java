package com.seentech.service;

import com.seentech.domain.ESClient;
import com.seentech.domain.GetObject;
import com.seentech.web.QueryParam;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by seentech on 2017/2/10.
 */
public class ExecRequest {

    /**
     * 执行 get API 请求
     * @param index
     * @param type
     * @param id
     * @return
     */
    public String ExecIndexGet(String index, String type, String id){

        GetObject getObject = new GetObject();

        ESClient esClient = new ESClient();

        String json =  getObject.getMacLogStr(esClient, index, type, id);


        if (json == null){
            return "No data: index : " + index + " ,type : " + type + " ,id :" + id ;
        }
        else {

            //拼装写入文件的字符串
            String indexTypeId = "{\"index\": {\"_type\": \""+ type + "\", \"_id\": \""+ id + "\"}}";

            System.out.println(indexTypeId);
            System.out.println(json);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(indexTypeId + "\n");
            stringBuilder.append(json + "\n");

            String filePath ="./" + index + "_" + type + ".json";
            writeStringToFile(filePath, stringBuilder);

            return json;
        }
    }


    public String ExecScroll(String index, String type, QueryParam queryParam){


        return null;
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
}
