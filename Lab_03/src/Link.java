public class Link{
	public String ref;
	// in the future there will be more fields
	public Link(String ref) {
		this.ref=ref;
	}

	@Override
	public boolean equals(Object o){
		if (o == null)
			return false;
		if (o instanceof Link){
			Link link1 = (Link) o;
			return ref.equals(link1.ref);
		}
		return false;
	}

	public String toString(){
		return ref;
	}
}