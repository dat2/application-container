package com.dujay.test;

import com.dujay.Application;
import com.google.inject.Inject;
import com.zaxxer.hikari.HikariDataSource;

public class TestApplication implements Application {

    @Inject
    private HikariDataSource dataSource;

    @Override
    public void start() {
        try {
            dataSource.getConnection();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        System.out.println("stopping");
    }
    
    
}
