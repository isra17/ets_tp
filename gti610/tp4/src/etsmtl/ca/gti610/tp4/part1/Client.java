package etsmtl.ca.gti610.tp4.part1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		if(args.length != 2) {
			System.out.println("Usage: client address port");
			return;
		}
		
		Socket socket = null;
		try {
			String address = args[0];
			int port = Integer.parseInt(args[1]);
			
			socket = new Socket(address, port);
			
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String msg = bufferRead.readLine();
			
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(msg);
			writer.flush();
			
			socket.close();			
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}

}
