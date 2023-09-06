package com.java.service;

import com.java.dto.OrdersSubmitDTO;
import com.java.vo.OrderSubmitVO;

public interface OrderService {

    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
