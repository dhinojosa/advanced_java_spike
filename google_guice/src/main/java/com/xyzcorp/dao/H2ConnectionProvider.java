package com.xyzcorp.dao;

import com.google.inject.Provider;
import com.google.inject.Provides;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class H2ConnectionProvider implements Provider<Connection> {


    private String url;
    private String userName;
    private String password;

    public H2ConnectionProvider(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Connection get() {
        JdbcConnectionPool cp = JdbcConnectionPool.create(
                url, userName, password);
        try {
            return cp.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create a connection", e);
        }
    }
}
