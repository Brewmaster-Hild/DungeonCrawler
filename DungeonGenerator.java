//
// 	Author: Rj Hild
// 	Date: 6/16/2015
// 	Description: DungeonCrawler Game
//

import java.util.*;

public class DungeonGenerator {

	public static int FLOOR = 0;
	public static final int WALL = -1, ROOMFLOOR = 1, CORRIDORFLOOR = 2, DOOR = 3, CHEST = 4, LADDER_DOWN = 10, LADDER_UP = 11, CHARACTER = 99;
	private int startRow, startCol;
	private Random rng = new Random();
	private int gameGrid[][][];

	public DungeonGenerator() { gameGrid = new int [25][25][25]; }

	public void generate() {

		for (int r = 0; r < gameGrid[FLOOR].length; r++)
			for (int c = 0; c < gameGrid[FLOOR][r].length; c++)
				gameGrid[FLOOR][r][c] = WALL;


		randomlyGenerate();

		makeLadderUp();
		makeLadderDown();

		makeChests();
	}

	private void randomlyGenerate() {

		int rngX, rngY, maxCorridors = 0, maxRooms = 1;

		makeRoom((gameGrid[FLOOR][0].length) / 2, (gameGrid[FLOOR].length) / 2, 5, 5, 2);

		for (int i = 0; i < 1000000; i++) {
			rngX = rng.nextInt(gameGrid[FLOOR][0].length); rngY = rng.nextInt(gameGrid[FLOOR].length);
			if ((maxCorridors < maxRooms) && (isAdjacent(rngX, rngY, CORRIDORFLOOR) == 4) && (isAdjacent(rngX, rngY, DOOR) == 4) && (gameGrid[FLOOR][rngY][rngX] == WALL) && (isAdjacent(rngX, rngY, ROOMFLOOR) != 4)) {
				if (makeCorridor(rngX, rngY, rng.nextInt(4) + 3, isAdjacent(rngX, rngY, ROOMFLOOR))) {
					setCell(rngX, rngY, DOOR);
					maxCorridors++;
				}
			}
			if ((maxRooms < 8) && (gameGrid[FLOOR][rngY][rngX] == WALL) && (isAdjacent(rngX, rngY, CORRIDORFLOOR) != 4)) {
				if (makeRoom(rngX, rngY, rng.nextInt(4) + 3, rng.nextInt(4) + 3, isAdjacent(rngX, rngY, WALL))) {
					setCell(rngX, rngY, DOOR);
					maxRooms++;
				}
			}
		}

	}

	private boolean makeRoom(int x, int y, int height, int width, int direction) {

		int xStart = x, xEnd = x, yStart = y, yEnd = y;

		if (direction == 0 || direction == 1)
			direction += 2;
		else if (direction == 2 || direction == 3)
			direction -= 2;

		if (direction == 0) {
			y++;
			yEnd = y;
			yStart = y - height;
			xStart = x - width / 2;
			xEnd = x + (width + 1) / 2;
		}
		else if (direction == 1) {
			x++;
			yStart = y - height / 2;
			yEnd = y + (height + 1) / 2;
			xEnd = x + width;
		}
		else if (direction == 2) {
			y--;
			yStart = y;
			yEnd = y + height;
			xStart = x - width / 2;
			xEnd = x + (width + 1) / 2;
		}
		else if (direction == 3) {
			x--;
			xEnd = x;
			yStart = y - height / 2;
			yEnd = y + (height + 1) / 2;
			xStart = x - width - 1;
		}

		if (xStart < 0 || xEnd >= gameGrid[FLOOR][y].length)
			return false;
		else if (yStart < 0 || yEnd >= gameGrid[FLOOR].length)
			return false;

		if (!areaUnused(xStart, yStart, xEnd, yEnd))
			return false;

		setCells(xStart + 1, yStart + 1, xEnd, yEnd, ROOMFLOOR);

		return true;
	}

	private boolean makeCorridor(int x, int y, int length, int direction) {

		int xStart = x, xEnd = x, yStart = y, yEnd = y;

		if (direction == 0 || direction == 1)
			direction += 2;
		else if (direction == 2 || direction == 3)
			direction -= 2;

		if (direction == 0)
			yStart = y - length;
		else if (direction == 1)
			xEnd = x + length;
		else if (direction == 2)
			yEnd = y + length;
		else if (direction == 3)
			xStart = x - length;

		if (xStart < 0 || xEnd >= gameGrid[FLOOR][y].length)
			return false;
		else if (yStart < 0 || yEnd >= gameGrid[FLOOR].length)
			return false;

		if (!areaUnused(xStart, yStart, xEnd, yEnd))
			return false;

		setCells(xStart, yStart, xEnd, yEnd, CORRIDORFLOOR);

		return true;
	}

	private boolean makeLadderDown() {

		int x, y, tempX, tempY, xStart, yStart, xEnd, yEnd;
		if (FLOOR == gameGrid.length - 1)
			return false;
		for (int i = 0; i < 400; i++) {
			x = rng.nextInt(gameGrid[FLOOR][0].length); y = rng.nextInt(gameGrid[FLOOR].length);
			tempX = gameGrid[FLOOR][0].length / 2; tempY = gameGrid[FLOOR].length / 2;
			yStart = tempY-1; yEnd = tempY+4; xStart = tempX - 5 / 2; xEnd = tempX + (6) / 2;
			if ((!isInside(x, y, xStart, yStart, xEnd, yEnd)) && !isAlsoAdjacent(x, y, DOOR) && (gameGrid[FLOOR][y][x] == ROOMFLOOR)) {
				gameGrid[FLOOR][y][x] = LADDER_DOWN;
				return true;
			}
		}
		return false;
	}

	private boolean makeLadderUp() {

		int x, y, tempX, tempY, xStart, yStart, xEnd, yEnd;
		if (FLOOR == 0)
			return false;
		for (int i = 0; i < 400; i++) {
			x = rng.nextInt(gameGrid[FLOOR][0].length); y = rng.nextInt(gameGrid[FLOOR].length);
			tempX = gameGrid[FLOOR][0].length / 2; tempY = gameGrid[FLOOR].length / 2;
			yStart = tempY-1; yEnd = tempY+4; xStart = tempX - 5 / 2; xEnd = tempX + (6) / 2;
			if (isInside(x, y, xStart, yStart, xEnd, yEnd) && !isAlsoAdjacent(x, y, DOOR) && (gameGrid[FLOOR][y][x] == ROOMFLOOR)) {
				gameGrid[FLOOR][y][x] = LADDER_UP;
				return true;
			}
		}
		return false;
	}

	private void makeChests() {

		int x, y, tempX, tempY, xStart, yStart, xEnd, yEnd, chestsPerFloor;

		for (chestsPerFloor = 0; chestsPerFloor < 3; chestsPerFloor++) {

			for (int i = 0; i < 400; i++) {
				x = rng.nextInt(gameGrid[FLOOR][0].length); y = rng.nextInt(gameGrid[FLOOR].length);
				tempX = gameGrid[FLOOR][0].length / 2; tempY = gameGrid[FLOOR].length / 2;
				yStart = tempY-1; yEnd = tempY+4; xStart = tempX - 5 / 2; xEnd = tempX + (6) / 2;
				if (!isInside(x, y, xStart, yStart, xEnd, yEnd) && !isAlsoAdjacent(x, y, DOOR) && gameGrid[FLOOR][y][x] == ROOMFLOOR) {
					gameGrid[FLOOR][y][x] = CHEST;
					System.out.print("chest made. ");
					DungeonCrawlerGame.chests.set(new Chest(x, y), chestsPerFloor + (FLOOR * 3));
					break;
				}

			}

		}

		System.out.print("\n");

	}

	private void setCells(int xStart, int yStart, int xEnd, int yEnd, int tile) {
		for (int y = yStart; y < yEnd + 1; y++)
			for (int x = xStart; x < xEnd + 1; x++)
				setCell(x, y, tile);
	}

	private void setCell(int x, int y, int tile) { gameGrid[FLOOR][y][x] = tile; }

	private void scanForDoors() {

		for (int r = 0; r < gameGrid[FLOOR].length; r++)
			for (int c = 0; c < gameGrid[FLOOR][r].length; c++)
				if (needDoor(c, r))
					setCell(c, r, DOOR);

	}

	private boolean needDoor(int x, int y) {

		if (x-1 != -1 && x+1 != gameGrid[FLOOR][0].length && y-1 != -1 && y+1 != gameGrid[FLOOR].length) {
			if (gameGrid[FLOOR][y][x] == CORRIDORFLOOR && (gameGrid[FLOOR][y][x - 1] == ROOMFLOOR || gameGrid[FLOOR][y][x + 1] == ROOMFLOOR))
				return true;
			else if (gameGrid[FLOOR][y][x] == CORRIDORFLOOR && (gameGrid[FLOOR][y - 1][x] == ROOMFLOOR || gameGrid[FLOOR][y + 1][x] == ROOMFLOOR))
				return true;
		}
		return false;
	}

	private int isAdjacent(int x, int y, int tile) {

		if (x-1 != - 1 && gameGrid[FLOOR][y][x - 1] == tile)
			return 1;
		else if (x+1 != gameGrid[FLOOR][0].length && gameGrid[FLOOR][y][x + 1] == tile)
			return 3;
		else if (y-1 != -1 && gameGrid[FLOOR][y - 1][x] == tile)
			return 0;
		else if (y+1 != gameGrid[FLOOR].length && gameGrid[FLOOR][y + 1][x] == tile)
			return 2;
		return 4;
	}

	private boolean isAlsoAdjacent(int x, int y, int tile) {

		if (x-1 != - 1 && gameGrid[FLOOR][y][x - 1] == tile)
			return true;
		else if (x+1 != gameGrid[FLOOR][0].length && gameGrid[FLOOR][y][x + 1] == tile)
			return true;
		else if (y-1 != -1 && gameGrid[FLOOR][y - 1][x] == tile)
			return true;
		else if (y+1 != gameGrid[FLOOR].length && gameGrid[FLOOR][y + 1][x] == tile)
			return true;
		return false;
	}

	private boolean isDiagonal(int x, int y, int tile) {

		if (gameGrid[FLOOR][y + 1][x - 1] == tile || gameGrid[FLOOR][y + 1][x + 1] == tile)
			return true;
		else if (gameGrid[FLOOR][y - 1][x + 1] == tile || gameGrid[FLOOR][y - 1][x + 1] == tile)
			return true;
		return false;

	}

	private boolean areaUnused(int xStart, int yStart, int xEnd, int yEnd) {

		if (!(xStart <= xEnd) && !(yStart <= yEnd))
			return false;

		for (int y = yStart; y <= yEnd; y++)
			for (int x = xStart; x <= xEnd; x++)
				if (gameGrid[FLOOR][y][x] != WALL)
					return false;
		return true;

	}

	private boolean isInside(int x, int y, int xStart, int yStart, int xEnd, int yEnd) {
		for (int r = yStart; r < yEnd + 1; r++)
			for (int c = xStart; c < xEnd + 1; c++)
				if (y == r && x == c)
					return true;
		return false;
	}

	public int[][][] getGameGrid() { return gameGrid; }

	public void climbLadderDown() { FLOOR++; }

	public void climbLadderUp() { FLOOR--; }

	public void print() {

		    for (int r = 0; r < gameGrid[FLOOR].length; r++)
                for (int c = 0; c < gameGrid[FLOOR][r].length; c++)
                	System.out.print(gameGrid[FLOOR][r][c]);

	}

}