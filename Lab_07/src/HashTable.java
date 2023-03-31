import java.sql.Array;
import java.util.LinkedList;
import java.util.ListIterator;

public class HashTable{
	LinkedList arr[]; // use pure array
	private final static int defaultInitSize=8;
	private final static double defaultMaxLoadFactor=0.7;
	private int size;	
	private final double maxLoadFactor;
	private int actNumberOfElements;

	public HashTable() {
		this(defaultInitSize);
	}
	public HashTable(int size) {
		this(size,defaultMaxLoadFactor);
	}

//	public HashTable(int initCapacity, double maxLF) {
//		//DONE
//		this.arr = new LinkedList[initCapacity];
//		this.size = initCapacity;
//		this.maxLoadFactor=maxLF;
//		actNumberOfElements = 0;
//	}

	public HashTable(int initCapacity, double maxLF) {
		this.arr = new LinkedList[initCapacity];
		createLists(arr,initCapacity);
		this.size = initCapacity;
		this.maxLoadFactor=maxLF;
		actNumberOfElements = 0;
	}

	private void createLists(LinkedList [] arr, int size){
		for (int i=0; i<size; i++){
			arr[i]=new LinkedList<>();
		}
	}

//	public boolean ifContains(Object elem){
//		int index=index(elem, size);
//		if (arr[index]!=null){
//			ListIterator<Object> iter = arr[index].listIterator();
//			while (iter.hasNext()){
//				if (elem.equals(iter.next()))
//					return true;
//			}
//		}
//		return false;
//	}

	public boolean ifContains(Object elem){
		int index=index(elem, size);
		ListIterator<Object> iter = arr[index].listIterator();
		while (iter.hasNext()){
			if (elem.equals(iter.next()))
				return true;
		}
		return false;
	}

	private int index(Object elem, int size){
		return elem.hashCode()%size;
	}

//	public boolean add(Object elem) {
//		//DONE
//
//		if (ifContains(elem))
//			return false;
//
//		int index=index(elem,size);
//		if (arr[index]==null)
//			arr[index] = new LinkedList<>();
//		arr[index].add(elem);
//		actNumberOfElements++;
//
//		if ((double) actNumberOfElements/size > maxLoadFactor){
//			doubleArray();
//		}
//		return true;
//	}

	public boolean add(Object elem) {
		if (ifContains(elem))
			return false;

		int index=index(elem,size);
		arr[index].add(elem);
		actNumberOfElements++;

		if ((double) actNumberOfElements/size > maxLoadFactor)
			doubleArray();
		return true;
	}

	
//	private void doubleArray() {
//		//DONE
//		int newSize = size*2;
//		LinkedList newArr [] = new LinkedList[newSize];
//
//		for (int i=0; i<size; i++){
//			if (arr[i]!=null){
//				ListIterator<Object> iter = arr[i].listIterator();
//				while (iter.hasNext()){
//					Object ob = iter.next();
//					int index=index(ob,newSize);
//					if (newArr[index]==null)
//						newArr[index] = new LinkedList<>();
//					newArr[index].add(ob);
//				}
//			}
//		}
//
//		size = newSize;
//		arr=newArr;
//	}

	private void doubleArray() {
		int newSize = size*2;
		LinkedList newArr [] = new LinkedList[newSize];
		createLists(newArr,newSize);

		for (int i=0; i<size; i++){
			ListIterator<Object> iter = arr[i].listIterator();
			while (iter.hasNext()){
				Object ob = iter.next();
				int index=index(ob,newSize);
				newArr[index].add(ob);
			}
		}

		size = newSize;
		arr=newArr;
	}


//	@Override
//	public String toString() {
//		//DONE
//		// use	IWithName x=(IWithName)elem;
//
//		String retStr="";
//
//		for (int i=0; i<arr.length; i++) {
//			retStr = retStr + i + ":";
//			if (arr[i]!=null){
//				ListIterator iter = arr[i].listIterator();
//				IWithName x = (IWithName)iter.next();
//				retStr = retStr+" "+x.getName();
//				while (iter.hasNext()){
//					x = (IWithName)iter.next();
//					retStr = retStr+", "+x.getName();
//				}
//			}
//			retStr = retStr + "\n";
//		}
//		return retStr;
//	}

	@Override
	public String toString() {
		String retStr="";

		for (int i=0; i<arr.length; i++) {
			retStr = retStr + i + ":";

			ListIterator iter = arr[i].listIterator();
			if (iter.hasNext()){
				IWithName x = (IWithName)iter.next();
				retStr = retStr+" "+x.getName();

				while (iter.hasNext()){
					x = (IWithName)iter.next();
					retStr = retStr+", "+x.getName();
				}
			}

			retStr = retStr + "\n";
		}
		return retStr;
	}


//	public Object get(Object toFind) {
//		//DONE
//		Object ob = null;
//		int index=index(toFind,size);
//		if (arr[index]!=null){
//			ListIterator iter = arr[index].listIterator();
//			while (iter.hasNext()){
//				if (toFind.equals((ob = iter.next())))
//					return ob;
//			}
//		}
//		return ob;
//	}

	public Object get(Object toFind) {
		Object ob = null;
		int index=index(toFind,size);

		ListIterator iter = arr[index].listIterator();
		while (iter.hasNext()){
			if (toFind.equals((ob = iter.next())))
				return ob;
		}

		return ob;
	}
	
}

