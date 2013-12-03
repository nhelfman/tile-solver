
public class Tile 
{

	private final String[][] blocks;
	private final String name;
	public final int count;

	public int x;
	public int y;
	
	public Tile(String name, String[][] blocks)
	{
		this.name = name;
		this.blocks = blocks;
		
		int blocksCount = 0;
		for (int i=0; i < blocks.length; i++)
		{
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
				buf.append(block == null ? " " : blocks[i][j] + name + " ");
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
}
