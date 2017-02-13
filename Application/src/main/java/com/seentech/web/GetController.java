package com.seentech.web;

import com.google.gson.Gson;
import com.seentech.service.ExecRequest;
import org.springframework.web.bind.annotation.*;

/**
 * Created by seentech on 2017/2/10.
 */
@RestController
@RequestMapping(value = "/")
public class GetController {


    /**
     * http://localhost:8080/index/_search
     *
     * @return
     */
    @RequestMapping(value = "/{index}/_search")
    public String indexGet(@PathVariable String index) {

        return index;
    }

    /**
     * 通过以下链接获取值,并且生成对应的文件
     * http://localhost:8080/index/type/id/_search
     *
     * @param index
     * @param type
     * @param id
     * @return
     */
    @RequestMapping(value = "/{index}/{type}/{id}/_search")
    public String indexGet1(@PathVariable String index, @PathVariable String type, @PathVariable String id) {

        return new ExecRequest().ExecIndexGet(index, type, id);
    }


    /**
     * 通过以下链接获取值,并生成对应的文件
     * http://localhost:8080/mac_2020_01_01_01/type/_get?q=id:3815027
     *
     * @param index
     * @param type
     * @param questionPara
     * @return
     */
    @RequestMapping(value = "/{index}/{type}/_get")
    public String indexGet2(@PathVariable String index, @PathVariable String type, @RequestParam("q") String questionPara) {

        int num = questionPara.lastIndexOf(":");
        String id = questionPara.substring(num + 1);

        return new ExecRequest().ExecIndexGet(index, type, id);
    }

    /**
     * http://localhost:8080/mac_2020_01_01_01/type/_search
         {
             "query": {
             "term": {
             "id": "3815027"
             }
             }
         }
     * @param index
     * @param type
     * @param questionPara
     * @return
     */
    @RequestMapping(value = "/{index}/{type}/_search")
    public String indexGet3(@PathVariable String index, @PathVariable String type, @RequestBody String questionPara){

        Gson gson = new Gson();
        QueryParam queryParam = gson.fromJson(questionPara, QueryParam.class);
        String id = queryParam.getQuery().getTerm().getId();

        return new ExecRequest().ExecIndexGet(index, type, id);
    }

    @RequestMapping(value = "/{index}/{type}/_scroll")
    public String scroll(@PathVariable String index, @PathVariable String type, @RequestBody String questionParam){

        return null;
    }


}
