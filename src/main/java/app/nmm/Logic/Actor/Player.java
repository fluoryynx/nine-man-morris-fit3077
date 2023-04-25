package app.nmm.Logic.Actor;

import app.nmm.Application;
import app.nmm.Controller.GameController;
import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Observer.Observer;
import javafx.fxml.FXMLLoader;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player extends Actor implements Observer {

    private Integer userAction = null;
    public Player(String tokenColour, String playerName) {
        super(tokenColour, playerName);
    }

    @Override
    public Action playTurn(Map<Integer,List<Action>> allowableActions,GameController engine){


        while (this.userAction == null){

        }

        Action finalAction = null;
        while (finalAction == null){
            if(!allowableActions.containsKey(this.userAction)){
                List<Action> actionList =  allowableActions.get(this.userAction);
                ArrayList<Integer> actionResultNodeID = new ArrayList<>();
//                for (Action action: actionList){
//                    actionResultNodeID.add(action.getDistinationNodeId());
//                }

                engine.showAvailableMove(actionResultNodeID);

            }
            else{
                // Get the info and ask the user to choose again
            }
        }


        return null;
    }

    @Override
    public void sendUserAction(Integer nodeID) {
        this.userAction =  nodeID;

    }
}
