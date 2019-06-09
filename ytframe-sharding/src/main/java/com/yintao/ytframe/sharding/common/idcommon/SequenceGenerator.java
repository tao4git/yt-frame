package com.yintao.ytframe.sharding.common.idcommon;

/**
 * @Descroption
 * @Author YINTAO
 * @Date 2019/6/9 18:42
 **/
public interface SequenceGenerator {

    String getNextVal(DbAndTableEnum targetEnum, int dbIndex, int tbIndex);
}
