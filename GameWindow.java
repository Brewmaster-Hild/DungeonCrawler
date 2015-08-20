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
	public static boolean dead = false;
	public static Graphics2D g2d;
	public static moveThread move;
	private StartMenu startScreen;
	private EndMenu endScreen;

	public GameWindow() {

		this.setPreferredSize(new Dimension(600,600));

		startScreen = new StartMenu();
		add(startScreen);
		repaint();
		
	}

	public void startGame() {

		remove(startScreen);

		game = new DungeonCrawlerGame();
		getContentPane().setLayout(new BorderLayout());
		this.addKeyListener(new MoveListener());
		this.addMouseListener(new Mouse());
		getContentPane().add(game, BorderLayout.CENTER);
		game.setPreferredSize(new Dimension(600, 600));
		move = new moveThread();
		move.start();
		game.tracker.resetGrid();
		game.repaint();

		revalidate();
		pack();

	}

	public void finishGame() {

		remove(game);

		endScreen = new EndMenu();
		add(endScreen);
		revalidate();
		pack();
		setVisible(true);

	}

	public void dead() {

		DeathScreen dead = new DeathScreen();
		remove(game);

		add(dead);
		revalidate();
		pack();
		setVisible(true);
	}

}

class moveThread extends Thread
{
	public boolean alive = true;
    public void run()
    {
    	while(alive) {
	        try
	        {
	          	DungeonCrawlerGame.tracker.moveMonsters();
	          	GameWindow.game.repaint();
	          	if (DungeonCrawlerGame.tracker.adventurerDead) {
	          		DungeonCrawler.dungeonCrawler.dead();
	          		alive = false;
	          	}
	          	if (DungeonGenerator.FLOOR == 25) {
	          		DungeonCrawler.dungeonCrawler.finishGame();
	          		alive = false;
	          	}
	                
	            Thread.sleep(750);
	        }
	        catch(InterruptedException e)
	        {
	        	e.printStackTrace();
	        }
	    }
    }
}

class DeathScreen extends JPanel {

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2d;

		g2d = (Graphics2D) g.create();
		g2d.setColor(Color.GRAY);
        g2d.fill(new Rectangle(600, 600));
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
   	 	g2d.drawString("You are dead.", 175, 200);


	}


}

class StartMenu extends JPanel {

	public JButton start = new JButton("Start Game"), directions = new JButton("Controls and Objectives");

	public StartMenu() {

		setPreferredSize(new Dimension(600, 600));
		makeButtons();
		repaint();
		add(start);
		add(directions);

	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(Color.GRAY);
		g2d.fill(new Rectangle(600, 600));
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g2d.drawString("Dungeon Crawler", 150, 100);


	}

	private void makeButtons() {

		start.setFocusable(false);
		directions.setFocusable(false);

		start.setBounds(200, 200, 100, 100);
		directions.setBounds(400, 400, 100, 100);

		start.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {

            	DungeonCrawler.dungeonCrawler.startGame();

            }
        });

        directions.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {

            	System.out.print("DIRECTIONS: \nUse WASD and Arrow Keys to move.\nPress SPACE to open chests.\nPress I to open your Inventory.\n" +
            		"Press C to open your Character Menu.\nTo attack enemies just walk adjacent to them.\nWith your inventory open RIGHT CLICK on" + 
            		"items to interact with them.\n\nOBJECTIVES: The objectives of this game are to progress to the bottom floor without dying.\n");

            }
        });

	}

}

class EndMenu extends JPanel {

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2d;

		g2d = (Graphics2D) g.create();
		g2d.setColor(Color.GRAY);
        g2d.fill(new Rectangle(600, 600));
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
   	 	g2d.drawString("You escaped the dungeon. \nCongratulations.", 175, 200);


	}

}