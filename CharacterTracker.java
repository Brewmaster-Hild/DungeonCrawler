//
// Author: Rj Hild
// Date: 6/30/15
// Description: Tracks the characters on the board
//

import java.util.*;
import java.awt.Graphics2D;

public class CharacterTracker
{
	public int gridWithCharacters[][][] = new int[25][25][25];
	private int charsPerFloor = 10;
	public Character characters[] = new Character[charsPerFloor+1];
	private Random rng = new Random();

	public CharacterTracker() {
		gridWithCharacters = copyGameGrid(DungeonCrawlerGame.dungeon.getGameGrid());
	}

	private void createCharacter(int option, int x, int y, int index) {

		switch(option) {

			case 0: characters[index] = new Adventurer(x, y);
					break;	
			case 1: characters[index] = new Skeleton(x, y);
					break;
			case 2:	characters[index] = new Slime(x, y);
					break;
			case 3: characters[index] = new Snake(x, y);
					break;
			case 4: characters[index] = new Spider(x, y);
					break;
			case 5: characters[index] = new Goblin(x, y);
					break;

		}

	}

	public boolean spawnMonster(int option, int index, int tileId) {

		int x, y;

		for (int i = 0; i < 400; i++) {
			x = rng.nextInt(gridWithCharacters[DungeonGenerator.FLOOR][0].length); y = rng.nextInt(gridWithCharacters[DungeonGenerator.FLOOR].length);
			if ((gridWithCharacters[DungeonGenerator.FLOOR][y][x] == DungeonGenerator.ROOMFLOOR)) {
				gridWithCharacters[DungeonGenerator.FLOOR][y][x] = tileId;
				createCharacter(option, x, y, index); 
				return true;
			}
		}
		return false;
	}

	public boolean spawnCharacter(int option, int index) {

		int x, y, tempX, tempY, xStart, yStart, xEnd, yEnd;

		for (int i = 0; i < 400; i++) {
			x = rng.nextInt(gridWithCharacters[DungeonGenerator.FLOOR][0].length); y = rng.nextInt(gridWithCharacters[DungeonGenerator.FLOOR].length);
			tempX = gridWithCharacters[DungeonGenerator.FLOOR][0].length / 2; tempY = gridWithCharacters[DungeonGenerator.FLOOR].length / 2;
			yStart = tempY-2; yEnd = tempY+2; xStart = tempX - 2; xEnd = tempX + 2;
			if ((isInside(x, y, xStart, yStart, xEnd, yEnd)) && (gridWithCharacters[DungeonGenerator.FLOOR][y][x] == DungeonGenerator.ROOMFLOOR)) {
				gridWithCharacters[DungeonGenerator.FLOOR][y][x] = DungeonGenerator.CHARACTER;
				createCharacter(option, x, y, index); 
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

	public void generateMonsters() {

		int option, tileId;

		for (int i = 1; i <= charsPerFloor; i++) {
			option = rng.nextInt(5) + 1;
			tileId = rng.nextInt(5) + 50;
			spawnMonster(option, i, tileId);
		}

	}

	public void drawMonsters(Graphics2D g, int l) {

		for (int i = 1; i < charsPerFloor; i++)
			if (isAlive(i))
				characters[i].draw(g, l);

	}

	public void resetMonsters() {

		for (int i = 1; i < charsPerFloor; i++)
			characters[i] = new Character();

	}

	public void moveMonsters() {

		for (int i = 1; i < charsPerFloor + 1; i++) {
			if (isAlive(i) && !(characterInBattle(i)))
				characters[i].move();
			if (isAlive(i) && characterInBattle(i))
				fight(i);
		}

	}

	public boolean characterInBattle(int id) {

		if (id == 0) {
			for (int i = 0; i < charsPerFloor; i++) {
				if (characters[0].isAdjacentToCharacter(characters[i])) {
					return true;
				}
			}
		}
		else if (id != 0)
			if (characters[id].isAdjacentToCharacter(characters[0]))
				return true;
		return false;

	}

	public void fight(int id) {

		characters[0].takeDmg(characters[id].getDmg());
		characters[id].takeDmg(characters[0].getDmg());

		characters[0].printStats(); System.out.print(" Did " + (characters[0].getDmg() - characters[id].getDef()) + " damage to " + characters[id].getName() + "\n");
		characters[id].printStats(); System.out.print(" Did " + (characters[id].getDmg() - characters[0].getDef()) + " damage to " + characters[0].getName() + "\n");

		if (characters[0].isDead())
			GameWindow.game.displayDeadScreen();
		if (characters[id].isDead()) {
			characters[0].increaseExp(characters[id].getExp());
			destoryCharacter(id);
		}

	}

	private void destoryCharacter(int id) {

		characters[id] = null;

	}

	public boolean isAlive(int id) {

		if (characters[id] != null)
			return true;
		return false;

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