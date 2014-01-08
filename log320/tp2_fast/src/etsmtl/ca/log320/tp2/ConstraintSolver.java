package etsmtl.ca.log320.tp2;

import etsmtl.ca.log320.tp2.sudoku.BinaryGrid;
import etsmtl.ca.log320.tp2.sudoku.Grid;

public class ConstraintSolver extends SudokuSolver {
	
	protected Grid solve(Grid grid){
		try {
			NodeVisited++;
			Grid.CaseId bestCase = grid.getBestCase();
			if(bestCase == null) {
				return grid;
			}
				
			int unit = grid.getUnit(bestCase.x, bestCase.y);
			for(int i=1; unit > 0; unit = unit >> 1, i++) {
				if((unit & 1) == 1) {
					Grid newGrid = grid.clone();
					if( !newGrid.setCase(bestCase.x, bestCase.y, i) ) {
						continue;
					}
					
					if( (newGrid = solve(newGrid)) != null) {
						return newGrid;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return null;
	}

	public static void main(String[] args) {
		SudokuSolver solver = new ConstraintSolver();		
		solver.run(args);
	}

	@Override
	protected Grid createGrid() {
		return new BinaryGrid();
	}	
}
