package com.xyzcorp.dao;

import com.xyzcorp.models.Album;

import java.sql.SQLException;

/**
 * Created by danno on 10/10/16.
 */
public interface AlbumDAO {
    boolean insert(Album album) throws SQLException;

    boolean delete(Album album) throws SQLException;

    boolean update(Album album) throws SQLException;
}
