//
// Author: Rj Hild
// Date: 6/30/15
// Description: 
//

public class Monster extends Character
{

	public Monster() {}

	public void move() {

		direction = rng.nextInt(4);

		switch(direction) {
			//Increment y up 1
			case 0: if(isAdjacent(-1) == 0 && isAdjacent(3) == 0) y -= 1;
					break;

			//Increment x up 1
			case 1: if(isAdjacent(-1) == 1 && isAdjacent(3) == 1) x += 1;
					break;

			//Decrement y down 1
			case 2: if(isAdjacent(-1) == 2 && isAdjacent(3) == 2) y += 1;
					break;

			//Decrement x down 1
			case 3: if(isAdjacent(-1) == 3 && isAdjacent(3) == 3) x -= 1;
					break;
		}

	}

}