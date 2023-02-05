package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * @author Holden Brown
 */
class StreamerTest {
	
	@Test
	void constructor() {
		Town t = new Town(3,3);
		t.grid[0][0] = new Streamer(t,0,0);
		assertEquals(t.grid[0][0].who(),State.STREAMER);
	}
	@Test
	void next() {
		Town t2 = new Town(4,4);
		TownCell[][] g = new TownCell[][]
		{
			{new Outage(t2,0,0),new Reseller(t2,0,1),new Outage(t2,0,2),new Reseller(t2,0,3)},
			{new Empty(t2,1,0),new Empty(t2,1,1),new Casual(t2,1,2),new Outage(t2,1,3)},
			{new Empty(t2,2,0),new Streamer(t2,2,1),new Outage(t2,2,2),new Streamer(t2,2,3)},
			{new Empty(t2,3,0),new Outage(t2,3,1),new Reseller(t2,3,2),new Reseller(t2,3,3)},
		};
		t2.gridUpdate(g);
		
		Town tnew = ISPBusiness.updatePlain(t2);
		assertEquals(t2.grid[2][1].next(tnew),tnew.grid[2][1]);
		
	}
}
