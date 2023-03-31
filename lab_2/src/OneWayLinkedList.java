import javax.print.attribute.standard.JobKOctets;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayLinkedList<E> implements IList<E>{
	
	private class Element {
		public Element(E e) {
			this.object = e;
		}

		E object;
		Element next = null;

		public void next() {
		}
	}
	
	Element sentinel;
	
	private class InnerIterator implements Iterator<E>{
		// TODO
		Element actElem;
		public InnerIterator() {
			// DONE
			actElem = sentinel;
		}
		@Override
		public boolean hasNext() {
			// DONE
			return actElem!=null;
		}
		
		@Override
		public E next() {
			// DONE
			E value = actElem.object;
			actElem = actElem.next;
			return value;
		}


	}
	
	public OneWayLinkedList() {
		// make a sentinel	
		// DONE

//		sentinel = new Element((E) (new Object()));
		sentinel = null;
	}

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E e) {
		// DONE
		Element newElement = new Element(e);

		if (sentinel==null){
			sentinel=newElement;
			return true;
		}

		Element lastElement = sentinel;
		while (lastElement.next!=null)
			lastElement = lastElement.next;
		lastElement.next = newElement;

		return true;
	}

	@Override
	public void add(int index, E element) throws NoSuchElementException {
		// DONE
		if (index<0)
			throw new NoSuchElementException();
		Element newElem = new Element(element);
		if (index == 0){
			newElem.next = sentinel;
			sentinel = newElem;
		}
		Element actElem = getElement(index-1);
		newElem.next = actElem.next;
		actElem.next=newElem;
	}

	private Element getElement (int index){
		if (index<0) throw new NoSuchElementException();
		Element actElem = sentinel;
		while (index>0 && actElem!=null){
			index--;
			actElem = actElem.next;
		}
		if (actElem == null){
			throw new NoSuchElementException();
		}
		return actElem;
	}

	@Override
	public void clear() {
		// DONE
		sentinel = null;
	}

	@Override
	public boolean contains(E element) {
		// DONE
		return indexOf(element)>=0;
	}

	@Override
	public E get(int index) throws NoSuchElementException {
		// DONE
		Element actElem = getElement(index);
		return actElem.object;
	}

	@Override
	public E set(int index, E element) throws NoSuchElementException {
		// DONE
		Element actElem = getElement(index);
		E elemValue = actElem.object;
		actElem.object=element;
		return elemValue;
	}

	@Override
	public int indexOf(E element) {
		// DONE
		int index = 0;
		Element actElem = sentinel;
		while (actElem!=null){
			if (actElem.object.equals(element))
				return index;
			index++;
			actElem = actElem.next;
		}

		return -1;
	}

	@Override
	public boolean isEmpty() {
		// DONE
		return sentinel==null;
	}

	@Override
	public E remove(int index) throws NoSuchElementException {
		// DONE
		if (index<0 || sentinel == null) throw new NoSuchElementException();
		if (index==0){
			E dataToBeDeleted = sentinel.object;
			sentinel = sentinel.next;
			return dataToBeDeleted;
		}
		Element actElem = getElement(index-1);
		if (actElem.next==null) throw new NoSuchElementException();
		E dataToBeDeleted = actElem.next.object;
		actElem.next=actElem.next.next;
		return dataToBeDeleted;
	}

	@Override
	public boolean remove(E e) {
		// DONE

		if (sentinel == null){
			return false;
		}
		if (sentinel.object.equals(e)){
			sentinel = sentinel.next;
			return true;
		}
		Element actElem = sentinel;
		while (actElem.next!=null && !actElem.next.object.equals(e)){
			actElem = actElem.next;
		}
		if (actElem.next==null)
			return false;
		actElem.next = actElem.next.next;
		return true;
	}

	@Override
	public int size() {
		// DONE

		int index = 0;
		Element actElem = sentinel;
		while (actElem!=null){
			index++;
			actElem = actElem.next;
		}
		return index;
	}
	
}

