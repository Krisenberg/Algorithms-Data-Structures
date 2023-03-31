import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E>{

	private class Element{
		public Element(E e) {
			//DONE
			this.object = e;
		}
		public Element(E e, Element next, Element prev) {
			//DONE
			this.object = e;
			this.next = next;
			this.prev = prev;
		}
		// add element e after this
		public void addAfter(Element elem) {
			//DONE
			elem.next = this.next;
			elem.prev = this;
			this.next.prev = elem;
			this.next = elem;

		}
		// assert it is NOT a sentinel
		public void remove() {
			//DONE
			if (this.object!=null){
				this.prev.next=this.next;
				this.next.prev=this.prev;
			}
		}
		E object;
		Element next=null;
		Element prev=null;
	}


	Element sentinel;
	int size;

	private class InnerIterator implements Iterator<E>{
		//DONE
		Element pos;

		public InnerIterator() {
			//DONE
			pos = sentinel.next;
		}
		@Override
		public boolean hasNext() {
			//DONE
			return (pos!=sentinel);
		}

		@Override
		public E next() {
			//DONE
			E value = pos.object;
			pos = pos.next;
			return value;
		}
	}

	private class InnerListIterator implements ListIterator<E>{
		//DONE
		Element pos;
		public InnerListIterator() {
			//DONE
			this.pos = sentinel;
		}
		@Override
		public boolean hasNext() {
			//DONE
			return pos.next!=sentinel;
		}

		@Override
		public E next() {
			//DONE
			pos = pos.next;
			return pos.object;
		}
		@Override
		public void add(E arg0) {
			throw new UnsupportedOperationException();
		}
		@Override
		public boolean hasPrevious() {
			//DONE
			return pos!=sentinel;
		}
		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}
		@Override
		public E previous() {
			//DONE
			E value = pos.object;
			pos = pos.prev;
			return value;
		}
		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		@Override
		public void set(E arg0) {
			throw new UnsupportedOperationException();
		}
	}
	public TwoWayCycledOrderedListWithSentinel() {
		//DONE
		sentinel = new Element(null);
		clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(E e) {
		//DONE
		Element actElem = sentinel.next;
		Comparable<E> l = (Comparable<E>) e;
		while (actElem!=sentinel && l.compareTo(actElem.object)>=0){
			actElem = actElem.next;
		}
		Element newElem = new Element(e, actElem, actElem.prev);
		actElem.prev.next = newElem;
		actElem.prev = newElem;
		size++;
		return true;
	}

	private Element getElement(int index) throws NoSuchElementException{
		//DONE
		if (size==0 || index<0)
			throw new NoSuchElementException();
		Element actElem = sentinel.next;
		while (actElem!=sentinel && index>0){
			actElem = actElem.next;
			index--;
		}
		if (actElem==sentinel)
			throw new NoSuchElementException();
		return actElem;
	}

	private Element getElement(E obj) throws NoSuchElementException{
		//DONE
		Element actElem = sentinel.next;
		while (actElem!=sentinel && !obj.equals(actElem.object)){
			actElem = actElem.next;
		}
		if (actElem==sentinel)
			throw new NoSuchElementException();
		return actElem;
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		//DONE
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		size = 0;
	}

	@Override
	public boolean contains(E element) {
		//DONE
		return indexOf(element)>=0;
	}

	@Override
	public E get(int index) throws NoSuchElementException{
		//DONE
		return getElement(index).object;
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(E element) {
		//DONE
		int index = 0;
		Element actElem = sentinel.next;
		while (actElem!=sentinel && !actElem.object.equals(element)){
			actElem = actElem.next;
			index++;
		}
		if (actElem==sentinel)
			return -1;
		return index;
	}

	@Override
	public boolean isEmpty() {
		//DONE
		return sentinel.next==sentinel;
	}

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new InnerListIterator();
	}

	@Override
	public E remove(int index) throws NoSuchElementException{
		//DONE
		if (size==0 || index<0)
			throw new NoSuchElementException();
		Element actElem = sentinel.next;
		while (actElem!=sentinel && index>0){
			actElem = actElem.next;
			index--;
		}
		if (actElem==sentinel)
			throw new NoSuchElementException();
		E value = actElem.object;
		actElem.prev.next = actElem.next;
		actElem.next.prev = actElem.prev;
		size--;
		return value;
	}

	@Override
	public boolean remove(E e) {
		//TODO
		if (size==0)
			return false;
		Element actElem = sentinel.next;
		while (actElem!=sentinel && !e.equals(actElem.object)){
			actElem = actElem.next;
		}
		if (actElem==sentinel)
			return false;
		actElem.prev.next = actElem.next;
		actElem.next.prev = actElem.prev;
		size--;
		return true;
	}

	@Override
	public int size() {
		//DONE
		int size=0;
		ListIterator<E> iter=new InnerListIterator();
		while (iter.hasNext()){
			iter.next();
			size++;
		}
		return size;
	}

	@SuppressWarnings("unchecked")
	public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
		//TODO
		int secondListSize = other.size;
		if (secondListSize!=0 && other!=this){
			size = size + secondListSize;
			Element actElemFirstList = this.sentinel.next;
			while(secondListSize>0){
				if (actElemFirstList==this.sentinel){
					actElemFirstList.prev.next = other.sentinel.next;
					other.sentinel.next.prev = actElemFirstList.prev;
					other.sentinel.prev.next = this.sentinel;
					this.sentinel.prev = other.sentinel.prev;
					secondListSize = 0;
				} else {
					Comparable<E> link = (Comparable<E>) other.sentinel.next.object;
					if(link.compareTo(actElemFirstList.object)<0){
						actElemFirstList.prev.addAfter(new Element(other.sentinel.next.object, other.sentinel.next.next, other.sentinel));
						other.sentinel.next.remove();
						secondListSize--;
					} else {
						actElemFirstList = actElemFirstList.next;
					}
				}
			}
			other.clear();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void removeAll(E e) {
		//TODO
		Element actElem = sentinel.next;
		Comparable<E> link = (Comparable<E>) e;

		while (actElem!=sentinel && link.compareTo(actElem.object)>=0){
			actElem = actElem.next;
			if (link.compareTo(actElem.prev.object)==0){
				actElem.prev.remove();
				size--;
			}
		}
	}

	public int countDiff(){
		Element comparedElem = sentinel.next;
		Element actElem = sentinel.next;
		int diffCounter = 0;

		while (actElem!=sentinel){
			if (actElem.prev==sentinel){
				diffCounter++;
			} else {
				if (!actElem.object.equals(comparedElem.object)){
					comparedElem = actElem;
					diffCounter++;
				}
			}
			actElem = actElem.next;
		}
		return diffCounter;
	}

}

