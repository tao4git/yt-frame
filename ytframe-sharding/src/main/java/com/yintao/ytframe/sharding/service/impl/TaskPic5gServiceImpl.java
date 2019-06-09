package com.yintao.ytframe.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintao.ytframe.sharding.dao.TaskPic5gDao;
import com.yintao.ytframe.sharding.entity.TaskPic5g;
import com.yintao.ytframe.sharding.service.ITaskPic5gService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-06-02
 */
@Service("taskPic5gService")
public class TaskPic5gServiceImpl extends ServiceImpl<TaskPic5gDao, TaskPic5g> implements ITaskPic5gService {


    @Override
    public List<TaskPic5g> queryList(TaskPic5g taskPic5g) {
        return baseMapper.selectList(null);
    }

}
