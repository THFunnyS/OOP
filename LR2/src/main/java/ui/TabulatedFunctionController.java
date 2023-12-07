package ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class TabulatedFunctionController extends Application{
    @FXML
    private final TableView<Point> table = new TableView<>();
    @FXML
    private final TextField pointsField = new TextField();
    @FXML
    public Button createButton;
    @FXML
    public Label labelNumOfPoints;
    @FXML
    public AnchorPane pane;
    @FXML
    public TableColumn<Point, Double> xColumn;
    @FXML
    public TableColumn<Point, Double> yColumn;

    void PointsEnter(ActionEvent event) throws IOException {
        int size=Integer.parseInt(pointsField.getText());
        try {
            if (size <2) throw new IllegalArgumentException("Размер должен быть >=2");
            for (int i=0;i<size;++i){
                table.getItems().add(new Point());
            }
            pane.setVisible(false);
            table.setVisible(true);
            createButton.setVisible(true);
        }catch (IllegalArgumentException e){

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tabulated Function Creator");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label pointsLabel = new Label("Number of points:");
        GridPane.setConstraints(pointsLabel, 0, 0);
        grid.getChildren().add(pointsLabel);

        GridPane.setConstraints(pointsField, 1, 0);
        grid.getChildren().add(pointsField);

        Button createButton = new Button("Create");
        GridPane.setConstraints(createButton, 2, 0);
        grid.getChildren().add(createButton);

        TableColumn<Point, Double> xColumn = new TableColumn<>("X");
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        xColumn.setMinWidth(100);

        TableColumn<Point, Double> yColumn = new TableColumn<>("Y");
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        yColumn.setMinWidth(100);

        table.getColumns().addAll(xColumn, yColumn);
        table.setPlaceholder(new Label("Enter the number of points and click Create"));

        GridPane.setConstraints(table, 0, 1, 3, 1);
        grid.getChildren().add(table);

        createButton.setOnAction(e -> {
            int points = Integer.parseInt(pointsField.getText());
            for (int i = 0; i < points; i++) {
                table.getItems().add(new Point());
            }
            pointsField.setDisable(true);
            createButton.setDisable(true);
        });

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
