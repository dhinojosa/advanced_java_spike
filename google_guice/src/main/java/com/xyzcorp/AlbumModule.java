package com.xyzcorp;

import com.google.inject.AbstractModule;
import com.google.inject.PrivateModule;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import com.xyzcorp.annotations.H2;
import com.xyzcorp.annotations.Oracle;
import com.xyzcorp.dao.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class AlbumModule extends AbstractModule{
    @Override
    protected void configure() {
        //Wiring all takes place here.
        bind(String.class).annotatedWith(Names.named("url"))
                .toInstance("jdbc:h2:tcp://localhost/~/test");
        bind(String.class).annotatedWith(Names.named("userName"))
                .toInstance("sa");
        bind(String.class).annotatedWith(Names.named("password"))
                .toInstance("");

        bind(AlbumDAO.class).to(StandardAlbumDAO.class);

        install(new PrivateModule() {
            @Override
            protected void configure() {
                bind(DataSource.class)
                        .toProvider(H2DataSourceProvider.class)
                        .asEagerSingleton();

                bind(Connection.class).annotatedWith(H2.class)
                        .toProvider(new Provider<Connection>() {
                            @Override
                            public Connection get() {
                                return null;
                            }
                        });

                expose(Connection.class).annotatedWith(H2.class);
            }
        });

        install(new PrivateModule() {
            @Override
            protected void configure() {
                bind(DataSource.class)
                        .toProvider(OracleDataSourceProvider.class)
                        .asEagerSingleton();

                bind(Connection.class).annotatedWith(Oracle.class)
                        .toProvider(ConnectionProvider.class);

                expose(Connection.class).annotatedWith(Oracle.class);
            }
        });
    }
}