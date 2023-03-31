import java.util.Scanner;

public class Document{
	public String name;
	public OneWayLinkedList<Link> links;
	public Document(String name, Scanner scan) {
			// DONE
			this.name = name;
			links = new OneWayLinkedList<>();
			load(scan);
	}
	public void load(Scanner scan) {
		//DONE
		String lineToAnalyse = scan.nextLine();
		while (!lineToAnalyse.equals("eod")){
			String [] words = lineToAnalyse.split("\s+");
			for (int i=0; i<words.length; i++){
				String current = words[i];
				if (current.length()>4 && current.charAt(4) == '='){
					if (correctBeginning(current)){
						if (correctLink(current.substring(5))){
							links.add(new Link(convertLink(current.substring(current.indexOf('=') + 1))));
						}
					}
				}
			}
			lineToAnalyse= scan.nextLine();
		}
	}
	
	@Override
	public String toString() {
		String string = "Document: " + name;
		for (int i=0; i<links.size(); i++){
			string = string + "\n" + links.get(i).ref;
		}
		return string;
	}

	public boolean correctBeginning (String link){
		String beginning = link.substring(0,4);
		if (beginning.equalsIgnoreCase("link"))
			return true;
		return false;
	}

	// accepted only small letters, capitalic letter, digits and '_' (but not on the begin)
	public boolean correctLink(String link) {
		if (link!=null && link.length()>0){
			if (!Character.isLetter(link.charAt(0)))
				return false;
			for (int i=1; i<link.length(); i++){
				char current = link.charAt(i);
				if (!Character.isLetter(current) && !Character.isDigit(current) && !(current=='_'))
					return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public String convertLink(String link){
		StringBuilder stringBuilder = new StringBuilder(100);
		for (int i=0; i<link.length(); i++){
			char current = link.charAt(i);
			if (Character.isLetter(current)){
				if (Character.isUpperCase(current)){
					current = Character.toLowerCase(current);
				}
			}
			stringBuilder.append(current);
		}
		return stringBuilder.toString();
	}

}
