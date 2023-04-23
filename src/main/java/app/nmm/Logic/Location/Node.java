package app.nmm.Logic.Location;

import app.nmm.Logic.Token.Token;
import javafx.util.Pair;

public class Node {

    private Token contain;
    private int id;
    private Pair<Integer,Integer> position;

    public Node(int id,Token contain,Pair<Integer,Integer>position){
        this.id=id;
        this. contain= contain;
        this.position= position;
    }

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

    public Boolean hasToken(){
        return this.contain != null;
    }

    public void removeToken(){
        this.contain=null;
    }



}
