//
// 	Author: Rj Hild
// 	Date: 6/16/2015
// 	Description: DungeonCrawler Game
//

import java.awt.geom.Dimension2D;
import javax.swing.*;
import java.awt.*;

public class DungeonCrawler
{
	public static void main(String[] args)
	{
		GameWindow dungeonCrawler = new GameWindow();
		dungeonCrawler.setPreferredSize(new Dimension(500, 500));
		dungeonCrawler.setVisible(true);
		dungeonCrawler.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dungeonCrawler.pack();
	}
}