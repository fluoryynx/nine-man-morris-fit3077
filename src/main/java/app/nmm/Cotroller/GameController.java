package app.nmm.Cotroller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static jdk.javadoc.internal.doclets.toolkit.util.DocPath.parent;

public class GameController {

    private ImageView imageView;


    public GameController() {
        imageView = new ImageView();
        imageView.setImage(new Image("node.png"));
        imageView.setOnMouseClicked(this::changeToWhiteToken);
    }

    public ImageView getImageView() {
        return imageView;
    }

    private void changeToWhiteToken(MouseEvent event) {
        Image newImage = new Image("White_Token.png");
        imageView.setImage(newImage);
    }

    private void changeToBlackToken(MouseEvent event) {
        Image newImage = new Image("Black_Token.png");
        imageView.setImage(newImage);
    }





}
