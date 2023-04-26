package app.nmm.Logic.Action;


import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Location.Node;

import java.util.List;

public abstract class Action {
    private int nodeId;

    /**
     *
     * @param nodeId currentNode
     */
    public Action(int nodeId){
        this.nodeId = nodeId;
    }

    public int getNodeId(){
        return this.nodeId;
    }

    /**
     *
     * @param currentPlayer The player currently taking the turn
     * @param nodeList List of nodes
     */
    public abstract void execute(Actor currentPlayer, List<Node> nodeList);

    @Override
    public String toString() {
        return "Action{" +
                "nodeId=" + nodeId +
                '}';
    }
}
