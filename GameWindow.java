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

	public static DungeonCrawlerGame game;
	public Graphics g2d;

	public GameWindow() {

		game = new DungeonCrawlerGame();
		getContentPane().setLayout(new BorderLayout());
		this.addKeyListener(new moveListener());
		getContentPane().add(game, BorderLayout.CENTER);

	}

}