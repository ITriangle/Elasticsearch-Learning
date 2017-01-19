package com.seentech.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
