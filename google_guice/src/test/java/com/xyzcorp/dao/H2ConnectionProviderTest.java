package com.xyzcorp.dao;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;


public class H2ConnectionProviderTest {

    @Test
    public void testGetConnection() throws SQLException {
        DataSource dataSource = createMock(DataSource.class);
        Connection connection = createMock(Connection.class);

        expect(dataSource.getConnection()).andReturn(connection);

        replay(dataSource, connection);  //Don't need this for Mockito

        ConnectionProvider provider = new ConnectionProvider(dataSource);
        assertThat(provider.get()).isEqualTo(connection);
        verify(dataSource, connection);
    }
}