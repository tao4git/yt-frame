package com.yintao.ytframe.sharding;

import com.alibaba.fastjson.JSON;
import com.yintao.ytframe.sharding.common.idcommon.DbAndTableEnum;
import com.yintao.ytframe.sharding.common.idcommon.GenIdUtils;
import com.yintao.ytframe.sharding.entity.Device;
import com.yintao.ytframe.sharding.entity.Order;
import com.yintao.ytframe.sharding.service.IDeviceService;
import com.yintao.ytframe.sharding.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YtframeShardingApplicationTests {


    @Autowired
    IOrderService orderService;

    @Autowired
    IDeviceService deviceService;

    @Autowired
    GenIdUtils genIdUtils;

    @Test
    public void testDefault(){
        for (int i = 0; i < 3; i++) {
            String companyNo = (100000+i)+"";
            for (int j = 0; j < 2; j++) {
                String deviceNo = "devId"+j;
                Order order = new Order();
                order.setDeviceId(System.currentTimeMillis());
                order.setCreateTime(new Date());
                order.setDeviceNo(deviceNo);
                order.setCompanyNo(companyNo);
                orderService.insertOrder(order);
            }
        }
    }

    @Test
    public void testComplex(){
        for (int i = 0; i < 10; i++) {
            String companyNo = (100000+i)+"";
            for (int j = 0; j < 10; j++) {
                String deviceNo = "devId-"+ genIdUtils.getUUID().substring(0,10)+"-"+((i*10)+j);
                for (int k = 0; k < 50; k++) {
                    Device order = new Device();
                    order.setDeviceId(genIdUtils.generateKey(DbAndTableEnum.T_DEVICE,companyNo,deviceNo));
                    order.setCreateTime(new Date());
                    order.setDeviceNo(deviceNo);
                    order.setCompanyNo(companyNo);
                    deviceService.insertDevice(order);
                }
            }
        }
    }


    @Test
    public void queryComplex(){

        List<Device> list = deviceService.queryDeviceList(null);
        System.out.println(list.size());

        Device device = new Device();
        device.setCompanyNo("100004");
        List<Device> list1 = deviceService.queryDeviceList(device);
        System.out.println(list1.size());

        device.setDeviceNo("devId-87e9db3f-e-63");
        device.setCompanyNo("100006");
        List<Device> list2 = deviceService.queryDeviceList(device);
        System.out.println(list2.size());
    }

    @Test
    public void testGeneratorId(){
        //WG285WLAN19042700157
        for (int i = 0; i < 10; i++) {
            System.out.println("1:"+genIdUtils.generateKey(DbAndTableEnum.T_DEVICE,"10001","dev-1"));
        }
        for (int i = 0; i <10 ; i++) {
            System.out.println("2:"+genIdUtils.generateKey(DbAndTableEnum.T_DEVICE,"10002","dev-2"));
        }
    }

}
