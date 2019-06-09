package com.yintao.ytframe.sharding.config;

import com.alibaba.fastjson.JSON;
import com.yintao.ytframe.sharding.common.idcommon.DbAndTableEnum;
import com.yintao.ytframe.sharding.common.idcommon.ShardingConstant;
import com.yintao.ytframe.sharding.utils.StringUtil;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Descroption
 * @Author YINTAO
 * @Date 2019/6/9 0:12
 **/
public class DbComplexSharding implements ComplexKeysShardingAlgorithm {

    private static final Logger log = LoggerFactory.getLogger(DbComplexSharding.class);
    private static final String prefix = "DbShard:";

    @Override
    public Collection<String> doSharding(Collection<String> targetNames, Collection<ShardingValue> shardingValues) {
        log.info(prefix+"targetNames:" + JSON.toJSONString(targetNames) + ",shardingValues:" + JSON.toJSONString(shardingValues));
        List<String> shardingResults = new ArrayList<>();
        for (ShardingValue var : shardingValues) {
            ListShardingValue<String> listShardingValue = (ListShardingValue<String>)var;
            List<String> shardingValue = (List<String>)listShardingValue.getValues();

            String index = getIndex(listShardingValue.getLogicTableName(),
                    listShardingValue.getColumnName(),
                    shardingValue.get(0));
            // 3. 循环匹配数据源，匹配到则退出循环
            for (String name : targetNames) {
                // 4. 获取逻辑数据源索引后缀，即 0，1，2，3
                String nameSuffix = name.substring(ShardingConstant.LOGIC_DB_PREFIX_LENGTH);
                // 5. 当且仅当availableTargetNames中的数据源索引与路由值对应的分片索引相同退出循环
                if (nameSuffix.equals(index)) {
                    // 6. 添加到分片结果集合
                    shardingResults.add(name);
                    break;
                }
            }
            //匹配到一种路由规则就可以退出
            if (shardingResults.size() > 0) {
                break;
            }
        }
        return shardingResults;
    }


    /**
     * 根据分片键计算分片节点
     * @param logicTableName
     * @param columnName
     * @param shardingValue
     * @return
     */
    public String getIndex(String logicTableName, String columnName, String shardingValue) {
        String index = "";
        if (StringUtils.isBlank(shardingValue)) {
            throw new IllegalArgumentException("分片键值为空");
        }
        //截取分片键值-下标循环主键规则枚举类，匹配主键列名得到规则
        for (DbAndTableEnum targetEnum : DbAndTableEnum.values()) {
            /**目标表路由
             * 如果逻辑表命中，判断路由键是否与列名相同
             */
            if (targetEnum.getTableName().equals(logicTableName)) {
                //目标表的目标主键路由-例如：根据订单id查询订单信息
                if (targetEnum.getShardingKey().equals(columnName)) {
                    index = getDbIndexBySubString(targetEnum, shardingValue);
                }else{
                    //目标表的非目标主键路由-例如：根据内部用户id查询订单信息-内部用户id路由-固定取按照用户表库表数量
                    //兼容且仅限根据外部id路由 查询用户信息
                    index = getDbIndexByMod(targetEnum, shardingValue);
                }
                break;
            }
        }
        if (StringUtils.isBlank(index)) {
            String msg = "从分片键值中解析数据库索引异常：logicTableName=" + logicTableName + "|columnName=" + columnName + "|shardingValue=" + shardingValue;
            throw new IllegalArgumentException(msg);
        }
        return index;
    }

    /**
     * 内部用户id使用取模方式对目标表库表数量取模获取分片节点
     * @param shardingValue
     * @return
     */
    public String getDbIndexByMod(DbAndTableEnum targetEnum,String shardingValue) {
        String index = String.valueOf(StringUtil.getDbIndexByMod(shardingValue,targetEnum.getDbCount(),targetEnum.getTbCount()));
        return index;
    }

    /**
     * 该表主键使用下标方式截取数据库索引
     * @param targetEnum
     * @param shardingValue
     * @return
     */
    public String getDbIndexBySubString(DbAndTableEnum targetEnum, String shardingValue) {
        int indexBegin = targetEnum.getDbIndexBegin();
        int indexEnd = targetEnum.getDbIndexBegin() + ShardingConstant.DB_SUFFIX_LENGTH;
        return StringUtil.deleteZero(shardingValue.substring(indexBegin, indexEnd));
    }

    public static void main(String[] args) {
        DbComplexSharding dbComplexSharding = new DbComplexSharding();
        List<String> shardingValues = new ArrayList<>();
        //DbShard:targetNames:["ds0","ds1"],shardingValues:[{"columnName":"device_no","logicTableName":"t_device","values":["devId4"]}]
        String index = dbComplexSharding.getDbIndexBySubString(DbAndTableEnum.T_DEVICE,"DE020001190609220832062333539061");

        for (int i = 0; i < 100; i++) {
            int num = 100000+i;
            String index2 =dbComplexSharding.getDbIndexByMod(DbAndTableEnum.T_DEVICE,num+"");
            System.out.println(index2);
        }

    }
}
