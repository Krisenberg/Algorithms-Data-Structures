import java.util.LinkedList;
import java.util.ListIterator;

public class HashTable {
	LinkedList arr[]; 
	private final static int defaultInitSize=8;
	private final static double defaultMaxLoadFactor=0.7;
	private int size;	
	private final double maxLoadFactor;

	public HashTable() {
		this(defaultInitSize);
	}
	public HashTable(int size) {
		this(size,defaultMaxLoadFactor);
	}

	public HashTable(int initCapacity, double maxLF) {
		if(initCapacity<2)
			initCapacity=2;
		arr=new LinkedList[initCapacity];
		size = 0;
		createLists(arr, arr.length);
		maxLoadFactor=maxLF;
	}

	private void createLists(LinkedList [] arr, int size){
		for (int i=0; i<size; i++){
			arr[i]=new LinkedList<>();
		}
	}

	private int index(Object elem, int size){
		return elem.hashCode()%size;
	}

	private boolean ifContains(Object elem){
		int index = index(elem, arr.length);
		ListIterator iter = arr[index].listIterator();
		while (iter.hasNext()){
			if (elem.equals(iter.next()))
				return true;
		}
		return false;
	}

	public boolean add(Object elem) {
		if (ifContains(elem))
			return false;

		int index = index(elem, arr.length);
		arr[index].add(elem);
		size++;

		if (((double) size/arr.length) > maxLoadFactor)
			doubleArray();

		return true;
	}

	private void doubleArray(){
		int newSize = arr.length*2;
		LinkedList newArr [] = new LinkedList[newSize];
		createLists(newArr, newSize);

		for (int i=0; i<arr.length; i++){
			ListIterator iter = arr[i].listIterator();
			while (iter.hasNext()){
				Object ob = iter.next();
				int newIndex = index(ob, newSize);
				newArr[newIndex].add(ob);
			}
		}
		arr = newArr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		String retStr = "";

		for (int i=0; i<arr.length; i++){
			retStr = retStr + i + ":";
			ListIterator iter = arr[i].listIterator();

			if (iter.hasNext()){
				IWithName x = (IWithName) iter.next();
				retStr = retStr + " " + x.getName();

				while (iter.hasNext()){
					x = (IWithName) iter.next();
					retStr = retStr + ", " + x.getName();
				}
			}

			retStr = retStr + "\n";
		}
		return retStr;
	}

	public Object get(Object toFind) {
		int index = index(toFind, arr.length);
		Object retObj = null;

		ListIterator iter = arr[index].listIterator();

		while (iter.hasNext()){
			if (toFind.equals((retObj=iter.next())))
				return retObj;
		}
		return retObj;
	}
}

