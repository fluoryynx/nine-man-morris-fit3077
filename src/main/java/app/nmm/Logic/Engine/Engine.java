package app.nmm.Logic.Engine;

import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Handler.CheckLegalMove;
import app.nmm.Logic.Handler.CheckMill;
import app.nmm.Logic.Location.Node;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private List<Node> nodeList;
    private CheckMill checkMill;
    private CheckLegalMove checkLegalMove;
    private  List<Actor> playerList;

    public Engine(List<Actor> playerList){
        this.playerList = playerList;
        this.nodeList =  new ArrayList<Node>();

    }
}


//- nodeList: List < Node>
//- checkMill: CheckMill
//- checkLegal: CheckLegalMove
//- playerList: List<Actor>