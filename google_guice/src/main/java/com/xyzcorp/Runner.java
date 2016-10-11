package com.xyzcorp;


import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.xyzcorp.dao.AlbumDAO;
import com.xyzcorp.models.Album;

import java.sql.SQLException;

public class Runner {
    public static void main(String[] args) throws SQLException {
        Injector injector = Guice.createInjector(
                new AlbumModule());
        AlbumDAO albumDAO =
                injector.getInstance(AlbumDAO.class);
        albumDAO.insert(new Album
                ("My World 2.0 (Remastered)", "Justin Bieber", 2010));
    }
}
