package com.java.service;

import com.java.pojo.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    void create(Payment payment);

    Payment queryById(@Param("id")Long id);
}
