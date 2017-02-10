package com.seentech.service;

import com.seentech.domain.ESClient;
import com.seentech.domain.GetObject;

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
            return json;
        }
    }
}
