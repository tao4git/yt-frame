package com.yintao.ytframe.sharding.service;

import com.yintao.ytframe.sharding.entity.Order;

import java.util.List;

/**
 * @Descroption
 * @Author YINTAO
 * @Date 2019/6/8 11:20
 **/
public interface IOrderService {


    public int insertOrder(Order order);

    public List<Order> queryOrderList(Order order);
}
