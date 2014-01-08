package etsmtl.ca.log320.tp2.sudoku;

public class BinaryGrid extends Grid {
	public static final int DIMENSION = 9;
	
	public static final int UNKNOWN = 0x7ffffff;
	
	public static final int UNIT_MASK = 0x1FF;	
	public static final int COL_OFF = 0;
	public static final int COL_MASK = UNIT_MASK << COL_OFF;
	public static final int ROW_OFF = DIMENSION;
	public static final int ROW_MASK = UNIT_MASK;
	public static final int GROUP_OFF = DIMENSION*2;
	public static final int GROUP_MASK = UNIT_MASK << GROUP_OFF;	
	
	int[] m_gridArray;
	
	public BinaryGrid() {
		m_gridArray = new int[DIMENSION*DIMENSION];
		for(int i=0; i < m_gridArray.length; i++) {
			m_gridArray[i] = UNKNOWN; 
		}
	}
	
	public boolean setCase(int x, int y, int value) {		
		int shift = 1 << value - 1;
		shift = shift << GROUP_OFF |
				shift << COL_OFF |
				shift << ROW_OFF;
		m_gridArray[x * DIMENSION + y] = shift;
				
		// Propagate constraint
		int unshift = ~shift;
		
		// col
		for(int i = 0; i < DIMENSION; i++) {
			if(i == x)
				continue;
			
			int unit = m_gridArray[i * DIMENSION + y];
			
			if((unit & shift) > 0) {
				unit &= unshift;
				m_gridArray[i * DIMENSION + y] = unit;
				
				if(unit == 0) {
					return false;
				} else if(countChoice(unit) == 1) {
					if(!setCase(i, y, getValue(unit)))
						return false;
				}
			}
		}
		
		// row
		for(int i = 0; i < DIMENSION; i++) {
			if(i == y)
				continue;
						
			int unit = m_gridArray[x * DIMENSION + i];
			
			if((unit & shift) > 0) {
				unit &= unshift;
				m_gridArray[x * DIMENSION + i] = unit;
				
				if(unit == 0) {
					return false;
				} else if(countChoice(unit) == 1) {
					if(!setCase(x, i, getValue(unit)))
						return false;
				}
			}
		}
		
		// groups
		int gx = (int)(x/3) * 3;
		int gy = (int)(y/3) * 3;
		for(int i = 0; i < DIMENSION; i++) {
			int a = gx + (int)(i/3);
			int b = gy + i%3;
			if(a == x || b == y)
				continue;
			
			int unit = m_gridArray[a * DIMENSION + b];
			
			if((unit & shift) > 0) {
				unit &= unshift;
				m_gridArray[a * DIMENSION + b] = unit;
				
				if(unit == 0) {
					return false;
				} else if(countChoice(unit) == 1) {
					if(!setCase(a, b, getValue(unit)))
						return false;
				}
			}
		}
		
		return true;
	}
	
	public int countChoice(int unit) {
		return Integer.bitCount((unit | unit >> 9 | unit >> 18) & UNIT_MASK);
		
		/*int i = (unit | unit >> 9 | unit >> 18) & UNIT_MASK;
		i = i - ((i >>> 1) & 0x55555555);
	    i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
	    return (((i + (i >>> 4)) & 0x0F0F0F0F) * 0x01010101) >>> 24;*/
	}

	@Override
	public int getCase(int x, int y) {
		int unit = m_gridArray[x * DIMENSION + y];
		return  countChoice(unit) == 1? getValue(unit): 0;
	}

	@Override
	public int getUnit(int x, int y) {
		int unit = m_gridArray[x * DIMENSION + y];
		return (unit | unit >> 9 | unit >> 18) & UNIT_MASK;
	}
	
	public int getValue(int unit) {
		int bitValue = unit & UNIT_MASK;
		int i;
		for(i = 0; bitValue > 0; bitValue = bitValue >> 1, i++);
		return i;
	}

	public boolean testCase(int x, int y, int i) {
		int unit = m_gridArray[x * DIMENSION + y];
		
		int shift = 1 << i - 1;
		shift = shift << GROUP_OFF |
				shift << COL_OFF |
				shift << ROW_OFF;
		
		return (unit & shift) > 0;
	}
	
	public CaseId getBestCase() {
		int bestCase = 10;
		CaseId caseId = new CaseId();
		for(int i=0; i < m_gridArray.length; i++) {
			int unit = m_gridArray[i];
			int c = countChoice(unit);
			if(c > 1 && bestCase > c) {
				bestCase = c;
				caseId.x = i/DIMENSION;
				caseId.y = i%DIMENSION;
			}
		}
		
		return bestCase < 10? caseId: null;
	};
	
	@Override
	public Grid clone() {
		BinaryGrid grid = new BinaryGrid();		
		grid.m_gridArray = m_gridArray.clone();
		return grid;
	}
}
