//
// 	Author: Rj Hild
// 	Date: 6/16/2015
// 	Description: Playable Character
//

import java.awt.*;
import java.awt.Rectangle;
import java.util.*;
import javax.swing.*;

public class Adventurer extends Character
{
	private int startX, startY;
	private int maxExp, level;
	private Inventory items, equipped;
	private JOptionPane popup;

	public Adventurer(int x, int y) {

		name = "Adventurer";
		items = new Inventory(20);
		equipped = new Inventory(5);
		fillInventory();
		popup = new JOptionPane();
		this.x = x;
		startX = x;
		this.y = y;
		startY = y;
		maxHp = 25;
		hp = 25;
		dmg = 2;
		def = 0;
		maxExp = 30;
		exp = 0;
		level = 1;

	}

	public void move() {

		switch(direction) {
			//Increment y up 1
			case 0: if(isAdjacent(-1) == 0) y -= 1;
					break;

			//Increment x up 1
			case 1: if(isAdjacent(-1) == 1) x += 1;
					break;

			//Decrement y down 1
			case 2: if(isAdjacent(-1) == 2) y += 1;
					break;

			//Decrement x down 1
			case 3: if(isAdjacent(-1) == 3) x -= 1;
					break;
		}
	}

	public void draw(Graphics2D g, int l) { drawer.drawAdventurer(g, x * l, y * l, l, l); }

	public void drawInventory(Graphics2D g2d, int cellLength) {

		items.drawInventory(g2d, 150, 150, cellLength);

	}

	public void drawEquipped(Graphics2D g2d, int cellLength) {

		equipped.drawEquipped(g2d, 150, 150, cellLength);

	}

	private void checkEquipped() {

		Item temp;

		for (int i = 0; i < equipped.length(); i++) {

			temp = equipped.getItem(i);

			maxHp = maxHp + temp.getMaxHp();
			hp = hp + temp.getHp();
			dmg = dmg + temp.getDmg();
			def = def + temp.getDef();

		}
			
	}

	public void pickUpItem(Item item) {

		for (int i = 0; i < items.length(); i++)
			if(items.getItem(i) == null) {
				items.addItem(item);
				break;
			}

	}

	public void checkStats(Item item) {

		item.printStats();

	}

	public void unequip(int id) {

		Item temp = equipped.removeItem(id);
		maxHp = maxHp - temp.getMaxHp();
		hp = hp - temp.getHp();
		dmg = dmg - temp.getDmg();
		def = def - temp.getDef();
		pickUpItem(temp);

	}

	public void equip(int id) {

		Item temp = items.getItem(id);

		if(temp.getName() == "sword") {
			if(equipped.getItem(0) != null)
				popup.showMessageDialog(GameWindow.game, "You already have a sword equipped.", "Sword slot full!", popup.ERROR_MESSAGE);
			else {
				equipped.setItem(temp, 0);
				addStats(id);
			}
		}
		else if(temp.getName() == "shield") {
			if(equipped.getItem(1) != null)
				popup.showMessageDialog(GameWindow.game, "You already have a shield equipped.", "Shield slot full!", popup.ERROR_MESSAGE);
			else {
				equipped.setItem(temp, 1);
				addStats(id);
			}
		}
		else if(temp.getName() == "helmet") {
			if(equipped.getItem(2) != null)
				popup.showMessageDialog(GameWindow.game, "You already have a helmet equipped.", "Helmet slot full!", popup.ERROR_MESSAGE);
			else {
				equipped.setItem(temp, 2);
				addStats(id);
			}
		}
		else if(temp.getName() == "chestArmor") {
			if(equipped.getItem(3) != null)
				popup.showMessageDialog(GameWindow.game, "You already have chest armor equipped.", "Chest armor slot full!", popup.ERROR_MESSAGE);
			else {
				equipped.setItem(temp, 3);
				addStats(id);
			}
		}
		else if(temp.getName() == "boots") {
			if(equipped.getItem(4) != null)
				popup.showMessageDialog(GameWindow.game, "You already have boots equipped.", "Boots slot full!", popup.ERROR_MESSAGE);
			else {
				equipped.setItem(temp, 4);
				addStats(id);
			}
		}
		else
			popup.showMessageDialog(GameWindow.game, "You cannot equip this item.", "Cannot equip.", popup.ERROR_MESSAGE);
	}

	private void addStats(int id) {

		Item temp = items.removeItem(id);
		maxHp = maxHp + temp.getMaxHp();
		hp = hp + temp.getHp();
		dmg = dmg + temp.getDmg();
		def = def + temp.getDef();

	}

	public void use(int id) {

		Item temp = items.removeItem(id);
		maxHp = maxHp + temp.getMaxHp();
		hp = hp + temp.getHp();
		dmg = dmg + temp.getDmg();
		def = def + temp.getDef();

		if (hp > maxHp)
			hp = maxHp;

	}

	public void drop(int id) {

		items.dropItem(id);

	}

	private void fillInventory() {

		Item starterSword, starterShield, starterChestArmor, starterBoots, potion;

		starterSword = new Item(2, 0, 0, 0, "sword"); starterShield = new Item(0, 1, 0, 0, "shield");
		starterChestArmor = new Item(0, 0, 2, 2, "chestArmor"); starterBoots = new Item(0, 0, 0, 0, "boots");
		potion = new Item(0, 0, 20, 0, "potion");

		for (int i = 0; i < items.length(); i++)
			items.dropItem(i);

		for (int i = 0; i < equipped.length(); i++)
			equipped.dropItem(i);

		equipped.setItem(starterSword, 0); equipped.setItem(starterShield, 1); 
		equipped.setItem(starterChestArmor, 3); equipped.setItem(starterBoots, 4); 

		for (int i = 0; i < 5; i++)
			items.setItem(potion, i);

	}

	public Inventory getInventory(int id) {

		if (id == 0)
			return items;
		else
			return equipped;

	}

	public int checkForLadder() {

		switch (direction) {

			case 3: if (temp[DungeonCrawlerGame.dungeon.FLOOR][y][x - 1] == 10)
					   	return 10;
					if (temp[DungeonCrawlerGame.dungeon.FLOOR][y][x - 1] == 11)
						return 11;
					break;
			case 1: if (temp[DungeonCrawlerGame.dungeon.FLOOR][y][x + 1] == 10) 
						return 10;
					if (temp[DungeonCrawlerGame.dungeon.FLOOR][y][x + 1] == 11)
						return 11;
					break;
			case 0: if (temp[DungeonCrawlerGame.dungeon.FLOOR][y - 1][x] == 10)
						return 10;
					if (temp[DungeonCrawlerGame.dungeon.FLOOR][y - 1][x] == 11)
						return 11;
					break;
			case 2: if (temp[DungeonCrawlerGame.dungeon.FLOOR][y + 1][x] == 10) 
						return 10;
			        if (temp[DungeonCrawlerGame.dungeon.FLOOR][y + 1][x] == 11)
						return 11;
					break;

		}

		return 0;

	}

	public int checkForChest() {

		if (temp[DungeonCrawlerGame.dungeon.FLOOR][y][x - 1] == 4)
	  		return 3;

		if (temp[DungeonCrawlerGame.dungeon.FLOOR][y][x + 1] == 4) 
			return 1;

		if (temp[DungeonCrawlerGame.dungeon.FLOOR][y - 1][x] == 4)
			return 0;

		if (temp[DungeonCrawlerGame.dungeon.FLOOR][y + 1][x] == 4) 
			return 2;

		return 4;

	}

	public void resetCords() { x = startX;	y = startY; }

	public void increaseExp(int exp) {

		System.out.print("Gained " + exp + " experience.\n");
		this.exp += exp;
		if (this.exp > maxExp)
			levelUp();

	}

	private void levelUp() {

		System.out.print("Level up!\n");
		maxHp +=4;
		hp = maxHp;
		dmg++;
		exp = exp - maxExp;
		maxExp = maxExp * 2;
		level++;

	}
}