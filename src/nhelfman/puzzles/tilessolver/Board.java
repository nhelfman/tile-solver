package nhelfman.puzzles.tilessolver;

import java.util.HashSet;
import java.util.Set;


public class Board 
{
	
	final String[][] board;
//	final Set<String> tiles = new HashSet<String>();
	final Tile[] tiles = new Tile[256];
	
	boolean isCheckerBoard = false;
	
	private void hline(StringBuilder buf)
	{
		buf.append(" ");
		for (int i=0; i < board[0].length; i++)
		{
			buf.append("+--");
		}
		buf.append("+");
		buf.append("\n");
	}

	public Board(int rows, int cols)
	{
		this(rows, cols, false);
	}
	
	public Board(int rows, int cols, boolean isCheckerBoard)
	{
		if (rows <= 0 || cols <= 0)
		{
			throw new IllegalArgumentException("rows|cols <= 0");
		}
		
		board = new String[rows][cols];
		this.isCheckerBoard = isCheckerBoard; 
	}
	
	public int width()
	{
		return board[0].length;
	}
	
	public int height()
	{
		return board.length;
	}
	
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		
		buf.append("  ");
		for (int x=0; x < board[0].length; x++)
		{
			buf.append(x + "  ");
		}
		
		buf.append("\n");
		
		hline(buf);
		
		for (int y=0; y < board.length; y++)
		{
			buf.append(y + "|");
			
			for (int x=0; x < board[y].length; x++)
			{
				String item = board[y][x];
				
				String color = getColor(x, y);
				if (color == null)
				{
					buf.append(item == null ? "  " : board[y][x]);
				}
				else
				{
					buf.append(item == null ? " " + color.toLowerCase() : board[y][x]);
				}
				
				buf.append("|");
			}
			
			// break line
			buf.append("\n");
			hline(buf);
		}
		
		return buf.toString();
	}

	private String getColor(int x, int y)
	{
		if (isCheckerBoard)
		{
			return (x % 2 + y % 2) % 2 == 0 ? "X" : "O"; 
		}
		
		return null;
	}

	public int freeBlocks()
	{
		// TODO - replace with count on put/remove
		int used = 0;
		for (int y=0; y < board.length; y++)
		{
			for (int x=0; x < board[y].length; x++)
			{
				String item = board[y][x];
				
				if(item != null)
				{
					used++;
				}
			}
		}
		
		int total = board.length * board[0].length;
		
		return total - used;
	}
	
	public boolean put(Tile t)
	{
		verifyNotExist(t);
		
		String[][] tileBlocks = t.getBlocks();
		int xPos = t.x;
		int yPos = t.y;
		
		// boundaries check
		if (yPos + tileBlocks.length > board.length)
		{
			return false;
		}
		
		if (xPos + tileBlocks[0].length > board[0].length)
		{
			return false;
		}
		
		// check valid put
		for (int y=0; y < tileBlocks.length; y++)
		{
			for (int x=0; x < tileBlocks[y].length; x++)
			{
				String item = tileBlocks[y][x];
				if (item == null)
				{
					continue; // skip empty blocks
				}
				
				if (board[y + yPos][x + xPos] != null)
				{
					return false;
				}
				
				if (isCheckerBoard && !getColor(x + xPos, y + yPos).equals(item))
				{
					return false;
				}
			}
		}
		
		// place
		for (int y=0; y < tileBlocks.length; y++)
		{
			for (int x=0; x < tileBlocks[y].length; x++)
			{
				String item = tileBlocks[y][x];
				if (item == null)
				{
					continue; // skip empty blocks
				}
				
				board[y + yPos][x + xPos] = item + t.getName();
			}
		}
		
		addTile(t);
		
		return true;
	}

	
	private int tileIndex(Tile t)
	{
		return t.getName().charAt(0) - '0'; 
	}
	
	private void addTile(Tile t)
	{
		tiles[tileIndex(t)] = t;
	}

	private void verifyNotExist(Tile t)
	{
		if (tiles[tileIndex(t)] != null)
		{
			throw new IllegalStateException("attempt to put already placed tile on board");
		}
	}
	
	public void remove(Tile t)
	{
		if (tiles[tileIndex(t)] == null)
		{
			throw new IllegalStateException("attempt to remove non placed tile from board");
		}
		
		String[][] tileBlocks = t.getBlocks();

		int xPos = t.x;
		int yPos = t.y;
		
		// boundaries check
		if (yPos + tileBlocks.length > board.length)
		{
			throw new IllegalArgumentException("tile not on board y");
		}
		
		if (xPos + tileBlocks[0].length > board[0].length)
		{
			throw new IllegalArgumentException("tile not on board x");
		}
		
		// place
		for (int y=0; y < tileBlocks.length; y++)
		{
			for (int x=0; x < tileBlocks[y].length; x++)
			{
				String item = tileBlocks[y][x];
				if (item == null)
				{
					continue; // skip empty blocks in tile
				}
				
				String blockOnBoard = board[y + yPos][x + xPos];
				if (blockOnBoard == null)
				{
					throw new IllegalStateException("no block on on board for tile " + t);
				}
				
				if (!blockOnBoard.equals(item + t.getName()))
				{
					throw new IllegalStateException("remove block from other tile on on board for tile " + t);
				}
				
				board[y + yPos][x + xPos] = null;
			}
		}
		
		tiles[tileIndex(t)] = null;
	}
}
