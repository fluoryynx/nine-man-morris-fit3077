package app.nmm.Logic.Handler;

import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Location.Node;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckLegalMove {

    private Map<Integer, ArrayList<Integer>> adjacentPosition;

    public CheckLegalMove(){
        this.adjacentPosition = new HashMap<>();

    }

    public Pair<HashMap<Integer,ArrayList<Action>>,HashMap<Integer,ArrayList<Action>>> calculateLegalMove(Actor actor, ArrayList<Node> nodeList){

        HashMap allowableActions = new HashMap();
        HashMap removeActions = new HashMap();


        return  new Pair(allowableActions,removeActions);
    }
}
