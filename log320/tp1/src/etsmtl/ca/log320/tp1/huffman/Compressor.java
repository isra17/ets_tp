package etsmtl.ca.log320.tp1.huffman;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class Compressor {
	public static class HuffmanPair {
		public int bitSize;
		public int value;
	}

	public static void compress(InputStream in, OutputStream out) throws IOException {
		in.mark(Integer.MAX_VALUE);
		SortedNodeList<Node<Byte>> nodeList = getNodeList(in);
		Node<Byte> root = buildTree(nodeList);
		HuffmanPair[] huffmanMap = getHuffmanMap(root);
		
		writeTree(out, root);
		
		in.reset();
		OutputBitStream s = new OutputBitStream(out);
		int b;
		while((b = in.read()) != -1) {
			
			HuffmanPair huf = huffmanMap[b];
			s.write(huf.value, huf.bitSize);
		}
		
		int padding = s.flushLastByte();
		out.write(padding);
		out.flush();
	}

	private static void writeTree(OutputStream out, Node<Byte> root) throws IOException {
		OutputBitStream s = new OutputBitStream(out);
		ArrayList<Byte> treeValue = new ArrayList<Byte>();
		
		LinkedList<Node<Byte>> nodeQueue = new LinkedList<Node<Byte>>();
		nodeQueue.add(root.getLeft());
		nodeQueue.add(root.getRight());
		while(!nodeQueue.isEmpty()) {
			Node<Byte> node = nodeQueue.remove();
			if(node == null) continue;
			if(node.getValue() == null) {
				s.write(0, 1);				
				nodeQueue.add(node.getLeft());
				nodeQueue.add(node.getRight());
			} else {				
				s.write(1, 1);
				treeValue.add(node.getValue());
			}
		}
		
		s.flushLastByte();
		
		for(Byte b : treeValue) {
			out.write(b);
		}
	}

	private static HuffmanPair[] getHuffmanMap(Node<Byte> root) {
		HuffmanPair[] huffmanMap = new HuffmanPair[256];
		addPairFor(root, huffmanMap, 0, 0);
		return huffmanMap;
	}

	private static void addPairFor(Node<Byte> node, HuffmanPair[] huffmanMap, int depth, int path) {
		if(node.getValue() != null) {
			int value = node.getValue() & 0xFF;
			huffmanMap[value] = new HuffmanPair();
			huffmanMap[value].bitSize = depth;
			huffmanMap[value].value = path;
		} else {
			addPairFor(node.getLeft(), huffmanMap, depth + 1, path);
			addPairFor(node.getRight(), huffmanMap, depth + 1, path | (1 << depth));
		}
	}

	private static Node<Byte> buildTree(SortedNodeList<Node<Byte>> nodeList) {
		Node<Node<Byte>> current = nodeList.getFirst();
		while(current.getRight() != null) {
			Node<Node<Byte>> right = current.getRight();
			int count = current.getCount() + right.getCount();
			Node<Byte> newNode = new Node<Byte>(null, count);
			newNode.setRight(current.getValue());
			newNode.setLeft(right.getValue());
			
			nodeList.remove(current);
			nodeList.remove(right);
			
			nodeList.insert(newNode, count);
			
			current = nodeList.getFirst();
		}
		
		return nodeList.getFirst().getValue();
	}

	private static SortedNodeList<Node<Byte>> getNodeList(InputStream in) throws IOException {
		int size = 256;
		int occurenceTable[] = new int[size];
		int b;
		while((b = in.read()) != -1) {
			// because java lack unsigned primitive...
			int unsignedByte = b & 0xFF;
			occurenceTable[unsignedByte]++;
		}
		
		SortedNodeList<Node<Byte>> nodeList = new SortedNodeList<Node<Byte>>();
		
		for(int i=0; i<size; i++) {
			int occ = occurenceTable[i];
			if(occ > 0) {
				nodeList.insert(new Node<Byte>(Byte.valueOf((byte) i), occ), occ);
			}
		}
		
		return nodeList;
	}

}
