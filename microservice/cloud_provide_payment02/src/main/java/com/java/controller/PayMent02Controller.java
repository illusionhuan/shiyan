package com.java.controller;

import com.java.config.ServerConfig;
import com.java.pojo.CommonResult;
import com.java.pojo.Payment;
import com.java.service.Payment02Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/test/payment")
public class PayMent02Controller {

    @Autowired
    private Payment02Service payment02Service;
    @Autowired
    private ServerConfig serverPort;

    @Value("${server.port}")
    private String port;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment){

        System.out.println(payment);
        payment02Service.create(payment);
        log.info("新建订单:{}",payment);
        int i = 1;
        if(i>0){
            return new CommonResult(200,"插入数据成功");
        }else{
            return new CommonResult(444,"插入数据库失败",null);
        }

    }

    @GetMapping("/get/{id}")
    public CommonResult queryById(@PathVariable("id")Long id){
        Payment payment = payment02Service.queryById(id);
        log.info("查询成功");
        if(payment!=null){
            return new CommonResult(200,"查询成功"+port+payment);
        }else{
            return new CommonResult(444,"查询失败",null);
        }

    }

}
