package app.nmm.UiEditor;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.File;

public class BoardSceneEditor extends SceneEditor {


    public BoardSceneEditor(Scene scene, AnchorPane anchorPane) {
        super(scene, anchorPane);
    }

    /**
     * remove image from the board
     * @param nodeId - int, nodeId
     * @param nodeFxId - String, type of image (the fxid)
     */
    public void removeImage(int nodeId, String nodeFxId){
        ObservableList<Node> childList =  getChildList(nodeId);
        if (childList.size() > 1){
            Node node =  childList.get(childList.size()-1);
            if (node.getId().equals(nodeFxId)){
                childList.remove(node);
            }
        }
    }

    public ObservableList<Node> getChildList(int nodeId){
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        return group.getChildren();
    }

    /**
     * a method to put the image on the board
     * @param pathname the path to locate the image
     * @param itemID the fxid to be assigned
     * @param xLayout x coordinate
     * @param yLayout y coordinate
     * @param fitWidth width of the image
     * @param fitHeight height of the image
     * @param nodeId nodeId to add the image
     * @return
     */
    @FXML
    public ImageView addItemToBoard(String pathname, String itemID, int xLayout, int yLayout, int fitWidth, int fitHeight, Integer nodeId) {

        ObservableList<Node> childList = getChildList(nodeId);
        // get the file
        File file = new File(pathname);
        // convert to image
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView();
        // set the image view properties
        imageView.setImage(image);
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
        imageView.setLayoutX(xLayout);
        imageView.setLayoutY(yLayout);
        // id: "w" + "token" + "tokencount"
        imageView.setId(itemID);
        childList.add(imageView);
        return imageView;
    }


    /**
     * A method to change the image of a token
     * @param nodeId - The id of the node to be changed
     * @param tokenColour - The colour of the token to be changed
     * @param paths - The path of the image to be changed to
     * @param size -   The size of the image to be changed to
     * @param offSet - The offset of the image to be changed to
     */

    public void changeTokenImage(int nodeId, String tokenColour, String paths, Integer size, Integer offSet) {
        // Get the list of children of the node
        ObservableList<Node>currentChildList =  getChildList(nodeId);
        // Create a new file with the path of the image
        File newFile = new File(paths);
        // Change the graphic of the selected token to toke with highlight around.
        for(Node node : currentChildList){
            // If the node is an image view and the id contains the colour of the token
            if(node.getId().contains(tokenColour) ){
                ((ImageView) node).setImage(new Image(newFile.toURI().toString()));
                ((ImageView) node).setFitWidth(size);
                ((ImageView) node).setFitHeight(size);
                ((ImageView) node).setLayoutX(offSet);
                ((ImageView) node).setLayoutY(offSet);
                break;
            }
        }
    }


    /***
     * a method to update token count on the UI
     * @param whiteTokenCount
     * @param blackTokenCount
     * @param teamColour
     * @param tokenOnBoard
     */

    public void updateTokenCount(Text whiteTokenCount, Text blackTokenCount  , String teamColour, Integer tokenOnBoard) {
        // update the token count on the UI
        if (teamColour.equals("White")){
            whiteTokenCount.setText(Integer.toString(tokenOnBoard));
        }
        else{
            blackTokenCount.setText(Integer.toString(tokenOnBoard));
        }
    }
}
