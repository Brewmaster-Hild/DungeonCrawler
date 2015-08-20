//
// Author: Rj Hild
// Date: 6/30/15
// Description: 
//

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.*;

public class Skeleton extends Monster
{

	public Skeleton(int x, int y)
	{

		name = "Skeleton";
		hp = 15;
		maxHp = hp;
		dmg = 4;
		def = 1;
		exp = 95;
		this.x = x;
		this.y = y;

	}

	public void draw(Graphics2D g, int l) {
		drawer.drawSkeleton(g, x * l, y * l, l, l);
	}

}