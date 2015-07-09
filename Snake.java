//
// Author: Rj Hild
// Date: 6/30/15
// Description: 
//

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.*;

public class Snake extends Monster
{

	public Snake(int x, int y)
	{

		name = "Snake";
		hp = 3;
		maxHp = hp;
		dmg = 5;
		def = 0;
		exp = 5;
		this.x = x;
		this.y = y;

	}

	public void draw(Graphics2D g, int l) {
		drawer = new Sprites();
		drawer.drawSnake(g, x * l, y * l, l, l);
	}

}