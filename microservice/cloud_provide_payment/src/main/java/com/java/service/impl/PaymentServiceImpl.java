package com.java.service.impl;

import com.java.mapper.PaymentMapper;
import com.java.pojo.Payment;
import com.java.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;


    @Override
    public void create(Payment payment) {
        paymentMapper.create(payment);
    }

    @Override
    public Payment queryById(Long id) {
        return paymentMapper.queryById(id);
    }
}
