package etsmtl.ca.log320.tp2.sudoku;


public abstract class Grid implements Cloneable {
	public class CaseId {
		public int x;
		public int y;
	}
	
	public abstract boolean setCase(int x, int y, int value);
	public abstract int getCase(int x, int y);
	public abstract boolean testCase(int x, int y, int i);
	
	public int getUnit(int x, int y) {
		return 0;
	};
	
	public CaseId getBestCase() {
		return null;
	};
	
	public Grid clone() throws CloneNotSupportedException {
		return (Grid) super.clone();
	}
		
	public void print() {
		for(int y=0; y<9; y++) {
			if(y>0 && y%3==0){
				System.out.println("---+---+---");				
			}
			
			for(int x=0; x<9; x++) {
				if(x>0 && x%3==0){
					System.out.print('|');
				}
				System.out.print(getCase(x, y));
			}
			System.out.println();
		}
	}
	
	public boolean isSolved() {
		return false;
	}
}
