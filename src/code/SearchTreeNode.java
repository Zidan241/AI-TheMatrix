package code;
public class SearchTreeNode {
    State state;
    SearchTreeNode parentNode;
    String operator;
    int depth;
    int pathCost;
    public SearchTreeNode(State state, SearchTreeNode parentNode, String operator, int depth,  int pathCost){
        this.state = state;
        this.parentNode = parentNode;
        this.operator = operator;
        this.depth = depth;
        this.pathCost = pathCost;
    }
    public String toString(){
        return "State: " + state.toString() + " Operator: " + operator + " Depth: " + depth + " PathCost: " + pathCost;
        //return " Operator: " + operator + " Depth: " + depth + " PathCost: " + pathCost;
    }
}
