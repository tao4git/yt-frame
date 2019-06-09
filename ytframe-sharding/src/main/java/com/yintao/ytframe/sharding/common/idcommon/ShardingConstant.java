package com.yintao.ytframe.sharding.common.idcommon;

/**
 * @Descroption
 * @Author YINTAO
 * @Date 2019/6/9 19:19
 **/
public class ShardingConstant {

    /**逻辑数据库源前缀非索引部分长度-用于路由策略截取逻辑数据源匹配,即ds0,ds1,ds2...*/
    public static final int LOGIC_DB_PREFIX_LENGTH = 2;

    /**
     * 数据库后缀索引长度，例如: db_00 ~ db_31
     */
    public static final int DB_SUFFIX_LENGTH = 2;
    /**
     * 表后缀索引长度，例如：user_info_00 ~ user_info_01 , order_info_00 ~ order_info_01
     */
    public static final int TABLE_SUFFIX_LENGTH = 2;
}
