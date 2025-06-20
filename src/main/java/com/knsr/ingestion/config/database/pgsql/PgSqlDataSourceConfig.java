package com.knsr.ingestion.config.database.pgsql;


import com.knsr.ingestion.properties.DbProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.knsr.ingestion.repository",
        entityManagerFactoryRef = "entityManagerPgSql",
        transactionManagerRef = "transactionManagerPgSql"
)
public class PgSqlDataSourceConfig {

    private final DbProperties dbProperties;

    public PgSqlDataSourceConfig(DbProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Bean
    @Primary
    public DataSource dsPgSql() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbProperties.getUrl());
        config.setUsername(dbProperties.getUsername());
        config.setPassword(dbProperties.getPassword());
        config.setDriverClassName(dbProperties.getDriverClassName());
        config.setConnectionTimeout(dbProperties.getConnectionTimeout());
        config.setMaximumPoolSize(dbProperties.getMaxPoolSize());
        config.setMinimumIdle(dbProperties.getMinPoolSize());
        // Optional: map maxWaitTime to validationTimeout or similar if needed
        return new HikariDataSource(config);
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerPgSql() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dsPgSql());
        em.setPackagesToScan("com.knsr.ingestion.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", "true");

        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(name = "transactionManagerPgSql")
    @Primary
    public JpaTransactionManager transactionManagerPgSql() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerPgSql().getObject());
        return transactionManager;
    }

}
