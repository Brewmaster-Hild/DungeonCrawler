//
// 	Author: Rj Hild
// 	Date: 6/16/2015
// 	Description: Playable Character
//

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Adventurer extends Character
{
	private Sprites drawer;
	private int[][][] temp;
	private int startX, startY;

	public Adventurer(int x, int y) {

		this.x = x;
		startX = x;
		this.y = y;
		startY = y;
		temp = DungeonCrawlerGame.dungeon.getGameGrid();

	}

	public void move() {

		switch(direction) {
			//Increment y up 1
			case 0: if(isAdjacent(-1) == 0) y -= 1;
					break;

			//Increment x up 1
			case 1: if(isAdjacent(-1) == 1) x += 1;
					break;

			//Decrement y down 1
			case 2: if(isAdjacent(-1) == 2) y += 1;
					break;

			//Decrement x down 1
			case 3: if(isAdjacent(-1) == 3) x -= 1;
					break;
		}
	}

	public void draw(Graphics2D g, int l) {

		drawer = new Sprites();
		drawer.drawAdventurer(g, x * l, y * l, l, l);

	}

	public boolean hitWall() { 
		

		return true;

	}

	private int isAdjacent(int tile) {

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

	public int checkForLadder() {

		switch (direction) {

			case 3: if (temp[DungeonCrawlerGame.dungeon.FLOOR][y][x - 1] == 10)
					   	return 10;
					if (temp[DungeonCrawlerGame.dungeon.FLOOR][y][x - 1] == 11)
						return 11;
					break;
			case 1: if (temp[DungeonCrawlerGame.dungeon.FLOOR][y][x + 1] == 10) 
						return 10;
					if (temp[DungeonCrawlerGame.dungeon.FLOOR][y][x + 1] == 11)
						return 11;
					break;
			case 0: if (temp[DungeonCrawlerGame.dungeon.FLOOR][y - 1][x] == 10)
						return 10;
					if (temp[DungeonCrawlerGame.dungeon.FLOOR][y - 1][x] == 11)
						return 11;
					break;
			case 2: if (temp[DungeonCrawlerGame.dungeon.FLOOR][y + 1][x] == 10) 
						return 10;
			        if (temp[DungeonCrawlerGame.dungeon.FLOOR][y + 1][x] == 11)
						return 11;
					break;

		}

		return 0;

	}

	public void resetCords() {
		x = startX;
		y = startY;
	}
}