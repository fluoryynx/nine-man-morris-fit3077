package app.nmm.Cotroller;

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

public class MainPageController {

    Parent fxmlLoader;
    Stage stage;
    Scene scene;

    @FXML
    void closeApplication(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void playerVsPlayerMode(MouseEvent event)throws IOException {
        fxmlLoader = FXMLLoader.load(Application.class.getResource("SelectToken.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.setTitle("Nine Man's Morris");
        stage.show();
    }


}
