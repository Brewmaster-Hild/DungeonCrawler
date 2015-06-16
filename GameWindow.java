//
// 	Author: Rj Hild
// 	Date: 6/16/2015
// 	Description: DungeonCrawler Game
//

import java.awt.Graphics2D;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.*;

public class GameWindow extends JFrame {

	public static JPanel dungeonCrawlerGame;
	public Graphics g2d;

	public GameWindow() {

		dungeonCrawlerGame = new JPanel() {
												public void paintComponent(Graphics g) {
													
													super.paintComponent(g);

												}
										};
		dungeonCrawlerGame.setBackground(Color.BLACK);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(dungeonCrawlerGame, BorderLayout.CENTER);

	}

} 