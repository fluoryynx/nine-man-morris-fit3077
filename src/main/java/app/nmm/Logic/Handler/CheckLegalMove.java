package app.nmm.Logic.Handler;

import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Action.MoveTokenAction;
import app.nmm.Logic.Action.PutTokenAction;
import app.nmm.Logic.Action.RemoveTokenAction;
import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Location.Node;

import java.lang.reflect.Array;
import java.util.*;

public class CheckLegalMove {

    // a hashmap that stores adjacent position of every node
    private final Map<Integer, ArrayList<Integer>> adjacentPosition  = new HashMap<Integer, ArrayList<Integer>>() {{
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
    private Map<Integer,ArrayList<Action>> currentActions;

    /**
     * this method will be use then player has no tokens left in hand
     * check for positions that tokens on each given nodes can be moved to and add the movetokenaciton into the list
     * @param actor
     * @param nodeList
     * @return hashmap of move actions then can be perform on a given node
     */
    public Map<Integer,ArrayList<Action>> calculateLegalMove(Actor actor, ArrayList<Node> nodeList){

        Map<Integer,ArrayList<Action>> legalMoves= new HashMap<Integer, ArrayList<Action>>();

        for (int i = 0; i < nodeList.size(); i++) {
            //if current node has a token that the current player owns, check adjacent nodes
            if (nodeList.get(i).getToken() != null && nodeList.get(i).getToken().getColour()==actor.getTokenColour()){

                ArrayList<Integer> adjacentPositionOfNode=adjacentPosition.get(i); // get adjacent nodes of that node

                // process which adjacent node can the token on that given node be moved to
                ArrayList<Action> listOfActions = nodeList.get(i).allowableAction(nodeList,adjacentPositionOfNode, actor.getTokenColour()).getKey();

                legalMoves.put(i,listOfActions);

            }
        }
        currentActions = legalMoves;
        return legalMoves;
    }

    /**
     * this method will be used when both players are in PUT_TOKEN phase,
     * ends when both players have placed 9 tokens on the board(tokens on hand ==0.)
     * @param nodeList
     * @return  an array list of put token actions then can be perform on a given node
     */
    public ArrayList<Action> calculateLegalPut(ArrayList<Node> nodeList){

        ArrayList<Action> legalMoves= new ArrayList<Action>();

        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).getToken() == null){ // if the node does not contain a token
                legalMoves.add(new PutTokenAction(i)); // add a put token action on that node
            }
        }
        return legalMoves;
    }


    public ArrayList<Action> calculateLegalRemove(Actor actor, ArrayList<Node> nodeList){
        ArrayList<Action> legalRemoves= new ArrayList<Action>();

        for (int i = 0; i < nodeList.size(); i++) {

            if (nodeList.get(i).getToken() != null){

                if (nodeList.get(i).getToken().getColour() != actor.getTokenColour() && nodeList.get(i).getToken().getIsMill() == false){ // if the token is not part of a mill
                    legalRemoves.add(new RemoveTokenAction(i));

                }

            }

        }

        return legalRemoves;
    }


    /**
     * this method is used when player is left with 3 tokens on the board
     * @param actor
     * @param nodeList
     * @return
     */
    public Map<Integer,ArrayList<Action>> calculateLegalFly(Actor actor, ArrayList<Node> nodeList){

        Map<Integer,ArrayList<Action>> output = new HashMap<>();
        ArrayList<Integer> emptyNodes = new ArrayList<>(); //Stores all nodes that do not have a token
        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).getToken() == null){
                emptyNodes.add(i);
            }
        }
        // If there is a node that has a current player's token, allow it to be moved to any empty nodes
        for (int i=0; i< nodeList.size(); i++){

            if (nodeList.get(i).getToken() != null && nodeList.get(i).getToken().getColour() == actor.getTokenColour()){

                ArrayList<Action> legalMoves= new ArrayList<Action>();

                for (int j =0; j < emptyNodes.size(); j++){
                    legalMoves.add(new MoveTokenAction(i, emptyNodes.get(j)));
                }
                output.put(i,legalMoves);


            }
        }
        currentActions = output;
        return output;
    }

    public Map<Integer, ArrayList<Action>> getCurrentActions() {
        return currentActions;
    }
}
