import java.util.*;
import java.util.Map.Entry;

public class Graph {
	int arr[][];
	int graphSize;
	private final int max = (int) Math.pow(2,30);
	//TODO? Collection to map Document to index of vertex 
	// You can change it
	HashMap<String,Integer> name2Int;
	@SuppressWarnings("unchecked")
	//TODO? Collection to map index of vertex to Document
	// You can change it
	Entry<String, Document>[] arrDoc;
	
	// The argument type depend on a selected collection in the Main class
	public Graph(SortedMap<String,Document> internet){
		if (internet!=null){
			int size=internet.size();
			arr=new int[size][size];
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
							arr[i][j] = max;
					} else
						arr[i][i] = 0;
				}
			}
		}
	}
	
	public String bfs(String start) {
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
					if (arr[i][j]<max && !ifVisited[j]){
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
			if (arr[index][j]<max && !ifVisited[j])
				retStr += dfsRecursive(j, ifVisited);
		}
		return retStr;
	}

	public int connectedComponents() {
		DisjointSetForest forest = new DisjointSetForest(graphSize);
		for (int i=0; i<graphSize; i++){
			forest.makeSet(i);
		}
		for (int i=0; i<graphSize; i++){
			for (int j=0; j<graphSize; j++){
				if (arr[i][j]<max && forest.findSet(i)!=forest.findSet(j))
					forest.union(i,j);
			}
		}
		return forest.countSets();
	}
	
	public String DijkstraSSSP(String startVertexStr) {
		if (name2Int.containsKey(startVertexStr)) {
			String retStr="";

			DisjointSetDataStructure Q = new DisjointSetForest(graphSize);

			int[] d = new int[graphSize];
			int[] p = new int[graphSize];
			int t;

			int startIndex = name2Int.get(startVertexStr);

			for (int i = 0; i < graphSize; i++) {
				Q.makeSet(i);
				if ((t = arr[startIndex][i]) < max) {
					if (startIndex == i) {
						d[i] = 0;
						p[i] = -1;
					} else {
						d[i] = t;
						p[i] = startIndex;
					}
				} else {
					d[i] = max;
					p[i] = -1;
				}
			}

			while (Q.countSets() != 1) {
				int minIndex = -1;
				int minIndexNotFound = -1;
				int min = max;
				for (int i = 0; i < graphSize; i++) {
					if (Q.findSet(startIndex)!=Q.findSet(i) && d[i] < min){
						min = d[i];
						minIndex = i;
					} else {
						if (minIndexNotFound<0 && Q.findSet(startIndex)!=Q.findSet(i))
							minIndexNotFound = i;
					}
				}

				if (minIndex>=0){
					Q.union(startIndex, minIndex);

					for (int i = 0; i < graphSize; i++) {
						if (Q.findSet(minIndex) != Q.findSet(i) && (t = arr[minIndex][i]) < max) {
							if (d[i] > d[minIndex] + t){
								d[i] = d[minIndex] + t;
								p[i] = minIndex;
							}
						}
					}
				} else
					Q.union(startIndex, minIndexNotFound);
			}

			for (int i = 0; i < graphSize; i++) {
				if (i != startIndex) {
					if (p[i] < 0)
						retStr += "no path to " + arrDoc[i].getKey() + "\n";
					else {
						String s = pathPrint(p, i);
						retStr += s.substring(0, s.length()-2) + "=" + d[i] + "\n";
					}
				} else
					retStr += startVertexStr + "=0" + "\n";
			}
			return retStr;
		}
		return null;
	}

	private String pathPrint (int[] p, int j){
		if (p[j]<0)
			return arrDoc[j].getKey() + "->";
		else {
			return pathPrint(p, p[j]) + arrDoc[j].getKey() + "->";
		}
	}
}
