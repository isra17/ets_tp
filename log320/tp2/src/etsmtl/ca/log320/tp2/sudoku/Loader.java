package etsmtl.ca.log320.tp2.sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Loader {
	public static Grid load(String path) throws IOException {
		BufferedReader reader = null;
		Grid grid = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			grid = new Grid();
			
			String line;
			int x, y = 0;
			while((line = reader.readLine()) != null) {
				if(y >= 9)
					throw new RuntimeException("Invalid file format");
				
				x = 0;
				
				for(char c : line.toCharArray()) {
					if(x >= 9)
						throw new RuntimeException("Invalid file format");
					
					int value = Character.digit(c, 10);
					grid.setCase(x,y, value);
					x++;
				}
				y++;				
			}			
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(reader != null)
				reader.close();
		}		
		
		return grid;
	}
}
