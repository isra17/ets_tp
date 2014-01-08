package etsmtl.ca.gti610.tp4.part2;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLConnection;

public class ClientHandler extends Thread {

	Socket m_clientSocket;
	
	public ClientHandler(Socket clientSocket) {
		m_clientSocket = clientSocket;
	}
	
	public HttpResponse handleRequest(String request) {
		String[] parts = request.split(" ");
		File target = new File("." + parts[1]);
		if(target.isDirectory())
			target = new File(target.getPath() + "/index.html");
		HttpResponse response;
		
		try {
			InputStream input = new FileInputStream(target);
			response = new HttpResponse("200 OK");
			response.setContentType(URLConnection.guessContentTypeFromName(target.getName()));
			response.setContent(input);
		} catch (IOException e) {
			response = new HttpResponse("404 Not Found");
			try {
				response.setContent(new ByteArrayInputStream((parts[1] + " Not Found").getBytes()));
				response.setContentType("text/plain");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
				
		return response;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(m_clientSocket.getInputStream()));
			String request = bufferRead.readLine(); 
			if(request != null) {
				System.out.println(request);
				HttpResponse response = handleRequest(request);
				response.send(m_clientSocket.getOutputStream());
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try{
				m_clientSocket.close();
			} catch(Exception e) {
				
			}
		}
	
	}

}
