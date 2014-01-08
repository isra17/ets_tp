package etsmtl.ca.log320.tp2;

import java.io.IOException;

import etsmtl.ca.log320.tp2.sudoku.Grid;
import etsmtl.ca.log320.tp2.sudoku.Loader;

public abstract class SudokuSolver {	
	
	static private final int N = 1; 
	
	protected int NodeVisited = 0;
	
	protected void run(String[] args) {
		if(args.length != 1) {
			System.out.println("Usage: SudokuSolver sudokuFile.sud");
			return;
		}
		
		String filename = args[0];
		try {
			long totalTime = 0;
			Grid solvedGrid = null;
			for(int i=0; i<N; i++) {
				Grid grid = createGrid();
				Loader.load(filename, grid);
				NodeVisited = 0;
				final long startTime = System.currentTimeMillis();
				solvedGrid = solve(grid);
				final long endTime = System.currentTimeMillis();
				
				totalTime += endTime - startTime;
			}
			System.out.println("Total execution time: " + totalTime + " ms with N=" + N + " (Average: " + (float)totalTime/N + " ms)");
			
			if(solvedGrid != null) {
				if(!isCorrect(solvedGrid)) {
					System.out.println("Solved grid incorrect...");
					return;
				}
				
				System.out.println("Grid solved:");
				solvedGrid.print();				
			} else {
				System.out.println("Cannot solve grid");
			}
			System.out.println(String.format("%d nodes visted", NodeVisited));
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public boolean isCorrect(Grid grid) {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(!checkCase(grid, i, j))
					return false;
			}
		}
		
		return true;
	}
	
	private boolean checkCase(Grid grid, int x, int y) {
		int val = grid.getCase(x, y);
		for(int i=0; i<9; i++) {
			if(i == x)
				continue;
			
			if(grid.getCase(i, y) == val)
				return false;
		}
		for(int i=0; i<9; i++) {
			if(i == y)
				continue;
			
			if(grid.getCase(x, i) == val)
				return false;
		}
		
		int gx = (int)(x/3) * 3;
		int gy = (int)(y/3) * 3;
		
		for(int i=0; i<3; i++) {
			for(int j=0; i<3; i++) {
				if((gx+i) == x && (gy+j) == y)
					continue;
				
				if(grid.getCase(gx+i, gy+j) == val)
					return false;
			}
		}
		return true;
	}
	
	abstract protected Grid createGrid();

	abstract protected Grid solve(Grid grid);
}
