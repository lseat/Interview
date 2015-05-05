import java.util.*;

// This is basic to construct a graph and to find the root.
// We first find all the possible arrangements of people in both sides and we construct these as nodes
// Then we connect all the nodes which is convertible by cross the river once.
// Then all we need to do is to find the shortest path between node:
// one is which represent all nodes in one side
// the other is which represent all nodes in another side.
// This is guaranteed to find the shortest path
// At least output one of the possible shortest path.
public class Solution {
	
	public static boolean isPossible (HashSet<String> h){
		if (h.contains("father") && (h.contains("daughter1") || h.contains("daughter2")) && !h.contains("mother"))
			return false;
		if (!h.contains("father") && (h.contains("son1") || h.contains("son2")) && h.contains("mother"))
			return false;
		if (h.contains("prison")){
			if (h.contains("guard"))
				return true;
			else if (h.size() == 1)
				return true;
			else
				return false;
		}
		return true;
	}
	
	public HashSet<HashSet<String>> help(HashSet<String> h){
		HashSet<HashSet<String>> result = new HashSet<HashSet<String>>();
		if (h.size() == 0)
			return result;
		String s = "";
		for (String str: h){
			s = str;
			break;
		}
		
		result.add(new HashSet<String> (h));
		HashSet<String> a = new HashSet<String>();
		a.add(s);
		result.add(a);
		h.remove(s);
		HashSet<HashSet<String>> temp = help(h);
		
		for (HashSet<String> hs:temp){
			HashSet<String> hs2 = new HashSet<String> (hs);
			hs2.add(s);
			
			result.add(hs2);
			
			HashSet<String> all2 = new HashSet<String> (h);
			all2.removeAll(hs);
			all2.add(s);
			
			result.add(hs);		
		}
		
		return result;
	}
	
	public HashSet<Node> generateNode(){
		HashSet<Node> r = new HashSet<Node>();
		HashSet<String> all = new HashSet<String>();
		all.add("father");
		all.add("mother");
		all.add("guard");
		all.add("son1");
		all.add("son2");
		all.add("daughter1");
		all.add("daughter2");
		all.add("prison");
		HashSet<HashSet<String>> result = help(new HashSet<String>(all));
		result.add(new HashSet<String>());
		for (HashSet<String> temp: result){
			HashSet<String> all2 = new HashSet<String>(all);
			all2.removeAll(temp);
			if (isPossible(temp) && isPossible(all2)){
				r.add(new Node(temp));
			}
		}
		return r;
	}
	
	public ArrayList<Node> createGraph (HashSet<Node> h){
		ArrayList<Node> al = new ArrayList<Node>();
		for (Node i:h){
			al.add(i);
		}
		for (Node i:h){
			Node temp = new Node(new HashSet<String>(i.getData()));
			al.add(temp);
		}
		return al;
	}
	
	public boolean isNeighbour (Node a, Node b){
		HashSet<String> as = a.getData();
		HashSet<String> bs = b.getData();
		if (as.containsAll(bs) && as.size() - bs.size() <= 2){
			HashSet<String> temp = new HashSet<String>(as);
			temp.removeAll(bs);
			if (temp.contains("father") || temp.contains("mother") || temp.contains("guard")){
				if (temp.contains("father") && (temp.contains("daughter1") || temp.contains("daughter2")))
					return false;
				else if (temp.contains("mother") && (temp.contains("son1") || temp.contains("son2")))
					return false;
				else
					return true;
			}
			else
				return false;
		}
		return false;
	}
	
	public void createEdge (ArrayList<Node> al){
		for (int i = 0; i < al.size() / 2; i++){
			for (int j = al.size() /2; j < al.size(); j++){
				if (isNeighbour(al.get(i), al.get(j))){
					al.get(i).getNeighbour().add(al.get(j));
					al.get(j).getNeighbour().add(al.get(i));
				}
			}
		}
	}
	
	public ArrayList<Node> search (Node i, Node j){
		HashSet<Node> visited = new HashSet<Node>();
		HashSet<Node> frontier = new HashSet<Node>();
		frontier.add(i);
		while (!visited.contains(j) && !frontier.isEmpty()){
			visited.addAll(frontier);
			HashSet<Node> newfrontier = new HashSet<Node>();
			for (Node temp: frontier){
				for (Node temp2: temp.getNeighbour()){
					if (!visited.contains(temp2)){
						temp2.setPre(temp);
						newfrontier.add(temp2);
					}
				}
			}
			frontier = newfrontier;
		}
		ArrayList<Node> al = new ArrayList<Node>();
		Node n = j;
		while (n != i){
			al.add(n);
			n = n.getPre();
		}
		al.add(i);
		ArrayList<Node> r = new ArrayList<Node>();
		for (int k = 0; k < al.size(); k++){
			r.add(al.get(al.size() - k - 1));
		}
		return al;
		
	}
	
	
	public static void main(String[] args){
		Solution a = new Solution();
		ArrayList<Node> al = a.createGraph(a.generateNode());
		a.createEdge(al);
		Node start = null;
		Node end = null;
		for (int i = 0; i < al.size()/2; i++){
			if (al.get(i).getData().size() == 8)
				start = al.get(i);
		}
		for (int i = al.size()/2; i < al.size(); i++){
			if (al.get(i).getData().size() == 0)
				end = al.get(i);
		}
		ArrayList<Node> root = a.search(start, end);
		for (int i = 0; i < root.size() - 1; i++){
			HashSet<String> one = new HashSet<String>(root.get(i).getData());
			HashSet<String> other = new HashSet<String>(root.get(i + 1).getData());
			HashSet<String> diff = null;
			if (one.size() > other.size()){
				one.removeAll(other);
				diff = new HashSet<String>(one);
			}
			else {
				other.removeAll(one);
				diff = new HashSet<String>(other);
			}
			System.out.println ("No." + (i + 1) + diff + "move");
		}
	}
	
	
}
