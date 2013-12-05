
public class Tile 
{
	private final String[][] blocks;
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


	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		for (int i=0; i < blocks.length; i++)
		{
			for (int j=0; j < blocks[i].length; j++)
			{
				String block = blocks[i][j];
				buf.append(block == null ? "   " : blocks[i][j] + name + " ");
			}
			
			buf.append("\n");
		}
		
		return "[" + x + "," + y + "]:\n" + buf.toString();
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
		int width = blocks[0].length;
		for (int y=0; y < blocks.length; y++)
		{
			if (blocks[y].length > width)
			{
				width = blocks[y].length;
			}
		}
		
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

		
	}
	
	
}
