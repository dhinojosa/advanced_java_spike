package com.xyzcorp.dao;

import com.xyzcorp.annotations.UnitTest;
import com.xyzcorp.models.Album;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;

public class StandardAlbumDAOTest {

    private Connection connection;
    private PreparedStatement preparedStatement;

    @Before
    public void setUp() {
        connection = createMock(Connection.class);
        preparedStatement = createMock(PreparedStatement.class);
    }

    @Test
    public void testInsert() throws SQLException {
        expect(connection.prepareStatement(StandardAlbumDAO.INSERT_STMT)).andReturn(preparedStatement);
        expect(preparedStatement.execute()).andReturn(true);

        preparedStatement.setString(1, "Zeppelin IV");
        preparedStatement.setString(2, "Led Zeppelin");
        preparedStatement.setInt(3, 1973);

        replay(connection, preparedStatement);

        StandardAlbumDAO albumDAO = new StandardAlbumDAO();
        albumDAO.setConnection(connection);
        boolean result = albumDAO.insert(new Album("Zeppelin IV", "Led Zeppelin", 1973));
        assertThat(result).isTrue();

        verify(connection, preparedStatement);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testExceptionWhenPreparedStatementOccurs() throws SQLException {
        thrown.expect(SQLException.class);
        thrown.expectMessage("SQL Exception Occurred");

        expect(connection.prepareStatement(StandardAlbumDAO.INSERT_STMT)).andThrow(
                new SQLException("SQL Exception Occurred")
        );

        replay(connection, preparedStatement);

        StandardAlbumDAO albumDAO = new StandardAlbumDAO();
        albumDAO.setConnection(connection);
        albumDAO.insert(new Album("Zeppelin IV", "Led Zeppelin", 1973));

        verify(connection, preparedStatement);
    }

    @Test
    @Category(value = UnitTest.class)
    public void testDelete() throws SQLException {
        expect(connection.prepareStatement(StandardAlbumDAO.DELETE_STMT)).andReturn(preparedStatement);
        preparedStatement.setInt(1, 103);
        expect(preparedStatement.execute()).andReturn(true);

        replay(connection, preparedStatement);
        StandardAlbumDAO albumDAO = new StandardAlbumDAO();
        albumDAO.setConnection(connection);
        boolean result = albumDAO.delete(new Album(103, "Zeppelin IV", "Led Zeppelin", 1973));
        assertThat(result).isTrue();
        verify(connection, preparedStatement);
    }

    @Test
    @Category(value = UnitTest.class)
    public void testUpdate() throws SQLException {
        expect(connection.prepareStatement(StandardAlbumDAO.UPDATE_STMT)).andReturn(preparedStatement);
        preparedStatement.setString(1, "Dark Side of the Moon");
        preparedStatement.setString(2, "Pink Floyd");
        preparedStatement.setInt(3, 1973);
        preparedStatement.setInt(4, 103);

        expect(preparedStatement.execute()).andReturn(true);
        replay(connection, preparedStatement);
        StandardAlbumDAO albumDAO = new StandardAlbumDAO();
        albumDAO.setConnection(connection);
        boolean result = albumDAO.update(new Album(103, "Dark Side of the Moon", "Pink Floyd", 1973));
        assertThat(result).isTrue();
        verify(connection, preparedStatement);
    }
}
