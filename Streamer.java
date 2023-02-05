package edu.iastate.cs228.hw1;
/**
 * @author Holden Brown
 */
public class Streamer extends TownCell {
	
	public Streamer(Town p, int r, int c) {
		super(p,r,c);
	}
	
	public State who() {
		return State.STREAMER;
	}
	
	public TownCell next(Town tnew) {
		return tnew.grid[row][col];
	}
}
