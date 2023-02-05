package edu.iastate.cs228.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Holden Brown
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		TownCell[][] gNew = new TownCell[tOld.getLength()][tOld.getWidth()];

		
			
		
		for(int i = 0; i < tOld.getLength(); i++) {
			for(int j = 0; j < tOld.getWidth(); j++) {
				tOld.grid[i][j].census(tOld.grid[i][j].nCensus);
				
				if(tOld.grid[i][j].who().equals(State.CASUAL)) {
					
					if(tOld.grid[i][j].nCensus[0] > 0) {
						gNew[i][j] = new Outage(tNew,i,j);
					}
					else if(tOld.grid[i][j].nCensus[4] > 0) {
						gNew[i][j] = new Streamer(tNew,i,j);
					}
					else if((tOld.grid[i][j].nCensus[1] + tOld.grid[i][j].nCensus[3]) <= 1) {
						gNew[i][j] = new Reseller(tNew,i,j);
					}
					else if(tOld.grid[i][j].nCensus[2] >= 5) {
						gNew[i][j] = new Streamer(tNew,i,j);
					}
					else {
						gNew[i][j] = tOld.grid[i][j];
					}
				}
				
				else if(tOld.grid[i][j].who().equals(State.STREAMER)) {
					
					if(tOld.grid[i][j].nCensus[0] > 0) {
						gNew[i][j] = new Outage(tNew,i,j);
					}
					else if(tOld.grid[i][j].nCensus[3] > 0) {
						gNew[i][j] = new Empty(tNew,i,j);
					}
					else if((tOld.grid[i][j].nCensus[1] + tOld.grid[i][j].nCensus[3]) <= 1) {
						gNew[i][j] = new Reseller(tNew,i,j);
					}
					else if(tOld.grid[i][j].nCensus[2] >= 5) {
						gNew[i][j] = new Streamer(tNew,i,j);
					}
					else {
						gNew[i][j] = tOld.grid[i][j];
					}
				}
				
				
				else if(tOld.grid[i][j].who().equals(State.RESELLER)) {
					if(tOld.grid[i][j].nCensus[2] <= 3 || tOld.grid[i][j].nCensus[1] >= 3) {
						gNew[i][j] = new Empty(tNew,i,j);
					}
					else if(tOld.grid[i][j].nCensus[2] >= 5) {
						gNew[i][j] = new Streamer(tNew,i,j);
					}
					else {
						gNew[i][j] = tOld.grid[i][j];
					}
				}
				
				else if(tOld.grid[i][j].who().equals(State.OUTAGE)) {
					
					if(tOld.grid[i][j].nCensus[2] >= 5) {
						gNew[i][j] = new Streamer(tNew,i,j);
					}
					else {
						gNew[i][j] = new Empty(tNew,i,j);
					}
				}
				
				else if(tOld.grid[i][j].who().equals(State.EMPTY)) {
					if((tOld.grid[i][j].nCensus[1] + tOld.grid[i][j].nCensus[3]) <= 1) {
						gNew[i][j] = new Reseller(tNew,i,j);
					}
					else {
						gNew[i][j] = new Casual(tNew,i,j);
					}
				}
			}
		}
		
		tNew.gridUpdate(gNew);
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		
		int result = 0;
		for(int i = 0; i < town.getLength(); i++) {
			for(int j = 0; j < town.getWidth(); j++) {
				if(town.grid[i][j].who().equals(State.CASUAL)) {
					result++;
				}
			}
		}
		return result;
	}
	/**
	 * calculates profit utilization for 12 months when given a town
	 * @param t
	 * @return profit utilization
	 */
	public static double profit(Town t) {
		int len = t.getLength();
		int wid = t.getWidth();
		double potential = t.getLength()*t.getWidth()*12.0;
		System.out.println(potential);
		
		Town t2 = new Town(len,wid);
		Town t3 = new Town(len,wid);
		Town t4 = new Town(len,wid);
		Town t5 = new Town(len,wid);
		Town t6 = new Town(len,wid);
		Town t7 = new Town(len,wid);
		Town t8 = new Town(len,wid);
		Town t9 = new Town(len,wid);
		Town t10 = new Town(len,wid);
		Town t11 = new Town(len,wid);
		Town t12 = new Town(len,wid);
		
		t2 = updatePlain(t);
		t3 = updatePlain(t2);
		t4 = updatePlain(t3);
		t5 = updatePlain(t4);
		t6 = updatePlain(t5);
		t7 = updatePlain(t6);
		t8 = updatePlain(t7);
		t9 = updatePlain(t8);
		t10 = updatePlain(t9);
		t11 = updatePlain(t10);
		t12 = updatePlain(t11);
		
		double c = (double)(getProfit(t) + getProfit(t2) + getProfit(t3) + getProfit(t4) + getProfit(t5) + getProfit(t6) + 
				getProfit(t7) + getProfit(t8) + getProfit(t9) + getProfit(t10) + getProfit(t11) + getProfit(t12));
		
		return (double)(  (100*c)/potential   );
	}
	

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */

	public static void main(String []args) throws FileNotFoundException {
		Scanner s = new Scanner(System.in);
		System.out.println("How would you like to populate the grid(1 or 2)? \n1: from file address\n2: random with seed");
		int answer = s.nextInt();
		if(answer == 1) {
			System.out.println("Enter file address path ");
			String file = s.nextLine();
			Town t = new Town(file);
			System.out.println(t.toString());
			System.out.println(profit(t));
		}
		else if(answer == 2) {
			System.out.println("Provide rows, cols and seed integer separated by \r\n"
					+ "spaces:");
			int rows = s.nextInt();
			int cols = s.nextInt();
			int seed = s.nextInt();
			Town t = new Town(cols,rows);
			t.randomInit(seed);
			System.out.println(t.toString());
			System.out.println(profit(t));
		}
		
	}
}
