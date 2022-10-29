package com.example.ssmdemo203.module.sys.dao;


import com.example.ssmdemo203.module.sys.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("roleDao")
public interface RoleDao {

    @Select("SELECT\n" +
            "\tsr.id, \n" +
            "\tsr.role_name, \n" +
            "\tsr.role_desc, \n" +
            "\tsr.`status`, \n" +
            "\tsr.create_user, \n" +
            "\tsr.create_time, \n" +
            "\tsr.update_user, \n" +
            "\tsr.update_time\n" +
            "FROM\n" +
            "\tsys_role AS sr\n" +
            "\tINNER JOIN\n" +
            "\tsys_user_role AS sur\n" +
            "\tON \n" +
            "\t\tsr.id = sur.role_id\n" +
            "WHERE\n" +
            "\tsur.user_id = #{uid}")
    List<Role> findByUid(Integer uid);

    @Select("SELECT\n" +
            "\tsr.role_name\n" +
            "FROM\n" +
            "\tsys_role AS sr\n" +
            "\tINNER JOIN\n" +
            "\tsys_user_role AS sur\n" +
            "\tON \n" +
            "\t\tsr.id = sur.role_id\n" +
            "WHERE\n" +
            "\tsur.user_id = #{uid}")
    List<String> findRoleNameByUid(Integer uid);
}
