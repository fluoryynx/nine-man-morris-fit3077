package app.nmm.Controller;

import app.nmm.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController {

    Parent fxmlLoader;
    Stage stage;
    Scene scene;

    @FXML
    public void initialize(){
        System.out.println("im in main page");
    }

    @FXML
    void closeApplication(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void playerVsPlayerMode(MouseEvent event)throws IOException {
        fxmlLoader = FXMLLoader.load(Application.class.getResource("SelectToken.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        System.out.println(scene);
        stage.setTitle("Nine Man's Morris");
        stage.show();
    }


}
