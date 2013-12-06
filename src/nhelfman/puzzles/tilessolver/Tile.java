package nhelfman.puzzles.tilessolver;

public class Tile 
{
	private String[][] blocks;
	private final String name;
	public final int count;

	public int x;
	public int y;
		
	public Tile(String name, String[][] blocks)
	{
		if (blocks.length == 0)
		{
			throw new IllegalArgumentException("height == 0");
		}
		
		
		this.name = name;
		this.blocks = blocks;
		
		int blocksCount = 0;
		
		int width = blocks[0].length;
		
		for (int i=0; i < blocks.length; i++)
		{
			if (blocks[i].length != width)
			{
				throw new IllegalArgumentException("width of rows is not equal");
			}
			
			for (int j=0; j < blocks[i].length; j++)
			{
				String block = blocks[i][j];
				if(block != null)
				{
					blocksCount++;
				}
			}
		}
		
		count = blocksCount;
		
	}
	
	
	public String getName()
	{
		return name;
	}


	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		for (int i=0; i < blocks.length; i++)
		{
			for (int j=0; j < blocks[i].length; j++)
			{
				String block = blocks[i][j];
				buf.append(block == null ? " . " : blocks[i][j] + name + " ");
			}
			
			buf.append("\n");
		}
		
		return "[" + x + "," + y + "]:\n" + buf.toString();
	}

	
	@Override
	public boolean equals(Object obj)
	{
		if (obj != null && obj instanceof Tile)
		{
			Tile other = (Tile)obj;
			if (blocks.length != other.blocks.length)
			{
				return false;
			}
			
			if (blocks[0].length != other.blocks[0].length)
			{
				return false;
			}
			
			for (int i=0; i < blocks.length; i++)
			{
				for (int j=0; j < blocks[i].length; j++)
				{
					String block = blocks[i][j];
					String otherBlock = other.blocks[i][j];
					
					if (block != null && otherBlock != null)
					{
						if (!block.equals(otherBlock))
						{
							return false;
						}
					}
					else if (block == null && otherBlock != null)
					{
						return false;
					} 
					else if(block != null && otherBlock == null)
					{
						return false;
					}
				}
			}
			
			return true;
		}
		
		return false;
	}


	public String[][] getBlocks()
	{
		return blocks;
	}
	
	public void pos(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void hflip()
	{
		for (int y=0; y < blocks.length; y++)
		{
			for (int x=0; x < blocks[y].length / 2; x++)
			{
				// swap
				String block = blocks[y][x];
				blocks[y][x] = blocks[y][blocks[y].length - 1 - x];
				blocks[y][blocks[y].length - 1 - x] = block;
			}
		}
	}


	public void rotate()
	{
		String[][] rotated = new String[blocks[0].length][blocks.length];
		
		for (int i=0; i < blocks[0].length; i++)
		{
			for (int j=blocks.length - 1; j >= 0; j--)
			{
				rotated[i][rotated[0].length - 1 - j] = blocks[j][i];
			}
		}
		
		blocks = rotated;
	}
	
	
}
