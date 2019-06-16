package com.yintao.ytframe.nosql.mongo.base;

import com.yintao.ytframe.common.data.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Note:
 * Create by: yintao
 * Create date: 2019/5/30 10:40
 */
public class MongoBaseDao<T> {

    private static final int DEFAULT_SKIP = 0;
    private static final int DEFAULT_LIMIT = 200;
    /**
     * spring mongodb　集成操作类　
     */
    @Autowired
    protected MongoTemplate mongoTemplate;

    protected String collection;


    /**
     * 通过条件查询实体(集合)
     *
     * @param query
     */
    public List<T> findList(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    public List<T> findList(Query query, String collectionName) {
        return mongoTemplate.find(query, this.getEntityClass(), collectionName);
    }

    /**
     * 通过一定的条件查询一个实体
     *
     * @param query
     * @return
     */
    public T findOne(Query query) {
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    public T findOne(Query query, String collectionName) {
        return mongoTemplate.findOne(query, this.getEntityClass(), collectionName);
    }


    /**
     * 通过条件查询更新数据
     *
     * @param query
     * @param update
     * @return
     */
    public void update(Query query, Update update) {
        mongoTemplate.findAndModify(query, update, this.getEntityClass());
    }

    public void update(Query query, Update update, String collectionName) {
        mongoTemplate.findAndModify(query, update, this.getEntityClass(), collectionName);
    }

    public void removeById(String id, String collectionName){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, collectionName);
    }

    /**
     * 保存一个对象到mongodb
     *
     * @param entity
     * @return
     */
    public T save(T entity) {
        mongoTemplate.insert(entity);
        return entity;
    }

    public T save(T entity, String collectionName) {
        mongoTemplate.insert(entity, collectionName);
        return entity;
    }

    /**
     * 通过ID获取记录
     *
     * @param id
     * @return
     */
    public T findById(String id) {
        return mongoTemplate.findById(id, this.getEntityClass());
    }

    /**
     * 通过ID获取记录,并且指定了集合名(表的意思)
     *
     * @param id
     * @param collectionName
     *            集合名
     * @return
     */
    public T findById(String id, String collectionName) {
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
    }



    /**
     * 分页查询
     * @param page
     * @param query
     * @return
     */
    public Page findPage(Page page, Query query){
        long count = this.count(query);
        page.setTotal((int)count);
        int pageNumber = page.getPageSize();
        int pageSize = page.getPageSize();
        query.skip((pageNumber - 1) * pageSize).limit(pageSize);
        List<T> rows = this.findList(query);
        page.setRows(rows);
        return page;
    }

    /**
     * 分页查询
     * @param page
     * @param query
     * @param collectionName
     * @return
     */
    public Page findPage(Page page, Query query, String collectionName){
        long count = this.count(query, collectionName);
        page.setTotal((int)count);
        int pageNumber = page.getPageSize();
        int pageSize = page.getPageSize();
        query.skip((pageNumber - 1) * pageSize).limit(pageSize);
        List<T> rows = this.findList(query, collectionName);
        page.setRows(rows);
        return page;
    }

    /**
     * 求数据总和
     * @param query
     * @return
     */
    public long count(Query query){
        return mongoTemplate.count(query, this.getEntityClass());
    }

    public long count(Query query, String collectionName){
        return mongoTemplate.count(query, this.getEntityClass(), collectionName);
    }

    /**
     * 获取需要操作的实体类class
     *
     * @return
     */
    private Class<T> getEntityClass(){
        Type superclass = this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType)superclass).getActualTypeArguments();
        return (Class) actualTypeArguments[0];
    }

}
