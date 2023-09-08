package com.java.controller;

import com.java.config.ServerConfig;
import com.java.pojo.CommonResult;
import com.java.pojo.Payment;
import com.java.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/test/payment")
public class PayMentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ServerConfig serverPort;

    @Value("${server.port}")
    private String port;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment){

        System.out.println(payment);
        paymentService.create(payment);
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
        Payment payment = paymentService.queryById(id);
        log.info("查询成功");
        if(payment!=null){
            return new CommonResult(200,"查询成功"+port+payment);
        }else{
            return new CommonResult(444,"查询失败",null);
        }

    }

    @GetMapping("/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String s : services){
            log.info("********注册到eureka中的服务中有:"+services);
        }
        List <ServiceInstance> instances = discoveryClient.getInstances("MCROSERVICE-PAYMENT");
        for (ServiceInstance s: instances) {
            log.info("当前服务的实例有"+s.getServiceId()+"\t"+s.getHost()+"\t"+s.getPort()+"\t"+s.getUri());
        }
            return this.discoveryClient;
        }
}
