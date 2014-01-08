package etsmtl.ca.log320.tp2.sudoku;

public class SimpleGrid extends Grid {

	public static final int Unknown = 0;
	int[][] m_gridArray;
	
	public SimpleGrid() {
		m_gridArray = new int[9][9];
	}
	
	public boolean setCase(int x, int y, int value) {		
		m_gridArray[x][y] = value;
		return true;
	}

	public int getCase(int x, int y) {
		return m_gridArray[x][y];
	}

	public boolean testCase(int x, int y, int i) {
		return testGroup(x,y,i) && testVertical(x, i) && testHorizontal(y, i);
	}
	
	public boolean testGroup(int x, int y, int i) {
		int lx = (int)(x/3)*3;
		int ly = (int)(y/3)*3;
		for(int ix = lx; ix < lx+3; ix++) {
			for(int iy = ly; iy < ly+3; iy++) {
				if(m_gridArray[ix][iy] == i) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean testVertical(int x, int i) {
		for(int y=0; y<9; y++) {
			if(m_gridArray[x][y] == i) {
				return false;
			}
		}
		return true;
	}
	
	public boolean testHorizontal(int y, int i) {
		for(int x=0; x<9; x++) {
			if(m_gridArray[x][y] == i) {
				return false;
			}
		}
		return true;
	}
}
