package app.nmm.UiEditor;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public abstract class SceneEditor {

    @FXML
    AnchorPane anchorPane;
    Scene currentScene;



    public SceneEditor(Scene scene, AnchorPane anchorPane){
        this.currentScene =  scene;
        this.anchorPane =  anchorPane;
    }
}
