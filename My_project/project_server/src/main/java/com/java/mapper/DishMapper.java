package com.java.mapper;

import com.github.pagehelper.Page;
import com.java.annotation.AutoFill;
import com.java.dto.DishPageQueryDTO;
import com.java.entity.Dish;
import com.java.enumeration.OperationType;
import com.java.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {

    /**
     * 插入菜品数据
     *
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 功能描述:
     *
     * @return
     */

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 功能描述:按主键ID查询菜品
     *
     * @param id
     * @return
     */

    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /**
     * 功能描述:按ID删除
     *
     * @param id
     * @return
     */

    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);
}
