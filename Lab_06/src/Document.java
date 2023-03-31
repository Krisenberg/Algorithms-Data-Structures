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
	
	// accepted only small letters, capital letters, digits and '_' (but not on the begin)
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

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
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
		for (int i=arr.length-2; i>=0; i--){
			int j=i;
			int temp=arr[i];
			while (j<arr.length-1 && temp>arr[j+1]){
				arr[j]=arr[j+1];
				j++;
			}
			arr[j]=temp;
			showArray(arr);
		}
		
	}
	public void selectSort(int[] arr) {
		showArray(arr);
		for (int i=arr.length-1; i>0; i--){
			int potentialMaxIndex = indexOfMaxElem(arr,i);
			if (arr[potentialMaxIndex]>arr[i])
				swap(arr,potentialMaxIndex,i);
			showArray(arr);
		}
	}
	

	public void iterativeMergeSort(int[] arr) {
		showArray(arr);
		//DONE
		int size = arr.length;
		int partition = 2;
		int result[] = new int[size];

		while ((partition/2)<size){
			int leftPos = 0;
			int rightPos = partition/2;
			int stopFlagLeft = rightPos;
			int stopFlagRight = partition;
			int resultArrayIndex = 0;

			if (partition>size){
				stopFlagRight = size;
			}

			while (resultArrayIndex<size){
				if (leftPos<stopFlagLeft && rightPos<stopFlagRight){
//					Sytuacja, gdy z obu tablic mamy jeszcze elementy do scalenia i bedziemy wybierac mniejszy z nich
					if (arr[leftPos]<=arr[rightPos]){
						result[resultArrayIndex] = arr[leftPos];
						leftPos++;
					} else {
						result[resultArrayIndex] = arr[rightPos];
						rightPos++;
					}
				} else if (leftPos<stopFlagLeft || rightPos<stopFlagRight){
//					Sytuacja, gdy tylko w jednej tablicy zostalo cos do scalenia, tzn juz tylko do przepisania
					if (rightPos>=stopFlagRight){
						result[resultArrayIndex] = arr[leftPos];
						leftPos++;
					} else {
						result[resultArrayIndex] = arr[rightPos];
						rightPos++;
					}
				} else {
//					Zakonczone scalanie danych dwoch podtablic - przygotowujemy flagi do scalenia kolejnych dwoch
					leftPos = stopFlagRight;
					rightPos = leftPos + (partition / 2);
					if (rightPos > size - 1) {
						stopFlagLeft = size;
						stopFlagRight = size;
					} else {
						stopFlagLeft = rightPos;
						stopFlagRight = stopFlagRight + partition;
						if (stopFlagRight > size)
							stopFlagRight = size;
					}
					resultArrayIndex--;
				}

				resultArrayIndex++;
			}

			for (int i=0; i<size; i++){
				arr[i] = result[i];
			}
			showArray(arr);
			partition = partition*2;
		}

	}

	public void iterativeMergeSortSecond(int[] arr) {
		showArray(arr);
		//DONE
		int size = arr.length;
		int partition = 2;
		int result[] = new int[size];

		while ((partition/2)<size){
			int leftPos = 0;
			int rightPos = partition/2;
			int stopFlagLeft = rightPos;
			int stopFlagRight = partition;
			int resultArrayIndex = 0;

			if (partition>size){
				stopFlagRight = size;
			}

			for (int i=0; i<((size/partition)+1); i++){
				while (leftPos<stopFlagLeft && rightPos<stopFlagRight){
					if (arr[leftPos]<=arr[rightPos]){
						result[resultArrayIndex] = arr[leftPos];
						leftPos++;
					} else {
						result[resultArrayIndex] = arr[rightPos];
						rightPos++;
					}
					resultArrayIndex++;
				}

				while (leftPos<stopFlagLeft){
					result[resultArrayIndex] = arr[leftPos];
					leftPos++;
					resultArrayIndex++;
				}

				while (rightPos<stopFlagRight){
					result[resultArrayIndex] = arr[rightPos];
					rightPos++;
					resultArrayIndex++;
				}

				leftPos = stopFlagRight;
				rightPos = leftPos + (partition / 2);
				if (rightPos > size - 1) {
					stopFlagLeft = size;
					stopFlagRight = size;
				} else {
					stopFlagLeft = rightPos;
					stopFlagRight = stopFlagRight + partition;
					if (stopFlagRight > size)
						stopFlagRight = size;
				}

			}

			for (int i=0; i<size; i++){
				arr[i] = result[i];
			}

			showArray(arr);
			partition = partition*2;
		}

	}

	public void radixSort(int[] arr) {
		showArray(arr);
		//DONE
		int d = 3;
		int k = 10;
		int n = arr.length;
		int [] result = new int[n];
		int [] pos = new int [k];
		int divisor = 1;

		for (int i=0; i<d; i++){
			for (int j=0; j<k; j++)
				pos[j] = 0;

			for (int j=0; j<n; j++){
				pos[(arr[j]/divisor)%10]++;
			}
//			KOREKTA POZYCJI Z UWAGI NA NUMERACJE TABLICY OD ZERA
			pos[0]--;
			for (int j=1; j<k; j++){
				pos[j]=pos[j]+pos[j-1];
			}
			for (int j=n-1; j>=0; j--){
				result[pos[(arr[j]/divisor)%10]]=arr[j];
				pos[(arr[j]/divisor)%10]--;
			}
			for (int j=0; j<n; j++)
				arr[j]=result[j];
			divisor = divisor * 10;
			showArray(arr);
		}
	}

	public void radixSortModified(int[] arr) {
		showArray(arr);
		//DONE

		int maks = arr[0];
		for (int i=1; i<arr.length; i++){
			if (arr[i]>maks)
				maks = arr[i];
		}

		int d = (int) (Math.floor(Math.log10(maks*1.0)) + 1);

		int k = 10;
		int n = arr.length;
		int [] result = new int[n];
		int [] pos = new int [k];
		int divisor = 1;

		for (int i=0; i<d; i++){
			for (int j=0; j<k; j++)
				pos[j] = 0;

			for (int j=0; j<n; j++){
				pos[(arr[j]/divisor)%10]++;
			}
//			KOREKTA POZYCJI Z UWAGI NA NUMERACJE TABLICY OD ZERA
			pos[0]--;
			for (int j=1; j<k; j++){
				pos[j]=pos[j]+pos[j-1];
			}
			for (int j=n-1; j>=0; j--){
				result[pos[(arr[j]/divisor)%10]]=arr[j];
				pos[(arr[j]/divisor)%10]--;
			}
			for (int j=0; j<n; j++)
				arr[j]=result[j];
			divisor = divisor * 10;
			showArray(arr);
		}
	}
}
