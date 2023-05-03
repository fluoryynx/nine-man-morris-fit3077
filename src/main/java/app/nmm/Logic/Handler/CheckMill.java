package app.nmm.Logic.Handler;

import app.nmm.Logic.Location.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

        put(0, new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 2)),
                new ArrayList<Integer>(Arrays.asList(3, 4)))));

    }};
    ;

    /**
     * check whether a mill is form after token is placed on the node
     * @param nodeList list of all nodes on the board
     * @param nodeId the node where player recently placed token on
     * @return true if a mill is formed
     */
    public boolean checkPossibleMill(ArrayList<Node> nodeList, Integer nodeId) {

        // get corresponding mill list
        ArrayList<ArrayList<Integer>> nodeToCheck = millPosition.get(nodeId);

        for (int i = 0; i < nodeToCheck.size(); i++) {

            for (int j = 0; i < nodeToCheck.get(i).size(); j++) {

                Integer theNode = nodeToCheck.get(i).get(j);
                Integer millCheck = 0; // if it reaches 2 means mill is formed

                if (nodeList.get(theNode).getToken() != null) {

                    // if the node contains player's token
                    if (nodeList.get(theNode).getToken().getColour() == nodeList.get(nodeId).getToken().getColour()) {
                        millCheck += 1;
                    }

                }
                if (millCheck == 2){
                    return true;
                }
            }
        }
        return false;
    }

}
