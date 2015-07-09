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

	public void drawAdventurer(Graphics2D g, int x, int y, int w, int h) { g.drawImage(bigImg.getSubimage(62, 210, 36, 36), x, y, w, h, null); }

	public void drawSkeleton(Graphics2D g, int x, int y, int w, int h) { g.drawImage(bigImg.getSubimage(134, 210, 36, 36), x, y, w, h, null); }

	public void drawSlime(Graphics2D g, int x, int y, int w, int h) { g.drawImage(bigImg.getSubimage(170, 246, 36, 36), x, y, w, h, null); }

	public void drawSnake(Graphics2D g, int x, int y, int w, int h) { g.drawImage(bigImg.getSubimage(494, 210, 36, 36), x, y, w, h, null); }

	public void drawSpider(Graphics2D g, int x, int y, int w, int h) { g.drawImage(bigImg.getSubimage(134, 246, 36, 36), x, y, w, h, null); }

	public void drawGoblin(Graphics2D g, int x, int y, int w, int h) { g.drawImage(bigImg.getSubimage(458, 210, 36, 36), x, y, w, h, null); }

	public void drawWoodFloor(Graphics2D g, int x, int y, int w, int h) { g.drawImage(bigImg.getSubimage(58, 474, 32, 32), x, y, w, h, null); }

	public void drawStoneWall(Graphics2D g, int x, int y, int w, int h) { g.drawImage(bigImg.getSubimage(26, 442, 32, 32), x, y, w, h, null); }

	public void drawWoodDoor(Graphics2D g, int x, int y, int w, int h) { g.drawImage(bigImg.getSubimage(192, 442, 32, 32), x, y, w, h, null); }

	public void drawStoneStepsDown(Graphics2D g, int x, int y, int w, int h) { g.drawImage(bigImg.getSubimage(220, 474, 32, 32), x, y, w, h, null); }

	public void drawStoneStepsUp(Graphics2D g, int x, int y, int w, int h) { g.drawImage(bigImg.getSubimage(224, 442, 28, 32), x, y, w, h, null); }

	//public void drawSkeleton(Graphics2D g, int x, int y, int w, int h) { g.drawImage(bigImg.getSubimage(134, 210, 36, 36), x, y, w, h, null); }
}