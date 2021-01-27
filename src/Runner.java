import java.util.ArrayList;

public class Runner {
	public static void main(String[] args) {
		Map map = new Map(120, 10, new Node(0, -1, 0, 10, 4), new Node(1, 11, 10, 110, 1));
		
		map.printMap();
		
		AStarAlgorithm aStar = new AStarAlgorithm(map.startNode, map.goalNode, map);
		
		for (int i=1; i<=60; i++) {	//Drawing a horizontal line of obstacles
			map.setObstacle(1, 30, i, NodeUsage.OBSTACLE);
			map.setObstacle(2, 30, i, NodeUsage.OBSTACLE);
		}
		map.setObstacle(1, 30, 0, NodeUsage.OBSTACLE);
		
		ArrayList<Node> path = aStar.generatePath();
		
		//Printing out the resulting path
		boolean inPath = false;
		//Looping through the whole map from (-xSize, +ySize) to (+xSize, -ySize)
		for (int row=-(aStar.map.grids-1); row>aStar.map.grids-1; row--) {
			for (int col=aStar.map.grids-1; col>-(aStar.map.grids-1); col--) {
				
				for (int n=0; n < path.size(); n++) {	//Looking through the final path
					if (aStar.map.getNode(row, col).equals(path.get(n))) {
						System.out.print("X");
						inPath = true;
					}
				}
				if (!inPath) {
					System.out.print("O");
				}
				
			}
			System.out.println("\n");
		}
		
		System.out.println("\nAlgorithm Complete");
	}
}
