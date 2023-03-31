import java.util.ListIterator;
import java.util.Scanner;

public class Document{
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;
	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		link=new TwoWayCycledOrderedListWithSentinel<Link>();
		load(scan);
	}

	public void load(Scanner scan) {
		String lineToAnalyse = scan.nextLine();
		while (!lineToAnalyse.equals("eod")){
			String [] words = lineToAnalyse.split(" ");
			for (int i=0; i<words.length; i++){
				String current = words[i];
				if (current.length()>5 && current.substring(0,5).equalsIgnoreCase("link=")){
					Link potentialLink = createLink(current.substring(5));
					if (potentialLink!=null){
						link.add(potentialLink);
					}
				}
			}
			lineToAnalyse= scan.nextLine();
		}
	}
	
	// accepted only small letters, capital letter, digits and '_' (but not on the beginning)
	public static boolean isCorrectId(String id) {
		if (id!=null && id.length()>0) {
			if (!Character.isLetter(id.charAt(0)))
				return false;
			for (int i = 1; i < id.length(); i++) {
				char current = id.charAt(i);
				if (!Character.isLetter(current) && !Character.isDigit(current) && !(current == '_'))
					return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isCorrectWeight(String potentialLink){
		if (potentialLink.charAt(potentialLink.length()-1)==')' && potentialLink.contains("(")){
			String potentialNumber = potentialLink.substring(potentialLink.lastIndexOf("(")+1, potentialLink.lastIndexOf(")"));
			if (potentialNumber.equals("0"))
				return false;
			for (int i=0; i<potentialNumber.length(); i++){
				if (!Character.isDigit(potentialNumber.charAt(i)))
					return false;
			}
			return true;
		}
		return false;
	}

	// accepted only small letters, capital letter, digits and '_' (but not on the beginning)
	public static Link createLink(String link) {
		if (isCorrectWeight(link)){
			String potentialLink = link.substring(0,link.lastIndexOf("("));
			int weight = Integer.parseInt(link.substring(link.lastIndexOf("(")+1, link.lastIndexOf(")")));
			if (isCorrectId(potentialLink)){
				Link newLink = new Link(convertLink(potentialLink),weight);
				return newLink;
			}
		} else {
			if (isCorrectId(link)){
				Link newLink = new Link(convertLink(link));
				return newLink;
			}
		}
		return null;
	}

	public static String convertLink(String link){
		StringBuilder stringBuilder = new StringBuilder(100);
		for (int i=0; i<link.length(); i++){
			char current = link.charAt(i);
			if (Character.isLetter(current) && Character.isUpperCase(current)){
				current = Character.toLowerCase(current);
			}
			stringBuilder.append(current);
		}
		return stringBuilder.toString();
	}

	@Override
	public String toString() {
		String retStr="Document: "+name;
		ListIterator<Link> iter = link.listIterator();
		int counter=1;
		while (iter.hasNext()){
			if (counter==1)
				retStr = retStr + "\n";
			else
				retStr = retStr + " ";
			retStr = retStr + iter.next();
			counter++;
			if (counter==11)
				counter=1;
		}
		return retStr;
	}

	public String toStringReverse() {
		String retStr="Document: "+name;
		ListIterator<Link> iter=link.listIterator();
		while(iter.hasNext())
			iter.next();
		int counter=1;
		while(iter.hasPrevious()){
			if (counter==1)
				retStr = retStr + "\n";
			else
				retStr = retStr + " ";
			retStr = retStr + iter.previous();
			counter++;
			if (counter==11)
				counter=1;
		}
		return retStr;
	}

	public int[] getWeights() {
		//DONE
		int [] array = new int[link.size];
		int i=0;
		ListIterator<Link> iter = link.listIterator();
		while (iter.hasNext()){
			array[i]=iter.next().weight;
			i++;
		}
		return array;
	}

	public static void showArray(int[] arr) {
		//DONE
		int size = arr.length;
		StringBuilder sb = new StringBuilder(size);
		for (int i=0; i<size; i++){
			if (i!=0)
				sb.append(" ");
			sb.append(arr[i]);
		}
		System.out.println(sb);
	}

	public void swap(int[] arr, int firtIndex, int secondIndex){
		int temp = arr[firtIndex];
		arr[firtIndex]=arr[secondIndex];
		arr[secondIndex]=temp;
	}

	public int indexOfMaxElem(int[] arr, int regex){
		int currentMaxIndex = 0;
		for (int i=1; i<regex; i++){
			if (arr[i]>arr[currentMaxIndex])
				currentMaxIndex=i;
		}
		return currentMaxIndex;
	}

	void bubbleSort(int[] arr) {
		showArray(arr);
		//DONE
		for (int i=0; i<arr.length-1; i++){
			for (int j=arr.length-1; j>i; j--){
				if (arr[j]<arr[j-1])
					swap(arr,j,j-1);
			}
			showArray(arr);
		}
	}

	void bubbleSortUpgraded(int[] arr) {
		showArray(arr);
		//DONE
		for (int i=0; i<arr.length-1; i++){
			boolean wasSwap=false;
			for (int j=arr.length-1; j>i; j--){
				if (arr[j]<arr[j-1]){
					swap(arr,j,j-1);
					wasSwap=true;
				}
			}
			if (!wasSwap)
				i=arr.length;
			else
				showArray(arr);
		}
	}

	public void insertSort(int[] arr) {
		showArray(arr);
		//DONE
		for (int i=arr.length-2; i>=0; i--){
			int j=i;
			int temp=arr[i];
			while (j<arr.length-1 && temp>arr[j+1]){
//				swap(arr,j,j+1);
				arr[j]=arr[j+1];
				j++;
			}
			arr[j]=temp;
			showArray(arr);
		}
	}

//	public void insertSort(int[] arr) {
//		showArray(arr);
//		//DONE
//		for (int i=arr.length-2; i>=0; i--){
//			int temp = arr[i];
//			int j = arr.length-1;
//			boolean isFound = false;
//			int foundIndex = j;
//			int memory = 0;
//			while (j>i){
//				if (!isFound && temp>arr[j]){
//					foundIndex=j;
//					memory=arr[j-1];
//					arr[j-1]=arr[j];
//					isFound=true;
//				} else if (isFound){
//					arr[foundIndex]=memory;
//					memory=arr[j-1];
//					arr[j-1]=arr[foundIndex];
//				}
//				j--;
//			}
//			arr[foundIndex]=temp;
//			showArray(arr);
//		}
//	}

	public void selectSort(int[] arr) {
		showArray(arr);
		//DONE
		for (int i=arr.length-1; i>0; i--){
			int potentialMaxIndex = indexOfMaxElem(arr,i);
			if (arr[potentialMaxIndex]>arr[i])
				swap(arr,potentialMaxIndex,i);
			showArray(arr);
		}
	}

}
