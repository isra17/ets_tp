package etsmtl.ca.gti610.tp4.part2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

public class HttpResponse {
	String m_status;
	String m_server = "Simple http server 0.1";
	int m_contentLength = 0;
	String m_contentType = "application/octet-stream";
	InputStream m_content = null;
		
	public HttpResponse(String status) {
		m_status = status;
	}		
	
	void setContent(InputStream input) throws IOException {
		m_content = input;
		m_contentLength = input.available();
	}
	
	void setContentType(String contentType) {
		m_contentType = contentType;
	}
	
	void send(OutputStream out) throws IOException {
		if(m_contentType == null)
			m_contentType = URLConnection.guessContentTypeFromStream(m_content);
		if(m_contentType == null)
			m_contentType = "application/octet-stream";
		
		out.write(("HTTP/1.1 " + m_status + "\r\n").getBytes());
		out.write(("Server: " + m_server + "\r\n").getBytes());
		out.write(("Content-Length: " + m_contentLength + "\r\n").getBytes());
		out.write(("Content-Type: " + m_contentType + "\r\n").getBytes());
		out.write("\r\n".getBytes());
		
		if(m_content != null) {
			byte[] buf = new byte[512];
			int n = 0;
			while((n = m_content.read(buf)) != -1) {
				out.write(buf, 0, n);
			}
		}
	}
}
