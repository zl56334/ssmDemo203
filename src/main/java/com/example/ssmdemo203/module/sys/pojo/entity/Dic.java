package com.example.ssmdemo203.module.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "sys_dic")
public class Dic {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String dicName;
    private Integer dicType;
    private String dicTypeName;
    private Integer status;
    private Integer createUser;
    private Date createTime;
    private Integer updateUser;
    private Date updateTime;

}
