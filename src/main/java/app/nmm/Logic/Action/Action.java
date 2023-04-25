package app.nmm.Logic.Action;

public class Action {
    public Integer getNodeID() {
        return nodeID;
    }

    private Integer nodeID;

    public Action(Integer nodeID){
        this.nodeID = nodeID;
    }

    public void execute(){
        System.out.println("Action is Executed!");

    }
}
