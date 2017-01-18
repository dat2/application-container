package com.dujay.test;

import com.dujay.Config;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseModule extends AbstractModule {

    @Config(name = "database.host")
    private String databaseHost = "localhost";

    @Config(name = "database.user")
    private String databaseUser = "";

    @Config(name = "database.password")
    private String databasePassword = "";

    @Config(name = "database.poolsize")
    private Integer databasePoolsize = 10;

    @Override
    protected void configure() {
    }

    @Provides
    public HikariDataSource provideDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(databaseUser);
        dataSource.setPassword(databasePassword);
        dataSource.setMaximumPoolSize(databasePoolsize);
        
        return dataSource;
    }
}
