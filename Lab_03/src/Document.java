import java.util.Scanner;

public class Document{
	public String name;
	public TwoWayUnorderedListWithHeadAndTail<Link> link;
	public Document(String name, Scanner scan) {
		this.name=name;
		link=new TwoWayUnorderedListWithHeadAndTail<>();
		load(scan);
	}
	public void load(Scanner scan) {
		String lineToAnalyse = scan.nextLine();
		while (!lineToAnalyse.equals("eod")){
			String [] words = lineToAnalyse.split(" ");
			for (int i=0; i<words.length; i++){
				String current = words[i];
				if (current.length()>5 && current.substring(0,5).equalsIgnoreCase("link=")){
					if (correctLink(current.substring(5))){
						link.add(new Link(convertLink(current.substring(5))));
					}
				}
			}
			lineToAnalyse= scan.nextLine();
		}
	}
	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	public static boolean correctLink(String link) {
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
	
	@Override
	public String toString() {
		String string = "Document: " + name;
		for (int i=0; i<link.size(); i++){
			string = string + "\n" + link.get(i).ref;
		}
		return string;
	}
	
	public String toStringReverse() {
		String retStr="Document: "+name;
		return retStr+link.toStringReverse();
	}

}
