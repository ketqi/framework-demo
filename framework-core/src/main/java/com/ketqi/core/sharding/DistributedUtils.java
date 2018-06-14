package com.ketqi.core.sharding;

import com.ketqi.common.PlatformConstant;
import com.ketqi.common.utils.PlatformUtils;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;

import java.util.Date;

/**
 * 分布式工具
 * User: ketqi
 * Date: 2018-06-08 11:26
 */
public abstract class DistributedUtils {
    //分布式主键生成器
    private static DefaultKeyGenerator keyGenerator;

    static {
        DefaultKeyGenerator.setWorkerId(PlatformUtils.getCurrentProcessID() % PlatformConstant.LENGTH_1024);
        keyGenerator = new DefaultKeyGenerator();
    }

    public static DefaultKeyGenerator getKeyGenerator() {
        return keyGenerator;
    }

    public static long generateKey() {
        return keyGenerator.generateKey().longValue();
    }

    public static Date parseTime(long id) {
        long num = id >> 22;
        num = num + DefaultKeyGenerator.EPOCH;
        return new Date(num);
    }
}
