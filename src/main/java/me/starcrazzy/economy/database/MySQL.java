package me.starcrazzy.economy.database;

import lombok.RequiredArgsConstructor;

import java.sql.*;

/**
 * @author oNospher
 **/
@RequiredArgsConstructor
public class MySQL {
    private final String host,user,password,database;
    private Connection connection;

    /**
     * @throws SQLException
     */
    public void start() throws SQLException {
        this.connection = DriverManager.getConnection(String.format("jdbc:mysql://%s/%s", host, database), user, password);
    }

    /**
     * @throws SQLException
     */
    public void refresh() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) this.start();
    }

    /**
     * @param query
     * @return PreparedStatement
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return this.connection.prepareStatement(query);
    }

    /**
     * @param query
     * @return Boolean
     * @throws SQLException
     */
    public Boolean execute(String query) throws SQLException {
        return this.connection.createStatement().execute(query);
    }

    /**
     * @return Connection
     */
    public Connection getConnection() { return this.connection; }
}