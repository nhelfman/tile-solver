import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/*
 * Basic testing
 * 
 */
public class Test1 
{
	Tile t1 = new Tile("1", new String[][] 
			{
				{"X", "X"},
				{"X", null}
			}
	);
	
	Tile t2 = new Tile("2", new String[][] 
			{
				{"X"}
			}
	);
	
	Tile t3 = new Tile("3", new String[][] 
			{
				{"X"},
				{"X"},
			}
	);
	
	Tile t4 = new Tile("4", new String[][] 
			{
				{"X", "X", "X"}
			}
	);

	@Test
	public void testTile() 
	{
		Tile t = new Tile("t", new String[][] 
				{
					{"X", "X", "X"},
					{"X", "X", null},
					{"X", null, null}
				}
		);

		System.out.println(t);
		
		assertEquals(0, t.x);
		assertEquals(0, t.y);
		assertEquals(6, t.count);
		
		t.pos(2, 3);
		assertEquals(2, t.x);
		assertEquals(3, t.y);
		
		t = new Tile("t", new String[][] 
				{
					{"X"}
				}
		);
		
		assertEquals(1, t.count);
	}
	
	@Test
	public void testTileHfilp()
	{
		Tile t = new Tile("t", new String[][] 
				{
					{"O", "X", "O", "X"},
					{"X", "O", null, null},
					{"O", null, null, null}
				}
		);
		
		t.hflip();
		
		System.out.println(t);
		
		t.hflip();
		
		System.out.println(t);
		
		Tile t2 = new Tile("t2", new String[][] 
				{
					{"O", "X"}
				}
		);
		
		t2.hflip();
		System.out.println(t2);
		
		Tile t3 = new Tile("t3", new String[][] 
				{
					{"O"}
				}
		);
		
		t3.hflip();
		System.out.println(t3);
	}
	
	@Test
	public void testTileRotate()
	{
		Tile t = new Tile("t", new String[][] 
				{
					{"O", "X", "O", "X"},
					{"X", "O", null, null},
					{"O", null, null, null}
				}
		);
		
		System.out.println("base:" + t);
		
		t.rotate();
		
		System.out.println("rotated:" + t);
		
		Tile expected = new Tile("t", new String[][] 
				{
				{"O",  "X",  "O"},
				{null, "O",  "X"},
				{null, null, "O"},
				{null, null, "X"}
			});
		
		System.out.println("expected:" + t);
		
		assertEquals(expected, t);
		
	}
	
	@Test
	public void testBoard() 
	{
		Board b = new Board(3,3);
		
		assertEquals(9, b.freeBlocks());
	}
	
	@Test
	public void testBoard2() 
	{
		Board b = new Board(3, 3, true);
		System.out.println(b);
		assertEquals(9, b.freeBlocks());
	}
	
	@Test
	public void testPut1() 
	{
		Board b = new Board(3,3);
		
		boolean result;
		t1.pos(10, 10);
		result = b.put(t1);
		
		assertFalse(result);
		assertEquals(9, b.freeBlocks());
		
		t1.pos(2, 0);
		result = b.put(t1);
		assertFalse(result);
		assertEquals(9, b.freeBlocks());
		
		t3.pos(0, 2);
		result = b.put(t3);
		assertFalse(result);
		assertEquals(9, b.freeBlocks());
		
		t1.pos(0, 0);
		result = b.put(t1);
		assertTrue(result);
		assertEquals(6, b.freeBlocks());
		
		System.out.println(t1);
		System.out.println(b.toString());

		t2.pos(1, 1);
		result = b.put(t2);
		assertTrue(result);
		assertEquals(5, b.freeBlocks());
		
		t3.pos(2, 0);
		result = b.put(t3);
		assertTrue(result);
		assertEquals(3, b.freeBlocks());
		
		System.out.println(t3);
		System.out.println(b.toString());

		t4.pos(0, 2);
		result = b.put(t4);
		assertTrue(result);
		assertEquals(0, b.freeBlocks());
		
		System.out.println(t4);
		System.out.println(b.toString());
	}
	
	@Test (expected=IllegalStateException.class)
	public void testPut2() 
	{
		Board b = new Board(3, 3);
		
		t2.pos(1, 1);
		b.put(t2);
		
		t2.pos(2, 1);
		b.put(t2); // shouldn't be able to put the same tile twice
	}
	
	@Test
	public void testPutCheckerBoard() 
	{
		Board b = new Board(3, 3, true);
		
		Tile t = new Tile("1", new String[][] 
				{
					{"O", "X"},
					{"X", null}
				}
		);
		
		t.pos(0, 0);
		assertFalse(b.put(t));
		
		t.pos(1, 1);
		assertFalse(b.put(t));
		
		t.pos(1, 0);
		assertTrue(b.put(t));
		System.out.println(b);
		b.remove(t);
		
		
		t.pos(0, 1);
		assertTrue(b.put(t));
		System.out.println(b);
		b.remove(t);
		
		
	}
	
	@Test
	public void testRemove1() 
	{
		Board b = new Board(3, 3);
	
		t1.pos(0, 0);
		b.put(t1);
		b.remove(t1);
		
		System.out.println(b.toString());
		
		assertEquals(9, b.freeBlocks());
	}

	@Test (expected=IllegalStateException.class)
	public void testRemove2() 
	{
		Board b = new Board(3, 3);
		
		t1.pos(0, 0);
		b.put(t1);
		
		t1.pos(1, 0);
		b.remove(t1);
	}
	
	@Test
	public void testSolver1()
	{
		Board b = new Board(3, 3);
		
		List<Tile> tiles = new ArrayList(Arrays.asList(new Tile[]{t1, t2, t3, t4}));
				
		Solver solver = new Solver(b, tiles);
		
		boolean result = solver.solve();
		assertTrue(result);
	}
	
	@Test
	public void testSolver2()
	{
		Board b = new Board(3, 3);
		
		List<Tile> tiles = new ArrayList(Arrays.asList(new Tile[]{t4, t3, t2, t1}));
				
		Solver solver = new Solver(b, tiles);
		
		boolean result = solver.solve();
		assertTrue(result);
	}
	
	@Test
	public void testSolver3()
	{
		Board b = new Board(3, 3);
		
		List<Tile> tiles = new ArrayList(Arrays.asList(new Tile[]{t3, t2, t1, t4}));
				
		Solver solver = new Solver(b, tiles);
		
		boolean result = solver.solve();
		assertTrue(result);
	}
	
	@Test
	public void testSolverWithFlip()
	{
		Board b = new Board(3, 3);
		
		Tile t1 = new Tile("1", new String[][] 
				{
					{"X", "X", "X"},
					{"X", null, null}
				}
		);
		
		// this one has to be fliped for a solution to exist
		Tile t2 = new Tile("2", new String[][] 
				{
					{"X", null, null},
					{"X", "X", "X"},
				}
		);
		
		Tile t3 = new Tile("3", new String[][] 
				{
					{"X"}
				}
		);
		
		List<Tile> tiles = new ArrayList(Arrays.asList(new Tile[]{t1, t2, t3}));
				
		Solver solver = new Solver(b, tiles);
		
		boolean result = solver.solve();
		assertTrue(result);
	}
	
	@Test
	public void testSolverCheckerBoard()
	{
		Board b = new Board(3, 3, true);
		
		Tile c1 = new Tile("1", new String[][] 
				{
					{"X", "O"},
					{"O", null}
				}
		);
		
		Tile c2 = new Tile("2", new String[][] 
				{
					{"X"}
				}
		);
		
		Tile c3 = new Tile("3", new String[][] 
				{
					{"O"},
					{"X"},
				}
		);
		
		Tile c4 = new Tile("4", new String[][] 
				{
					{"X", "O", "X"}
				}
		);
		
		List<Tile> tiles = new ArrayList(Arrays.asList(new Tile[]{c1, c2, c3, c4}));
				
		Solver solver = new Solver(b, tiles);
		
		boolean result = solver.solve();
		assertTrue(result);
	}
	
	@Test
	public void testSolverRotate()
	{
		// create puzzle that cannot be solved without rotating a tile
		Board b = new Board(3, 3);
		
		Tile t1 = new Tile("1", new String[][] 
				{
					{"X", "X", "X"},
				}
		);
		
		Tile t2 = new Tile("2", new String[][] 
				{
					{"X", "X", "X"},
				}
		);
		
		Tile t3 = new Tile("3", new String[][] 
				{
					{"X"},
					{"X"},
					{"X"},
				}
		);
		
		
		
		List<Tile> tiles = new ArrayList(Arrays.asList(new Tile[]{t1, t2, t3}));
				
		Solver solver = new Solver(b, tiles);
		
		boolean result = solver.solve();
		assertTrue(result);
	}
}
