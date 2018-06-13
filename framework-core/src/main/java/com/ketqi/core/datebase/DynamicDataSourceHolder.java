package com.ketqi.core.datebase;

/**
 * 数据源管理器
 * User: ketqi
 * Date: 2018-06-13 19:14
 */
public class DynamicDataSourceHolder {
    private static enum DataSourceType {
        WRITE, READ
    }

    private static final ThreadLocal<DataSourceType> HOLDER = new ThreadLocal<>();

    /** 数据源名称 */
    public static final String DATASOURCE_WRITE = "WRITE";
    public static final String DATASOURCE_READ = "READ";

    /** 标记为写数据源 */
    public static void markWrite() {
        HOLDER.set(DataSourceType.WRITE);
    }

    /** 标记为读数据源 */
    public static void markRead() {
        HOLDER.set(DataSourceType.READ);
    }

    /** 重置 */
    public static void reset() {
        HOLDER.set(null);
    }

    /** 是否还未设置数据源 */
    public static boolean isChoiceNone() {
        return null == HOLDER.get();
    }

    /** 当前是否选择了写数据源 */
    public static boolean isChoiceWrite() {
        DataSourceType type = HOLDER.get();
        return type != null && DataSourceType.WRITE == type;
    }

    /** 当前是否选择了读数据源 */
    public static boolean isChoiceRead() {
        DataSourceType type = HOLDER.get();
        return type != null && DataSourceType.READ == type;
    }
}
