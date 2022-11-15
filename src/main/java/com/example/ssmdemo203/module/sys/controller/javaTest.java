package com.example.ssmdemo203.module.sys.controller;

import java.io.IOException;

public class javaTest {
    // public 权限
    // String 反参类型
    // String res 入参类型 入参变量名
    public String test(String res){

        String rs = res;

        if(res != null || res != null && res != null){

        }

        try {

            System.out.println(1);
            System.out.println(2);
            System.out.println(3);

        } catch (Exception e) {

            e.printStackTrace();

            if (e.equals("timeout")){
                test("123");
            }


        } finally {

            rs = res;

        }

        return rs;
    }

}
