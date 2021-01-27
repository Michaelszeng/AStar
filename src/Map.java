import java.util.ArrayList;

public class Map {
	
	public int grids;	//Size of 1 quadrant in grid coordinates including the 0,0 axes
	public double gridsPerIn;
    public Node startNode;
    public Node goalNode;
    ArrayList<ArrayList<Node>> quadrant1 = new ArrayList<>();   //domain (grid coordinates): 0-infinity    range: 0-infinity
    ArrayList<ArrayList<Node>> quadrant2 = new ArrayList<>();   //domain (grid coordinates): 1-infinity    range: 0-infinity
    ArrayList<ArrayList<Node>> quadrant3 = new ArrayList<>();   //domain (grid coordinates): 1-infinity    range: 1-infinity
    ArrayList<ArrayList<Node>> quadrant4 = new ArrayList<>();   //domain (grid coordinates): 0-infinity    range: 1-infinity

    public Map(int sizeIn, double gridsPerIn) {
    	this.gridsPerIn = gridsPerIn;
    	
    	int grids = (int) (sizeIn * gridsPerIn);
    	System.out.println("grids: " + grids);
    	for (int i=0; i<grids; i++) {
    		quadrant1.add(new ArrayList<Node>());
    		for (int j=0; j<grids; j++) {
    			quadrant1.get(i).add(new Node(i/gridsPerIn, j/gridsPerIn, i, j, 1));
    		}
    		
    		if (i > 0) {	//Quadrant 2 and 3 domain is 1-infinity
    			quadrant2.add(new ArrayList<Node>());
        		for (int j=0; j<grids; j++) {
        			quadrant2.get(i).add(new Node(-i/gridsPerIn, j/gridsPerIn, i, j, 2));
        		}
        		
        		quadrant3.add(new ArrayList<Node>());
        		for (int j=1; j<grids; j++) {	//Start loop at 1 because Q3's range is from 1-infinity
        			quadrant3.get(i).add(new Node(-i/gridsPerIn, -j/gridsPerIn, i, j, 3));
        		}
    		}
    		
    		quadrant4.add(new ArrayList<Node>());
    		for (int j=1; j<grids; j++) {	//Start loop at 1 because Q4's range is from 1-infinity
    			quadrant4.get(i).add(new Node(i/gridsPerIn, -j/gridsPerIn, i, j, 4));
    		}
    	}
    }
    
    public Map(int sizeIn, double gridsPerIn, Node startNode, Node goalNode) {
    	this.startNode = startNode;
    	this.goalNode = goalNode;
    	this.gridsPerIn = gridsPerIn;
    	
    	this.grids = (int) (sizeIn * gridsPerIn);	//Convert from the mapsize in inches to "grids"
    	System.out.println("grids: " + grids);
    	for (int i=0; i<grids; i++) {
    		quadrant1.add(new ArrayList<Node>());
    		quadrant2.add(new ArrayList<Node>());
    		quadrant3.add(new ArrayList<Node>());
    		quadrant4.add(new ArrayList<Node>());
    		
    		for (int j=0; j<grids; j++) {
    			quadrant1.get(i).add(new Node(i/gridsPerIn, j/gridsPerIn, i, j, 1));
    		}
    		
    		if (i > 0) {	//Quadrant 2 and 3 domain is 1-infinity
        		for (int j=0; j<grids; j++) {
        			quadrant2.get(i).add(new Node(-i/gridsPerIn, j/gridsPerIn, i, j, 2));
        		}
        		
        		for (int j=1; j<grids; j++) {	//Start loop at 1 because Q3's range is from 1-infinity
        			quadrant3.get(i).add(new Node(-i/gridsPerIn, -j/gridsPerIn, i, j, 3));
        		}
    		}
    		
    		for (int j=1; j<grids; j++) {	//Start loop at 1 because Q4's range is from 1-infinity
    			quadrant4.get(i).add(new Node(i/gridsPerIn, -j/gridsPerIn, i, j, 4));
    		}
    	}
    }
    
    public void setObstacle(int q, int i, int j, NodeUsage usage) {
    	if (q == 1) {
    		quadrant1.get(i).get(j).usage = usage;
    	}
    	else if (q == 2) {
    		quadrant2.get(i).get(j).usage = usage;
    	}
    	else if (q == 3) {
    		quadrant3.get(i).get(j).usage = usage;
    	}
    	else if (q == 4) {
    		quadrant4.get(i).get(j).usage = usage;
    	}
    	
    }

    public ArrayList<Node> getNeighbors(Node n) {
        /*
        Function that returns all neighbors of a node. Checks in each quadrant, and ensure the neihgbor is not closed and is traversable.
         */

        ArrayList<Node> neighbors = new ArrayList<>();
        Node check;

        if (n.row > 0) {
            if (n.col > 0) {    //row>0 and col>0
                for (int i=n.row-1; i<n.row+3; i++) {
                    for (int j=n.col-1; j<n.col+3; j++) {
                        if (i==n.row && j==n.col) { //Do nothing; this is the input node
                        }
                        else {
                            check = getNode(n.quadrant, i, j);
                            if (check != startNode && check.usage != NodeUsage.OBSTACLE && check.usage != NodeUsage.SAFETY && check.status != NodeStatus.CLOSED) {
                                neighbors.add(n);
                            }
                        }
                    }
                }
            }
            else {  //row>0 and col<=0
                for (int i=n.row-1; i<n.row+3; i++) {
                    for (int j=n.col; j<n.col+2; j++) {
                        if (i==n.row && j==n.col) { //Do nothing; this is the input node
                        }
                        else {
                            check = getNode(n.quadrant, i, j);
                            if (check != startNode && check.usage != NodeUsage.OBSTACLE && check.usage != NodeUsage.SAFETY && check.status != NodeStatus.CLOSED) {
                                neighbors.add(n);
                            }
                        }
                    }
                    check = getNode(n.quadrant, i, -1);
                    if (check != startNode && check.usage != NodeUsage.OBSTACLE && check.usage != NodeUsage.SAFETY && check.status != NodeStatus.CLOSED) {
                        neighbors.add(n);
                    }
                }
            }
        }
        else {
            if (n.col > 0) {    //row<=0 and col>0
                for (int i=n.row; i<n.row+2; i++) {
                    for (int j=n.col-1; j<n.col+3; j++) {
                        if (i==n.row && j==n.col) { //Do nothing; this is the input node
                        }
                        else {
                            check = getNode(n.quadrant, i, j);
                            if (check != startNode && check.usage != NodeUsage.OBSTACLE && check.usage != NodeUsage.SAFETY && check.status != NodeStatus.CLOSED) {
                                neighbors.add(n);
                            }
                        }
                    }
                }
                check = getNode(n.quadrant, -1, n.col-1);
                if (check != startNode && check.usage != NodeUsage.OBSTACLE && check.usage != NodeUsage.SAFETY && check.status != NodeStatus.CLOSED) {
                    neighbors.add(n);
                }
                check = getNode(n.quadrant, -1, n.col);
                if (check != startNode && check.usage != NodeUsage.OBSTACLE && check.usage != NodeUsage.SAFETY && check.status != NodeStatus.CLOSED) {
                    neighbors.add(n);
                }
                check = getNode(n.quadrant, -1, n.col+1);
                if (check != startNode && check.usage != NodeUsage.OBSTACLE && check.usage != NodeUsage.SAFETY && check.status != NodeStatus.CLOSED) {
                    neighbors.add(n);
                }
            }
            else {  //row<=0 and col<=0
                for (int i=n.row; i<n.row+2; i++) {
                    for (int j=n.col; j<n.col+2; j++) {
                        if (i==n.row && j==n.col) { //Do nothing; this is the input node
                        }
                        else {
                            check = getNode(n.quadrant, i, j);
                            if (check != startNode && check.usage != NodeUsage.OBSTACLE && check.usage != NodeUsage.SAFETY && check.status != NodeStatus.CLOSED) {
                                neighbors.add(n);
                            }
                        }
                    }
                    check = getNode(n.quadrant, i, -1);
                    if (check != startNode && check.usage != NodeUsage.OBSTACLE && check.usage != NodeUsage.SAFETY && check.status != NodeStatus.CLOSED) {
                        neighbors.add(n);
                    }
                }
                check = getNode(n.quadrant, -1, -1);
                if (check != startNode && check.usage != NodeUsage.OBSTACLE && check.usage != NodeUsage.SAFETY && check.status != NodeStatus.CLOSED) {
                    neighbors.add(n);
                }
                check = getNode(n.quadrant, -1, n.col-1);
                if (check != startNode && check.usage != NodeUsage.OBSTACLE && check.usage != NodeUsage.SAFETY && check.status != NodeStatus.CLOSED) {
                    neighbors.add(n);
                }
                check = getNode(n.quadrant, -1, n.col);
                if (check != startNode && check.usage != NodeUsage.OBSTACLE && check.usage != NodeUsage.SAFETY && check.status != NodeStatus.CLOSED) {
                    neighbors.add(n);
                }
            }
        }
        return neighbors;
    }

    public Node getNode(int quadrant, int row, int col) {
        /*
        getNode function if the quadrant is known
         */
        switch (quadrant) {
            case 1:
                return quadrant1.get(Math.abs(row)).get(Math.abs(col));
            case 2:
                return quadrant2.get(Math.abs(row)).get(Math.abs(col));
            case 3:
                return quadrant3.get(Math.abs(row)).get(Math.abs(col));
            case 4:
                return quadrant4.get(Math.abs(row)).get(Math.abs(col));
            default:
                return null;
        }
    }

    public Node getNode(int row, int col) {
        /*
        getNode function if the quadrant is known, and instead we only have cartesian row, column coords
         */
        if (row >= 0) {
            if (col >= 0) {
                return quadrant1.get(Math.abs(row)).get(Math.abs(col));
            }
            else {
                return quadrant4.get(Math.abs(row)).get(Math.abs(col));
            }
        }
        else {
            if (col >= 0) {
                return quadrant2.get(Math.abs(row)).get(Math.abs(col));
            }
            else {
                return quadrant3.get(Math.abs(row)).get(Math.abs(col));
            }
        }
    }
    
    public void printMap() {
    	//Printing out the resulting path
    	
		//Looping through the whole map from (-xSize, +ySize) to (+xSize, -ySize)
		for (int row=-(grids-1); row>grids-1; row--) {
			for (int col=grids-1; col>-(grids-1); col--) {
				if (getNode(row, col).usage == NodeUsage.OBSTACLE || getNode(row, col).usage == NodeUsage.SAFETY) {
					System.out.print("_");
				}
				else if (getNode(row, col).usage == NodeUsage.TARGETPOINT) {
					System.out.print("X");
				}
				else {
					System.out.print("O");
				}
				
			}
			System.out.println("\n");
		}
    }

}
