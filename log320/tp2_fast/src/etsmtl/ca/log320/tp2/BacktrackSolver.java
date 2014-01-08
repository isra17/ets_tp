package etsmtl.ca.log320.tp2;

import etsmtl.ca.log320.tp2.sudoku.Grid;
import etsmtl.ca.log320.tp2.sudoku.SimpleGrid;

public class BacktrackSolver extends SudokuSolver {

	public static void main(String[] args) {
		SudokuSolver solver = new BacktrackSolver();
		solver.run(args);
	}
	
	protected Grid solve(Grid grid){
		NodeVisited++;
		for(int x=0; x<9; x++) {
			for(int y=0; y<9; y++) {
				if(grid.getCase(x,y) == SimpleGrid.Unknown) {
					for(int i=1; i<=9; i++) {
						if(grid.testCase(x,y,i)) {
							grid.setCase(x, y, i);
							if(solve(grid) != null) {
								return grid;
							} else {
								grid.setCase(x, y, SimpleGrid.Unknown);
							}
						}
					}
					return null;
				}
			}
		}
		return grid;
	}
	
	@Override
	protected Grid createGrid() {
		return new SimpleGrid();
	}
}
