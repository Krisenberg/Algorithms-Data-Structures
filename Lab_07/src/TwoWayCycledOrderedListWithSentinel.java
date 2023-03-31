import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E>{

	private class Element{
		public Element(E e) {
			this.object = e;
		}
		public Element(E e, Element next, Element prev) {
			this.object = e;
			this.next = next;
			this.prev = prev;
		}
		// add element e after this
		public void addAfter(Element elem) {
			elem.next = this.next;
			elem.prev = this;
			this.next.prev = elem;
			this.next = elem;
		}
		// assert it is NOT a sentinel
		public void remove() {
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
		Element pos;

		public InnerIterator() {
			pos = sentinel.next;
		}
		@Override
		public boolean hasNext() {
			return (pos!=sentinel);
		}

		@Override
		public E next() {
			E value = pos.object;
			pos = pos.next;
			return value;
		}
	}

	private class InnerListIterator implements ListIterator<E>{
		Element pos;
		public InnerListIterator() {
			this.pos = sentinel;
		}
		@Override
		public boolean hasNext() {
			return pos.next!=sentinel;
		}

		@Override
		public E next() {
			pos = pos.next;
			return pos.object;
		}
		@Override
		public void add(E arg0) {
			throw new UnsupportedOperationException();
		}
		@Override
		public boolean hasPrevious() {
			return pos!=sentinel;
		}
		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}
		@Override
		public E previous() {
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
		sentinel = new Element(null);
		clear();
	}

	//@SuppressWarnings("unchecked")
	@Override
	public boolean add(E e) {
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

	private Element getElement(int index) throws NoSuchElementException {
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
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		size = 0;
	}

	@Override
	public boolean contains(E element) {
		return indexOf(element)>=0;
	}

	@Override
	public E get(int index) {
		return getElement(index).object;
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(E element) {
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
	public E remove(int index) {
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
		return size;
	}

	//@SuppressWarnings("unchecked")
	public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
		int secondListSize = other.size;
		if (secondListSize!=0 && other!=this){
			size = size + secondListSize;
			Element actElemFirstList = this.sentinel.next;
			Element actElemSecondList = other.sentinel.next;
			while(actElemSecondList!=sentinel){
				if (actElemFirstList==this.sentinel){
					actElemFirstList.prev.next = actElemSecondList;
					actElemSecondList.prev = actElemFirstList.prev;
					other.sentinel.prev.next = this.sentinel;
					this.sentinel.prev = other.sentinel.prev;
					actElemSecondList=sentinel;
				} else {
					Comparable<E> link = (Comparable<E>) actElemSecondList.object;
					if(link.compareTo(actElemFirstList.object)<0){
						actElemFirstList.prev.addAfter(new Element(actElemSecondList.object));
						actElemSecondList = actElemSecondList.next;
					} else {
						actElemFirstList = actElemFirstList.next;
					}
				}
			}
			other.clear();
		}
	}
	
	//@SuppressWarnings({ "unchecked", "rawtypes" })
	public void removeAll(E e) {
		Element actElem = sentinel.next;
		Comparable<E> link = (Comparable<E>) e;

		while (actElem!=sentinel && link.compareTo(actElem.object)>=0){
			actElem = actElem.next;
			if (link.equals(actElem.prev.object)){
				actElem.prev.remove();
				size--;
			}
		}
	}

}

