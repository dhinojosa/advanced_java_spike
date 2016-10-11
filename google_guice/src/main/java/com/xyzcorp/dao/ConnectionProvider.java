package com.xyzcorp.dao;

import com.google.inject.Provider;
import com.google.inject.Provides;
import com.xyzcorp.annotations.H2;
import com.xyzcorp.annotations.Oracle;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider implements Provider<Connection> {


    private DataSource dataSource;

    @Inject
    public ConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection get() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create a connection", e);
        }
    }
}
