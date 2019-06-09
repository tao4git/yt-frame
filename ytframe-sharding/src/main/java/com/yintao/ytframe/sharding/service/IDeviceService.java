package com.yintao.ytframe.sharding.service;

import com.yintao.ytframe.sharding.entity.Device;
import com.yintao.ytframe.sharding.entity.Order;

import java.util.List;

/**
 * @Descroption
 * @Author YINTAO
 * @Date 2019/6/8 11:20
 **/
public interface IDeviceService {


    public int insertDevice(Device device);

    public List<Device> queryDeviceList(Device device);
}
