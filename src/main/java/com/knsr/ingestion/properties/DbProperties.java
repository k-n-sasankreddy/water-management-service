package com.knsr.ingestion.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/* Annotated with @Configuration and @ConfigurationProperties(prefix = "db") to bind properties from application.yml.
   Uses Lombok's @Data to generate getters, setters, toString(), etc.
*/
@Data
@Configuration
@ConfigurationProperties(prefix = "db")
public class DbProperties {

    private String url;
    private String username;
    private String password;
    private String driverClassName;

    private int maxPoolSize;
    private int minPoolSize;
    private int maxWaitTime;
    private long connectionTimeout;

    // Optional HikariCP properties (uncomment if used in YAML and config)
    // private long idleTimeout;
    // private long maxLifetime;
}
