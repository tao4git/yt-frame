package com.yintao.ytframe.nosql.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author YINTAO
 * @Descroption
 * @date 2020/7/5 20:46
 **/
@Component
public class RedisService {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;


    public Boolean expire(String key,Long time){
        try {
            if(time > 0 ){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return Boolean.TRUE;
        }catch (Exception e){
            return Boolean.FALSE;
        }
    }


    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


    public Object get(String key){
        return StringUtils.isEmpty(key) ? null : redisTemplate.opsForValue().get(key);
    }


    public void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    public void setEx(String key,Object value,Long time,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,time,timeUnit);
    }


    //-----------------hashMap------------------

    public Object hget(String key,String item){
        return  redisTemplate.opsForHash().get(key,item);
    }


    public void hset(String key,String item,Object value){
        redisTemplate.opsForHash().put(key,item,value);
    }

    public void hsetEx(String key,String item,Object value,Long time){
        redisTemplate.opsForHash().put(key,item,value);
        if(time > 0){
            expire(key,time);
        }
    }

    public void hdel(String key,Object... item){
        redisTemplate.opsForHash().delete(key,item);
    }


    public Boolean hHasKey(String key,String item){
        return redisTemplate.opsForHash().hasKey(key,item);
    }


}
