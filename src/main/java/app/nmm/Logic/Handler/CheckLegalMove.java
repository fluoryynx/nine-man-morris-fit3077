package app.nmm.Logic.Handler;

import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Location.Node;

import java.util.*;

import static java.util.Map.entry;

public class CheckLegalMove {

    Map<Integer, ArrayList<Integer>> adjacentPosition  = new HashMap<Integer, ArrayList<Integer>>() {{
        put(0, new ArrayList<>(Arrays.asList(0,9)));
        put(1, new ArrayList<>(Arrays.asList(0,2,4)));
        put(2, new ArrayList<>(Arrays.asList(1,14)));
        put(3, new ArrayList<>(Arrays.asList(4,10)));
        put(4, new ArrayList<>(Arrays.asList(1,3,5,7)));
        put(5, new ArrayList<>(Arrays.asList(4,13)));
        put(6, new ArrayList<>(Arrays.asList(7,11)));
        put(7, new ArrayList<>(Arrays.asList(4,6,8)));
        put(8, new ArrayList<>(Arrays.asList(7,12)));
        put(9, new ArrayList<>(Arrays.asList(0,10,21)));
        put(10, new ArrayList<>(Arrays.asList(3,9,11,18)));
        put(11, new ArrayList<>(Arrays.asList(6,10,15)));
        put(12, new ArrayList<>(Arrays.asList(8,13,17)));
        put(13, new ArrayList<>(Arrays.asList(5,12,14,20)));
        put(14, new ArrayList<>(Arrays.asList(2,13,23)));
        put(15, new ArrayList<>(Arrays.asList(11,16)));
        put(16, new ArrayList<>(Arrays.asList(15,17,19)));
        put(17, new ArrayList<>(Arrays.asList(12,16)));
        put(18, new ArrayList<>(Arrays.asList(10,19)));
        put(19, new ArrayList<>(Arrays.asList(16,18,20,22)));
        put(20, new ArrayList<>(Arrays.asList(13,19)));
        put(21, new ArrayList<>(Arrays.asList(9,22)));
        put(22, new ArrayList<>(Arrays.asList(19,21,23)));
        put(23, new ArrayList<>(Arrays.asList(22,14)));

    }};

    public Map<Integer,ArrayList<Action>> calculateLegalMove(Actor actor, ArrayList<Node> nodeList){

        return null;
    }

}
