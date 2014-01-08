package etsmtl.ca.log320.tp1.huffman;

public class Node<T> {
	Node<T> m_right = null;
	Node<T> m_left = null;
	T m_value;
	int m_count = 0;
	
	public Node(T value, int count) {
		m_value = value;
		m_count = count;
	}
	
	public Node<T> getRight() {
		return m_right;
	}
	
	public void setRight(Node<T> right) {
		m_right = right;
	}
	
	public Node<T> getLeft() {
		return m_left;
	}
	
	public void setLeft(Node<T> left) {
		m_left = left;
	}
	
	public T getValue() {
		return m_value;
	}
	
	public void setValue(T value) {
		m_value = value;
	}
		
	public int getCount() {
		return m_count;
	}
	
	public void setCount(int count) {
		m_count = count;
	}
}
