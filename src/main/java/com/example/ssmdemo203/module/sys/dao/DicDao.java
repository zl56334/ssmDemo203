package com.example.ssmdemo203.module.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ssmdemo203.module.sys.pojo.entity.Dic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DicDao extends BaseMapper<Dic> {
    List<Map<String,Object>> getDicWithCreateUsername();
}
