package com.seentech;

import org.junit.Test;

/**
 * Created by seentech on 2017/2/15.
 */
public class TestType {

    @Test
    public void test1(){
        String string = "true";

        boolean bl = Boolean.valueOf(string);

        System.out.println(string + " : " + bl);
    }
}
