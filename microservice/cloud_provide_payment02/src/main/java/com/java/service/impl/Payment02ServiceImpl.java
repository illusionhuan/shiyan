package com.java.service.impl;

import com.java.mapper.Payment02Mapper;
import com.java.pojo.Payment;
import com.java.service.Payment02Service;
import com.java.service.Payment02Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Payment02ServiceImpl implements Payment02Service {

    @Autowired
    private Payment02Mapper payment02Mapper;


    @Override
    public void create(Payment payment) {
        payment02Mapper.create(payment);
    }

    @Override
    public Payment queryById(Long id) {
        return payment02Mapper.queryById(id);
    }
}
