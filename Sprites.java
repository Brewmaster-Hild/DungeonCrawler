//
// Author: Rj Hild
// Date: 6/30/15
// Description: Gets and draws the sprites for Dungeon Crawler
//

import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class Sprites
{
	private BufferedImage bigImg;

	public Sprites()
	{
		try
		{
			getSprites();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void getSprites() throws IOException
	{
		bigImg = ImageIO.read(new File("spriteSheet.png"));
	}

	public void drawAdventurer(Graphics2D g, int x, int y, int w, int h) {

		g.drawImage(bigImg.getSubimage(26, 210, 36, 36), x, y, w, h, null);

	}
}