public class Node {

    public NodeStatus status;

    public double x;
    public double y;

    public int row;
    public int col;
    public int quadrant;

    public NodeUsage usage;
    public double gScore;   //distance from start node
    public double hScore;   //heuristic; distance from end node
    private double fScore;   //sum of f and g scores

    public Node parentNode;

    public Node(double x, double y, int row, int col, int quadrant) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.col = col;
        this.quadrant = quadrant;
        gScore = -1;
        hScore = -1;
        fScore = -1;
        status = NodeStatus.NONE;
        usage = NodeUsage.OPEN;
    }

    public double fScore() {
        return (gScore + hScore);
    }
    
    public boolean equals(Node n) {
    	if (n.row == this.row && n.col == this.col && n.x-this.x < 0.001 && n.y-this.y < 0.001 && n.quadrant == this.quadrant) {
    		return true;
    	}
    	return false;
    }
    
    public String toString() {
    	return "x: " + x + "; y: " + y + "; quad: " + quadrant + "; row: " + row + "; col: " + col + "; status: " + status + "; usage: " + usage;
    }

}
