package com.example.ssmdemo203.module.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.example.ssmdemo203.module.sys.dao.DicDao;
import com.example.ssmdemo203.module.sys.pojo.entity.Dic;
import com.example.ssmdemo203.module.sys.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {

    @Resource
    private DicDao dicDao;

    @Override
    public Integer addDic(Dic dic) {
        return dicDao.insert(dic);
    }

    @Override
    public Integer removeDic(Dic dic) {
        return dicDao.deleteById(dic);
    }

    @Override
    public Integer updateDic(Dic dic) {
        return dicDao.updateById(dic);
    }

    @Override
    public List<Dic> getDicListByType(Dic dic) {
        QueryWrapper<Dic> queryWrapper =new QueryWrapper<>();
        // where dic.dic_type = #{dic_type} and status = #{status}
        queryWrapper.eq("dic_type",dic.getDicType()).eq("status",dic.getStatus());
        return dicDao.selectList(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> getDicWithCreateUsername() {
        return dicDao.getDicWithCreateUsername();
    }
}
