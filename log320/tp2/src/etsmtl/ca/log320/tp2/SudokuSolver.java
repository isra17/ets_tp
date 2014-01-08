package etsmtl.ca.log320.tp2;

import java.io.IOException;

import etsmtl.ca.log320.tp2.sudoku.Grid;
import etsmtl.ca.log320.tp2.sudoku.Loader;

public class SudokuSolver {	
	
	private static int NodeVisited = 0;
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Usage: SudokuSolver sudokuFile.sud");
			return;
		}
		
		String filename = args[0];
		try {
			Grid grid = Loader.load(filename);
			NodeVisited = 0;
			final long startTime = System.currentTimeMillis();
			boolean isSolved = solve(grid);
			final long endTime = System.currentTimeMillis();
			System.out.println("Total execution time: " + (endTime - startTime) + " ms");
			
			if(isSolved) {
				System.out.println("Grid solved:");
				grid.print();
				System.out.println(String.format("%d nodes visted", NodeVisited));
			} else {
				System.out.println("Cannot solve grid");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private static boolean solve(Grid grid){
		NodeVisited++;
		for(int x=0; x<9; x++) {
			for(int y=0; y<9; y++) {
				if(grid.getCase(x,y) == Grid.Unknown) {
					for(int i=1; i<=9; i++) {
						if(grid.testCase(x,y,i)) {
							grid.setCase(x, y, i);
							if(solve(grid)) {
								return true;
							} else {
								grid.setCase(x, y, Grid.Unknown);
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}

}
