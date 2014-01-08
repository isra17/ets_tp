package etsmtl.ca.log320.tp1.huffman;

import java.io.IOException;
import java.io.InputStream;

public class InputBitStream {
	InputStream m_in;
	int m_bufferByte;
	int m_paddingByte;
	int m_eofByte;
	int m_bitIndex;
	int m_end = Integer.MAX_VALUE;
	
	public InputBitStream(InputStream in) throws IOException {
		m_in = in;
		m_paddingByte = in.read();
		m_eofByte = in.read();
		if(m_eofByte == -1|| m_paddingByte == -1){
			m_end = 0;
		}
	}
	
	public int read() throws IOException {
		if(m_bitIndex >= m_end) return -1;
		if(m_bitIndex % 8 == 0) {
			m_bufferByte = m_paddingByte;
			m_paddingByte = m_eofByte;
			m_eofByte = m_in.read();
			if(m_eofByte == -1) {
				m_end = m_bitIndex + 8 - m_paddingByte;
			}
		}
		
		return (m_bufferByte >> (m_bitIndex++ % 8)) & 1;
	}
	
	public int readByte() throws IOException {
		if(m_bitIndex % 8 != 0) {
			goToNextByte();
		}
		
		m_bufferByte = m_paddingByte;
		m_paddingByte = m_eofByte;
		m_eofByte = m_in.read();
		if(m_eofByte == -1) {
			m_end = m_bitIndex + (8-m_paddingByte);
		}
		
		m_bitIndex += 8;
		
		return m_bufferByte;
	}
	
	public void goToNextByte() throws IOException {
		while(m_bitIndex % 8 != 0 && read() != -1) {}
	}
}
