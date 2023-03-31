import java.util.Scanner;
import java.util.*;

public class Document implements IWithName{
	public String name;
	// TODO? You can change implementation of Link collection
	public SortedMap<String,Link> link;
	
	public Document(String name) {
		this.name=name.toLowerCase();
		link=new TreeMap<String,Link>();
	}

	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		link=new TreeMap<String,Link>();
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
						link.put(potentialLink.ref, potentialLink);
					}
				}
			}
			lineToAnalyse= scan.nextLine();
		}
	}

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

	// accepted only small letters, capital letters, digits and '_' (but not at the beginning)
	static Link createLink(String link) {
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
		String retStr="Document: "+name+"\n";
		int counter = 0;

		for (String ref : link.keySet()){
			if (counter==0)
				retStr += "\n";
			else
				retStr += " ";
			retStr += link.get(ref).toString();
			if (counter==9)
				counter = 1;
			else
				counter++;
		}
		return retStr;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String getName() {
		return name;
	}
}
