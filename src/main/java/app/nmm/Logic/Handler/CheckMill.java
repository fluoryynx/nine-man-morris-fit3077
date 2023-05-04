package app.nmm.Logic.Handler;

import app.nmm.Logic.Location.Node;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckMill {

    private final Map<Integer, ArrayList<ArrayList<Integer>>> millPosition =  new HashMap<>();


    public ArrayList<ArrayList<Integer>> getMillPosition(Integer id){
        return this.millPosition.get(id);
    }

    public Pair<Boolean, Boolean> checkPossibleMill(ArrayList<Node> nodeList, Integer nodeId) {


        return new Pair<>(true,false);
    }


    }
