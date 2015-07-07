//
// Author: Rj Hild
// Date: 6/30/15
// Description: Tracks the characters on the board
//

import java.util.*;

public class CharacterTracker
{
	public int gridWithCharacters[][][] = new int[25][25][25];
	public Character characters[] = new Character[50];
	private Random rng = new Random();

	public CharacterTracker() {
		gridWithCharacters = copyGameGrid(DungeonCrawlerGame.dungeon.getGameGrid());
	}

	private void createCharacter(int option, int x, int y) {

		switch(option) {

			case 0: characters[0] = new Adventurer(x, y);
					break;

		}

	}

	public boolean spawnCharacter(int option) {

		int x, y, tempX, tempY, xStart, yStart, xEnd, yEnd;

		for (int i = 0; i < 400; i++) {
			x = rng.nextInt(gridWithCharacters[DungeonGenerator.FLOOR][0].length); y = rng.nextInt(gridWithCharacters[DungeonGenerator.FLOOR].length);
			tempX = gridWithCharacters[DungeonGenerator.FLOOR][0].length / 2; tempY = gridWithCharacters[DungeonGenerator.FLOOR].length / 2;
			yStart = tempY-1; yEnd = tempY+4; xStart = tempX - 5 / 2; xEnd = tempX + (6) / 2;
			if ((isInside(x, y, xStart, yStart, xEnd, yEnd)) && (gridWithCharacters[DungeonGenerator.FLOOR][y][x] == DungeonGenerator.ROOMFLOOR)) {
				gridWithCharacters[DungeonGenerator.FLOOR][y][x] = DungeonGenerator.CHARACTER;
				createCharacter(option, x, y); 
				return true;
			}
		}
		return false;

	}

	public void resetGrid() {
		//Resets the gridWithCharacters in DungeonCrawlerGame to the original gridWithCharacters

		gridWithCharacters = copyGameGrid(DungeonCrawlerGame.dungeon.getGameGrid());
		setCharacterLocation(0);

	}

	public void track(int id) {



	}

	private void setCharacterLocation(int id) {

		int x = characters[id].getX();
		int y = characters[id].getY();
		gridWithCharacters[DungeonGenerator.FLOOR][y][x] = DungeonGenerator.CHARACTER;

	}

	private boolean isInside(int x, int y, int xStart, int yStart, int xEnd, int yEnd) {
		for (int r = yStart; r < yEnd + 1; r++)
			for (int c = xStart; c < xEnd + 1; c++)
				if (y == r && x == c)
					return true;
		return false;
	}

	public void print() {

		    for (int r = 0; r < gridWithCharacters[DungeonCrawlerGame.dungeon.FLOOR].length; r++)
                for (int c = 0; c < gridWithCharacters[DungeonCrawlerGame.dungeon.FLOOR][r].length; c++)
                	System.out.print(gridWithCharacters[DungeonCrawlerGame.dungeon.FLOOR][r][c]);

	}

	private int[][][] copyGameGrid(int[][][] original) {

		int[][][] temp = new int[original.length][original[0].length][];
		for (int r = 0; r < original[DungeonCrawlerGame.dungeon.FLOOR].length; r++)
			for (int c = 0; c < original[DungeonCrawlerGame.dungeon.FLOOR][r].length; c++)
				temp[DungeonCrawlerGame.dungeon.FLOOR][r] = (original[DungeonCrawlerGame.dungeon.FLOOR][r]).clone();
		return temp;


	}

}