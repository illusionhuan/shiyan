package com.java.mapper;

import com.java.pojo.Payment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Payment02Mapper {
    /*
    * 创建订单
    * */
    @Insert("insert into payment (serial) value (#{serial})")
    void create(Payment payment);
    /*
    *
    * 按ID查询
    * */
    @Select("select * from payment where id = #{id}")
    Payment queryById(@Param("id")Long id);
}
