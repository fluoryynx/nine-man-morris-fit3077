package app.nmm.Logic.Actor;

import app.nmm.Logic.Action.Action;

import java.util.List;
import java.util.Map;

public class Player extends Actor{
    public Player(String tokenColour) {
        super(tokenColour);
    }



    @Override
    public Action playTurn(Map<Integer,List<Action>> allowableActions) {
        Integer selectedID =  1;
        Action finalAction = null;
        while (finalAction == null){
            if(allowableActions.containsKey(selectedID)){
                List<Action> actionList =  allowableActions.get(selectedID);
                // To be continued........
            }
            else{
                // Get the info and ask the user to choose again
            }
        }


        return null;
    }
}
