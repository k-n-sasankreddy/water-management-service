package com.knsr.wmgmt.properties;


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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
// Optional HikariCP properties (uncomment if used in YAML and config)
    // private long idleTimeout;
    // private long maxLifetime;
}
