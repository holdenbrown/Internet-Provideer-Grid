package edu.iastate.cs228.hw1;

/**
 * 
 * @author Holden Brown
 *	abstract class that is the adt for TownCell
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;
	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	
	public void helper(int r, int c){
		
		if(plain.getCell(r, c).equals(State.RESELLER)) {
			nCensus[RESELLER] += 1;
		}
		else if(plain.getCell(r, c).equals(State.EMPTY)) {
			nCensus[EMPTY] += 1;
		}
		else if(plain.getCell(r, c).equals(State.CASUAL)) {
			nCensus[CASUAL] += 1;
		}
		else if(plain.getCell(r, c).equals(State.OUTAGE)) {
			nCensus[OUTAGE] += 1;
		}
		else {
			nCensus[STREAMER] += 1;
		}
		
	}

	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 
		
		if( (row - 1) >= 0 && (row + 1) < plain.getLength() && (col - 1) >= 0 && (col + 1) < plain.getWidth() ) {//can move everywhere
			helper(row-1,col); // up
			helper(row,col-1); // left
			helper(row+1,col); // down
			helper(row,col+1); // right
			
			helper(row+1,col+1); // down right
			helper(row+1,col-1); // down left
			helper(row-1,col+1); // up right
			helper(row-1,col-1); // up left
		}
		else { // ( (row - 1) >= 0 ) && ( (row + 1) < plain.getLength() ) && ( (col - 1) >= 0 && (col + 1) < plain.getWidth() )
			  //         up                           down                          left                   right
			if( ( (row - 1) >= 0 ) && !( (row + 1) < plain.getLength() ) && ( (col - 1) >= 0 ) && ( (col + 1) < plain.getWidth() ) ) {//cant move down
				helper(row-1,col); // up
				helper(row,col-1); // left
				helper(row,col+1); // right
				
				helper(row-1,col+1); // up right
				helper(row-1,col-1); // up left
			}	
			else if( !( (row - 1) >= 0 ) && ( (row + 1) < plain.getLength() ) && ( (col - 1) >= 0 ) && ( (col + 1) < plain.getWidth() ) ) {//cant move up
				helper(row,col-1); // left
				helper(row+1,col); // down
				helper(row,col+1); // right
				
				helper(row+1,col+1); // down right
				helper(row+1,col-1); // down left
			}
			else if( ( (row - 1) >= 0 ) && ( (row + 1) < plain.getLength() ) && !( (col - 1) >= 0 ) && ( (col + 1) < plain.getWidth() ) ) {//cant move left
				helper(row-1,col); // up
				helper(row+1,col); // down
				helper(row,col+1); // right
				
				helper(row+1,col+1); // down right
				helper(row-1,col+1); // up right
			}
			else if( ( (row - 1) >= 0 ) && ( (row + 1) < plain.getLength() ) && ( (col - 1) >= 0 ) && !( (col + 1) < plain.getWidth() ) ) {//cant move right
				helper(row-1,col); // up
				helper(row,col-1); // left
				helper(row+1,col); // down
				
				helper(row+1,col-1); // down left
				helper(row-1,col-1); // up left
			}
			else if( ( (row - 1) >= 0 ) && !( (row + 1) < plain.getLength() ) && !( (col - 1) >= 0 ) && ( (col + 1) < plain.getWidth() ) ) {//cant move down & left
				helper(row-1,col); // up
				helper(row,col+1); // right
				
				helper(row-1,col+1); // up right
			}
			else if( ( (row - 1) >= 0 ) && !( (row + 1) < plain.getLength() ) && ( (col - 1) >= 0 ) && !( (col + 1) < plain.getWidth() )) {//cant move down right
				helper(row-1,col); // up
				helper(row,col-1); // left
				
				helper(row-1,col-1); // up left
			}
			else if( !( (row - 1) >= 0 ) && ( (row + 1) < plain.getLength() ) && !( (col - 1) >= 0 ) && ( (col + 1) < plain.getWidth() )) {//cant move up left
				helper(row+1,col); // down
				helper(row,col+1); // right
				
				helper(row+1,col+1); // down right
			}
			else if( !( (row - 1) >= 0 ) && ( (row + 1) < plain.getLength() ) && ( (col - 1) >= 0 ) && !( (col + 1) < plain.getWidth() )) {//cant move up right
				helper(row,col-1); // left
				helper(row+1,col); // down
				
				helper(row+1,col-1); // down left
			}
		}
	}


	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}