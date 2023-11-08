package com.tokarevaa.webapp.sql;

import com.tokarevaa.webapp.exception.ExistStorageException;
import com.tokarevaa.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {

    private static final String SQL_DUPLICATE_ERROR = "23505";
    private final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory cf) {
        connectionFactory = cf;
    }

    public <T> T execute(String sql, SQLExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw exceptionTransform(e);
        }
    }

    public void transactionalExecute(SQLTransaction executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                executor.execute(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw exceptionTransform(e);
        }
    }

    private StorageException exceptionTransform(SQLException e) {
        if (e.getSQLState().equals(SQL_DUPLICATE_ERROR)) {
            return new ExistStorageException(null);
        }
        return new StorageException(e);
    }

    public void execute(String sql) {
        execute(sql, PreparedStatement::execute);
    }

    @FunctionalInterface
    public interface SQLExecutor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    public interface SQLTransaction {
        void execute(Connection connection) throws SQLException;
    }
}