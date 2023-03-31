public class DisjointSetLinkedList implements DisjointSetDataStructure {

	private class Element{
		int representant;
		int next;
		int length;
		int last;
	}
	
	private static final int NULL=-1;
	
	Element arr[];
	
	public DisjointSetLinkedList(int size) {
		arr = new Element[size];
	}
	
	@Override
	public void makeSet(int item) {
		arr[item] = new Element();
		arr[item].representant = item;
		arr[item].next = NULL;
		arr[item].length = 1;
		arr[item].last = item;
	}

	@Override
	public int findSet(int item) {
		if (item>=0 && item<arr.length)
			return arr[item].representant;
		return NULL;
	}

	@Override
	public boolean union(int itemA, int itemB) {
		int repA = findSet(itemA);
		int repB = findSet(itemB);
		if (repA!=NULL && repB!=NULL && repA!=repB){
			if (arr[repB].length>arr[repA].length){
				int t = repA;
				repA = repB;
				repB = t;
			}

			int Bsize = arr[repB].length;
			arr[repA].length+=Bsize;

			arr[arr[repA].last].next=repB;
			arr[repA].last=arr[repB].last;
			arr[repB].last=repB;

			int currentIndex = repB;
			arr[repB].representant=repA;

			for (int i=1; i<Bsize; i++){
				currentIndex = arr[currentIndex].next;
				arr[currentIndex].representant=repA;
			}
			return true;
		}
		return false;
	}

	public int size() {
		int size = 0;
		for (int i=0; i<arr.length; i++){
			if (i == arr[i].representant)
				size++;
		}
		return size;
	}

	
	@Override
	public String toString() {
		String retStr = "Disjoint sets as linked list:";
		boolean [] temp = new boolean[arr.length];
		for (int i=0; i<temp.length; i++){
			temp[i]=false;
		}
		int counter = 0;
		int actIndex = 0;

		while (counter<arr.length){
			retStr += "\n" + findSet(actIndex);
			temp[findSet(actIndex)] = true;
			counter++;
			int currentIndex = findSet(actIndex);
			int tSize = arr[findSet(actIndex)].length;
			for (int i=1; i<tSize; i++){
				currentIndex=arr[currentIndex].next;
				retStr += ", " + currentIndex;
				temp[currentIndex] = true;
				counter++;
			}

			currentIndex=0;
			while(currentIndex<temp.length && temp[currentIndex]!=false){
				currentIndex++;
			}
			actIndex = currentIndex;
		}
		return retStr;
	}

}
