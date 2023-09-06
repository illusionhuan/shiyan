package com.java.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.java.constant.MessageConstant;
import com.java.constant.StatusConstant;
import com.java.dto.DishDTO;
import com.java.dto.DishPageQueryDTO;
import com.java.entity.Dish;
import com.java.entity.DishFlavor;
import com.java.entity.Setmeal;
import com.java.exception.DeletionNotAllowedException;
import com.java.mapper.DishFlavorMapper;
import com.java.mapper.DishMapper;
import com.java.mapper.SetmealDishMapper;
import com.java.mapper.SetmealMapper;
import com.java.result.PageResult;
import com.java.service.DishService;
import com.java.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.bcel.BcelWeaver;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 功能描述:新增菜品
     *
     * @param dishDTO
     * @return
     */

    @Transactional
    @Override
    public void saveWithFlavor(DishDTO dishDTO){
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        //往菜品表dish里面插入一条数据
        dishMapper.insert(dish);
        //获取菜品的主键
        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();

        if(flavors != null && flavors.size() > 0){
            //往口味表dish_flavor里面插入n条
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            //批量插入
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 功能描述:分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids){
        ids.forEach(id->{
            Dish dish = dishMapper.getById(id);
            //判断当前要删除的菜品状态是否为起售中
            if(dish.getStatus()==StatusConstant.ENABLE){
                //如果是起售中，抛出业务异常
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });

        //判断当前要删除的菜品是否被套餐关联了
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(setmealIds != null && setmealIds.size() > 0){
            //如果关联了,抛出异常
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品表里的数据
        ids.forEach(id -> {
            dishMapper.deleteById(id);
            //删除口味表中的数据
            dishFlavorMapper.deleteByDishId(id);
        });
    }

    @Override
    public DishVO getByIdWithFlavor(Long id) {
        //查询菜品表
        Dish dish = dishMapper.getById(id);

        //查询关联的口味
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);

        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }

    /**
     * 功能描述:根据Id修改菜品和关联的口味
     *
     * @param dishDTO
     * @return
     */

    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        //修改菜品表dish,执行update操作
        dishMapper.update(dish);

        //删除当前菜品关联的口味数据,操作dish_flavor,执行delete操作
        dishFlavorMapper.deleteByDishId(dishDTO.getId());

        //插入最新的口味表
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 功能描述:起售、停售商品
     *
     * @param status
     * @param id
     * @return
     */

    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.update(dish);
    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }

}