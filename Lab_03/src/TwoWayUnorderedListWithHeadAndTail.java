import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class TwoWayUnorderedListWithHeadAndTail<E> implements IList<E>{
	
	private class Element{
		public Element(E e) {
			this.object = e;
		}
		public Element(E e, Element next, Element prev) {
			this.object = e;
			this.next = next;
			this.prev = prev;
		}
		E object;
		Element next=null;
		Element prev=null;
	}
	
	Element head;
	Element tail;
	// can be realization with the field size or without
	//	int size;
	
	private class InnerIterator implements Iterator<E>{
		Element pos;
		// TODO maybe more fields....
		
		public InnerIterator() {
		//TODO
			pos = head;
		}
		@Override
		public boolean hasNext() {
			//TODO
			return pos!=null;
		}
		
		@Override
		public E next() {
			//TODO
			E value = pos.object;
			pos = pos.next;
			return value;
		}
	}
	
	private class InnerListIterator implements ListIterator<E>{
		Element p;
		boolean isTail = false;
		// TODO maybe more fields....

		public InnerListIterator (){
			this.p=head;
		}

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return p!=null;
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			if (p!=null && p.prev!=null)
				return true;
			if (isTail)
				return true;
			return false;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			if (p==tail || (p==head && tail==null))
				isTail=true;
			E value = p.object;
			p=p.next;
			return value;
		}

		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public E previous() {
			// TODO Auto-generated method stub
			E value;
			if (isTail){
				if (tail!=null){
					value= tail.object;
					p=tail;
				} else {
					value = head.object;
					p=head;
				}
				isTail=false;
				return value;
			}
			p=p.prev;
			value = p.object;
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
		public void set(E e) {
			// TODO Auto-generated method stub
			p.object = e;
		}
	}
	
	public TwoWayUnorderedListWithHeadAndTail() {
		// make a head and a tail	
		head=null;
		tail=null;
	}
	
	@Override
	public boolean add(E e) {
		//TODO
		if (head==null){
			head = new Element(e);
			return true;
		}

		if (tail==null){
			tail = new Element(e,null,head);
			head.next = tail;
			return true;
		}

		Element newElem = new Element(e,null,tail);
		tail.next = newElem;
		tail = newElem;
		return true;
	}

	@Override
	public void add(int index, E element) throws NoSuchElementException{
		//TODO
		if (index==0){
			if (head==null) head = new Element(element);
			else {
				Element newElem = new Element(element,head,null);
				head.prev=newElem;
				head=newElem;
			}
			return;
		}

		Element actElem = head;
		while (actElem!=null && index>0){
			actElem = actElem.next;
			index--;
		}

		if (actElem==null && index==0){
			if (tail!=null){
				Element newElem = new Element(element, null, tail);
				tail.next = newElem;
				tail = newElem;
			} else {
				tail = new Element(element,null,head);
				head.next=tail;
			}
		} else {
			if (index==0){
				Element newElem = new Element(element,actElem,actElem.prev);
				actElem.prev.next=newElem;
				actElem.prev=newElem;
			}
			if (actElem==null || index<0){
				throw new NoSuchElementException();
			}
		}
	}

	@Override
	public void clear() {
		//TODO
		head=null;
		tail=null;
	}

	@Override
	public boolean contains(E element) {
		//TODO
		return indexOf(element)>=0;
	}

	@Override
	public E get(int index) throws NoSuchElementException{
		//TODO
		Element actElem = getElement(index);
		return actElem.object;
	}

	public Element getElement(int index) throws NoSuchElementException{
		if (index<0)
			throw new NoSuchElementException();
		Element actElem = head;
		while (index>0 && actElem!=null){
			index--;
			actElem=actElem.next;
		}
		if (actElem==null){
			throw new NoSuchElementException();
		}
		return actElem;
	}

	@Override
	public E set(int index, E element) throws NoSuchElementException{
		//TODO
		Element actElem = getElement(index);
		E value = actElem.object;
		actElem.object = element;
		return value;
	}

	@Override
	public int indexOf(E element) {
		//TODO
		int index = 0;
		ListIterator<E> iter = new InnerListIterator();
		while (iter.hasNext()){
			E actElem = iter.next();
			if (element.equals(actElem))
				return index;
			index++;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		//TODO
		return head==null;
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
	public E remove(int index) throws NoSuchElementException{
		//TODO
		Element actElem = head;
		while (index>0 && actElem!=null){
			actElem = actElem.next;
			index--;
		}
		if (actElem==null || index<0)
			throw new NoSuchElementException();

		E value;
		if (actElem.next!=null){
			if (actElem==head){
				value = head.object;
				if (actElem.next==tail){
					tail.prev=null;
					head=tail;
					tail=null;
				} else {
					head = head.next;
					head.prev=null;
				}
			} else {
				value=actElem.object;
				actElem.prev.next=actElem.next;
				actElem.next.prev=actElem.prev;
			}
		} else {
			if (actElem==head){
				value=head.object;
				head=null;
			}
			else {
				value= tail.object;
				if (actElem.prev==head){
					tail=null;
					head.next=null;
				} else {
					actElem.prev.next=null;
					tail = actElem.prev;
				}
			}
		}
		return value;
	}

	@Override
	public boolean remove(E e) {
		//TODO
		Element actElem = head;
		while (actElem!=null && !e.equals(actElem.object)){
			actElem = actElem.next;
		}
		if (actElem==null)
			return false;

		if (actElem.next!=null){
			if (actElem==head){
				if (actElem.next==tail){
					tail.prev=null;
					head=tail;
					tail=null;
				} else {
					head = head.next;
					head.prev=null;
				}
			} else {
				actElem.prev.next=actElem.next;
				actElem.next.prev=actElem.prev;
			}
		} else {
			if (actElem==head){
				head=null;
			}
			else {
				if (actElem.prev==head){
					tail=null;
					head.next=null;
				} else {
					actElem.prev.next=null;
					tail = actElem.prev;
				}
			}
		}
		return true;
	}

	public void removeEven(){
		if (head!=null && head.next!=null){
			if (head.next==tail){
				head=tail;
				head.prev=null;
				tail=null;
			} else {
				head=head.next;
				head.prev=null;
				Element actElem = head;
				while (actElem.next!=tail && actElem.next.next!=tail){
					actElem.next=actElem.next.next;
					actElem.next.prev=actElem;
					actElem=actElem.next;
				}
				if (actElem.next==tail){
					tail=actElem;
					tail.next=null;
				} else {
					actElem.next=tail;
					tail.prev=actElem;
				}
			}
		} else {
			if (head!=null && head.next==null)
				head=null;
		}
	}

	@Override
	public int size() {
		//TODO
		int size=0;
		ListIterator<E> iter=new InnerListIterator();
		while (iter.hasNext()){
			iter.next();
			size++;
		}
		return size;
	}

	public String toStringReverse() {
		ListIterator<E> iter=new InnerListIterator();
		while(iter.hasNext())
			iter.next();
		String retStr="";
		//TODO use reverse direction of the iterator
		while(iter.hasPrevious()){
			retStr=retStr+"\n" + iter.previous().toString();
		}
		return retStr;
	}

	public void add(TwoWayUnorderedListWithHeadAndTail<E> other) {
		//TODO
		if (other!=this && other.head!=null){
			if (head==null){
				head=other.head;
				tail=other.tail;
			} else {
				if (tail == null) {
					head.next = other.head;
					tail = other.tail;
				} else {
					tail.next = other.head;
					other.head.prev = tail;
					tail = other.tail;
				}
			}
			other.clear();
		}
	}
}

