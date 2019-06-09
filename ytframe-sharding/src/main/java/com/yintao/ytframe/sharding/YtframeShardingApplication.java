package com.yintao.ytframe.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@MapperScan("com.yintao.ytframe.sharding.dao")
public class YtframeShardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(YtframeShardingApplication.class, args);
    }

}
