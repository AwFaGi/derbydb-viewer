package org.awfagi.dbviewer.scenes;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

import java.util.List;
import java.util.Map;

public class Table extends TableView<Map<String, String>> {
    public Table(List<String> columns, List<Map<String, String>> data) {

        this.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        for (String name: columns){
            TableColumn<Map<String, String>, String> column = new TableColumn<>(name);
            column.setCellValueFactory(new MapValueFactory(name));
            this.getColumns().add(column);
        }

        this.setItems(FXCollections.observableList(data));

        System.out.println(data);

    }

}
