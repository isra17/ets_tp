package etsmtl.ca.log320.tp1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import etsmtl.ca.log320.tp1.huffman.Huffman;

public class Log320Tp1 {

	public static void main(String[] args) {
		if(args.length != 2 || args[0] == "-compress" || args[0] == "-decompress") {
			System.out.println("Usage: huffman -compress|decompress file");
			return;
		}
		
		try {
			
			if(args[0].equals("-compress")) {
				String inputFile = args[1];

				InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
				OutputStream out = new BufferedOutputStream(new FileOutputStream(inputFile + ".huf"));
				
				Huffman.compress(in, out);
			} else if(args[0].equals("-decompress")){
				String inputFile = args[1];
				String outputFile;
				if(inputFile.indexOf(".huf") != -1){
					outputFile = inputFile.substring(0, inputFile.indexOf(".huf"));
				} else {
					outputFile = inputFile + ".raw";
				}
				InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
				OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));
				
				Huffman.uncompress(in, out);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
