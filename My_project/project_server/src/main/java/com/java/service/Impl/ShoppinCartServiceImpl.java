package com.java.service.Impl;

import com.java.context.BaseContext;
import com.java.dto.ShoppingCartDTO;
import com.java.entity.Dish;
import com.java.entity.Setmeal;
import com.java.entity.ShoppingCart;
import com.java.mapper.DishMapper;
import com.java.mapper.SetmealMapper;
import com.java.mapper.ShoppingCartMapper;
import com.java.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppinCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 功能描述:添加购物车
     *
     * @param shoppingCartDTO
     * @return
     */

    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);

        //只能查询自己的购物车数据
//        Long userId = BaseContext.getCurrentId();
//        shoppingCart.setUserId(userId);
        shoppingCart.setUserId(1L);



        //判断商品是否在购物车中
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if(list != null && list.size() == 1){
            //如果已经存在，就更新数量
            shoppingCart = list.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(shoppingCart);
        }else {
            //如果不存在，需要插入一条购物车数据
            //判断本次添加到购物车的是菜品还是套餐
            Long dishId = shoppingCartDTO.getDishId();
            if(dishId != null){
                //本次添加到购物车的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }else{
                //本次添加到购物车的是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    /**
     * 功能描述:展示购物车
     *
     * @return
     */

    @Override
    public List<ShoppingCart> showShoppingCart() {
        return shoppingCartMapper.list(ShoppingCart.builder().userId(1L).build());
    }

    /**
     * 功能描述:清空购物车
     *
     * @return
     */

    @Override
    public void cleanShoppingCart() {
//        shoppingCartMapper.deleteById(BaseContext.getCurrentId());
        shoppingCartMapper.deleteById(1L);
    }
}
