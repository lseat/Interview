import java.util.ArrayList;
import java.util.HashSet;


public class Node {
	private HashSet<String> data;
	private ArrayList<Node> neighbour;
	private Node preNode;
	
	public Node (HashSet<String> h){
		data = new HashSet<String>(h);
		neighbour = new ArrayList<Node>();
		preNode = null;
	}
	
	public HashSet<String> getData(){
		return data;
	}
	
	public ArrayList<Node> getNeighbour(){
		return neighbour;
	}
	
	public Node getPre(){
		return preNode;
	}
	
	public void setPre(Node n){
		preNode = n;
	}
	
	public String toString(){
		return "data" + data;
	}
}
