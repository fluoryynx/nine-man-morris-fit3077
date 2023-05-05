package app.nmm.Logic.Location;

import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Action.MoveTokenAction;
import app.nmm.Logic.Action.RemoveTokenAction;
import app.nmm.Logic.Token.Token;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Token contain;
    private int id;
    private Pair<Integer,Integer> position;

    /**
     * constructor
     * @param id
     * @param position
     */
    public Node(int id, Pair<Integer,Integer> position){
        this.id=id;
        this.contain= null;
        this.position= position;
    }

    // getters and setters
    public Token getToken(){
        return this.contain;
    }

    public int getId(){
        return this.id;
    }

    public Pair<Integer,Integer> getPosition(){
        return this.position;
    }

    public void addToken(Token token){
        this.contain=token;
    }

    public void setId(int newId){
        this.id=newId;
    }


    /**
     * check if the node has a token on it
     * @return true is the node has a token on it. false otherwise
     */
    public Boolean hasToken(){
        return this.contain != null;
    }

    /**
     * remove a token from the node
     */
    public void removeToken(){
        this.contain=null;
    }

    /**
     *
     * @param nodeList
     * @param adjacentList
     * @param tokenColour
     * @return a list of allowable actions
     */
    public Pair<ArrayList<Action>, ArrayList<Action>> allowableAction(List<Node> nodeList, ArrayList<Integer> adjacentList, String tokenColour) {
        ArrayList<ArrayList<Action>> actionList = new ArrayList<ArrayList<Action>>();
        actionList.add(new ArrayList<Action>()); // non-remove action
        actionList.add(new ArrayList<Action>()); // remove actions


        for (int i = 0; i < adjacentList.size(); i++) { // check it's adjacent nodes
            if (nodeList.get(adjacentList.get(i)).contain==null){ // if the adjacent node is empty
                // create and add a move token action towards that empty adjacent node into the list
                actionList.get(0).add(new MoveTokenAction(this.id, adjacentList.get(i)));
            }
            // if adjacent token is enemy token and is not part of a mill, consider it removable
            if ((nodeList.get(adjacentList.get(i)).contain.getColour() != tokenColour) && !nodeList.get(adjacentList.get(i)).contain.getIsMill()){
                actionList.get(1).add((new RemoveTokenAction(adjacentList.get(i))));
            }
        }
        Pair<ArrayList<Action>, ArrayList<Action>> output = new Pair<>(actionList.get(0),actionList.get(1));

        return output;
    }

    @Override
    public String toString() {
        return "Node{" +
                "contain=" + contain +
                ", id=" + id +
                ", position=" + position +
                '}';
    }
}
