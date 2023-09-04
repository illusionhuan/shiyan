package com.java.service;

import com.java.dto.DishDTO;
import com.java.dto.DishPageQueryDTO;
import com.java.result.PageResult;
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
}
