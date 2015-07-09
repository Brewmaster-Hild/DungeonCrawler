//
// Author: Rj Hild
// Date: 6/30/15
// Description: 
//

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.*;

public class Slime extends Monster
{

	public Slime(int x, int y)
	{

		name = "Slime";
		hp = 8;
		maxHp = hp;
		dmg = 1;
		def = 0;
		exp = 15;
		this.x = x;
		this.y = y;

	}

	public void draw(Graphics2D g, int l) {
		drawer = new Sprites();
		drawer.drawSlime(g, x * l, y * l, l, l);
	}

}