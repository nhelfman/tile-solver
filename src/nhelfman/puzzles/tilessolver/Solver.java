package nhelfman.puzzles.tilessolver;

import java.util.ArrayList;
import java.util.List;



public class Solver
{
	private final Board board;
	private final List<Tile> tiles;
	private int iteration;
	private long start;
	private final boolean debug;
	
	public Solver(Board board, List<Tile> tiles)
	{
		this(board, tiles, true /* debug on by default*/);
	}
	
	public Solver(Board board, List<Tile> tiles, boolean debug)
	{
		this.board = board;
		this.tiles = tiles;
		this.debug = debug;
	}
	
	public boolean solve()
	{
		
		start = System.currentTimeMillis();
		
		int totalTilesBlocks = 0;
		for(Tile t : tiles)
		{
			totalTilesBlocks += t.count;
		}
		
		if (board.freeBlocks() != totalTilesBlocks)
		{
			// available blocks on board does not match total number of tile blocks therefore there is no solution
			return false;
		}
		
		iteration = 0;
		
		return solve(tiles);
	}
	
	static int level = 0;
	
	private boolean solve(List<Tile> remaining)
	{
		try
		{
			level++;

			debugln("--------------------------------------------------");
			debug("level: " + level + " remaining ");
			printTiles(remaining);

			List<Tile> removed = new ArrayList<Tile>();
			
			while (remaining.size() > 0)
			{
				Tile tile = remaining.remove(0);
				debugln("got tile: " + tile.getName());
				
				removed.add(tile);
				
				boolean tilePlaced = false;
				for (int y = 0; y < board.height(); y++)
				{
					for (int x = 0; x < board.width(); x++)
					{
						tile.pos(x, y);

						for (int flips = 0; flips < 2; flips++)
						{
							for (int i=0; i < 4; i++)
							{
								debugln("Try put tile " + tile.getName() + " at [" + tile.x + "," + tile.y + "]");
								
								iteration++;
								
								if (iteration % 1000000 == 0)
								{
									long elapsed = System.currentTimeMillis() - start + 1;
									long rate = iteration * 1000L / elapsed;
									
									System.out.println(">>>rate=" + rate + " tries/s");
								}
								
								if (board.put(tile))
								{
									debugln("Safe put tile " + tile.getName() + "\n" + tile);
									debugln("Board:\n" + board);
									tilePlaced = true;

									if (solve(remaining))
									{
										return true;
									} 
									else
									{
										debugln("Removing tile " + tile.getName());
										board.remove(tile);
									}
								}
								
								debugln("Rotating tile");
								tile.rotate();
							}
							
							debugln("Flipping tile");
							tile.hflip();
						}
					}
				}
				
				if (!tilePlaced)
				{
					// we searched everywhere and failed to put it any place
					debugln("Did not place tile " + tile.getName());
				}
			}
			
			if (board.freeBlocks() == 0)
			{
				// solution complete
				return true;
			}
			
			debugln("No solution found");
			
			// add back tiles that were removed
			debug("Adding removed ");
			printTiles(removed);
			for (Tile t : removed)
			{
				remaining.add(t);
			}
			
			return false;
		} 
		finally
		{
			level--;
		}
	}

	private void printTiles(List<Tile> tiles)
	{
		debug("tiles: [");
		for (Tile t : tiles)
		{
			debug(t.getName() + " ");
		}
		debugln("]");
	}
	
	
	private void debugln(String s)
	{
		if (debug )
		{
			System.out.println(s);
		}
	}
	
	private void debug(String s)
	{
		if (debug )
		{
			System.out.print(s);
		}
	}
}
