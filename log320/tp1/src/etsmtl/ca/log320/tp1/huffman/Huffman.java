package etsmtl.ca.log320.tp1.huffman;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Huffman {
	public static void compress(InputStream in, OutputStream out) throws IOException{		
		Compressor.compress(in, out);
	}

	public static void uncompress(InputStream in, OutputStream out) throws IOException {
		Uncompressor.uncompress(in, out);
	}
}
