package edu.iastate.cs228.hw1;
/**
 * @author Holden Brown
 */
public class Reseller extends TownCell{
	
	public Reseller(Town p, int r, int c) {
		super(p,r,c);
	}
	
	public State who() {
		return State.RESELLER;
	}
	
	public TownCell next(Town tnew) {
		return tnew.grid[row][col];
	}
}
