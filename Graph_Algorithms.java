import java.util.*;
import java.io.FileReader;
import java.math.BigDecimal;

public class Graph_Algorithms{
	
	private Map<Integer,ArrayList<String>> DFS_Forest = new HashMap<>();
	private Map<Integer,ArrayList<String>> tporder_Forest = new HashMap<>();
	private Map<String,ArrayList<String>>  adjList    = new HashMap<>();
	private Map<String,String> adjListweights = new HashMap<>();
	private AdjListGraph UnG;
	private int Flag =0;
	private int tree_num =0;
	
	public AdjListGraph BuildUGraph(ArrayList<String> DFStree_nodes){
		UnG = new AdjListGraph();
		BigDecimal bg2 = new BigDecimal("2");
		for(String node : DFStree_nodes){
			if(node != null && adjList.get(node) != null){
			//continue;
			for(String Edgenode : adjList.get(node)){
				if(!UnG.has_edge(node,Edgenode)){
					UnG.addnewEdge(node,Edgenode);
					UnG.addnewEdge(Edgenode,node);
					String w1 = adjListweights.get(node+" "+Edgenode); 
					String w2 = adjListweights.get(Edgenode+" "+node); 
					BigDecimal w = new BigDecimal("0");
					if(w1 == null && w2 != null){
						w = new BigDecimal(w2);
					}
					else if(w2 == null && w1 != null){
						w = new BigDecimal(w1);
					}
					else if( w1 != null && w2 != null){
						w = 
						((new BigDecimal(w1)).add(new BigDecimal(w2))).divide(bg2);
					}
					UnG.addweights(node,Edgenode,w.toPlainString());	
					UnG.addweights(Edgenode,node,w.toPlainString());	
				}
			}
		}
		}
		return UnG;
	}
		
	private Map<String,String> Parents = new HashMap<>();	
	private int time =0;
	private	int count = 0;
	private ArrayList<String> topological_sort = new ArrayList<String>();
	public void DFS_visit(Map<String,ArrayList<String>> adjList,
			String current_vertex,String RequiredGoal){
		if(current_vertex.equals(RequiredGoal)){
		}
		if(Flag == 1){
			System.out.print(current_vertex+" ");
		}
		if(Flag == 0){
			DFS_Forest.get(count).add(current_vertex);
			int start_t = 0;
			time = time + 1;
			start_t = time;
		}
		if(adjList.containsKey(current_vertex)){
			for (String element : adjList.get(current_vertex)){
				if(!Parents.containsKey(element)){
					Parents.put(element , current_vertex);
					DFS_visit(adjList,element,RequiredGoal);
				}
			}
		}
		if(Flag == 0){
			time = time + 1;
			int End_t = time;
			topological_sort.add(current_vertex+"-"+Parents.get(current_vertex)+"--"+End_t);
			tporder_Forest.get(count).add(current_vertex);
		}
		return;
	}
	public void DFS(Map<String,ArrayList<String>> adjList , String RequiredGoal){
		if(Flag == 1){
			Parents = new HashMap<>(); 
			int len_tp = tporder_Forest.get(tree_num).size()-1;
			for (int i =len_tp;i >= 0;i--){
				String current_vertex = tporder_Forest.get(tree_num).get(i);
				if(!Parents.containsKey(current_vertex)){
					DFS_visit(adjList,current_vertex,RequiredGoal);
					System.out.println();
				}
			}
		}
		if(Flag == 0){
			for (String current_vertex : adjList.keySet()){
				if(!Parents.containsKey(current_vertex)){
					count++;
					add_dfsindex();
					DFS_visit(adjList,current_vertex,RequiredGoal);
				}
			}
			int len_tp = topological_sort.size()-1;
			for(int i = len_tp;i >= 0;i--){
				System.out.println(topological_sort.get(i));
			}
			print_scc();
			System.out.println("All spanning trees");
			print_MST();
			System.out.println("All Shortest Paths");
			print_Shortest_Paths();
		}
        }
	public void print_scc(){
		for(int i = 0;i<DFS_Forest.size();i++){
			tree_num = i;
			if(DFS_Forest.get(i) != null){
				//System.out.println(DFS_Forest.get(i));
				SCCs_of_tree(DFS_Forest.get(i));
			    }
		}
		return;
	}
	void print_MST(){
		for(int i = 0;i<DFS_Forest.size();i++){
                        tree_num = i;
                        if(DFS_Forest.get(i) != null){
                                //System.out.println(DFS_Forest.get(i));
                                MST_Kruskals(DFS_Forest.get(i));
                            }
                }
	}
	void print_Shortest_Paths(){
		Dijkstra D = new Dijkstra();
		D.set_graph(adjList);
		D.set_graphweights(adjListweights);
		for(int i = 0;i<DFS_Forest.size();i++){
                        tree_num = i;
                        if(DFS_Forest.get(i) != null){
                                System.out.println("Shortest_path Ouput "+i);
                                D.algorithm(DFS_Forest.get(i));
                            }
                }
	}
	Map<String,String> find_parent;
        Map<String,String> MST     ;
        public String find(String element){
                while(!find_parent.get(element).equals(element)){
                        element = find_parent.get(element);
                }
                return element;
        }
        public void Union(String u,String v){
                String uroot = find(u);
                String vroot = find(v);
                find_parent.put(uroot,vroot);
        }
	public void MST_Kruskals(ArrayList<String> DFStree_nodes){
		find_parent  = new HashMap<String,String>();
		MST          = new HashMap<String,String>();
		AdjListGraph UG = BuildUGraph(DFStree_nodes);	
		Map<String,String> adjListweights1 = UG.return_graphWeights(); 
                ArrayList<String> edges   = new ArrayList<String>(adjListweights1.keySet());
                ArrayList<String> weights = new ArrayList<String>(adjListweights1.values());
                Sorting S = new Sorting();
		AdjListGraph G = new AdjListGraph();
		String root_node = null;
                ArrayList<ArrayList<String>> rt = S.quickSort(
                                                weights,edges,0,edges.size()-1);
                edges   = rt.get(1);
                weights = rt.get(0);	
		Map<String,ArrayList<String>>  adjList1    = UG.return_graph();
                for(String Key : adjList1.keySet()){
                        find_parent.put(Key,Key);
                }
                int edge_count = 0;
                int vertices = adjList1.size();
                while(edge_count < (vertices-1)){
                        String edge  = edges.get(edge_count);
                        String weight  = weights.get(edge_count);
                        String[] val = edge.split(" ");
                        String src_node  = val[0];
			//System.out.println(edge);
                        String dest_node = val[1];
                        String uroot = find(src_node);
                        String vroot = find(dest_node);
			if(edge_count == 0){
				root_node = vroot;
			}
                        if(!uroot.equals(vroot)){
                                MST.put(edge,weight);
				G.addnewEdge(val[0],val[1]);
				G.addweights(val[0],val[1],weight);
				G.addnewEdge(val[1],val[0]);
				G.addweights(val[1],val[0],weight);
                                Union(uroot,vroot);
                        } 
			edge_count++;
		}
		System.out.println("MST of Tree "+tree_num);
		Display_MST(UG,G,root_node);
        }
	private Map<String,Boolean> visited = new HashMap<>();
	public void Display_MST(AdjListGraph UG,AdjListGraph G,String current_vertex){
		  
		Map<String,ArrayList<String>>  adjList1    = G.return_graph();
		if(current_vertex == null){
			return;
		}
		if(!adjList1.containsKey(current_vertex)){
			return;
		}
		for (String element : adjList1.get(current_vertex)){
		     if(!(visited.containsKey(element+current_vertex) || 
					visited.containsKey(current_vertex+element))){
				visited.put(element+current_vertex,true);
				System.out.println(element +"--"+
							current_vertex+"--- "+
							G.return_weight(current_vertex,element));
				Display_MST(UG,G,element);
			}
		}
                       
        }
			
	public void set_graph(Map<String,ArrayList<String>> newadjList){
		adjList = newadjList;		
		
	}
	public void set_graphweights(Map<String,String> newadjListWeights){

		adjListweights = newadjListWeights;

        }
	public void add_dfsindex(){
		
                ArrayList<String> nodes = new ArrayList<String>();
                ArrayList<String> nodes1 = new ArrayList<String>();
                DFS_Forest.put(count,nodes);
		tporder_Forest.put(count,nodes1);
        
	}
	public void SCCs_of_tree(ArrayList<String> nodes){
		Map<String,ArrayList<String>>  tree_adjList    = new HashMap<>();
		AdjListGraph G = new AdjListGraph();	
		for(String node : nodes){
			tree_adjList.put(node , adjList.get(node));
		}
		//Creating transpose of the graph
		for(String key :tree_adjList.keySet()){
			if(tree_adjList.get(key) == null){
				//G.addnewEdge(node,key);
				continue;
			}
			for(String node : tree_adjList.get(key)){
				G.addnewEdge(node,key);
			}
		}
		Flag = 1;
		//Calling DFS on the transposed graph
		System.out.println("SCC's of the tree "+tree_num);
		DFS(G.return_graph()," ");
		
	}
}
