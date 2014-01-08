package etsmtl.ca.gti610.tp4.part1;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Usage: serveur port");
			return;
		}
		
		ServerSocket socket = null;
		try {
			int port = Integer.parseInt(args[0]);
			
			socket = new ServerSocket(port);
			Socket client = socket.accept();
			
			byte[] msg = new byte[1024];
			while(client.getInputStream().read(msg) != -1)
				System.out.write(msg);
			
			client.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}

}
