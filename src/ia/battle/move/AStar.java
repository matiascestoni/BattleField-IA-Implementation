package ia.battle.move;

import ia.battle.camp.BattleField;
import ia.battle.camp.ConfigurationManager;
import ia.battle.camp.FieldCell;
import ia.battle.camp.actions.Attack;
import ia.exceptions.OutOfMapException;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStar {

	private int[][] map;
	private ArrayList<Node> nodes;
	private ArrayList<Node> closedNodes, openedNodes;
	private Node origin, destination;

	public AStar() {
		
	}
	
	public void discoverMap(){
		int height = ConfigurationManager.getInstance().getMapHeight();
		int width = ConfigurationManager.getInstance().getMapWidth();

		map = new int[width][height];

		for (int x=0; x<width; x++)
			for (int y=0; y<height; y++) {
				
				FieldCell fieldCell;
				try {
					fieldCell = BattleField.getInstance().getFieldCell(x, y);
					switch(fieldCell.getFieldCellType()){
						case BLOCKED:
							map[x][y] = 1;
							break;
						case NORMAL: 
							// if(fieldCell.hasSpecialItem())
							map[x][y] = 0;
							break;
					}		
				} catch (OutOfMapException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	}

	public ArrayList<Node> findPath(int x1, int y1, int x2, int y2) {
		nodes = new ArrayList<Node>();
		closedNodes = new ArrayList<Node>();
		openedNodes = new ArrayList<Node>();

		// A node is added for each passable cell in the map
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0)
					nodes.add(new Node(i, j));
			}

		origin = nodes.get(nodes.indexOf(new Node(x1, y1)));
		destination = nodes.get(nodes.indexOf(new Node(x2, y2)));

		Node currentNode = origin;
		while (!currentNode.equals(destination)) {
			processNode(currentNode);
			currentNode = getMinFValueNode();
		}

		return retrievePath();
	}

	public ArrayList<Node> findPath(FieldCell source, FieldCell target) {
		nodes = new ArrayList<Node>();
		boolean stopProcessing = false;
		closedNodes = new ArrayList<Node>();
		openedNodes = new ArrayList<Node>();

		// A node is added for each passable cell in the map
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0)
					nodes.add(new Node(i, j));
			}

		origin = nodes.get(nodes.indexOf(new Node(source.getX(), source.getY())));
		destination = nodes.get(nodes.indexOf(new Node(target.getX(), target.getY())));

		Node currentNode = origin;
		while (!stopProcessing && !currentNode.equals(destination)) {
			processNode(currentNode);
			
			if (openedNodes.size() > 0)
				currentNode = getMinFValueNode();
			else
				stopProcessing = true;
		}

		return retrievePath();
	}

/*
				
				// No tiene donde avanzar => rompo pared.
				System.out.append("************* ROMPIENDO PARED ************\n");
				
				// Calcula qué pared romper.
				FieldCell wall = wallToBreak(source, target);
				nodes.add(new Node(wall.getX(), wall.getY()));
				Attack at = new Attack(wall);
				
				try {
					FieldCell paredRota = BattleField.getInstance().getFieldCell(wall.getX(), wall.getY());
					
					if (paredRota.getFieldCellType() == paredRota.getFieldCellType().BLOCKED)
						System.out.append("********************** NO SE ROMPIO *********************************************\n");
					else
						System.out.append("****************************************** SE ROMPIO!!! *********************************************\n");
					
				} catch (OutOfMapException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
 */	
	
	public FieldCell getWallToBreak(FieldCell source, FieldCell target) {
		FieldCell wall = null;
		
		int sourceX = source.getX();
		int sourceY = source.getY();
		
		try {
			// Intenta a la izquierda
		    if (map[sourceX-1][sourceY] == 1) {
		    	wall = BattleField.getInstance().getFieldCell(sourceX-1, sourceY);
		    }
		} catch(Exception ex) {
		}		    
		    
		try {
			// Intenta a la derecha
		    if (map[sourceX+1][sourceY] == 1) {
		    	wall = BattleField.getInstance().getFieldCell(sourceX+1, sourceY);
		    }
		} catch(Exception ex) {
		}
	    
		try {
			// Intenta arriba
		    if (map[sourceX][sourceY+1] == 1) {
		    	wall = BattleField.getInstance().getFieldCell(sourceX, sourceY+1);
		    }
		} catch(Exception ex) {
		}		

		try {
			// Intenta abajo
		    if (map[sourceX][sourceY-1] == 1) {
		    	wall = BattleField.getInstance().getFieldCell(sourceX, sourceY-1);
		    }
		} catch(Exception ex) {
		}
		
		return wall;
	}
	
	private ArrayList<Node> retrievePath() {
		ArrayList<Node> path = new ArrayList<Node>();
		Node node = destination;

		try {
			while (!node.equals(origin)) {
				path.add(node);
				node = node.getParent();
			}
	
			Collections.reverse(path);
		} catch (Exception ex) {
			path = null;
		}
		
		return path;
	}

	private void processNode(Node node) {

		ArrayList<Node> adj = getAdjacentNodes(node);

		openedNodes.remove(node);
		closedNodes.add(node);

		for (Node n : adj) {

			if (closedNodes.contains(n))
				continue;

			//Compute the Manhattan distance from node 'n' to destination
			int h = Math.abs(origin.getX() - n.getX());
			h += Math.abs(origin.getY() - n.getY());

			//Compute the distance from origin to node 'n' 
			int g = node.getG();
			if (node.getX() == n.getX() || node.getY() == n.getY())
				g += 10;
			else
				g += 14;

			if (!openedNodes.contains(n)) {

				n.setParent(node);
				n.setH(h);
				n.setG(g);

				openedNodes.add(n);
			} else {

				if (h + g < n.getF()) {

					n.setParent(node);
					n.setH(h);
					n.setG(g);
				}
			}
		}
	}

	private Node getMinFValueNode() {
		Node node = openedNodes.get(0);

		for (Node n : openedNodes)
			if (node.getF() > n.getF())
				node = n;

		return node;
	}

	private ArrayList<Node> getAdjacentNodes(Node node) {
		ArrayList<Node> adjCells = new ArrayList<Node>();

		int x = node.getX();
		int y = node.getY();

		// Izquierda
		if (nodes.indexOf(new Node(x - 1, y)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x - 1, y))));
		
		// Derecha
		if (nodes.indexOf(new Node(x + 1, y)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x + 1, y))));

		// Arriba
		if (nodes.indexOf(new Node(x, y + 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x, y + 1))));

		// Abajo
		if (nodes.indexOf(new Node(x, y - 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x, y - 1))));

		// Diagonal inferior izquierda
		if (nodes.indexOf(new Node(x - 1, y - 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x - 1, y - 1))));

		// Diagonal inferior derecha
		if (nodes.indexOf(new Node(x + 1, y - 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x + 1, y - 1))));

		// Diagonal superior izquierda
		if (nodes.indexOf(new Node(x - 1, y + 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x - 1, y + 1))));		
		
		// Diagonal superior derecha
		if (nodes.indexOf(new Node(x + 1, y + 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x + 1, y + 1))));

		return adjCells;
	}

	@SuppressWarnings("unused")
	private void mergePath(ArrayList<Node> path) {
		for(Node node : path)
			map[node.getX()][node.getY()] = 2;
		
	}
	/**
	 * Retorna la cantidad de FieldCells de acuerdo a la cantidad maxima
	 * de movidas posibles pasadas por argumento
	 * @param nodes
	 * @param max
	 * @return
	 */
	public List<FieldCell> getMaxMoves(ArrayList<Node> nodes, int max){
		ArrayList<FieldCell> maxMoves = new ArrayList<FieldCell>();
		
		// fijarse que max no se mayor que nodes porque va a dar IndexArrayExeption
		max = max > nodes.size() ? nodes.size() : max;
		
		for(int i=0; i<max; i++){
			Node actualNode = nodes.get(i);
			try {
				FieldCell move = BattleField.getInstance().getFieldCell(actualNode.getX(), actualNode.getY());
				maxMoves.add(move);
			} catch (OutOfMapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return maxMoves;
	}
	
	public void printMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++)
				switch (map[i][j]) {
				case 0:
					System.out.print("   ");
					break;
					
				case 1:
					System.out.print("XXX");
					break;
					
				case 2:
					System.out.print(" o ");
					break;
				}
				
			System.out.println();
		}
	}
/*
	public static void main(String[] args) {
		
		MazeGenerator mg = new MazeGenerator((40 - 1) / 4, (40 - 1) / 2);
		
		AStar a = new AStar(mg.getMaze());
		
		System.out.println("The maze to resolve:");
		a.printMap();

		ArrayList<Node> bestPath = a.findPath(1, 1, 35, 37);
		
		a.mergePath(bestPath);
		
		System.out.println();
		System.out.println("The best path:");
		a.printMap();
	}
	*/
}
