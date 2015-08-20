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
        public static ChestTracker chests;
        public static Sprites drawer;
        private Graphics2D g2d;
        public static int inventoryCheck = 0;

        public DungeonCrawlerGame() {
            chests = new ChestTracker();
            dungeon = new DungeonGenerator();
           	dungeon.generate();
            tracker = new CharacterTracker();
            spawnCharacters();
            drawer = new Sprites();
        }

        public Dimension getPreferredSize() { return new Dimension(500, 500); }

        protected void paintComponent(Graphics g) {

            g2d = (Graphics2D) g.create();
            super.paintComponent(g);
            if (inventoryCheck == 0)
                drawFloor();
            else if (inventoryCheck == 1)
                drawInventory();
            else if (inventoryCheck == 2)
                drawEquipped();
            else
                drawLoot(chests.find(tracker.characters[0].getX(), tracker.characters[0].getY()));

        }

        private void drawFloor() {

            cellLength =  20;

            for (int r = 0; r < tracker.gridWithCharacters[dungeon.FLOOR].length; r++) {
                for (int c = 0; c < tracker.gridWithCharacters[dungeon.FLOOR][r].length; c++) {
                    if ((tracker.gridWithCharacters[dungeon.FLOOR][r][c]) == -1) drawer.drawStoneWall(g2d, c * cellLength, r * cellLength, cellLength, cellLength);
                    else if ((tracker.gridWithCharacters[dungeon.FLOOR][r][c]) == 1 || (tracker.gridWithCharacters[dungeon.FLOOR][r][c]) == 2) drawer.drawWoodFloor(g2d, c * cellLength, r * cellLength, cellLength, cellLength);
                    else if ((tracker.gridWithCharacters[dungeon.FLOOR][r][c]) == 3) drawer.drawWoodDoor(g2d, c * cellLength, r * cellLength, cellLength, cellLength);
                    else if ((tracker.gridWithCharacters[dungeon.FLOOR][r][c]) == 4) drawer.drawChest(g2d, c * cellLength, r * cellLength, cellLength, cellLength);
                    else if (tracker.gridWithCharacters[dungeon.FLOOR][r][c] == 10) drawer.drawStoneStepsDown(g2d, c * cellLength, r * cellLength, cellLength, cellLength);
                    else if (tracker.gridWithCharacters[dungeon.FLOOR][r][c] == 11) drawer.drawStoneStepsUp(g2d, c * cellLength, r    * cellLength, cellLength, cellLength);
                }
            }
            tracker.characters[0].draw(g2d, cellLength);
            tracker.drawMonsters(g2d, cellLength);

            repaint();
        }

        private void drawInventory() {

            drawFloor();

            cellLength = 30;

            tracker.characters[0].drawInventory(g2d, cellLength);

            repaint();

        }

        private void drawEquipped() {

            drawFloor();

            cellLength = 30;

            tracker.characters[0].drawEquipped(g2d, cellLength);

            repaint();

        }

        private void drawLoot(Chest loot) {

            drawFloor();

            cellLength = 30;

            loot.drawLoot(g2d, 150, 150, cellLength);

            repaint();

        }

        private void spawnCharacters() { tracker.spawnCharacter(0, 0);  tracker.generateMonsters(); }

        public static int getCellLength() { return cellLength; }

        public void displayDeadScreen() { 

            DungeonCrawler.dungeonCrawler.dead();

        }
}

class MoveListener implements KeyListener
{
    private int[][][] gameGridCopy;

    public void keyPressed(KeyEvent e)
    {
    	//System.out.print("work");
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP: if (GameWindow.game.inventoryCheck == 0) {
                                    DungeonCrawlerGame.tracker.characters[0].turnUp();
                                    callFunctions(); 
                                }
                                 break;
            case KeyEvent.VK_DOWN: if (GameWindow.game.inventoryCheck == 0) {
                                        DungeonCrawlerGame.tracker.characters[0].turnDown(); 
                                        callFunctions();
                                    }
                                   break;
            case KeyEvent.VK_LEFT: if (GameWindow.game.inventoryCheck == 0) {
                                        DungeonCrawlerGame.tracker.characters[0].turnLeft(); 
                                        callFunctions();
                                    }
                                   break;
            case KeyEvent.VK_RIGHT: if (GameWindow.game.inventoryCheck == 0) {
                                        DungeonCrawlerGame.tracker.characters[0].turnRight(); 
                                        callFunctions();
                                    }
                                    break;
            case KeyEvent.VK_W: if (GameWindow.game.inventoryCheck == 0) {
                                    DungeonCrawlerGame.tracker.characters[0].turnUp(); 
                                    callFunctions();
                                }
                                break;
            case KeyEvent.VK_S: if (GameWindow.game.inventoryCheck == 0) {
                                    DungeonCrawlerGame.tracker.characters[0].turnDown(); 
                                    callFunctions();
                                }
                                break;
            case KeyEvent.VK_A: if (GameWindow.game.inventoryCheck == 0) {
                                    DungeonCrawlerGame.tracker.characters[0].turnLeft(); 
                                    callFunctions();
                                }
                                break;
            case KeyEvent.VK_D: if (GameWindow.game.inventoryCheck == 0) {
                                    DungeonCrawlerGame.tracker.characters[0].turnRight(); 
                                    callFunctions();
                                }
                                break;
            case KeyEvent.VK_I: if (GameWindow.game.inventoryCheck == 1) 
                                    GameWindow.game.inventoryCheck = 0;

                                else
                                    GameWindow.game.inventoryCheck = 1; 

                                GameWindow.game.repaint(); 
                                break;
            case KeyEvent.VK_C: if (GameWindow.game.inventoryCheck == 2)
                                    GameWindow.game.inventoryCheck = 0;

                                else
                                    GameWindow.game.inventoryCheck = 2;

                                GameWindow.game.repaint(); 
                                break;
            case KeyEvent.VK_SPACE: if (GameWindow.game.inventoryCheck == 3)
                                        GameWindow.game.inventoryCheck = 0;

                                    else
                                        checkForChests(); 

                                    GameWindow.game.repaint();
                                    break;
        }
        
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    private void checkForChests() {

        int chestCheck = DungeonCrawlerGame.tracker.characters[0].checkForChest();

        if (chestCheck != 4) {

            GameWindow.game.inventoryCheck = 3;

            Chest temp = DungeonCrawlerGame.chests.find(DungeonCrawlerGame.tracker.characters[0].getX(), DungeonCrawlerGame.tracker.characters[0].getY());

            GameWindow.game.repaint();
        }

    }

    private void checkForLadders() {

        int ladderCheck = DungeonCrawlerGame.tracker.characters[0].checkForLadder();

        if (ladderCheck == 10) { 
            DungeonCrawlerGame.dungeon.climbLadderDown();
            DungeonCrawlerGame.tracker.resetGrid();
            DungeonCrawlerGame.tracker.characters[0].resetCords();
            gameGridCopy = DungeonCrawlerGame.dungeon.getGameGrid();
            if (gameGridCopy[DungeonCrawlerGame.dungeon.FLOOR][0][0] == 0) {
                DungeonCrawlerGame.dungeon.generate();
                DungeonCrawlerGame.tracker.resetGrid();
                DungeonCrawlerGame.tracker.generateMonsters();
            }
        }

        else if (ladderCheck == 11) { 
            DungeonCrawlerGame.dungeon.climbLadderUp();
            DungeonCrawlerGame.tracker.resetGrid();
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

class Mouse implements MouseListener {

    private int x, y, id;
    private JPopupMenu invPopup = new JPopupMenu(), equipPopup = new JPopupMenu(), chestLootPopup = new JPopupMenu();


    public void mouseClicked(MouseEvent e) {

        //System.out.print(x);

        if (x == 0 || y == 0)
            makeJMenus();

        x = e.getX();
        y = e.getY();

        if (SwingUtilities.isRightMouseButton(e)) {

            if (GameWindow.game.inventoryCheck == 1) {

                //check x and y, display menu for equip, drop, use
                if (x <= 350 && x >= 150 && y <= 350 && y >= 150) {

                    invPopup.show(GameWindow.game, x + 5, y - 10);

                }

            }

            else if (GameWindow.game.inventoryCheck == 2) {

                //check x and y, display menu for equip, drop, use
                if (x <= 350 && x >= 150 && y <= 350 && y >= 150) {

                    equipPopup.show(GameWindow.game, x + 5, y - 10);

                }

            }

            else if (GameWindow.game.inventoryCheck == 3) {

                if (x <= 330 && x >= 150 && y <= 210 & y >= 180) {

                    chestLootPopup.show(GameWindow.game, x + 5, y - 10);

                }

            }

        }


    }

    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    private void makeJMenus() {

        JMenuItem equip = new JMenuItem("Equip"), drop = new JMenuItem("Drop"), use = new JMenuItem("Use"),
        unequip = new JMenuItem("Unequip"), pickUp = new JMenuItem("Pick Up"), checkStats = new JMenuItem("Check Stats"),
        checkStats1 = new JMenuItem("Check Stats"), checkStats2 = new JMenuItem("Check Stats");

        equip.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {

                id = setId(0);
                if(id != -1) {
                    DungeonCrawlerGame.tracker.characters[0].equip(id);
                    System.out.print("Equipped\n");
                    GameWindow.game.repaint();
                }

            }
        });

        drop.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {

                id = setId(0);
                if(id != -1) {
                    DungeonCrawlerGame.tracker.characters[0].drop(id);
                    System.out.print("Dropped\n");
                    GameWindow.game.repaint();
                }

            }
        });

        use.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {

                id = setId(0);
                if(id != -1) {
                    DungeonCrawlerGame.tracker.characters[0].use(id);
                    System.out.print("Used\n");
                    GameWindow.game.repaint();
                }

            }
        });

        unequip.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {

                id = setId(1);
                if(id != -1) {
                    DungeonCrawlerGame.tracker.characters[0].unequip(id);
                    System.out.print("Unequipped\n");
                    GameWindow.game.repaint();
                }

            }
        });

        pickUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                id = setId(2);
                if(id != -1) {

                    Chest temp = DungeonCrawlerGame.chests.find(DungeonCrawlerGame.tracker.characters[0].getX(), DungeonCrawlerGame.tracker.characters[0].getY());
                    Item i = temp.pickUp(id);
                    DungeonCrawlerGame.tracker.characters[0].pickUpItem(i);
                    System.out.print("Picked Up\n");
                    GameWindow.game.repaint();

                }

            }
        });

        checkStats.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                id = setId(GameWindow.game.inventoryCheck - 1);
                if(id != -1) {

                    Chest temp = DungeonCrawlerGame.chests.find(DungeonCrawlerGame.tracker.characters[0].getX(), DungeonCrawlerGame.tracker.characters[0].getY());
                    Item i = temp.getLoot(id);
                    i.printStats();
                    GameWindow.game.repaint();

                }

            }
        });

        checkStats1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                id = setId(GameWindow.game.inventoryCheck - 1);
                if(id != -1) {

                    Inventory temp = DungeonCrawlerGame.tracker.characters[0].getInventory(0);
                    Item i = temp.getItem(id);
                    i.printStats();
                    GameWindow.game.repaint();

                }

            }
        });

        checkStats2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                id = setId(GameWindow.game.inventoryCheck - 1);
                if(id != -1) {

                    Inventory temp = DungeonCrawlerGame.tracker.characters[0].getInventory(1);
                    Item i = temp.getItem(id);
                    i.printStats();
                    GameWindow.game.repaint();

                }

            }
        });

        chestLootPopup.add(pickUp);
        chestLootPopup.add(checkStats);

        invPopup.add(equip);
        invPopup.add(drop);
        invPopup.add(use);
        invPopup.add(checkStats1);

        equipPopup.add(unequip);
        equipPopup.add(checkStats2);



    }

    private int setId(int menu) {

        int i = 0;

        if(menu == 0) {
            for (int r = 0; r < 5; r++) {
                for (int c = 0; c < 4; c++) {
                    if ((x > (180 + 30 * c)  && x < (210 + 30 * c)) && (y > (210 + 30 * r) && y < (240 + 30 * r)))
                        return i;
                    i++;
                } 
            }
        }

        else if (menu == 1) {
            //x + (l * 4), y + (l * 2), l)
            if ((x > 270 && x < 300) && (y > 240 && y < 270))
                return 0;
            else if ((x > 270 && x < 300) && (y > 270 && y < 300))
                return 1;
            else if ((x > 150 && x < 180) && (y > 180 && y < 210))
                return 2;
            else if ((x > 150 && x < 180) && (y > 240 && y < 270))
                return 3;
            else if ((x > 150 && x < 180) && (y > 300 && y < 330))
                return 4;

        }

        else if (menu == 2) {

            for (i = 0; i < 5; i++) {
                if ((x > (150 + 30 * i)  && x < (180 + 30 * i)))
                    return i;
            } 

        }

        return -1;

    }
}