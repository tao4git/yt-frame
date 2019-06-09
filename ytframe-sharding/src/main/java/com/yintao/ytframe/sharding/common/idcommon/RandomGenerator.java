package com.yintao.ytframe.sharding.common.idcommon;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Descroption
 * @Author YINTAO
 * @Date 2019/6/9 18:43
 **/
@Component(value = "randomGenerator")
public class RandomGenerator implements SequenceGenerator {


    @Override
    public String getNextVal(DbAndTableEnum targetEnum, int dbIndex, int tbIndex) {
        //生成5位数字随机数(高并发时有重复数字的问题)
        double randomNum = Math.random()*10000000;
        String rnum = (int)randomNum + "";
        rnum = StringUtils.leftPad(rnum,7,"0");
        return rnum;
    }


    public static void main(String[] args) {

        for (int i = 0; i <100 ; i++) {
            RandomGenerator randomGenerator = new RandomGenerator();
            System.out.println(randomGenerator.getNextVal(DbAndTableEnum.T_DEVICE,0,0));
        }

    }


}
