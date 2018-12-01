import java.util.*; 
import java.lang.*; 
import java.io.*; 
import java.math.BigDecimal;


class Dijkstra 
{ 
	private Map<String,ArrayList<String>>  adjList    = new HashMap<>();
	private Map<String,String>             adjListweights = new HashMap<>();
	Map<String,String>  Distances = new HashMap<>();
	Map<String,Boolean>  Short_Set = new HashMap<>();
	String val = String.valueOf(Long.MAX_VALUE);
	
	public String Minimum_dist(ArrayList<String> DFStree_nodes){
		String index= " ";
		BigDecimal min = new BigDecimal(val);
		for(String node : DFStree_nodes){
			BigDecimal v = new BigDecimal(Distances.get(node));
			int T = v.compareTo(min);
			if(Short_Set.get(node) == false &&  (T == -1 || T == 0) && node != null){
				min = v;
				index = node;
			}	
		}
		return index;
	}
	public void algorithm(ArrayList<String> DFStree_nodes)
	{
		Distances = new HashMap<>();
		Short_Set = new HashMap<>();
		int count =0;
		String Vertex = DFStree_nodes.get(0);
		Distances.put(Vertex,"0");
		for(String node : DFStree_nodes){
			Distances.put(node,val);
			Short_Set.put(node,false);
		}
		for(String node1 : DFStree_nodes){
			String node = Minimum_dist(DFStree_nodes);
			Short_Set.put(node,true);
			if(adjList.get(node) != null){
			for(String element:adjList.get(node)){
				if(DFStree_nodes.contains(element)){
				BigDecimal u = new BigDecimal(Distances.get(node));
				BigDecimal uv = new BigDecimal(adjListweights.get(node+" "+element));
				BigDecimal v = new BigDecimal(Distances.get(element));
				if(!Short_Set.get(element) && !Distances.get(node).equals(val) &&
				     (u.add(uv)).compareTo(v) == -1){
					Distances.put(element,(u.add(uv)).toPlainString());
				}
			}	
			}
			}
		}
		Map<String,Boolean> visited = new HashMap<>();
		Display_output(visited,Vertex);
	}
	public void Display_output(Map<String,Boolean> visited,String Vertex){
			visited.put(Vertex , true);
			if(adjList.get(Vertex) == null){
				return;
			}
			for(String element : adjList.get(Vertex)){
				if(!visited.containsKey(element)){
					System.out.println(element+"--"+
						Vertex+"--"+adjListweights.get(Vertex+" "+element));
					Display_output(visited,element);
				}
			}
	}
	public void set_graph(Map<String,ArrayList<String>> newadjList){
                adjList = newadjList;
        }
        public void set_graphweights(Map<String,String> newadjListWeights){
                adjListweights = newadjListWeights;
        }
} 
