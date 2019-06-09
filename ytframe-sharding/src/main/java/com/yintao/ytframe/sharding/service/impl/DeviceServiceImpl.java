package com.yintao.ytframe.sharding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintao.ytframe.sharding.dao.DeviceDao;
import com.yintao.ytframe.sharding.entity.Device;
import com.yintao.ytframe.sharding.service.IDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Descroption
 * @Author YINTAO
 * @Date 2019/6/8 11:21
 **/
@Service("deviceService")
public class DeviceServiceImpl extends ServiceImpl<DeviceDao,Device> implements IDeviceService {


    @Override
    public int insertDevice(Device device) {
        return baseMapper.insert(device);
    }

    @Override
    public List<Device> queryDeviceList(Device device) {
        if(null == device) {
            return baseMapper.selectList(null);
        }else{
            QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
            if(StringUtils.isNotBlank(device.getCompanyNo())) {
                queryWrapper.eq("company_no", device.getCompanyNo());
            }
            if(StringUtils.isNotBlank(device.getDeviceNo())) {
                queryWrapper.eq("device_no", device.getDeviceNo());
            }
            return baseMapper.selectList(queryWrapper);
        }
    }

}
