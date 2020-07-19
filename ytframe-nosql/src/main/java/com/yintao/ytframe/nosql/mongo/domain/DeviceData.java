package com.yintao.ytframe.nosql.mongo.domain;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import java.util.Date;

/**
 * @Descroption
 * @Author YINTAO
 * @Date 2019/6/15 22:33
 **/
@Data
@Serialization
public class DeviceData{

    private String devId;
    private String manuCode;
    private Date collectDt;
    private String imageUrl;
    private Float weight;
}
