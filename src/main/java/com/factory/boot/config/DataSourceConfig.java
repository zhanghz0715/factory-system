package com.factory.boot.config;

import java.sql.SQLException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@MapperScan(basePackages = { "com.factory.boot.dao", "com.factory.boot.attachment.dao" })
@Slf4j
public class DataSourceConfig {

	/**
	 * 单数据源连接池配置
	 */
	@Bean
	public DruidDataSource dataSource(DruidProperties druidProperties) {
		log.info("[初始化druid数据源~]");
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(druidProperties.getDriverClassName());
		dataSource.setUrl(druidProperties.getUrl());
		dataSource.setUsername(druidProperties.getUsername());
		dataSource.setPassword(druidProperties.getPassword());
		// 其它配置
		dataSource.setInitialSize(druidProperties.getInitialSize());
		dataSource.setMinIdle(druidProperties.getMinIdle());
		dataSource.setMaxActive(druidProperties.getMaxActive());
		dataSource.setMaxWait(druidProperties.getMaxWait());
		dataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
		dataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
		dataSource.setValidationQuery(druidProperties.getValidationQuery());
		dataSource.setTestWhileIdle(druidProperties.getTestWhileIdle());
		dataSource.setTestOnBorrow(druidProperties.getTestOnBorrow());
		dataSource.setTestOnReturn(druidProperties.getTestOnReturn());
		dataSource.setPoolPreparedStatements(druidProperties.getPoolPreparedStatements());
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(
				druidProperties.getMaxPoolPreparedStatementPerConnectionSize());
		dataSource.setConnectionProperties(druidProperties.getConnectionProperties());
		try {
			dataSource.setFilters(druidProperties.getFilters());
		} catch (SQLException e) {
			log.error("druid configuration initialization filter: " + e);
		}
		return dataSource;
	}
}
