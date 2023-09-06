package com.java.controller.admin;

import com.java.dto.DishDTO;
import com.java.dto.DishPageQueryDTO;
import com.java.entity.Dish;
import com.java.result.PageResult;
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
import java.util.Set;

/*
* 菜品管理
* */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController{

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 功能描述:新增菜品
     *
     * @param dishDTO
     * @return
     */

    @PostMapping
    @ApiOperation("新增菜品接口")
    public Result<String> save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品:{}", dishDTO);

        dishService.saveWithFlavor(dishDTO);

        Long categoryId = dishDTO.getCategoryId();
        String key = "dish_" + categoryId;
        cleanCache(key);
        return Result.success();
    }

    /**
     * 功能描述:分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */

    @GetMapping("/page")
    @ApiOperation("分页查询接口")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询:{}",dishPageQueryDTO);

        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 功能描述:批量删除
     *
     * @param ids
     * @return
     */

    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result<String> delete(@RequestParam List<Long> ids){
        log.info("批量删除菜品:{}",ids);
        dishService.deleteBatch(ids);

        //删除所有菜品的缓存数据
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 功能描述:按Id查询菜品
     *
     * @param id
     * @return
     */

    @GetMapping("/{id}")
    @ApiOperation("按ID查询菜品")
    public Result<DishVO> getByID(@PathVariable Long id){
        log.info("按ID查询菜品:{}",id);
        return Result.success(dishService.getByIdWithFlavor(id));
    }

    /**
     * 功能描述:修改菜品
     *
     * @param dishDTO
     * @return
     */

    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品:{}",dishDTO);
        dishService.updateWithFlavor(dishDTO);

        //删除所有菜品的缓存数据
        cleanCache("dish_*");

        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("起售/停售菜品")
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("起售/停售菜品:{},{}",status,id);
        dishService.startOrStop(status,id);

        //删除所有菜品的缓存数据
        cleanCache("dish_*");

        return Result.success();
    }
    /**
     * 清理缓存数据
     * @param pattern
     */

    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}