package com.example.ssmdemo203.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {

    //返回码
    private Integer code;
    //返回消息
    private String message;
    //返回数据
    private Map<String,Object> data = new HashMap<>();

    //构造函数1
    public Result(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    //构造函数2
    public Result(Integer code,String message,Map<String,Object> data){
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

    public Result() {

    }

    //
    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }

    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

}
