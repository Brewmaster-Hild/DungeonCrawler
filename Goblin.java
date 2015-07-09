//
// Author: Rj Hild
// Date: 6/30/15
// Description: 
//

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.*;

public class Goblin extends Monster
{

	public Goblin(int x, int y)
	{

		name = "Goblin";
		hp = 22;
		maxHp = hp;
		dmg = 6;
		def = 2;
		exp = 150;
		this.x = x;
		this.y = y;

	}

	public void draw(Graphics2D g, int l) {
		drawer = new Sprites();
		drawer.drawGoblin(g, x * l, y * l, l, l);
	}

}