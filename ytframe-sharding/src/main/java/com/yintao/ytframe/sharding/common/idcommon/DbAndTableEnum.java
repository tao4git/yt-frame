package com.yintao.ytframe.sharding.common.idcommon;

/**
 * @Descroption
 *
 * 业务编码(2位) + 库下标（2位）+ 表下标（2位）
 * + 序列版本号（默认为01，2位）+ 时间戳（yyMMddHHmmssSSS，精确到毫秒，15位）
 * + 机器id（2位）
 * + 序列号（7位）= 32
 * @Author YINTAO
 * @Date 2019/6/9 18:32
 **/
public enum DbAndTableEnum {

    T_DEVICE("t_device", "device_id", "01", "01", "DE", 2, 2, 4, 2, 8, "设备数据表枚举");

    /**分片表名*/
    private String tableName;
    /**分片键*/
    private String shardingKey;
    /**系统标识*/
    private String bizType;
    /**主键规则版本*/
    private String idVersion;
    /**表名字母前缀*/
    private String charsPrefix;
    /**分片键值中纯数字起始下标索引，第一位是0,第二位是1，依次类推*/
    private int numberStartIndex;
    /**数据库索引位开始下标索引*/
    private int dbIndexBegin;
    /**表索引位开始下标索引*/
    private int tbIndexBegin;
    /**分布所在库数量*/
    private int dbCount;
    /**分布所在表数量-所有库中表数量总计*/
    private int tbCount;
    /**描述*/
    private String desc;

    DbAndTableEnum(String tableName, String shardingKey, String bizType, String idVersion,
                   String charsPrefix, int numberStartIndex, int dbIndexBegin,
                   int tbIndexBegin, int dbCount, int tbCount, String desc){
        this.tableName = tableName;
        this.shardingKey = shardingKey;
        this.bizType = bizType;
        this.idVersion = idVersion;
        this.charsPrefix = charsPrefix;
        this.numberStartIndex = numberStartIndex;
        this.dbIndexBegin = dbIndexBegin;
        this.tbIndexBegin = tbIndexBegin;
        this.dbCount = dbCount;
        this.tbCount = tbCount;
        this.desc = desc;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getShardingKey() {
        return shardingKey;
    }

    public void setShardingKey(String shardingKey) {
        this.shardingKey = shardingKey;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(String idVersion) {
        this.idVersion = idVersion;
    }

    public String getCharsPrefix() {
        return charsPrefix;
    }

    public void setCharsPrefix(String charsPrefix) {
        this.charsPrefix = charsPrefix;
    }

    public int getNumberStartIndex() {
        return numberStartIndex;
    }

    public void setNumberStartIndex(int numberStartIndex) {
        this.numberStartIndex = numberStartIndex;
    }

    public int getDbIndexBegin() {
        return dbIndexBegin;
    }

    public void setDbIndexBegin(int dbIndexBegin) {
        this.dbIndexBegin = dbIndexBegin;
    }

    public int getTbIndexBegin() {
        return tbIndexBegin;
    }

    public void setTbIndexBegin(int tbIndexBegin) {
        this.tbIndexBegin = tbIndexBegin;
    }

    public int getDbCount() {
        return dbCount;
    }

    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }

    public int getTbCount() {
        return tbCount;
    }

    public void setTbCount(int tbCount) {
        this.tbCount = tbCount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
