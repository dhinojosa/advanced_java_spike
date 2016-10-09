package com.xyzcorp.dao;

import com.xyzcorp.models.Album;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;

public class FinderTest {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Consumer<PreparedStatement> nameAndTitleConsumer;

    @Before
    public void setUp() {
        connection = createMock(Connection.class);
        preparedStatement = createMock(PreparedStatement.class);
        resultSet = createMock(ResultSet.class);
        nameAndTitleConsumer = ps -> {
            try {
                ps.setString(1, "Dark Side of the Moon");
                ps.setString(2, "Pink Floyd");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    }

    @Test
    public void findAllAlbumsExamples() throws Exception {
        Function<ResultSet, Album> function = (Function<ResultSet, Album>) createMock(Function.class);
        expect(connection.prepareStatement("SELECT * FROM Album")).andReturn(preparedStatement);
        expect(preparedStatement.executeQuery()).andReturn(resultSet);
        expect(resultSet.next()).andReturn(true).times(3).andReturn(false);
        expect(function.apply(resultSet)).andReturn(createMock(Album.class)).times(3);
        resultSet.close();
        preparedStatement.close();
        connection.close();

        replay(function, connection, preparedStatement, resultSet);
        Finder<Album> finder = new Finder<>();
        finder.setConnection(connection);
        finder.setSql("SELECT * FROM Album");
        finder.setConverter(function);
        assertThat(finder.findAll()).hasSize(3);
        verify(function, connection, preparedStatement, resultSet);
    }

    @Test
    public void findAlbumsLikeNameExample() throws Exception {
        Function<ResultSet, Album> function = (Function<ResultSet, Album>) createMock(Function.class);
        expect(connection.prepareStatement("SELECT (id, name, title, year) FROM Album WHERE name like '%?%'"))
                .andReturn(preparedStatement);
        preparedStatement.setString(1, "Dark Side");
        expect(preparedStatement.executeQuery()).andReturn(resultSet);

        expect(resultSet.next()).andReturn(true).andReturn(true).andReturn(false);
        expect(function.apply(resultSet)).andReturn(createMock(Album.class)).times(2);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        Consumer<PreparedStatement> consumer = ps -> {
            try {
                ps.setString(1, "Dark Side");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };


        replay(function, connection, preparedStatement, resultSet);
        Finder<Album> finder = new Finder<>();
        finder.setConnection(connection);
        finder.setSql("SELECT (id, name, title, year) FROM Album WHERE name like '%?%'");
        finder.setConverter(function);
        finder.setFieldSetterConsumer(consumer);
        assertThat(finder.findAll()).hasSize(2);
        verify(function, connection, preparedStatement, resultSet);
    }

    @Test
    public void findAlbumsByNameAndTitleExample() throws Exception {
        Function<ResultSet, Album> function = (Function<ResultSet, Album>) createMock(Function.class);
        String sql = "SELECT (id, name, title, year) FROM Album WHERE name = ? AND title = ?";
        expect(connection.prepareStatement(sql)).andReturn(preparedStatement);
        preparedStatement.setString(1, "Dark Side of the Moon");
        preparedStatement.setString(2, "Pink Floyd");

        expect(preparedStatement.executeQuery()).andReturn(resultSet);
        expect(resultSet.next()).andReturn(true).times(5).andReturn(false);
        expect(function.apply(resultSet)).andReturn(createMock(Album.class)).times(5);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        Consumer<PreparedStatement> consumer = ps -> {
            try {
                ps.setString(1, "Dark Side of the Moon");
                ps.setString(2, "Pink Floyd");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };


        replay(function, connection, preparedStatement, resultSet);
        Finder<Album> finder = new Finder<>();
        finder.setConnection(connection);
        finder.setSql(sql);
        finder.setConverter(function);
        finder.setFieldSetterConsumer(consumer);
        assertThat(finder.findAll()).hasSize(5);
        verify(function, connection, preparedStatement, resultSet);
    }

    @Test
    public void findSingleAlbumByNameAndTitleExample() throws Exception {
        Function<ResultSet, Album> function = (Function<ResultSet, Album>) createMock(Function.class);
        String sql = "SELECT (id, name, title, year) FROM Album WHERE name = ? AND title = ?";
        expect(connection.prepareStatement(sql)).andReturn(preparedStatement);
        preparedStatement.setString(1, "Dark Side of the Moon");
        preparedStatement.setString(2, "Pink Floyd");

        expect(preparedStatement.executeQuery()).andReturn(resultSet);
        expect(resultSet.next()).andReturn(true);
        Album album = createMock(Album.class);

        expect(function.apply(resultSet)).andReturn(album).times(1);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        replay(function, connection, preparedStatement, resultSet);
        Finder<Album> finder = new Finder<>();
        finder.setConnection(connection);
        finder.setSql(sql);
        finder.setConverter(function);
        finder.setFieldSetterConsumer(nameAndTitleConsumer);
        assertThat(finder.findSingle()).contains(album);
        verify(function, connection, preparedStatement, resultSet);
    }

    @Test
    public void findSingleAlbumByNameAndTitleNotFoundExample() throws Exception {
        Function<ResultSet, Album> function = (Function<ResultSet, Album>) createMock(Function.class);
        String sql = "SELECT (id, name, title, year) FROM Album WHERE name = ? AND title = ?";
        expect(connection.prepareStatement(sql)).andReturn(preparedStatement);
        preparedStatement.setString(1, "Dark Side of the Moon");
        preparedStatement.setString(2, "Pink Floyd");

        expect(preparedStatement.executeQuery()).andReturn(resultSet);
        expect(resultSet.next()).andReturn(false);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        replay(function, connection, preparedStatement, resultSet);
        Finder<Album> finder = new Finder<>();
        finder.setConnection(connection);
        finder.setSql(sql);
        finder.setConverter(function);
        finder.setFieldSetterConsumer(nameAndTitleConsumer);
        assertThat(finder.findSingle()).isEmpty();
        verify(function, connection, preparedStatement, resultSet);
    }
}
