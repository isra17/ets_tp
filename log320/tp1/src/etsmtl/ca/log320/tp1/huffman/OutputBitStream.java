package etsmtl.ca.log320.tp1.huffman;

import java.io.IOException;
import java.io.OutputStream;

public class OutputBitStream {
	private int m_bitIndex = 0;
	private OutputStream m_out;
	int m_bufferByte=0;
	
	public OutputBitStream(OutputStream out) {
		m_out = out;
	}
	
	public void write(int bits, int size) throws IOException {
		if(size == 0) return;
		
		int byteSpace = 8 - (m_bitIndex % 8);
		int adjustedSize = Math.min(size, byteSpace);
		
		m_bufferByte = m_bufferByte | ((bits & (0xFF >> (8-adjustedSize))) << (8-byteSpace));
		m_bitIndex += adjustedSize;
		
		if(byteSpace - adjustedSize == 0) {
			m_out.write(m_bufferByte);
			m_bufferByte = 0;

			write(bits >> adjustedSize, size - adjustedSize);
		}		
	}
	
	public int flushLastByte() throws IOException {
		int padding = m_bitIndex % 8;
		if(padding != 0) {
			m_out.write(m_bufferByte);
			m_bitIndex += 8 - padding;			
			return 8 - padding;
		}
		
		return 0;
	}
}
