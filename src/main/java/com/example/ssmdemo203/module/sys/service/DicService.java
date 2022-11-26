package com.example.ssmdemo203.module.sys.service;

import com.example.ssmdemo203.module.sys.pojo.entity.Dic;

import java.util.List;
import java.util.Map;

public interface DicService {
    Integer addDic(Dic dic);
    Integer removeDic(Dic dic);
    Integer updateDic(Dic dic);
    List<Dic> getDicListByType(Dic dic);
    List<Map<String, Object>> getDicWithCreateUsername();
}
