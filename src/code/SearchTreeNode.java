package code;

public class SearchTreeNode implements Comparable<SearchTreeNode> {
    State state;
    SearchTreeNode parentNode;
    String operator;
    int depth;
    int pathCost;
    int heuristic;
    public SearchTreeNode(State state, SearchTreeNode parentNode, String operator, int depth,  int pathCost, int heuristic){
        this.state = state;
        this.parentNode = parentNode;
        this.operator = operator;
        this.depth = depth;
        this.pathCost = pathCost;
        this.heuristic = heuristic;
    }
    public String toString(){
        return "State: " + state.toString() + " Operator: " + operator + " Depth: " + depth + " PathCost: " + pathCost;
    }
    @Override
    public int compareTo(SearchTreeNode o) {
        // TODO Auto-generated method stub
        return o.pathCost;
    }

}
