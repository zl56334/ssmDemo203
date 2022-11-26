package com.example.ssmdemo203.module.sys.pojo.dto;

import lombok.Data;

@Data
public class GetDicListDto {
    private Long id;
    private String dic_name;
    private Integer dic_type;
    private String dic_type_name;
    private Integer status;
    private String create_username;
}
