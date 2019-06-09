package com.yintao.ytframe.sharding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Descroption
 * @Author YINTAO
 * @Date 2019/6/8 11:14
 **/
@Data
@TableName("t_device")
public class Device {

    @TableId(value = "device_id",type = IdType.NONE)
    private String deviceId;

    @TableField(value = "device_no")
    private String deviceNo;

    @TableField(value = "company_no")
    private String companyNo;

    @TableField(value = "create_time")
    private Date createTime;
}
