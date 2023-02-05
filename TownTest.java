package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
/**
 * @author Holden Brown
 */
import org.junit.jupiter.api.Test;

class TownTest {

	@Test
	void lengthWidth() {
		Town t = new Town(3,3);
		assertEquals(t.getLength(),3);
		assertEquals(t.getWidth(),3);
		
	}
	@Test
	void lengthWidthBigger() {
		Town t = new Town(5,9);
		assertEquals(t.getLength(),5);
		assertEquals(t.getWidth(),9);
	}
	@Test
	void tostring() {
		Town t = new Town(5,5);
		t.randomInit(5);
		System.out.println("should be 5x5 random grid");
		System.out.println(t.toString());
	}
	@Test
	void updateCells() {
		Town t = new Town(5,5);
		t.randomInit(5);
		TownCell[][] g = new TownCell[t.getLength()][t.getWidth()];
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				g[i][j] = new Casual(t,i,j);
			}
		}
		t.gridUpdate(g);
		assertTrue(Arrays.deepEquals(g, t.grid));
	}
	@Test
	void fileConstructor() throws FileNotFoundException {
		//these are equal as can be seen through the print statments I dont 
		//understand what is wrong with the asserttrue statement
		Town t2 = new Town(4,4);
		TownCell[][] g = new TownCell[][]
		{
			{new Outage(t2,0,0),new Reseller(t2,0,1),new Outage(t2,0,2),new Reseller(t2,0,3)},
			{new Empty(t2,1,0),new Empty(t2,1,1),new Casual(t2,1,2),new Outage(t2,1,3)},
			{new Empty(t2,2,0),new Streamer(t2,2,1),new Outage(t2,2,2),new Streamer(t2,2,3)},
			{new Empty(t2,3,0),new Outage(t2,3,1),new Reseller(t2,3,2),new Reseller(t2,3,3)},
		};
		t2.gridUpdate(g);
		Town t = new Town("C:\\Users\\Mr. Mister\\Desktop\\Com s 228\\hw1\\ISP4x4.txt");
		System.out.println(t.toString() );
		System.out.println(t2.toString());
		assertTrue(Arrays.deepEquals(t2.grid, t.grid));
	}
}
