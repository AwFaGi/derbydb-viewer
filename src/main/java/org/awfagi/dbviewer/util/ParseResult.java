package org.awfagi.dbviewer.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseResult {

    public static List<String> getColumnNames(ResultSet resultSet){
        try{
            ResultSetMetaData data = resultSet.getMetaData();
            List<String> list = new ArrayList<>();
            for (int i = 1; i <= data.getColumnCount(); i++){
                list.add(data.getColumnName(i));
            }

            return list;

        } catch (SQLException ignored) {
            return new ArrayList<>();
        }
    }

    public static List<Map<String, String>> getData(ResultSet resultSet){
        try{
            ResultSetMetaData data = resultSet.getMetaData();
            int columnCount = data.getColumnCount();

            List<String> columns = getColumnNames(resultSet);

            List<Map<String, String>> result = new ArrayList<>();

            while (resultSet.next()){
                Map<String, String> row = new HashMap<>();

                for (int i = 1; i <= columnCount; i++){
                    row.put(columns.get(i-1), resultSet.getString(i));
                }

                result.add(row);

            }

            return result;

        } catch (SQLException ignored) {

            System.out.println("getData");
            System.out.println(ignored.getMessage());

            return new ArrayList<>();
        }
    }

}
