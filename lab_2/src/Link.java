
public class Link{
	public String ref;
	public Link(String ref) {
		this.ref=ref;
	}

	public boolean equals(Object o){
		if (o == null)
			return false;
		if (o instanceof Link){
			Link link1 = (Link) o;
			return ref.equals(((Link) o).ref);
		}
		return false;
	}
	// in the future there will be more fields
}
