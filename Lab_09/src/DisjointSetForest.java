public class DisjointSetForest implements DisjointSetDataStructure {
	
	private class Element{
		int rank;
		int parent;
	}

	Element []arr;
	
	public DisjointSetForest(int size) {
		arr = new Element[size];
	}

	
	@Override
	public void makeSet(int item) {
		arr[item] = new Element();
		arr[item].parent = item;
		arr[item].rank = 0;
	}

	@Override
	public int findSet(int item) {
		if (item!=arr[item].parent)
			arr[item].parent = findSet(arr[item].parent);
		return arr[item].parent;
	}

	@Override
	public boolean union(int itemA, int itemB) {
		return makeUnion(findSet(itemA), findSet(itemB));
	}

	private boolean makeUnion (int parentA, int parentB){
		if (parentA!=parentB){
			if (arr[parentA].rank>arr[parentB].rank){
				arr[parentB].parent = parentA;
			} else {
				arr[parentA].parent = parentB;
				if (arr[parentA].rank==arr[parentB].rank)
					arr[parentB].rank+=1;
			}
			return true;
		}
		return false;
	}

	public int size() {
		int size = 0;
		for (int i=0; i<arr.length; i++){
			if (i == arr[i].parent)
				size++;
		}
		return size;
	}

	
	@Override
	public String toString() {
		String retStr = "Disjoint sets as forest:";
		for (int i=0; i<arr.length; i++){
			retStr += "\n" + i + " -> " + arr[i].parent;
		}
		return retStr;
	}
}
