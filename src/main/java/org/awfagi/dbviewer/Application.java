package org.awfagi.dbviewer;

import org.awfagi.dbviewer.scenes.Table;
import org.awfagi.dbviewer.util.DBManager;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Application extends javafx.application.Application {

    private DBManager dbManager;

    @Override
    public void start(Stage stage) {

        Label welcomeLabel = new Label("View table");
        welcomeLabel.setFont(new Font("System Bold", 16));

        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(20);
        form.getColumnConstraints().add(new ColumnConstraints(100));
        form.getColumnConstraints().add(new ColumnConstraints(100));
        form.getColumnConstraints().add(new ColumnConstraints(100));

        form.add(welcomeLabel, 0, 0, 3, 1);

        form.add(new Label("Connection url:"), 0, 1);
        form.add(new Label("Table name:"), 0, 2);

        TextField connectionField = new TextField();
        ComboBox<String> tablesBox = new ComboBox<>();

        connectionField.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1){
                try {
                    DBManager manager = new DBManager(connectionField.getText());
                    tablesBox.setItems(FXCollections.observableList(manager.getTables()));
                    this.dbManager = manager;
                } catch (SQLException ignored) {

                }
            }
        });

        form.add(connectionField, 1, 1, 2, 1);
        form.add(tablesBox, 1, 2, 2, 1);

        Button signInButton = new Button("View");

        form.add(signInButton, 2, 3);

        Label resultLabel = new Label();

        signInButton.setOnAction(actionEvent -> {
            if (dbManager == null){
                return;
            }

            Pair<List<String>, List<Map<String, String>>> pair =
                    dbManager.getDataFromTable(tablesBox.getSelectionModel().getSelectedItem());

            if (pair == null){
                return;
            }

            Table table = new Table(pair.getKey(), pair.getValue());
            Scene scene = new Scene(table, 400, 300);
            stage.setScene(scene);
        });

        VBox root = new VBox(form, resultLabel);
        root.setMinSize(400, 300);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(30);

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("Database viewer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}