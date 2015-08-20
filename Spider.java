//
// Author: Rj Hild
// Date: 6/30/15
// Description: 
//

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.*;

public class Spider extends Monster
{

	public Spider(int x, int y)
	{

		name = "Spider";
		hp = 6;
		maxHp = hp;
		dmg = 3;
		def = 0;
		exp = 20;
		this.x = x;
		this.y = y;

	}

	public void draw(Graphics2D g, int l) {
		drawer.drawSpider(g, x * l, y * l, l, l);
	}

}