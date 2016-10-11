package com.xyzcorp.dao;

import com.google.inject.Provider;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

public class OracleDataSourceProvider implements Provider<DataSource> {
    private String url;
    private String userName;
    private String password;

    @Inject
    public OracleDataSourceProvider(@Named("url") String url,
                                    @Named("userName") String userName,
                                    @Named("password") String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public DataSource get() {
        return JdbcConnectionPool.create(
                url, userName, password);
    }
}
