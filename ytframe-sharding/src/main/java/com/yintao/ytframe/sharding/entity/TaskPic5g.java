package com.yintao.ytframe.sharding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
* <p>
    * 
    * </p>
*
* @author jobob
* @since 2019-06-02
*/
    @Data
    @Accessors(chain = true)
    @TableName(value = "task_pic_5g")
    public class TaskPic5g {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.NONE)
    private Long id;
    /**
    * 状态
    */
    private String status;

    /**
    * 类型
    */
    @TableField(value = "task_type")
    private String taskType;

    /**
    * 业务数据
    */
    @TableField(value = "task_data")
    private String taskData;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
    * 关键属性
    */
    private String keyword;

    /**
    * 扩展1
    */
    @TableField(value = "extend_1")
    private String extend1;

    /**
    * 扩展2
    */
    @TableField(value = "extend_2")
    private String extend2;

    /**
    * 扩展3
    */
    @TableField(value = "extend_3")
    private String extend3;


}
