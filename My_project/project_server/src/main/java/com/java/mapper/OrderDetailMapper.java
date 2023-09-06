package com.java.mapper;

import com.java.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * 功能描述:批量插入订单明细数据
     *
     * @param orderDetailList
     * @return
     */

    void insertBatch(List<OrderDetail> orderDetailList);
}
