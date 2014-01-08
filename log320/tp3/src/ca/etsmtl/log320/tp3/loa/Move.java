package ca.etsmtl.log320.tp3.loa;

public class Move {
	public int xSrc;
	public int ySrc;
	
	public int xDst;
	public int yDst;
	
	public Move(String src, String dst) {
		xSrc = src.charAt(0) - 'A';
		ySrc = Board.DIM - (src.charAt(1) - '0');
		
		xDst = dst.charAt(0) - 'A';
		yDst = Board.DIM - (dst.charAt(1) - '0');
	}
	
	public Move(int xSrc, int ySrc, int xDst, int yDst) {
		this.xSrc = xSrc;
		this.ySrc = ySrc;
		this.xDst = xDst;
		this.yDst = yDst;
	}
	
	public byte[] serialize() {
		byte[] data = new byte[]{
			(byte) ('A' + xSrc),
			(byte) ('0' + Board.DIM - ySrc),
			(byte) ('A' + xDst),
			(byte) ('0' + Board.DIM - yDst)
		};
		
		return data;
	}
	
	@Override
	public String toString() {
		return new String(serialize());
	}
}
