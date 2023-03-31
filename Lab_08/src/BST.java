import java.util.NoSuchElementException;

public class BST<T> {

	private class Node{
		T value;
		Node left,right,parent;
		public Node(T v) {
			value=v;
		}
		public Node(T value, Node left, Node right, Node parent) {
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}

	private Node root=null;

	public BST() {
	}

	public T getElement(T toFind) {
		//DONE
		Node x;
		if ((x=getNode(toFind, root))!=null)
			return x.value;
		return null;
	}

	@SuppressWarnings("unchecked")
	private Node getNode(T toFind, Node currentRoot){
		if (currentRoot==null || toFind.equals(currentRoot.value))
			return currentRoot;
		Comparable<T> value = (Comparable<T>) toFind;
		if (value.compareTo(currentRoot.value)>0)
			return getNode(toFind, currentRoot.right);
		return getNode(toFind, currentRoot.left);
	}

	public T successor(T elem) {
		//DONE
		Node currentNode = getNode(elem, root);
		if (currentNode==null)
			return null;
		if (currentNode.right!=null){
			currentNode=currentNode.right;
			while (currentNode.left!=null)
				currentNode=currentNode.left;
			return currentNode.value;
		} else {
			while (currentNode.parent!=null && currentNode.parent.left!=currentNode){
				currentNode = currentNode.parent;
			}
			if (currentNode.parent!=null)
				return currentNode.parent.value;
		}
		return null;
	}


	private void inOrderWalk(StringBuilder sb, Node currentNode){
		if (currentNode!=null){
			inOrderWalk(sb, currentNode.left);
			sb.append(currentNode.value.toString() + ", ");
			inOrderWalk(sb, currentNode.right);
		}
	}


	public String toStringInOrder() {
		//DONE
		StringBuilder sb = new StringBuilder();
		inOrderWalk(sb, root);
		String retStr = sb.toString();
		if (retStr.length()>2)
			return retStr.substring(0,retStr.length()-2);
		return retStr;
	}

	private void preOrderWalk(StringBuilder sb, Node currentNode){
		if (currentNode!=null){
			sb.append(currentNode.value.toString() + ", ");
			preOrderWalk(sb, currentNode.left);
			preOrderWalk(sb, currentNode.right);
		}
	}

	public String toStringPreOrder() {
		//DONE
		StringBuilder sb = new StringBuilder();
		preOrderWalk(sb, root);
		String retStr = sb.toString();
		if (retStr.length()>2)
			return retStr.substring(0,retStr.length()-2);
		return retStr;
	}

	private void postOrderWalk(StringBuilder sb, Node currentNode){
		if (currentNode!=null){
			postOrderWalk(sb, currentNode.left);
			postOrderWalk(sb, currentNode.right);
			sb.append(currentNode.value.toString() + ", ");
		}
	}

	public String toStringPostOrder() {
		//DONE
		StringBuilder sb = new StringBuilder();
		postOrderWalk(sb, root);
		String retStr = sb.toString();
		if (retStr.length()>2)
			return retStr.substring(0,retStr.length()-2);
		return retStr;
	}

	@SuppressWarnings("unchecked")
	public boolean add(T elem) {
		// TODO
		if (root == null){
			root = new Node(elem,null,null,null);
			return true;
		} else {
			Node currentNode = null;
			Node temp = root;
			Comparable<T> value = (Comparable<T>) elem;
			while (temp!=null){
				currentNode = temp;
				if (value.compareTo(temp.value)>0)
					temp = temp.right;
				else
					temp = temp.left;
			}
			if (value.compareTo(currentNode.value)==0){
				return false;
			} else {
				Node nodeToAdd = new Node(elem,null,null,currentNode);
				if (value.compareTo(currentNode.value)>0)
					currentNode.right=nodeToAdd;
				else
					currentNode.left=nodeToAdd;
			}
		}
		return true;
	}


	public T remove(T value) {
		//DONE
		Node nodeToDelete = getNode(value, root);

		if (nodeToDelete==null)
			return null;

		Node temp = nodeToDelete;

		if (nodeToDelete.right!=null && nodeToDelete.left!=null){
			nodeToDelete = getNode(successor(value), root);
		}
		Node child;

		if (nodeToDelete.left!=null)
			child = nodeToDelete.left;
		else
			child = nodeToDelete.right;

		if (child!=null)
			child.parent=nodeToDelete.parent;

		if (nodeToDelete.parent==null)
			root = child;
		else {
			if (nodeToDelete.parent.left==nodeToDelete)
				nodeToDelete.parent.left=child;
			else
				nodeToDelete.parent.right=child;
		}

		if (nodeToDelete!=temp){
			T val = nodeToDelete.value;
			nodeToDelete.value=temp.value;
			temp.value=val;
		}

		return nodeToDelete.value;
	}

	private int numberOfDoubleParentsRecursive(Node currentNode){
		if (currentNode==null)
			return 0;
		if (currentNode.left!=null && currentNode.right!=null){
			return numberOfDoubleParentsRecursive(currentNode.left)+1+numberOfDoubleParentsRecursive(currentNode.right);
		}
		return numberOfDoubleParentsRecursive(currentNode.left)+numberOfDoubleParentsRecursive(currentNode.right);
	}

	public int numberOfDoubleParents(){
		return numberOfDoubleParentsRecursive(root);
	}
	
	public void clear() {
		//DONE
		root = null;
	}

	public int size() {
		//DONE
		return subTreeSize(root);
	}

	public int subTreeSize(Node currentRoot){
		if (currentRoot==null)
			return 0;
		return subTreeSize(currentRoot.left)+1+subTreeSize(currentRoot.right);
	}

}
