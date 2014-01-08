package ca.etsmtl.log320.tp3.loa;

public class Position {
	public int x;
	public int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isAdjacent(Position other) {
		int dx = other.x - x;
		int dy = other.y - y;
		dx *= dx;
		dy *= dy;
		return  dx <= 1 && dy <= 1;
	}
}
