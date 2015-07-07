//
//  Author: Rj Hild
//  Date: 6/16/2015
//  Description: DungeonCrawler Game
//

import java.awt.Graphics2D;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.*;

public class DungeonCrawlerGame extends JPanel {

		public static DungeonGenerator dungeon;
        public static CharacterTracker tracker;
        public static int cellLength, grid[][][];

        public DungeonCrawlerGame() {
            dungeon = new DungeonGenerator();
           	dungeon.generate();
            tracker = new CharacterTracker();
            spawnCharacters();
            this.setBackground(Color.WHITE);
        }

        public Dimension getPreferredSize() { return new Dimension(500, 500); }

        protected void paintComponent(Graphics g) {

        	super.paintComponent(g);
            drawFloor(g);

        }

        private void drawFloor(Graphics g) {

            Graphics2D g2d = (Graphics2D) g.create();

            int width = getWidth();
            int height = getHeight();

            cellLength =  20;

            for (int r = 0; r < tracker.gridWithCharacters[dungeon.FLOOR].length; r++) {
                for (int c = 0; c < tracker.gridWithCharacters[dungeon.FLOOR][r].length; c++) {
                    if ((tracker.gridWithCharacters[dungeon.FLOOR][r][c]) == -1) g2d.setColor(Color.BLACK);
                    else if ((tracker.gridWithCharacters[dungeon.FLOOR][r][c]) == 1 || (tracker.gridWithCharacters[dungeon.FLOOR][r][c]) == 2)  g2d.setColor(Color.WHITE);
                    else if ((tracker.gridWithCharacters[dungeon.FLOOR][r][c]) == 3)  g2d.setColor(Color.GRAY);
                    else if ((tracker.gridWithCharacters[dungeon.FLOOR][r][c]) == 10 || (tracker.gridWithCharacters[dungeon.FLOOR][r][c]) == 11) g2d.setColor(Color.RED);
                    else if (tracker.gridWithCharacters[dungeon.FLOOR][r][c] == 99) g2d.setColor(Color.CYAN);

                    g2d.fillRect(c * cellLength, r * cellLength, cellLength, cellLength);
                    if (tracker.gridWithCharacters[dungeon.FLOOR][r][c] == 99) tracker.characters[0].draw(g2d, cellLength);
                    g2d.setColor(Color.BLUE);
                    g2d.draw(new Rectangle(c*cellLength,r*cellLength,cellLength,cellLength));
                }
            }

        }

        private void spawnCharacters() {

            tracker.spawnCharacter(0);

        }

        public static int getCellLength() { return cellLength; }
}

class moveListener implements KeyListener
{
    private int[][][] gameGridCopy;

    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP: DungeonCrawlerGame.tracker.characters[0].turnUp();
                                 callFunctions(); 
                                 break;
            case KeyEvent.VK_DOWN: DungeonCrawlerGame.tracker.characters[0].turnDown(); 
                                   callFunctions();
                                   break;
            case KeyEvent.VK_LEFT: DungeonCrawlerGame.tracker.characters[0].turnLeft(); 
                                   callFunctions();
                                   break;
            case KeyEvent.VK_RIGHT: DungeonCrawlerGame.tracker.characters[0].turnRight(); 
                                    callFunctions();
                                    break;
            case KeyEvent.VK_W: DungeonCrawlerGame.tracker.characters[0].turnUp(); 
                                callFunctions();
                                break;
            case KeyEvent.VK_S: DungeonCrawlerGame.tracker.characters[0].turnDown(); 
                                callFunctions();
                                break;
            case KeyEvent.VK_A: DungeonCrawlerGame.tracker.characters[0].turnLeft(); 
                                callFunctions();
                                break;
            case KeyEvent.VK_D: DungeonCrawlerGame.tracker.characters[0].turnRight(); 
                                callFunctions();
                                break;
        }
        
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    private void checkForLadders() {

        int ladderCheck = DungeonCrawlerGame.tracker.characters[0].checkForLadder();
        gameGridCopy = DungeonCrawlerGame.dungeon.getGameGrid();

        if (ladderCheck == 10) {
            DungeonCrawlerGame.dungeon.climbLadderDown();
            DungeonCrawlerGame.tracker.characters[0].resetCords();
            if (gameGridCopy[DungeonCrawlerGame.dungeon.FLOOR][0][0] == 0)
                DungeonCrawlerGame.dungeon.generate();
        }

        else if (ladderCheck == 11) {
            DungeonCrawlerGame.dungeon.climbLadderUp();
            DungeonCrawlerGame.tracker.characters[0].resetCords();
        }

    }

    private void callFunctions() {

        checkForLadders();
        DungeonCrawlerGame.tracker.characters[0].move(); 
        DungeonCrawlerGame.tracker.resetGrid(); 
        GameWindow.game.repaint();
    }
}
