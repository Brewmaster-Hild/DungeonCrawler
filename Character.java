//
// 	Author: Rj Hild
// 	Date: 6/16/2015
// 	Description: DungeonCrawler Game
//

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Character
{
	public int direction, x, y;

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

	public int getX() { return x; }

	public int getY() { return y; }

	public int getDirection() { return direction; }

	public boolean hitWall() { return false; }

	public int checkForLadder() { return 0; }

	public void resetCords() { }
}