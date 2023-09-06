package com.java.service;

import com.java.dto.DishDTO;
import com.java.dto.DishPageQueryDTO;
import com.java.entity.Dish;
import com.java.result.PageResult;
import com.java.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {
    /**
     * 功能描述:新增菜品
     *
     * @param dishDTO
     * @return
     * @date 2023/09/03
     */

    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 功能描述:分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 功能描述:批量删除菜品
     *
     * @param ids
     * @return
     */

    void deleteBatch(List<Long> ids);

    /**
     * 功能描述:查询菜品和口味
     *
     * @param id
     * @return
     */

    DishVO getByIdWithFlavor(Long id);

    /**
     * 功能描述:修改菜品
     *
     * @param dishDTO
     * @return
     */

    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 功能描述:起售、停售商品
     *
     * @param status
     * @param id
     * @return
     */

    void startOrStop(Integer status,Long id);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}
