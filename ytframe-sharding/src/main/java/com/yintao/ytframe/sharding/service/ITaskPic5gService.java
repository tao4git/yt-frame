package com.yintao.ytframe.sharding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yintao.ytframe.sharding.entity.TaskPic5g;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-06-02
 */
public interface ITaskPic5gService extends IService<TaskPic5g> {


    public List<TaskPic5g> queryList(TaskPic5g taskPic5g);

}
