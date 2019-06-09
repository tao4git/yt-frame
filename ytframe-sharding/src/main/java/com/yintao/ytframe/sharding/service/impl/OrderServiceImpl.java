package com.yintao.ytframe.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintao.ytframe.sharding.dao.OrderDao;
import com.yintao.ytframe.sharding.entity.Order;
import com.yintao.ytframe.sharding.service.IOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Descroption
 * @Author YINTAO
 * @Date 2019/6/8 11:21
 **/
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements IOrderService {


    @Override
    public int insertOrder(Order order) {
        return baseMapper.insert(order);
    }

    @Override
    public List<Order> queryOrderList(Order order) {
        return baseMapper.selectList(null);
    }
}
