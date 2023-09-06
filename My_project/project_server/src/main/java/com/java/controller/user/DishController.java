package com.java.controller.user;

import com.java.constant.StatusConstant;
import com.java.entity.Dish;
import com.java.result.Result;
import com.java.service.DishService;
import com.java.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId) {
        //构造redis缓存key,规则为dish_分类id
        String key = "dish_" + categoryId;

        //查询redis中是否有缓存数据
        List<DishVO> list = (List<DishVO>)redisTemplate.opsForValue().get(key);

        //存在缓存数据,直接返回
        if(list != null && list.size() > 0){
            return Result.success(list);
        }

        Dish dish = new Dish();

        dish.setCategoryId(categoryId);
        System.out.println(categoryId);
        dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品

        list = dishService.listWithFlavor(dish);

        //将查询到的数据存载入缓存
        redisTemplate.opsForValue().set(key,list);

        return Result.success(list);
    }

}
