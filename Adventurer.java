//
// 	Author: Rj Hild
// 	Date: 6/16/2015
// 	Description: Playable Character
//

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.*;

public class Adventurer extends Character
{
	private int startX, startY;
	private int maxExp, level;

	public Adventurer(int x, int y) {

		name = "Adventurer";
		this.x = x;
		startX = x;
		this.y = y;
		startY = y;
		maxHp = 25;
		hp = 25;
		dmg = 2;
		def = 0;
		maxExp = 50;
		exp = 0;
		level = 1;

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

	public void increaseExp(int exp) {

		System.out.print("Gained " + exp + " experience.\n");
		this.exp += exp;
		if (exp > maxExp)
			levelUp();

	}

	private void levelUp() {

		System.out.print("Level up!\n");
		maxHp +=4;
		hp = maxHp;
		dmg++;
		exp = exp - maxExp;
		maxExp = maxExp * 2;
		level++;

	}
}