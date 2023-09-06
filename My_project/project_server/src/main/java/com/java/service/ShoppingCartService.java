package com.java.service;

import com.java.dto.ShoppingCartDTO;
import org.springframework.stereotype.Service;

@Service
public interface ShoppingCartService {

    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
