import java.util.ArrayList;
import java.util.List;


public class Solver
{
	private final Board board;
	private final List<Tile> tiles;
	
	public Solver(Board board, List<Tile> tiles)
	{
		this.board = board;
		this.tiles = tiles;
	}
	
	public boolean solve()
	{
		return solve(tiles);
	}
	
	static int level = 0;
	
	private boolean solve(List<Tile> remaining)
	{
		try
		{
			level++;

			System.out.println("--------------------------------------------------");
			System.out.print("level: " + level + " remaining ");
			printTiles(remaining);
			

			List<Tile> removed = new ArrayList<Tile>();
			
			while (remaining.size() > 0)
			{
				Tile tile = remaining.remove(0);
				System.out.println("got tile: " + tile.getName());
				
				removed.add(tile);
				
				boolean tilePlaced = false;
				for (int y = 0; y < board.height(); y++)
				{
					for (int x = 0; x < board.width(); x++)
					{
						tile.pos(x, y);

						System.out.println("Try put tile " + tile.getName() + " at [" + tile.x + "," + tile.y + "]");
						if (board.put(tile))
						{
							System.out.println("Safe put tile " + tile.getName() + "\n" + tile);
							System.out.println("Board:\n" + board);
							tilePlaced = true;

							if (solve(remaining))
							{
								return true;
							} 
							else
							{
								System.out.println("Removing tile " + tile.getName());
								board.remove(tile);
							}
						}
					}
				}
				
				if (!tilePlaced)
				{
					// we searched everywhere and failed to put it any place
					System.out.println("Did not place tile " + tile.getName());
				}
			}
			
			if (board.freeBlocks() == 0)
			{
				// solution complete
				return true;
			}
			
			System.out.println("No solution found");
			
			// add back tiles that were removed
			System.out.print("Adding removed ");
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
		System.out.print("tiles: [");
		for (Tile t : tiles)
		{
			System.out.print(t.getName() + " ");
		}
		System.out.println("]");
	}
}
