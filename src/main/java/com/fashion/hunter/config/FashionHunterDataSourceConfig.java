package com.fashion.hunter.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Configuration for FashionHunter datasources with MyBatis and JdbcTemplate.
 */
@Configuration
public class FashionHunterDataSourceConfig {

    // ----- Property prefixes -----
    private static final String FASHION_HUNTER_DATASOURCE_PROPERTY_PREFIX = "spring.datasource.fashion-hunter-api";

    // ----- Bean Names -----
    public static final String FASHION_HUNTER_DATA_SOURCE_BEAN = "fashionHunterDataSource";

    private static final String FASHION_HUNTER_SESSION_FACTORY_BEAN = "fashionHunterSessionFactory";

    public static final String FASHION_HUNTER_TRANSACTION_MANAGER = "FASHION_HUNTER_TX_Manager";
	public static final String CHAINED_TX_MANAGER = "chainedTXManager";


    // ----- MyBatis Config Files -----
    private static final String FASHION_HUNTER_CONFIG = "mybatis/fashionHunter-portal-mybatis-config.xml";

    // ----- DAO Packages -----
    private static final String FASHION_HUNTER_DAO_BASE = "com.fashion.hunter.dao";

    // ----- DataSources -----

    @Bean(name = FASHION_HUNTER_DATA_SOURCE_BEAN)
    @ConfigurationProperties(prefix = FASHION_HUNTER_DATASOURCE_PROPERTY_PREFIX)
    public DataSource fashionHunterDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    // ----- JdbcTemplates -----

    @Bean(name = "fashionHunterJdbcTemplate")
    public JdbcTemplate fashionHunterJdbcTemplate(@Qualifier(FASHION_HUNTER_DATA_SOURCE_BEAN) DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // ----- MapperScannerConfigurers -----

    @Bean
    public MapperScannerConfigurer fashionHunterMapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(FASHION_HUNTER_DAO_BASE);
        configurer.setSqlSessionFactoryBeanName(FASHION_HUNTER_SESSION_FACTORY_BEAN);
        return configurer;
    }

    // ----- SqlSessionFactoryBeans -----

    @Bean(name = FASHION_HUNTER_SESSION_FACTORY_BEAN)
    public SqlSessionFactoryBean fashionHunterSessionFactory(@Qualifier(FASHION_HUNTER_DATA_SOURCE_BEAN) DataSource dataSource) {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setConfigLocation(new ClassPathResource(FASHION_HUNTER_CONFIG));
        return sessionFactoryBean;
    }

    // ----- Transaction Managers -----

    @Bean(name = FASHION_HUNTER_TRANSACTION_MANAGER)
    public DataSourceTransactionManager fashionHunterTransactionManager(@Qualifier(FASHION_HUNTER_DATA_SOURCE_BEAN) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

	@SuppressWarnings("deprecation")
	@Bean(CHAINED_TX_MANAGER)
	public ChainedTransactionManager transactionManager(			
			@Qualifier(FASHION_HUNTER_TRANSACTION_MANAGER) DataSourceTransactionManager ds) {
		return new ChainedTransactionManager(ds);
	}
	
}
