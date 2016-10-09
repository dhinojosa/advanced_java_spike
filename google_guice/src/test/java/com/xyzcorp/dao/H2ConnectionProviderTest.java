package com.xyzcorp.dao;

import org.junit.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;


public class H2ConnectionProviderTest {

    @Test
    public void testGetConnection() {
        H2ConnectionProvider provider = new H2ConnectionProvider( "jdbc:h2:tcp://localhost/~/test", "sa", "");
        assertThat(provider.get()).isNotNull().isInstanceOf(Connection.class);
    }
}