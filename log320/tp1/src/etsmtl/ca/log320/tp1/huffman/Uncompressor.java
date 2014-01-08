package etsmtl.ca.log320.tp1.huffman;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

public class Uncompressor {	
	public static void uncompress(InputStream in, OutputStream out) throws IOException {
		InputBitStream s = new InputBitStream(in);
		Node<Byte> root = getTree(s);
		
		Node<Byte> currentNode = root;
		int b;
		while((b = s.read()) != -1) {
			if(b == 1) {
				currentNode = currentNode.getRight();
			} else {
				currentNode = currentNode.getLeft();
			}
			
			if(currentNode.getValue() != null) {
				out.write(currentNode.getValue());
				currentNode = root;
			}
		}
		
		out.flush();
	}

	private static Node<Byte> getTree(InputBitStream s) throws IOException {
		
		// Build tree structure
		LinkedList<Node<Byte>> nodeQueue = new LinkedList<Node<Byte>>();
		Node<Byte> root = new Node<Byte>(null, 0);
		nodeQueue.add(root);
		while(!nodeQueue.isEmpty()) {
			Node<Byte> node = nodeQueue.remove();
			node.setLeft(new Node<Byte>(null, 0));
			node.setRight(new Node<Byte>(null, 0));
			int leftIsLeaf = s.read();
			int rightIsLeaf = s.read();
			if(leftIsLeaf == -1 || rightIsLeaf == -1) {
				throw new IOException("Unexpected EOF in file header");
			}
			
			if(leftIsLeaf == 0) {
				nodeQueue.add(node.getLeft());
			}
			
			if(rightIsLeaf == 0) {
				nodeQueue.add(node.getRight());
			}
		}
		
		s.goToNextByte();
		
		// Fill the tree with its value
		nodeQueue.add(root.getLeft());
		nodeQueue.add(root.getRight());
		while(!nodeQueue.isEmpty()){
			Node<Byte> node = nodeQueue.remove();
			if(node == null) continue;
			if(node.getLeft() == null && node.getRight() == null) {
				int byteRead = s.readByte();
				if(byteRead == -1) {
					throw new IOException("Unexpected EOF in file header");
				}
				node.setValue((byte)byteRead);
			} else {
				nodeQueue.add(node.getLeft());
				nodeQueue.add(node.getRight());
			}
		}
		
		return root;
	}
}
