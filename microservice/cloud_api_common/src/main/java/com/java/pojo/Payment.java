package com.java.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//网络通信 一定要实现序列化
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Payment {

    private Long id;
    // 微服务 一个服务对应一个数据库，同一个信息可能存在不同的数据库
    private String serial;
}
