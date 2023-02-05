package edu.iastate.cs228.hw1;


/**
 * @author Holden Brown
 */

public class Casual extends TownCell {
	
	public Casual(Town p, int r, int c) {
		super(p,r,c);
	}
	
	public State who() {
		return State.CASUAL;
	}
	
	public TownCell next(Town tnew) {
		return tnew.grid[row][col];
	}
	
}
