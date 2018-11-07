/********************************************
This class implements a graph using adjacency
list.
********************************************/
import java.util.*;
import java.io.FileReader;
import java.math.BigDecimal;

public class AdjListGraph extends Graph{ 

	private Map<String,ArrayList<String>> adjList = new HashMap<>();
	private Map<String,String> adjListweights = new HashMap<>();

	public void implementNewVertex(String vertex){
		ArrayList<String> neighbours = new ArrayList<String>();
		adjList.put(vertex,neighbours);
	}	

	public void implementNewEdge(String u, String v){
		if( u != null && v !=null){
			boolean out = hasVertex(u);
			//String out = adjList.get(u);
			if(out == false){
				addnewVertex(u);
			}
			out = hasVertex(u);
			if(out == false){
				addnewVertex(v);
			}
			if(adjList.get(u).contains(v) == false){
				adjList.get(u).add(v);
			}
		}
	}
	public void addweights(String u,String v,String weights){
		String key = u+v;
		String val = adjListweights.get(key);
		BigDecimal d = new BigDecimal(weights);
		String val1 = null;
		if(val!=null){
			//System.out.println(d.toPlainString());
			BigDecimal d1 = new BigDecimal(val);
			val1 = (d1.add(d)).toPlainString();
			adjListweights.replace(key,val,val1);
			d = d1;
		}
		else{
			val1 = d.toPlainString();
			if(adjListweights.get(key) == null){
				adjListweights.put(key,val1);
			}
			if(!adjListweights.get(key).equals(val1)){
				adjListweights.replace(key,val,val1);
			}

		}

		//System.out.println(d.toPlainString());
		return;
	}
	public ArrayList<String> getNeighborsofVertex(String v){

		ArrayList<String> neighbours = new ArrayList<String>(adjList.get(v));
		return neighbours;
	}
	public boolean hasVertex(String Vertexname) {
		if(adjList.isEmpty()){
			return false;
		}
		return adjList.containsKey(Vertexname);
	}
	public  void DisplayGraph(){

		for (String key : adjList.keySet()) {
			System.out.print(key+"--->");
			for(String element : adjList.get(key)){
				System.out.print(element+"(");
				System.out.print(adjListweights.get(key+element)+"), ");
			}
			System.out.println();
		}

	}
	public void BFS(String vertex){
		Map<String,Integer> Level = new HashMap<>();
		int level = 0;
		ArrayList<String> current =   new ArrayList<String>();
		current.add(vertex);
		if(!Level.containsKey(vertex)){
			Level.put(vertex,level);
			level++;
		}	
		while(!current.isEmpty()){
			ArrayList<String> next = new ArrayList<String>();
			for (String key : current){
				if(adjList.containsKey(key)){
					for(String element:adjList.get(key)){
						if(!Level.containsKey(element)){
							Level.put(element,level);
							next.add(element);
						}
					}}


			}
			current = next;
			level++;
		}
		for (String key : Level.keySet()) {
			System.out.println(key+"--->"+Level.get(key));

		}

	}
	private Map<String,String> Parents = new HashMap<>();	
	private int time =0;
	private ArrayList<String> topological_sort = new ArrayList<String>();
	public void DFS_visit(String current_vertex,String RequiredGoal){
		if(current_vertex.equals(RequiredGoal)){
			return;
		}
		int start_t = 0;
		time = time + 1;
		start_t = time;
		if(adjList.containsKey(current_vertex)){

			for (String element : adjList.get(current_vertex)){
				if(!Parents.containsKey(element)){
					Parents.put(element , current_vertex);
					DFS_visit(element,RequiredGoal);
				}
			}
		}
		time = time + 1;
		int End_t = time;
		topological_sort.add(current_vertex+"-"+End_t);
		return;
	}
	public void DFS(String RequiredGoal){
		for (String current_vertex : adjList.keySet()){
		   if(!Parents.containsKey(current_vertex)){
			DFS_visit(current_vertex,RequiredGoal);
		   }
		}
		int len_tp = topological_sort.size()-1;	
		for(int i = len_tp;i >= 0;i--){
			System.out.println(topological_sort.get(i));
		}
	}
}
