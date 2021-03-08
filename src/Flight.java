import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Flight {

	public static void main(String[] args) {
		Node DUB = new Node("DUB");
		Node CDG = new Node("CDG");
		Node ORD = new Node("ORD");
		Node LHR = new Node("LHR"); 
		Node NYC = new Node("NYC");
		Node BOS = new Node("BOS");
		Node BKK = new Node("BKK");
		Node LAX = new Node("LAX"); 
		Node LAS = new Node("LAS");
		Node SYD = new Node("SYD");


		DUB.addDestination(LHR, 1);
		DUB.addDestination(CDG, 2);
		DUB.addDestination(ORD, 6);
		
		
		CDG.addDestination(BOS, 6);
		CDG.addDestination(BKK, 9);
		
		ORD.addDestination(LAS, 2);
		
		LHR.addDestination(NYC, 5);
		LHR.addDestination(BKK, 9);
		
		NYC.addDestination(LAS, 3);
		
		BOS.addDestination(LAX, 4);
		
		BKK.addDestination(SYD, 11);
		
		LAX.addDestination(LAS, 2);
		LAX.addDestination(SYD, 13);
		
		LAS.addDestination(SYD, 14);
		
		Graph graph = new Graph();

		graph.addNode(DUB);
		graph.addNode(CDG);
		graph.addNode(ORD);
		graph.addNode(LHR);
		graph.addNode(NYC);
		graph.addNode(BOS);
		graph.addNode(BKK);
		graph.addNode(LAX);
		graph.addNode(LAS);
		graph.addNode(SYD);
		graph = Flight.calculateShortestPathFromSource(graph, DUB);
		for( Node node : graph.getNodes()){
			if(node.getName().equalsIgnoreCase("SYD")) {
				Node previousNode = null;
				for( Node childNode :node.getShortestPath()) {
					if(childNode.getDistance() == 0) {
						previousNode = childNode;
						continue;
					}
					System.out.println(previousNode.getName()+"-->"+childNode.getName()+" ( " + previousNode.getAdjacentNodes().get(childNode)+" ) ");
					previousNode = childNode;
				}
				System.out.println(previousNode.getName()+"-->"+node.getName()+" ( " + previousNode.getAdjacentNodes().get(node)+" ) ");
				System.out.println("Time : " + node.getDistance());
			}
			
		}
			

	}
	
	public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
	    source.setDistance(0);

	    Set<Node> settledNodes = new HashSet<>();
	    Set<Node> unsettledNodes = new HashSet<>();

	    unsettledNodes.add(source);

	    while (unsettledNodes.size() != 0) {
	        Node currentNode = getLowestDistanceNode(unsettledNodes);
	        unsettledNodes.remove(currentNode);
	        for (Entry<Node, Integer> adjacencyPair:  currentNode.getAdjacentNodes().entrySet()) {
	            Node adjacentNode = adjacencyPair.getKey();
	            Integer edgeWeight = adjacencyPair.getValue();
	            if (!settledNodes.contains(adjacentNode)) {
	                CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
	                unsettledNodes.add(adjacentNode);
	            }
	        }
	        settledNodes.add(currentNode);
	    }
	    return graph;
	}
	private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
	    Node lowestDistanceNode = null;
	    int lowestDistance = Integer.MAX_VALUE;
	    for (Node node: unsettledNodes) {
	        int nodeDistance = node.getDistance();
	        if (nodeDistance < lowestDistance) {
	            lowestDistance = nodeDistance;
	            lowestDistanceNode = node;
	        }
	    }
	    return lowestDistanceNode;
	}
	private static void CalculateMinimumDistance(Node evaluationNode,
			  Integer edgeWeigh, Node sourceNode) {
			    Integer sourceDistance = sourceNode.getDistance();
			    if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
			        evaluationNode.setDistance(sourceDistance + edgeWeigh);
			        LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			        shortestPath.add(sourceNode);
			        evaluationNode.setShortestPath(shortestPath);
			    }
			}

}

class Graph {

    private Set<Node> nodes = new HashSet<>();
    
    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

	public Set<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}

   
}

class Node {
    
    private String name;
    
    private List<Node> shortestPath = new LinkedList<>();
    
    private Integer distance = Integer.MAX_VALUE;
    
    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }
 
    public Node(String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Node> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(List<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Map<Node, Integer> getAdjacentNodes() {
		return adjacentNodes;
	}

	public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
		this.adjacentNodes = adjacentNodes;
	}
    
    
}