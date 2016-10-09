package com.xyzcorp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Finder<T> {

    private Connection connection;
    private String sql;
    private Function<ResultSet, T> converter;
    private Consumer<PreparedStatement> fieldSetterConsumer;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setConverter(Function<ResultSet, T> converter) {
        this.converter = converter;
    }

    private void closeResources(PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> findAll() throws SQLException {
        List<T> result = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (fieldSetterConsumer != null) fieldSetterConsumer.accept(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(converter.apply(resultSet));
            }
        } finally {
            closeResources(preparedStatement, resultSet);
        }
        return result;
    }

    public void setFieldSetterConsumer(Consumer<PreparedStatement> fieldSetterConsumer) {
        this.fieldSetterConsumer = fieldSetterConsumer;
    }

    public Optional<T> findSingle() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            fieldSetterConsumer.accept(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return Optional.of(converter.apply(resultSet));
        } finally {
            closeResources(preparedStatement, resultSet);
        }
        return Optional.empty();
    }
}
