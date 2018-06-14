package com.ketqi.core.datebase;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动态数据源,读写分离
 * User: ketqi
 * Date: 2018-06-13 19:30
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private Object writeDataSource;
    private List<Object> readDataSources;
    private int readDataSourceSize = 0;
    private AtomicInteger readIndex = new AtomicInteger();

    /** 数据源键名 */
    private static final String DATASOURCE_KEY_WRITE = "write";
    private static final String DATASOURCE_KEY_READ = "read";

    @Override
    public void afterPropertiesSet() {
        if (this.writeDataSource == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }
        setDefaultTargetDataSource(writeDataSource);

        Map<Object, Object> targetDataSourceMap = new HashMap<>();
        targetDataSourceMap.put(DATASOURCE_KEY_WRITE, writeDataSource);
        if (readDataSources != null && !readDataSources.isEmpty()) {
            for (int i = 0; i < readDataSources.size(); i++) {
                targetDataSourceMap.put(DATASOURCE_KEY_READ + i, readDataSources.get(i));
            }
        }
        readDataSourceSize = targetDataSourceMap.size();

        setTargetDataSources(targetDataSourceMap);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (DynamicDataSourceHolder.isChoiceNone() || DynamicDataSourceHolder.isChoiceWrite() || readDataSourceSize == 0) {
            return DATASOURCE_KEY_WRITE;
        }
        int index = readIndex.incrementAndGet() % readDataSourceSize;
        return DATASOURCE_KEY_READ + index;
    }

    public void setWriteDataSource(Object writeDataSource) {
        this.writeDataSource = writeDataSource;
    }

    public void setReadDataSources(List<Object> readDataSources) {
        this.readDataSources = readDataSources;
    }
}
