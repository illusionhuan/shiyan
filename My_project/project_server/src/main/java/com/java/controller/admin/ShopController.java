package com.java.controller.admin;

import com.java.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺操作接口")
@Slf4j
public class ShopController {
    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 功能描述:设置店铺状态
     *
     * @param status
     * @return
     */

    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result<String> setStatus(@PathVariable Integer status){
        log.info("设置店铺状态:{}",status==1?"营业中":"打烊中");
        redisTemplate.opsForValue().set(KEY,status);

        return Result.success();
    }

    /**
     * 功能描述:查询店铺营业状态
     *
     * @return
     */

    @GetMapping("/status")
    @ApiOperation("查询店铺营业状态")
    public Result<Integer> getStatus() {
        Integer status = (Integer)redisTemplate.opsForValue().get(KEY);
        log.info("查询店铺营业状态:{}",status == 1?"营业中":"打烊中");
        return Result.success(status);
    }
}
