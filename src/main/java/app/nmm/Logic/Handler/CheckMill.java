package app.nmm.Logic.Handler;

import app.nmm.Logic.Location.Node;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
//import org.javatuples.Pair;

public class CheckMill {

    private final Map<Integer, ArrayList<ArrayList<Integer>>> millPosition = new HashMap<Integer, ArrayList<ArrayList<Integer>>>() {{
        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(9, 21)))));

        put(1, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(0, 2)),
                new ArrayList<Integer>(Arrays.asList(4, 7)))));

        put(2, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(0, 1)),
                new ArrayList<Integer>(Arrays.asList(14, 23)))));

        put(3, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(4, 5)),
                new ArrayList<Integer>(Arrays.asList(10, 18)))));

        put(4, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 7)),
                new ArrayList<Integer>(Arrays.asList(3, 5)))));

        put(5, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(3, 4)),
                new ArrayList<Integer>(Arrays.asList(13, 20)))));

        put(6, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(7, 8)),
                new ArrayList<Integer>(Arrays.asList(11, 15)))));

        put(7, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(6, 7)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(8, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(6, 7)),
                new ArrayList<Integer>(Arrays.asList(12, 17)))));

        put(9, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(10, 11)),
                new ArrayList<Integer>(Arrays.asList(0, 21)))));

        put(10, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(9, 11)),
                new ArrayList<Integer>(Arrays.asList(3, 18)))));

        put(11, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(9, 10)),
                new ArrayList<Integer>(Arrays.asList(6, 15)))));

        put(12, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(13, 14)),
                new ArrayList<Integer>(Arrays.asList(8, 17)))));

        put(13, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(12, 14)),
                new ArrayList<Integer>(Arrays.asList(5, 20)))));

        put(14, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(12, 13)),
                new ArrayList<Integer>(Arrays.asList(2, 23)))));

        put(15, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(6, 11)),
                new ArrayList<Integer>(Arrays.asList(16, 17)))));

        put(16, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(15, 17)),
                new ArrayList<Integer>(Arrays.asList(19, 22)))));

        put(17, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(15, 16)),
                new ArrayList<Integer>(Arrays.asList(8, 12)))));

        put(18, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(3, 10)),
                new ArrayList<Integer>(Arrays.asList(19, 20)))));

        put(19, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(18, 20)),
                new ArrayList<Integer>(Arrays.asList(16, 22)))));

        put(20, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(18, 19)),
                new ArrayList<Integer>(Arrays.asList(5, 13)))));

        put(21, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(0, 9)),
                new ArrayList<Integer>(Arrays.asList(22, 23)))));

        put(22, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(21, 23)),
                new ArrayList<Integer>(Arrays.asList(16, 19)))));

        put(23, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(21, 22)),
                new ArrayList<Integer>(Arrays.asList(2, 14)))));

    }};

    private ArrayList<ArrayList<Integer>> millNodes;

    public CheckMill(){
        this.millNodes= new ArrayList<ArrayList<Integer>>();
    }

//    public Map<Integer, ArrayList<ArrayList<Integer>>> getMillPosition(){
//        return millPosition;
//    }

    public ArrayList<ArrayList<Integer>> getMillNodes(){
        return this.millNodes;
    }

    /**
     * check whether a mill is form after token is placed on the node
     * @param nodeList list of all nodes on the board
     * @param nodeId the node where player recently placed token on
     * @return true if a mill is formed
     */
    public Pair<Boolean,Boolean> checkPossibleMill(ArrayList<Node> nodeList, Integer nodeId) {

        // get corresponding mill list
        ArrayList<ArrayList<Integer>> nodeToCheck = millPosition.get(nodeId);
        Pair<Boolean,Boolean> result = new Pair<Boolean,Boolean>(false,false);

        for (int i = 0; i < nodeToCheck.size(); i++) {

            Integer millCheck = 0; // if it reaches 2 means mill is formed

            for (int j = 0; j < nodeToCheck.get(i).size(); j++) {

                Integer theNode = nodeToCheck.get(i).get(j);

                if (nodeList.get(theNode).getToken() != null) {

                    // if the node contains player's token
                    if (nodeList.get(theNode).getToken().getColour() == nodeList.get(nodeId).getToken().getColour()) {
                        millCheck += 1;
                    }

                }
                if (millCheck == 2 && i==0){
                    //result.setValue(0,true);
                    result = new Pair<Boolean,Boolean>(true,false);
                    this.millNodes.add(nodeToCheck.get(i));
                }
                else if (millCheck == 2 && i==1){
                    result = new Pair<Boolean,Boolean>(false,true);
                    this.millNodes.add(nodeToCheck.get(i));
                }

                if (millNodes.size()==2){
                    result = new Pair<Boolean,Boolean>(true,true);
                }

            }
        }
        return result;
    }


}
