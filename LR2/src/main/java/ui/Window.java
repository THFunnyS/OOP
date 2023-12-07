package ui;

import functions.factory.ArrayTabulatedFunctionFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Window extends Application {
    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage stage1) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/Window.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 400);
        stage1.setScene(scene);
        stage1.setResizable(false);
        stage1.setTitle("создание табулированной функции");
        stage1.show();
        Settings.getInstance().setFactory(new ArrayTabulatedFunctionFactory());

        stage1.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
