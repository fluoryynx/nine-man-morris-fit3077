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

    public ArrayList<ArrayList<Action>> allowableAction(List<Node> nodeList, ArrayList<Integer> adjacentList, String tokenColour) {
        ArrayList<Action> moveList = new ArrayList<>();
        ArrayList<Action> removeList = new ArrayList<>();
        ArrayList<ArrayList<Action>> allowableMoves = new ArrayList<>();



        for (int i = 0; i < adjacentList.size(); i++) { // check it's adjacent nodes
            if (nodeList.get(adjacentList.get(i)).contain==null){ // if the adjacent node is empty
                // create and add a move token action towards that empty adjacent node into the list
                moveList.add(new MoveTokenAction(this.id, adjacentList.get(i)));
            }
            // if adjacent token is enemy token and is not part of a mill, consider it removable
            else if ((nodeList.get(adjacentList.get(i)).contain.getColour() != tokenColour) && !nodeList.get(adjacentList.get(i)).contain.getIsMill()){
                removeList.add((new RemoveTokenAction(adjacentList.get(i))));
            }
        }
        allowableMoves.add(moveList);
        allowableMoves.add(removeList);
        return allowableMoves;
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
