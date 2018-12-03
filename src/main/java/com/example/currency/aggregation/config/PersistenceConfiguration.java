package com.example.currency.aggregation.config;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class PersistenceConfiguration implements TransactionManagementConfigurer {

    @Value("${dataSource.driverClassName}")
    private String driver;
    @Value("${dataSource.url}")
    private String url;
    @Value("${dataSource.hbmddl}")
    private String hbmddl;
    @Value("${dataSource.dialect}")
    private String dialect;
    @Value("${dataSource.package}")
    private String modelPackage;

    public PersistenceConfiguration(String driver, String url, String hbmddl, String dialect, String modelPackage) {
        this.driver = driver;
        this.url = url;
        this.hbmddl = hbmddl;
        this.dialect = dialect;
        this.modelPackage = modelPackage;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHbmddl() {
        return hbmddl;
    }

    public void setHbmddl(String hbmddl) {
        this.hbmddl = hbmddl;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }


    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
//            LOGGER.error("Driver not found!", e);
        }
        try {
            comboPooledDataSource.setDriverClass(driver);
        } catch (PropertyVetoException e) {
//            LOGGER.error("Driver not supported!", e);
        }
        //ссылка
        comboPooledDataSource.setJdbcUrl(url);
        //минимальный размер пула
        comboPooledDataSource.setMinPoolSize(5);
        //максимальный размер пула
        comboPooledDataSource.setMaxPoolSize(40);
        //начальный размер пула
        comboPooledDataSource.setInitialPoolSize(10);
        //сколько пулов разрешено взять поверх максимального числа
        comboPooledDataSource.setAcquireIncrement(10);
        //максимальное время получения содеинения под запрос
        comboPooledDataSource.setMaxIdleTime(300);
        //максимальное время жизни запроса
        comboPooledDataSource.setMaxConnectionAge(1200);
        //время простоя соединения, после которого оно уничтожается, пул сжимается до минимума
        comboPooledDataSource.setMaxIdleTimeExcessConnections(120);
        //время между повторами запроса на соединение
        comboPooledDataSource.setAcquireRetryDelay(1500);
        //размер кэша под preparestatements
        comboPooledDataSource.setMaxStatements(500);
        //размер кэша для одного соединения под preparestatements
        comboPooledDataSource.setMaxStatementsPerConnection(14);
        //время через которое проверяется соединение на состояние
        comboPooledDataSource.setIdleConnectionTestPeriod(300);

        //имя специальной таблицы для тестирования соединения с БД
        comboPooledDataSource.setAutomaticTestTable("c3p0DatabaseTestTable");

        comboPooledDataSource.setForceIgnoreUnresolvedTransactions(true);
        comboPooledDataSource.setAutoCommitOnClose(false);
        comboPooledDataSource.setNumHelperThreads(10);

        return comboPooledDataSource;

    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(modelPackage);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put(Environment.DIALECT, dialect);
        jpaProperties.put(Environment.HBM2DDL_AUTO, hbmddl);
        jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.setDataSource(dataSource());
        return entityManagerFactoryBean;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new JpaTransactionManager();
    }
}