package org.awfagi.dbviewer.util;

import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBManager {
    private final Connection connection;

    public DBManager(String connectionUrl) throws SQLException {
        connection = DriverManager.getConnection(connectionUrl);
    }

    public List<String> getTables() throws SQLException {
        String sql = "SELECT TABLENAME FROM sys.systables WHERE TABLETYPE = 'T'";
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            List<String> list = new ArrayList<>();

            while (rs.next()){
                list.add(rs.getString(1));
            }

            return list;

        }
    }

    public Pair<List<String>, List<Map<String, String>>> getDataFromTable(String tableName){
        String sql = "SELECT * FROM " + tableName;

        try (Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery(sql);

            return new Pair<>(ParseResult.getColumnNames(resultSet), ParseResult.getData(resultSet));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

}
