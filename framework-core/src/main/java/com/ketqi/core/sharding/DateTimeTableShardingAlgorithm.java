package com.ketqi.core.sharding;

import com.google.common.collect.Range;
import com.ketqi.common.utils.DateUtils;
import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;
import io.shardingsphere.core.api.config.TableRuleConfiguration;
import io.shardingsphere.core.api.config.strategy.StandardShardingStrategyConfiguration;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 按时间分片算法
 * User: ketqi
 * Date: 2018-06-08 14:21
 */
public class DateTimeTableShardingAlgorithm implements PreciseShardingAlgorithm<Date>, RangeShardingAlgorithm<Date> {
    private TreeMap<Date, String> commitRecordTableMap = new TreeMap<>(Comparator.reverseOrder());
    private TreeMap<Date, String> sendMessageTableMap = new TreeMap<>(Comparator.reverseOrder());

    public DateTimeTableShardingAlgorithm(List<String> dateList) {
        //排序
        Collections.sort(dateList);

        Date date;
        for (int i = 0; i < dateList.size(); i++) {
            date = DateUtils.parseDateTime(dateList.get(i));
            commitRecordTableMap.put(date, "commit_record_list_" + i);
            sendMessageTableMap.put(date, "send_message_list_" + i);
        }
    }

    public TableRuleConfiguration getCommitRecordTableRuleConfiguration() {
        String dataNodes = commitRecordTableMap.values().stream().map(str -> String.format("druidDataSource.%s", str)).collect(Collectors.joining(","));

        TableRuleConfiguration rule = new TableRuleConfiguration();
        rule.setLogicTable("commit_record_list");
        //dataSource.commit_record_list_${0..1}
        rule.setActualDataNodes(dataNodes);
        rule.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("createTime", this, this));
        rule.setKeyGeneratorColumnName("id");
        rule.setKeyGenerator(DistributedUtils.getKeyGenerator());
        return rule;
    }

    public TableRuleConfiguration getSendMessageTableRuleConfiguration() {
        String dataNodes = sendMessageTableMap.values().stream().map(str -> String.format("druidDataSource.%s", str)).collect(Collectors.joining(","));

        TableRuleConfiguration rule = new TableRuleConfiguration();
        rule.setLogicTable("send_message_list");
        //dataSource.send_message_list${0..1}
        rule.setActualDataNodes(dataNodes);
        rule.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("createTime", this, this));
        rule.setKeyGeneratorColumnName("id");
        rule.setKeyGenerator(DistributedUtils.getKeyGenerator());
        return rule;
    }

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        TreeMap<Date, String> tableNameMap = getTableNameMap(availableTargetNames);
        Date createTime = shardingValue.getValue();
        if (createTime == null) {
            return getDefaultTableName(tableNameMap);
        }

        for (Map.Entry<Date, String> entry : tableNameMap.entrySet()) {
            if (createTime.compareTo(entry.getKey()) >= 0) {
                return entry.getValue();
            }
        }

        return getDefaultTableName(tableNameMap);
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
        TreeMap<Date, String> tableNameMap = getTableNameMap(availableTargetNames);
        Range<Date> range = shardingValue.getValueRange();
        Date lower = range.lowerEndpoint();
        Date upper = range.upperEndpoint();

        if (lower == null && upper == null) {
            return Collections.singletonList(getDefaultTableName(tableNameMap));
        }

        Date key;
        String tableName;
        Map.Entry<Date, String> entry;
        List<String> tableList = new ArrayList<>();
        Iterator<Map.Entry<Date, String>> iterator = tableNameMap.entrySet().iterator();
        while (iterator.hasNext()) {
            entry = iterator.next();
            key = entry.getKey();
            tableName = entry.getValue();

            if (lower != null && upper != null) {
                if (lower.equals(upper) && DateUtils.isInRange(key, DateUtils.getBeginOfDate(lower), DateUtils.getEndOfDate(lower))) {
                    tableList.add(tableName);
                    if (iterator.hasNext()) {
                        tableList.add(iterator.next().getValue());
                    }
                    break;
                } else if (key.compareTo(lower) >= 0 && key.compareTo(upper) <= 0) {
                    tableList.add(tableName);
                }
            } else if (lower != null) {
                if (key.compareTo(lower) >= 0) {
                    tableList.add(tableName);
                }
            } else {
                if (key.compareTo(upper) <= 0) {
                    tableList.add(tableName);
                }
            }
        }

        if (tableList.isEmpty()) {
            tableList.add(getDefaultTableName(tableNameMap));
        }

        return tableList;
    }

    private TreeMap<Date, String> getTableNameMap(Collection<String> availableTargetNames) {
        if (sendMessageTableMap.values().contains(availableTargetNames.iterator().next())) {
            return sendMessageTableMap;
        }
        return commitRecordTableMap;
    }

    private String getDefaultTableName(TreeMap<Date, String> tableNameMap) {
        Date date = new Date();
        for (Map.Entry<Date, String> entry : tableNameMap.entrySet()) {
            if (date.compareTo(entry.getKey()) >= 0) {
                return entry.getValue();
            }
        }
        return tableNameMap.lastEntry().getValue();
    }
}
