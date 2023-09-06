package com.java.mapper;

import com.java.annotation.AutoFill;
import com.java.entity.AddressBook;
import com.java.enumeration.OperationType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    /**
     * 新增
     * @param addressBook
     */
    @Insert("insert into address_book" +
            "        (user_id, consignee, phone, sex, province_code, province_name, city_code, city_name, district_code," +
            "         district_name, detail, label, is_default)" +
            "        values (#{userId}, #{consignee}, #{phone}, #{sex}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}," +
            "                #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void insert(AddressBook addressBook);

    /**
     * 查询所有
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 按Id查询
     */
    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);

    /**
     * 按Id删除
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);

    /**
     * 按Id修改
     */
    void updateById(AddressBook addressBook);

    /**
     * 按Id修改
     */
    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefaultByUserId(AddressBook addressBook);
}
