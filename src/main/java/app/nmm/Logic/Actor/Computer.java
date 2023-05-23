package app.nmm.Logic.Actor;

import app.nmm.Controller.GameController;
import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Action.MoveTokenAction;
import app.nmm.Logic.Handler.CheckMill;
import app.nmm.Logic.Location.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Computer extends Actor {
    public Computer(String tokenColour, String playerName, int i) {
        super(tokenColour, playerName, i);
    }


    public Action getMoveAction(Map<Integer,ArrayList<Action>> allowableActionList, ArrayList<Node> nodeList, CheckMill millChecker){
        Iterator<Integer> tokenPositionList =  allowableActionList.keySet().iterator();

        while(tokenPositionList.hasNext()){
            int tokenID =  tokenPositionList.next();

            for(Action action : allowableActionList.get(tokenID)){

                int targetID =  ((MoveTokenAction) action).getTargetId();

                ArrayList<Boolean> result = millChecker.checkPossibleMill(nodeList,targetID);

                if (result.get(0) == true || result.get(1) ==  true){
                    return action;
                }
            }

        }


        return null;
    }
}
