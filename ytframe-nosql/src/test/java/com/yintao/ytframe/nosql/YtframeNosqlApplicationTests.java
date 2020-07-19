package com.yintao.ytframe.nosql;

import com.alibaba.fastjson.JSON;
import com.yintao.ytframe.nosql.domain.DeviceData;
import com.yintao.ytframe.nosql.mongo.dao.DeviceDao;
import com.yintao.ytframe.nosql.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YtframeNosqlApplicationTests {


    @Autowired
    DeviceDao deviceDao;

    @Test
    public void testInsert() throws InterruptedException {

        String devId = "dev"+System.currentTimeMillis();
        DeviceData deviceData = new DeviceData();
        deviceData.setCollectDt(new Date());
        deviceData.setDevId(devId);
        deviceData.setManuCode("10000");
        deviceData.setImageUrl("http://www.baidu.com");
        deviceData.setWeight(2.3f);

        deviceDao.save(deviceData,"farm");
        Thread.sleep(1000);

        DeviceData deviceDataList = deviceDao.findOne(new Query(Criteria.where("devId").is(devId)),"farm");
        System.out.println(JSON.toJSONString(deviceDataList));

        long count = deviceDao.count(new Query(),"farm");
        System.out.println("count:"+count);
    }

    @Autowired
    RedisService redisService;

    @Test
    public void testRedis(){
        redisService.setEx("123","123",10L, TimeUnit.SECONDS);
        System.out.println(redisService.get("123"));
    }

}
