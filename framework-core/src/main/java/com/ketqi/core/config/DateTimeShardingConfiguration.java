package com.ketqi.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ketqi
 * Date: 2018-06-12 12:08
 */
@Component
@ConfigurationProperties(prefix = "sms")
public class DateTimeShardingConfiguration {
    private List<String> dateTimeShardingList = new ArrayList<>();

    public List<String> getDateTimeShardingList() {
        return dateTimeShardingList;
    }
}
