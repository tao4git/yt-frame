package com.yintao.ytframe.sharding.common.idcommon;

import com.alibaba.fastjson.JSON;
import com.yintao.ytframe.sharding.entity.Device;
import com.yintao.ytframe.sharding.utils.StringUtil;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Descroption
 *
 * 业务编码(2位) + 库下标（2位）+ 表下标（4位）
 * + 序列版本号（默认为01，2位）+ 时间戳（yyMMddHHmmssSSS，精确到毫秒，15位）
 * + 机器id（2位）
 * + 序列号（5位）
 * @Author YINTAO
 * @Date 2019/6/9 17:25
 **/
@Component
public class GenIdUtils {

    @Resource(name = "randomGenerator")
    SequenceGenerator sequenceGenerator;

    /**默认集群机器总数*/
    private static final int DEFAULT_HOST_NUM = 64;

    /**
     * 根据路由id生成内部系统主键id，
     * 路由id可以是内部其他系统主键id，也可以是外部第三方用户id
     * @param targetEnum 待生成主键的目标表规则配置
     * @param relatedRouteId  路由id或外部第三方用户id
     * @return
     */
    public String generateKey(DbAndTableEnum targetEnum, String dbRouteCloumn,String tbRouteCloumn) {

        if (StringUtils.isBlank(dbRouteCloumn) && StringUtils.isBlank(tbRouteCloumn)) {
            throw new IllegalArgumentException("路由id参数为空");
        }

        StringBuilder key = new StringBuilder();
        /** 1.id业务前缀*/
        String idPrefix = targetEnum.getCharsPrefix();
        /** 2.id数据库索引位*/
        String dbIndex = getDbIndexAndTbIndexMap(targetEnum, dbRouteCloumn).get("dbIndex");
        /** 3.id表索引位*/
        String tbIndex = getDbIndexAndTbIndexMap(targetEnum, tbRouteCloumn).get("tbIndex");
        /** 4.id规则版本位*/
        String idVersion = targetEnum.getIdVersion();
        /** 5.id时间戳位*/
        String timeString = DateFormatUtils.format(new Date(),"yyMMddHHmmssSSS");
        /** 6.id分布式机器位 2位*/
        String distributedIndex = getDistributedId(2);
        /** 7.随机数位*/
        String sequenceId = sequenceGenerator.getNextVal(targetEnum, Integer.parseInt(dbIndex), Integer.parseInt(tbIndex));
        /** 库表索引靠前*/
        return key.append(idPrefix)
                .append(dbIndex)
                .append(tbIndex)
                .append(idVersion)
                .append(timeString)
                .append(distributedIndex)
                .append(sequenceId).toString();
    }


    /**
     * 根据已知路由id取出库表索引，外部id和内部id均 进行ASCII转换后再对库表数量取模
     * @param targetEnum 待生成主键的目标表规则配置
     * @param relatedRouteId 路由id
     * @return
     */
    private Map<String, String> getDbIndexAndTbIndexMap(DbAndTableEnum targetEnum,String relatedRouteId) {
        Map<String, String> map = new HashMap<>();
        /** 获取库索引*/
        String preDbIndex = String.valueOf(
                StringUtil.getDbIndexByMod(
                        relatedRouteId,
                        targetEnum.getDbCount(),
                        targetEnum.getTbCount()));
        String dbIndex = StringUtil.fillZero(preDbIndex, ShardingConstant.DB_SUFFIX_LENGTH);
        /** 获取表索引*/
        String preTbIndex = String
                .valueOf(StringUtil.getTbIndexByMod(relatedRouteId,targetEnum.getDbCount(),targetEnum.getTbCount()));
        String tbIndex = StringUtil
                .fillZero(preTbIndex,ShardingConstant.TABLE_SUFFIX_LENGTH);
        map.put("dbIndex", dbIndex);
        map.put("tbIndex", tbIndex);
        return map;
    }


    /**
     * 生成id分布式机器位
     * @return 分布式机器id
     * length与hostCount位数相同
     */
    private String getDistributedId(int length) {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostName=addr.getHostName().toString(); //获取本机计算机名称
            if(StringUtils.isNotEmpty(hostName)){
                return StringUtil.fillZero(StringUtil.getModValue(hostName,64)+"",2);
            }else{
                return "00";
            }
        } catch (UnknownHostException e) {
            return "00";
        }
    }


    /**
     * ShardingSphere 默认雪花算法 {@link io.shardingsphere.core.keygen.DefaultKeyGenerator}
     * 生成18位随机id，并提供静态方法设置workId
     * @return
     */
    public Long getSnowFlakeId() {
        return new DefaultKeyGenerator().generateKey().longValue();
    }

    /**
     * 使用ShardingSphere 内置主键生成器 生成分布式主键id
     * 共16位：其中4位减号，12位数字+小写字母
     * 形如：3e9c3ac9-d7a1-43de-9db3-dec502e9ab3e
     */
    public String getUUID() {
        return UUID.randomUUID().toString();
    }


    public static void main(String[] args) {
//        GenIdUtils genIdUtils = new GenIdUtils();
//        System.out.println(genIdUtils.getDistributedId(2));
        GenIdUtils genIdUtils = new GenIdUtils();
        for (int i = 0; i < 10; i++) {
            String companyNo = (100000+i)+"";
            for (int j = 0; j < 10; j++) {
                String deviceNo = "devId-"+ genIdUtils.getUUID().substring(0,10)+"-"+((i*10)+j);

                String preDbIndex = String.valueOf(
                        StringUtil.getDbIndexByMod(
                                deviceNo,
                                DbAndTableEnum.T_DEVICE.getDbCount(),
                                DbAndTableEnum.T_DEVICE.getTbCount()));
                String dbIndex = StringUtil.fillZero(preDbIndex, ShardingConstant.DB_SUFFIX_LENGTH);
                System.out.println("dbno:"+dbIndex);
                String preTbIndex = String
                        .valueOf(StringUtil.getTbIndexByMod(deviceNo,DbAndTableEnum.T_DEVICE.getDbCount(),DbAndTableEnum.T_DEVICE.getTbCount()));
                String tbIndex = StringUtil
                        .fillZero(preTbIndex,ShardingConstant.TABLE_SUFFIX_LENGTH);
                System.out.println("tbno:"+tbIndex);
                System.out.println("-------------------");
            }
        }

    }

}
