package com.xyzcorp.dao;

import com.xyzcorp.models.Album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StandardAlbumDAO implements AlbumDAO {
    public static final String INSERT_STMT =
            "INSERT INTO ALBUM (name, artist, year) VALUES (?,?,?)";
    public static final String DELETE_STMT =
            "DELETE FROM Album (name, artist, year) VALUES (?,?,?)";
    public static final String UPDATE_STMT =
            "UPDATE Album SET name = ?, artist = ?, year = ? WHERE id =?";

    private Connection connection;

    @Override
    public boolean insert(Album album) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STMT);
        preparedStatement.setString(1, album.getName());
        preparedStatement.setString(2, album.getArtist());
        preparedStatement.setInt(3, album.getYear());
        return preparedStatement.execute();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean delete(Album album) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STMT);
        preparedStatement.setInt(1, album.getId());
        return preparedStatement.execute();
    }

    @Override
    public boolean update(Album album) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STMT);
        preparedStatement.setInt(4, album.getId());
        preparedStatement.setString(1, album.getName());
        preparedStatement.setString(2, album.getArtist());
        preparedStatement.setInt(3, album.getYear());
        return preparedStatement.execute();
    }
}