import java.util.ListIterator;
import java.util.Scanner;

public class Document implements IWithName{
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;
	public Document(String name) {
		//DONE
		this.name = name;
	}

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
	
	// accepted only small letters, capital letters, digits and '_' (but not at the beginning)
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

	// accepted only small letters, capital letters, digits and '_' (but not at the beginning)
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
		//DONE
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
		//DONE
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

	public boolean equals(Object ob){
		if (ob == null)
			return false;
		if (ob instanceof Document){
			return this.name.equals(((Document) ob).name);
		}
		return false;
	}

	@Override
	public String getName() {
		// DONE
		return name;
	}


	public int hashCode(){
		int hash = 0;
		int [] multipliers = {7, 11, 13, 17, 19};
		int divisor = 323000; //it must be a multiple of a number 1000
//		int divisor = 1000;
//		int divisor = 7*11*13*17*19; //value = 323 323

		for (int i=0; i<name.length(); i++){
			if (hash==0){
				hash = name.charAt(0);
			} else {
				hash =(int) ((long) (hash*multipliers[(i-1)% multipliers.length]+name.charAt(i)))%divisor;
			}
		}

		return hash;
	}

	public int hashCode(int arraySize){
		int hash = 0;
		int [] multipliers = {7, 11, 13, 17, 19};

		for (int i=0; i<name.length(); i++){
			if (hash==0){
				hash = name.charAt(0);
			} else {
				if (hash*multipliers[(i-1)%5]+name.charAt(i)>2000000000)
					hash = (hash%arraySize)*multipliers[(i-1)% multipliers.length]+name.charAt(i);
				else
					hash = hash*multipliers[(i-1)% multipliers.length]+name.charAt(i);
			}
		}

		return hash;
	}

}

