package com.java.mapper;

import com.github.pagehelper.Page;
import com.java.dto.EmployeePageQueryDTO;
import com.java.entity.Employee;
import org.apache.ibatis.annotations.*;

/**
 * 员工映射器
 *
 * @author mz
 * @date 2023/08/30
 */
@Mapper
public interface EmployeeMapper {

    /**
     * 得到用户名
     *
     * @param username 用户名
     * @return {@link Employee}
     */
//    @Select("SELECT id,name,username,password,phone,sex,id_number AS idNumber, status,create_time AS createTime, update_time AS updateTime, create_user AS createUser, update_user AS updateUser FROM employee where username = #{username}")
//    @Results({
//            @Result(column = "idNumber", property = "idNumber"),
//            @Result(column = "createTime", property = "createTime"),
//            @Result(column = "updateTime", property = "updateTime"),
//            @Result(column = "createUser",property = "createUser"),
//            @Result(column = "updateUser", property = "updateUser")
//    })
    @Select("SELECT * FROM employee WHERE username = #{username}")
    Employee getByUsername(String username);

    @Insert("insert into employee(name,username,password,phone,sex,id_number,status,create_time,update_time,create_user,update_user) " +
            "values (#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Employee employee);


    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
}
