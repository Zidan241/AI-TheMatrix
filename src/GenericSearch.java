import java.util.LinkedList;

public abstract class GenericSearch {
    String[] operators;
    String initialState;
    LinkedList<SearchTreeNode> StateSpace;
    public GenericSearch(String[] operators, String initialState){
        this.operators = operators;
        this.initialState = initialState;
        this.StateSpace = new LinkedList<SearchTreeNode>(); 
    }
    abstract boolean GoalTest(String currentState);
    abstract int PathCost(String[] actions);
}
