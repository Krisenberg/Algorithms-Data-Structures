import java.util.*;
import java.util.Map.Entry;

public class Graph {
	int arr[][];
	int graphSize;
	//TODO? Collection to map Document to index of vertex 
	// You can change it
	HashMap<String,Integer> name2Int;
	@SuppressWarnings("unchecked")
	//TODO? Collection to map index of vertex to Document
	// You can change it
	Entry<String, Document>[] arrDoc;
	
	// The argument type depends on a selected collection in the Main class
	public Graph(SortedMap<String,Document> internet){
		if (internet!=null){
			int size=internet.size();
			arr=new int[size][size];
			//DONE
			graphSize = size;
			arrDoc = (Map.Entry<String, Document>[])new Map.Entry[size];
			name2Int = new HashMap<>();
			int index = 0;
			for (Map.Entry<String, Document> documentEntry : internet.entrySet()){
				arrDoc[index] = documentEntry;
				name2Int.put(documentEntry.getKey(), index);
				index++;
			}
			for (int i=0; i<size; i++){
				for (int j=0; j<size; j++) {
					if (i != j) {
						if (arrDoc[i].getValue().link.containsKey(arrDoc[j].getKey()))
							arr[i][j] = arrDoc[i].getValue().link.get(arrDoc[j].getKey()).weight;
						else
							arr[i][j] = -1;
					} else
						arr[i][i] = 0;
				}
			}
		}
	}
	
	public String bfs(String start) {
		//DONE
		if (name2Int.containsKey(start)){
			String retStr = "";

			boolean[] ifVisited = new boolean[graphSize];
			for (int i=0; i<graphSize; i++){
				ifVisited[i] = false;
			}

			LinkedList<Integer> queue = new LinkedList<>();

			queue.add(name2Int.get(start));
			ifVisited[name2Int.get(start)] = true;
			int i;

			while (!queue.isEmpty()){
				retStr += (arrDoc[(i=queue.remove())].getKey() + ", ");

				for (int j=0; j<graphSize; j++){
					if (arr[i][j]>0 && !ifVisited[j]){
						queue.add(j);
						ifVisited[j] = true;
					}
				}
			}
			return retStr.substring(0,retStr.length()-2);
		}
		return null;
	}
	
	public String dfs(String start) {
		//DONE
		if (name2Int.containsKey(start)){
			String retStr = "";

			boolean[] ifVisited = new boolean[graphSize];
			for (int i=0; i<graphSize; i++){
				ifVisited[i] = false;
			}

			retStr += dfsRecursive(name2Int.get(start), ifVisited);
			return retStr.substring(0,retStr.length()-2);
		}
		return null;
	}

	private String dfsRecursive(int index, boolean[] ifVisited){
		String retStr = arrDoc[index].getKey() + ", ";
		ifVisited[index] = true;
		for (int j=0; j<graphSize; j++){
			if (arr[index][j]>0 && !ifVisited[j])
				retStr += dfsRecursive(j, ifVisited);
		}
		return retStr;
	}

	public int connectedComponents() {
		//DONE
		DisjointSetForest forest = new DisjointSetForest(graphSize);
		for (int i=0; i<graphSize; i++){
			forest.makeSet(i);
		}
		for (int i=0; i<graphSize; i++){
			for (int j=0; j<graphSize; j++){
				if (arr[i][j]>0 && forest.findSet(i)!=forest.findSet(j))
					forest.union(i,j);
			}
		}
		return forest.countSets();
	}
}
