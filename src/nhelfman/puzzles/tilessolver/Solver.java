package nhelfman.puzzles.tilessolver;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Solver
{
	class Context
	{
		Tile tile;
		int flip;
		int rotation;
		
		@Override
		public String toString()
		{
			return " tile: " + tile.getName() + " [" + tile.x + "," + tile.y + "] flip: " + flip + " rot: " + rotation;
		}
		
		
	}
	
	private final Board board;
	private final List<Tile> tiles;
	private long iteration;
	private long start;
	private final boolean debug;
	
	private Context level1Context = new Context();
	
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
	
	int level = 0;
	
	private boolean solve(List<Tile> remaining)
	{
		try
		{
			level++;

			if (debug)
			{
				debugln("--------------------------------------------------");
				debug("level: " + level + " remaining ");
				printTiles(remaining);
			}
			
			List<Tile> removed = new ArrayList<Tile>();
			
			while (remaining.size() > 0)
			{
				Tile tile = remaining.remove(0);
				
				if (debug)
				{
					debugln("got tile: " + tile.getName());
				}
				
				removed.add(tile);
				
				boolean tilePlaced = false;
				for (int y = 0; y < board.height(); y++)
				{
					for (int x = 0; x < board.width(); x++)
					{
						tile.pos(x, y);

						for (int flips = 0; flips < 2; flips++)
						{
							for (int rotation=0; rotation < 4; rotation++)
							{
								if (tilePlaced)
								{
									debugln("Try put tile " + tile.getName() + " at [" + tile.x + "," + tile.y + "]");
								}
								
								
								printProgress(tile, flips, rotation);
								
								iteration++;
								
								if (board.put(tile))
								{
									if (debug)
									{
										debugln("Safe put tile " + tile.getName() + "\n" + tile);
										debugln("Board:\n" + board);
									}
									
									tilePlaced = true;

									if (solve(remaining))
									{
										return true;
									} 
									else
									{
										if (debug)
										{
											debugln("Removing tile " + tile.getName());
										}
										
										board.remove(tile);
									}
								}
								
								if (debug)
								{
									debugln("Rotating tile");
								}
								
								tile.rotate();
							}
							
							if (debug)
							{
								debugln("Flipping tile");
							}
							
							tile.hflip();
						}
					}
				}
				
				if (!tilePlaced)
				{
					if (debug)
					{
						// we searched everywhere and failed to put it any place
						debugln("Did not place tile " + tile.getName());
					}
				}
			}
			
			if (board.freeBlocks() == 0)
			{
				// solution complete
				return true;
			}
			
			if (debug)
			{
				debugln("No solution found");
				debug("Adding removed ");
				printTiles(removed);
			}
			
			// add back tiles that were removed
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

	private void printProgress(Tile tile, int flips, int rotation)
	{
		if (level == 1)
		{
			level1Context.tile = tile;
			level1Context.flip = flips;
			level1Context.rotation = rotation;
		}
		
		if (iteration % 100000000 == 0)
		{
			long elapsed = System.currentTimeMillis() - start + 1;
			long rate = iteration * 1000L / (long)elapsed;
			
			long elapsedSec = elapsed / 1000;
			String elapsedStr = String.format("%1$02d:%2$02d:%3$02d", (elapsedSec / (60*60)), ((elapsedSec / 60) % 60), (elapsedSec % 60));
			String timeStr = String.format("%1$tY-%1$tm-%1$te %1$tH:%1$tM:%1$tS", new Date());
			
			System.out.println(timeStr + " tries: " + iteration + " rate:" + rate + " tries/s" + " elapsed: " + elapsedStr + " " + level1Context.toString());
			System.out.println(board);
		}
	}

	private void printTiles(List<Tile> tiles)
	{
		if (debug)
		{
			debug("tiles: [");
			for (Tile t : tiles)
			{
				debug(t.getName() + " ");
			}
			debugln("]");
		}
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
