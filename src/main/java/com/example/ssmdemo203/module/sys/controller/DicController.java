package com.example.ssmdemo203.module.sys.controller;

import cn.hutool.core.net.NetUtil;
import com.example.ssmdemo203.common.Result;
import com.example.ssmdemo203.common.utils.JwtTokenUtil;
import com.example.ssmdemo203.module.sys.pojo.dto.GetDicListDto;
import com.example.ssmdemo203.module.sys.pojo.entity.Dic;
import com.example.ssmdemo203.module.sys.pojo.entity.User;
import com.example.ssmdemo203.module.sys.service.DicService;
import com.example.ssmdemo203.module.sys.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/sys/dic")
public class DicController {

    @Resource
    private DicService dicService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping("/adddic")
    public Result addDic(@RequestBody Dic dic, HttpServletRequest httpServletRequest){
        Result result = new Result();

        String LoginUsername = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        User loginUser = userService.selectUserByUsername(LoginUsername);

        dic.setCreateUser(loginUser.getId());
        dic.setCreateTime(new Date());
        dic.setUpdateUser(loginUser.getId());
        dic.setUpdateTime(new Date());

        Integer reInt = dicService.addDic(dic);
        return result.code(10000).message("新增 " + reInt + " 条字典");
    }

    @ResponseBody
    @RequestMapping("/getdiclistbytype")
    public Result getDicListByType(@RequestBody Dic dic){
        Result result = new Result();
        List<Dic> reList = dicService.getDicListByType(dic);
        return result.code(10000).message("查询成功").data("diclist",reList);
    }

    @ResponseBody
    @RequestMapping("/updatedicbyid")
    public Result updateDicById(@RequestBody Dic dic, HttpServletRequest httpServletRequest){

        Result result = new Result();

        String LoginUsername = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        User loginUser = userService.selectUserByUsername(LoginUsername);

        dic.setUpdateUser(loginUser.getId());
        dic.setUpdateTime(new Date());

        Integer reInt = dicService.updateDic(dic);

        return result.code(10000).message("修改成功 " + reInt + " 条字典");
    }

    @ResponseBody
    @RequestMapping("/removedicbyid")
    public Result removeDicById(@RequestBody Dic dic){

        Result result = new Result();

        Integer reInt = dicService.removeDic(dic);

        return result.code(10000).message("删除成功 " + reInt + " 条字典");
    }

    @ResponseBody
    @RequestMapping("/getDicListWithCreateUsername")
    public Result getDicListWithCreateUsername(){

        Result result = new Result();

        List<Map<String, Object>> selectList = dicService.getDicWithCreateUsername();

        List<GetDicListDto> reList = new ArrayList<>();

        String mac = "";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            mac = NetUtil.getMacAddress(inetAddress);
        }catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(mac);


        for (Map<String, Object> selectMap : selectList) {

            GetDicListDto getDicListDto = new GetDicListDto();

            getDicListDto.setId((Long) selectMap.get("id"));
            getDicListDto.setDic_type((Integer) selectMap.get("dic_type"));
            getDicListDto.setDic_name((String) selectMap.get("dic_name"));
            getDicListDto.setDic_type_name((String) selectMap.get("dic_type_name"));
            getDicListDto.setStatus((Integer) selectMap.get("status"));
            getDicListDto.setCreate_username((String) selectMap.get("create_username"));

            reList.add(getDicListDto);
        }

        return result.code(10000).message("查询成功").data("reList",reList);
    }

}