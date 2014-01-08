package etsmtl.ca.log320.tp1.huffman;

public class SortedNodeList<T> {
	Node<T> m_first = null;
	Node<T> m_last = null;
	
	public void insert(T value, int count) {
		Node<T> newNode = new Node<T>(value, count);
		
		if( m_first == null ) {
			m_first = newNode;
			m_last = newNode;
			return;
		}
				
		Node<T> current = m_first;
		while(current != null && current.getCount() < newNode.getCount()) {
			current = current.getRight();
		}
		
		if(current != null) {
			newNode.setRight(current);
			Node<T> left = current.getLeft();
			newNode.setLeft(left);
			current.setLeft(newNode);
			if(left == null) {
				m_first = newNode;				
			} else {
				left.setRight(newNode);
			}
		} else {
			newNode.setLeft(m_last);
			m_last.setRight(newNode);
			m_last = newNode;
		}
	}
	
	void remove(Node<T> node) {
		Node<T> left = node.getLeft();
		Node<T> right = node.getRight();
		
		if(left != null) {
			left.setRight(right);
		} else {
			m_first = right;
		}
		
		if(right != null) {
			right.setLeft(left);
		} else {
			m_last = left;
		}
		
		node.setLeft(null);
		node.setRight(null);
	}
	
	public Node<T> getFirst() {
		return m_first;
	}
	
	public Node<T> getLast() {
		return m_last;
	}
}
