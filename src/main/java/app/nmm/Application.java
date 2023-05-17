package app.nmm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Application extends javafx.application.Application {


    @Override
    public void start(Stage stage) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(Application.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Nine Man's Morris");
        stage.setScene(scene);
        stage.show();
        // set the minimum height of the application window
//        stage.setMinHeight(778);
//        stage.setMinWidth(665);
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}