//
// 	Author: Rj Hild
// 	Date: 6/16/2015
// 	Description: DungeonCrawler Game
//

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.*;

public class Character {

	protected Sprites drawer;
	protected Random rng = new Random();
	protected String name;
	public int direction, x, y;
	protected int maxHp, hp, dmg, def, exp;
	int[][][] temp = DungeonCrawlerGame.dungeon.getGameGrid();

	public void move() {

		switch(direction) {
			//Increment y up 1
			case 0: y -= 1;
					break;

			//Increment x up 1
			case 1: x += 1;
					break;

			//Decrement y down 1
			case 2: y += 1;
					break;

			//Decrement x down 1
			case 3: x -= 1;
					break;
		}
	}

	public void draw(Graphics2D g, int l) {}

	public void turnUp() { direction = 0; }

	public void turnDown() { direction = 2; }

	public void turnLeft() { direction = 3; }

	public void turnRight() { direction = 1; }

	public void takeDmg(int dmg) { hp = hp - (dmg - def); }

	public int getX() { return x; }

	public int getY() { return y; }

	public int getDirection() { return direction; }

	public boolean hitWall() { return false; }

	protected int isAdjacent(int tile) {

		switch(direction) {
			case 3: if (x-1 != - 1 && temp[DungeonCrawlerGame.dungeon.FLOOR][y][x - 1] != tile)
						return 3;
					break;
			case 1: if (x+1 != temp[DungeonCrawlerGame.dungeon.FLOOR][0].length && temp[DungeonCrawlerGame.dungeon.FLOOR][y][x + 1] != tile)
						return 1;
					break;
			case 0: if (y-1 != -1 && temp[DungeonCrawlerGame.dungeon.FLOOR][y - 1][x] != tile)
						return 0;
					break;
			case 2: if (y+1 != temp[DungeonCrawlerGame.dungeon.FLOOR].length && temp[DungeonCrawlerGame.dungeon.FLOOR][y + 1][x] != tile)
						return 2;
					break;
		}
		return 4;
	}

	protected boolean isAdjacentToCharacter(Character thing) {

		if (x == thing.getX() - 1 && y == thing.getY())
			return true;
		else if (x == thing.getX() + 1 && y == thing.getY())
			return true;
		else if (x == thing.getX() && y == thing.getY() + 1)
			return true;
		else if (x == thing.getX() && y == thing.getY() - 1)
			return true;
		return false;
	}

	public int checkForLadder() { return 0; }

	public void resetCords() { }

	public boolean isDead() { if (hp <= 0) return true; return false; }

	public int getHp() { return hp; }

	public int getDmg() { return dmg; }

	public int getDef() { return def; }

	public String getName() { return name; }

	protected int getExp() { return exp; }

	protected void increaseExp(int exp) { }

	public void printStats() { System.out.print(name + ": " + hp + "/" + maxHp); }
}