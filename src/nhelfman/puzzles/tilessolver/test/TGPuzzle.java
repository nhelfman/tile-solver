package nhelfman.puzzles.tilessolver.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nhelfman.puzzles.tilessolver.Board;
import nhelfman.puzzles.tilessolver.Solver;
import nhelfman.puzzles.tilessolver.Tile;

/*
 * Attempt to solve "The Thanks Giving puzzle" (which initiated this project)
 */
public class TGPuzzle
{
	public static void main(String[] args)
	{
		
		// X = black, O = white
		
		Tile t1 = new Tile("1",
				new String[][]
						{
				{"O",   "X",   "O"},
				{null,  null,  "X"},
				{null,  null,  "O"},
				{null,  null,  "X"},
						}
				); 
		
		Tile t2 = new Tile("2",
				new String[][]
						{
				{"X" ,  null,  "X"},
				{"O",   "X",   "O"},
				{"X" ,  null,  "X"},
						}
				);
		
		Tile t3 = new Tile("3",
				new String[][]
						{
				{"O" ,  "X",   "O"},
				{null,  "O",   null},
						}
				);
		
		
		Tile t4 = new Tile("4",
				new String[][]
						{
				{"X" , "O", "X",   "O"},
				{null, null, null, "X"},
						}
				);
		
		Tile t5 = new Tile("5",
				new String[][]
						{
				{"X" , "O", "X", "O"},
				{null, null,"O", null},
						}
				);
		
		// same as 5
		Tile t6 = new Tile("6",
				new String[][]
						{
				{"X" , "O", "X", "O"},
				{null, null,"O", null},
						}
				);
		
		Tile t7 = new Tile("7",
				new String[][]
						{
				{null , "O", "X", "O"},
				{ "O",  "X", "O", null},
						}
				);
		
		// same as 4
		Tile t8 = new Tile("8",
				new String[][]
						{
				{"X" , "O", "X",   "O"},
				{null, null, null, "X"},
						}
				);
		
		Tile t9 = new Tile("9",
				new String[][]
						{
				{"O" ,  "X",   "O"},
				{null,  "O",   "X"}, 
				{null, null,   "O"},
						}
				);
		
		Tile tA = new Tile("A",
				new String[][]
						{
				{"O" ,  "X",  null},
				{null,  "O",   "X"}, 
						}
				);
		
		Tile tB = new Tile("B",
				new String[][]
						{
				{"O" ,  "X",  "O"},
				{"X", null,   "X"}, 
						}
				);
		
		Tile tC = new Tile("C",
				new String[][]
						{
				{"X" ,  "O",   "X"},
				{null,  "X",   "O"}, 
				{null, null,   "X"},
						}
				);

		List<Tile> tiles = new ArrayList(Arrays.asList(new Tile[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, tA, tB, tC}));
		
		Board b = new Board(8, 8, true);
		Solver solver = new Solver(b, tiles);
		
		boolean result = solver.solve();
		
		if (result == true)
		{
			System.out.println("Solved!");
			System.out.println(b);
		}
		else
		{
			System.out.println("Failed");
		}
	}
}
