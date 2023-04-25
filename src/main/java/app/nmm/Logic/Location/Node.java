package app.nmm.Logic.Location;

import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Action.PutTokenAction;
import app.nmm.Logic.MoveTokenAction;
import app.nmm.Logic.Token.Token;
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

    public List<Action> allowableAction(List<Node> nodeList, ArrayList<Integer> adjacentList) {
        List<Action> actionList = new ArrayList<>();
        if (this.contain==null) {
            actionList.add( new PutTokenAction());
        }
        else{
            for (int i = 0; i < adjacentList.size(); i++) {
                if (nodeList.get(adjacentList.get(i)).contain==null){
                    actionList.add(new MoveTokenAction(this.id, adjacentList.get(i)));
                }
            }

        }
        return actionList;
    }



}
