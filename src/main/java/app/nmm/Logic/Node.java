package app.nmm.Logic;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Token contain;
    private int id;
    private List<Pair<Integer,Integer>> position;

    public Node(int id, List<Pair<Integer,Integer>> position){
        this.id=id;
        this. contain= null;
        this.position= position;
    }

    public Token getToken(){
        return this.contain;
    }

    public int getId(){
        return this.id;
    }

    public List<Pair<Integer,Integer>> getPosition(){
        return this.position;
    }

    public void addToken(Token token){
        this.contain=token;
    }

    public void setId(int newId){
        this.id=newId;
    }

    public Boolean hasToken(){
        return this.contain != null;
    }

    public void removeToken(){
        this.contain=null;
    }

    public List<Action> allowableAction(List<Node> nodeList) {
        List<Action> actionList = new ArrayList<>();
        if (this.contain==null) {
            actionList.add( new PutTokenAction());
        }
        else{
            // for every empty adjacent node
                        // actionList.add( new MoveTokenAction(adjacent node id) )
        }
        return actionList;
    }



}
