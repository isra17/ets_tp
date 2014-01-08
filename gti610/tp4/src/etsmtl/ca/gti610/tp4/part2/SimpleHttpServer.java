package etsmtl.ca.gti610.tp4.part2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {

	public static void main(String[] args) throws Exception {
		if(args.length != 1) {
			System.out.println("Usage: server port");
			return;
		}
		
		int port = Integer.parseInt(args[0]);
		SimpleHttpServer server = new SimpleHttpServer(port);
		server.run();
		server.close();
	}
	
	ServerSocket m_socket;
	
	public SimpleHttpServer(int port) throws IOException {
		m_socket = new ServerSocket(port);
	}
	
	public void run() throws IOException {
		while(true) {
			Socket client = m_socket.accept();
			serve(client);			
		}
	}
	
	public void serve(Socket client) {
		Thread handler = new ClientHandler(client);
		handler.start();
	}
	
	public void close() throws IOException {
		m_socket.close();
	}
}
