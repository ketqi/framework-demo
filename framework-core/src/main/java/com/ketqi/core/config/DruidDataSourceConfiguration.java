package com.ketqi.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ketqi.common.utils.PlatformUtils;
import com.ketqi.core.sharding.DateTimeTableShardingAlgorithm;
import io.shardingsphere.core.api.ShardingDataSourceFactory;
import io.shardingsphere.core.api.config.ShardingRuleConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * User: ketqi
 * Date: 2017-06-08 15:07
 */
@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfiguration {
    @Autowired
    private DateTimeShardingConfiguration dateTimeShardingConfiguration;


    @Primary
    @Bean(name = "druidDataSource", destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "shardingDataSource")
    public DataSource shardingDataSource(@Qualifier("druidDataSource") DataSource druidDataSource) {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("druidDataSource", druidDataSource);

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        shardingRuleConfig.setDefaultDataSourceName("druidDataSource");
        shardingRuleConfig.getBindingTableGroups().add("commit_record_list,send_message_list");

        List<String> dateTimeShardingList = dateTimeShardingConfiguration.getDateTimeShardingList();
        DateTimeTableShardingAlgorithm dateTimeTableAlgorithm = new DateTimeTableShardingAlgorithm(dateTimeShardingList);
        //添加提交记录表分表规则
        shardingRuleConfig.getTableRuleConfigs().add(dateTimeTableAlgorithm.getCommitRecordTableRuleConfiguration());
        //发送报告表分表规则
        shardingRuleConfig.getTableRuleConfigs().add(dateTimeTableAlgorithm.getSendMessageTableRuleConfiguration());

        Properties pros = new Properties();
        if (!PlatformUtils.isLinux()) {
            pros.setProperty("sql.show", "true");
        }

        try {
            return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new HashMap<>(), pros);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    @Primary
    public PlatformTransactionManager annotationDrivenTransactionManager(@Qualifier("shardingDataSource") DataSource shardingDataSource) {
        return new DataSourceTransactionManager(shardingDataSource);
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("shardingDataSource") DataSource shardingDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(shardingDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/sjdf/message/dao/mapper/*.xml"));
        bean.setTypeAliasesPackage("com.sjdf.message.bean");
        return bean.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
