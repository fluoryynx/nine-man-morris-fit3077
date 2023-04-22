package app.nmm.Controller;

import app.nmm.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;

import java.io.IOException;

public class SelectTokenController {

    Parent fxmlLoader;
    Stage stage;
    Scene scene;
    @FXML
    Button getscene;


    @FXML
    public void initialize(){
        System.out.println("im in select token");
    }

    @FXML
    void backToMain(MouseEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(Application.class.getResource("main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.setTitle("Nine Man's Morris");
        stage.show();
    }

    @FXML
    void flipCoin(MouseEvent event) throws IOException{
        Random r = new Random();
        int headOrTail = r.nextInt(100) + 1;
        if (headOrTail >= 50) {

        }
        else{

        }
    }

    @FXML
    void swap(MouseEvent event) throws IOException{
        // how to swap scene
        Text textField1 = (Text) scene.lookup("p1");
        Text textField2 = (Text) scene.lookup("p2");
        String str1 = textField1.getText();
        String str2 = textField2.getText();
        textField1.setText(str2);
        textField2.setText(str1);
    }
}
