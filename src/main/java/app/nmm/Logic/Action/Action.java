package app.nmm.Logic.Action;

public abstract class Action {
    private int nodeId;

    public Action(int nodeId){
        this.nodeId = nodeId;
    }
    public int getNodeId(){
        return this.nodeId;
    }

    public abstract Action newAction();
    public abstract void execute();
}
