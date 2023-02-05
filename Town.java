package edu.iastate.cs228.hw1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/**
 *  @author Holden Brown
 *
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		this.length = length;
		this.width = width;
		grid = new TownCell[length][width]; 
	}  
	/**
	 * replaces the old old TownCell grid with the states in the given TownCell grid
	 * @param gr
	 */
	public void gridUpdate(TownCell[][] gr) {
		
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < width; j++) {
				this.grid[i][j] = gr[i][j];
			}
		}
	}  
	/**
	 * returns the state in TownCell grid at given row and col 
	 * @param row
	 * @param col
	 * @return state at given row col
	 */
	public State getCell(int row, int col) {
		return grid[row][col].who();
	}
	

	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File file = new File(inputFileName);
		Scanner s = new Scanner(file);
		int row = 0;
		int col = 0;
		
		String firstLine = s.nextLine();
		firstLine.trim();
		int len = firstLine.length();
		for(int i = 0; i < len; i++) {
			if(firstLine.charAt(i) == ' ') {
				String strlength = firstLine.substring(0,i);
				strlength = strlength.trim();
				String strwidth = firstLine.substring(i+1,len);
				strwidth = strwidth.trim();
				
				length = Integer.valueOf(strlength);
				width = Integer.valueOf(strwidth);
			}
		}
		grid = new TownCell[length][width];
		while(s.hasNextLine()) {
			String line = s.nextLine();
			Scanner s2 = new Scanner(line);
			col = col % width;
			
			while(s2.hasNext() && col < width) {
				String papi = s2.next();
				
				if(papi.equals("c") || papi.equals("C")) {
					grid[row][col] = new Casual(this,row,col);
				}
				else if(papi.equals("s") || papi.equals("S")) {
					grid[row][col] = new Streamer(this,row,col);
				}
				else if(papi.equals("r") || papi.equals("R")) {
					grid[row][col] = new Reseller(this,row,col);
				}
				else if(papi.equals("e") || papi.equals("E")) {
					grid[row][col] = new Empty(this,row,col);
				}
				else{
					grid[row][col] = new Outage(this,row,col);
				}
				
				col++;
			}
			row++;
			s2.close();
		}
		
		s.close();
	}
	
	/**
	 * Returns width of the grid.
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		int newRandomValue = rand.nextInt(5);
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				newRandomValue = rand.nextInt(5);
				if(newRandomValue == 0) {
					grid[i][j] = new Reseller(this,i,j);
				}
				if(newRandomValue == 1) {
					grid[i][j] = new Empty(this,i,j);
				}
				if(newRandomValue == 2) {
					grid[i][j] = new Casual(this,i,j);
				}
				if(newRandomValue == 3) {
					grid[i][j] = new Outage(this,i,j);
				}
				if(newRandomValue == 4) {
					grid[i][j] = new Streamer(this,i,j);
				}
				
			}
		}
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				
				try {
				
				if(grid[i][j].who() == State.CASUAL) {
					s += "C  ";
				}
				if(grid[i][j].who() == State.STREAMER) {
					s += "S  ";
				}
				if(grid[i][j].who() == State.RESELLER) {
					s += "R  ";
				}
				if(grid[i][j].who() == State.EMPTY) {
					s += "E  ";
				}
				if(grid[i][j].who() == State.OUTAGE) {
					s += "O  ";
				}
				
				}
				catch(NullPointerException e) {
					System.out.print("Error Null pointer ");
				}
				
			}
			s += "\n";
		}
		return s;
	}
}






