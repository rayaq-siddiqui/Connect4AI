package connectFour;

import java.util.Arrays;

public class NewConnectRayaqAI 
{
	/* Data fields */
	// weights all of the values of the bottom-most slot (starts top left and goes counter clockwise)
	public static double[][] 
			weightedSlot = new double[14][7],			// top left, mid left, bottom left, bottom middle... + topleft-bottomright, mid(horizonatal), bottomleft-topright
			oppositeFutureWeightedSlot = new double[14][7],		// top left, mid left, bottom left, bottom middle... + topleft-bottomright, mid(horizonatal), bottomleft-topright
			myFutureWeightedSlot = new double[14][7];		// top left, mid left, bottom left, bottom middle... + topleft-bottomright, mid(horizonatal), bottomleft-topright
	// open slots (starting at column 0 until 6)
	public static int[] 
			lowestSlotRow = {5, 5, 5, 5, 5, 5, 5},
			secondLowestSlotRow = {5, 5, 5, 5, 5, 5, 5},
			thirdLowestSlotRow = {5, 5, 5, 5, 5, 5, 5};
	public static double[]
			overallWeight = {0, 0, 0, 0, 0, 0, 0},
			add = {.5, 1.95, 1.5},		// .5 and 1.2 are optimal
			subtract = {0.55, 1.1, 1.9};
	// what is the value of your piece
	public static String 
			yourPiece = "";
	// clone of the game board
	public static Slot[][] 
			originalClone, 
			oppositeFutureClone,
			myFutureClone;
	// player turn number
	public static int 
			playerNum,
			oppositeNum;
	
	/* Methods */
	// required method to play the turn
	public static int playTurn(Slot[][] gb, int playerNumber, String playerColour) throws InterruptedException
	{
		// initializing data fields *************************************
		originalClone = gb;
		oppositeFutureClone = Game.getBoardClone();
		myFutureClone = Game.getBoardClone();
		yourPiece = playerColour;
		playerNum = playerNumber;
		oppositeNum = playerNum == 1 ? 2 : 1;
		
		// lowest slot (current and future x 2) ******************************
		lowestSlotRow = Game.getListOfChoices(originalClone);
		
		for (int c = 0; c < Game.getBoardCols(); c++)
			Game.applyTurnToBoard(oppositeFutureClone, c, playerNum);
		secondLowestSlotRow = Game.getListOfChoices(oppositeFutureClone);
		
		for (int c = 0; c < Game.getBoardCols(); c++)
		{
			Game.applyTurnToBoard(oppositeFutureClone, c, playerNum);
			Game.applyTurnToBoard(oppositeFutureClone, c, playerNum);
		}
		thirdLowestSlotRow = Game.getListOfChoices(myFutureClone);
		// **************************************************************

		weightCalc();		
		return positionDecide();
	}
	
	public static int positionDecide() throws InterruptedException
	{
//		System.out.println(Arrays.toString(overallWeight));
		
		int tmpPosition = 0;
		boolean positionSelected = false;
		
		double highestWeight = 0;
		double lowestWeight = 0;
		int highPosition = 0;
		int lowPosition = 0;
		
		for (int col = 0; col < overallWeight.length; col++)
		{
			if (lowestSlotRow[col] > -1)
			{
				if (overallWeight[col] >= highestWeight)
				{
					highPosition = col;
					highestWeight = overallWeight[col];
					positionSelected = true;
				}
				if (overallWeight[col] <= lowestWeight)
				{
					lowPosition = col;
					lowestWeight = overallWeight[col];
					positionSelected = true;
				}
			}
			
			if (positionSelected)
			{
				tmpPosition = highestWeight > Math.abs(lowestWeight) ? highPosition : lowPosition;
			}
		}
		
//		System.out.println(highestWeight);
//		System.out.println(lowestWeight);
//		System.out.println(highPosition);
//		System.out.println(lowPosition);
		
		// random - this means the board is empty
		if (!positionSelected && (lowestSlotRow[3] >= 0 && Game.slotOpen(originalClone, lowestSlotRow[3], 3)))
		{
			tmpPosition = 3;
			positionSelected = true;
		}
		else if (!positionSelected && (lowestSlotRow[0] >= 0 && Game.slotOpen(originalClone, lowestSlotRow[0], 0)))
		{
			tmpPosition = 0;
			positionSelected = true;
		}
		else if (!positionSelected && (lowestSlotRow[6] >= 0 && Game.slotOpen(originalClone, lowestSlotRow[6], 6)))
		{
			tmpPosition = 6;
			positionSelected = true;
		}
		
		return tmpPosition;
	}
	
	// counts the value of the given input
	public static double valCount(int r, int c, int type, int boardNum)
	{
		double count = 0;
		boolean continuous = true;
		
		int[] row = {r, 0, 0, 0};
		int[] col = {c, 0, 0, 0};
		
		// All combinations given type **********************************
		if (type == 0)			// top left
		{
			row[1] = row[0] - 1;
			col[1] = col[0] - 1;
			row[2] = row[1] - 1;
			col[2] = col[1] - 1;
			row[3] = row[2] - 1;
			col[3] = col[2] - 1;
		}
		else if (type == 1)		// middle left
		{
			row[1] = row[0];
			col[1] = col[0] - 1;
			row[2] = row[1];
			col[2] = col[1] - 1;
			row[3] = row[2];
			col[3] = col[2] - 1;
		}
		else if (type == 2)		// bottom left
		{
			row[1] = row[0] + 1;
			col[1] = col[0] - 1;
			row[2] = row[1] + 1;
			col[2] = col[1] - 1;
			row[3] = row[2] + 1;
			col[3] = col[2] - 1;
		}
		else if (type == 3)		// middle bottom
		{
			row[1] = row[0] + 1;
			col[1] = col[0];
			row[2] = row[1] + 1;
			col[2] = col[1];
			row[3] = row[2] + 1;
			col[3] = col[2];
		}
		else if (type == 4)		// right bottom
		{
			row[1] = row[0] + 1;
			col[1] = col[0] + 1;
			row[2] = row[1] + 1;
			col[2] = col[1] + 1;
			row[3] = row[2] + 1;
			col[3] = col[2] + 1;
		}
		else if (type == 5)		// right middle
		{
			row[1] = row[0];
			col[1] = col[0] + 1;
			row[2] = row[1];
			col[2] = col[1] + 1;
			row[3] = row[2];
			col[3] = col[2] + 1;
		}
		else if (type == 6)		// right top
		{
			row[1] = row[0] - 1;
			col[1] = col[0] + 1;
			row[2] = row[1] - 1;
			col[2] = col[1] + 1;
			row[3] = row[2] - 1;
			col[3] = col[2] + 1;
		}
		// does the actual counting*************************************************
		
		if (boardNum == 0)
		{
			// beside slot
			if (row[1] >= 0 && row[1] < 6 && col[1] >= 0 && col[1] < 7 && continuous)
			{
				if (originalClone[row[1]][col[1]].owner() == playerNum && originalClone[row[1]][col[1]].owner() != 0) 
				{
					count+=add[0];
				}
				else if (originalClone[row[1]][col[1]].owner() == oppositeNum && originalClone[row[1]][col[1]].owner() != 0)
				{
					count-=subtract[0];
				}
				else
				{
					continuous = false;
				}
			}
			
			//two slots away
			if (row[2] >= 0 && row[2] < 6 && col[2] >= 0 && col[2] < 7 && continuous)
			{
				if (originalClone[row[2]][col[2]].owner() == originalClone[row[1]][col[1]].owner())
				{
					if (originalClone[row[2]][col[2]].owner() == playerNum)
					{
						count+=add[1];
					}
					else if (originalClone[row[2]][col[2]].owner() == oppositeNum)
					{
						count-=subtract[1];
					}
				}
				else
				{
					continuous = false;
				}
			}
			
			// three slots away
			if (row[3] >= 0 && row[3] < 6 && col[3] >= 0 && col[3] < 7 && continuous)
			{
				if (originalClone[row[3]][col[3]].owner() == originalClone[row[2]][col[2]].owner())
				{
					if (originalClone[row[3]][col[3]].owner() == playerNum)
					{
						count+=add[2];
					}
					else if (originalClone[row[3]][col[3]].owner() == oppositeNum)
					{
						count-=subtract[2];
					}
				}
				else
				{
					continuous = false;
				}
			}
		}
		else if (boardNum == 1)
		{
			// beside slot
			if (row[1] >= 0 && row[1] < 6 && col[1] >= 0 && col[1] < 7 && continuous)
			{
				if (oppositeFutureClone[row[1]][col[1]].owner() == playerNum && oppositeFutureClone[row[1]][col[1]].owner() != 0)
				{
					count+=add[0];
				}
				else if (oppositeFutureClone[row[1]][col[1]].owner() == oppositeNum && oppositeFutureClone[row[1]][col[1]].owner() != 0)
				{
					count-=subtract[0];
				}
				else
				{
					continuous = false;
				}
			}
			
			//two slots away
			if (row[2] >= 0 && row[2] < 6 && col[2] >= 0 && col[2] < 7 && continuous)
			{
				if (oppositeFutureClone[row[2]][col[2]].owner() == oppositeFutureClone[row[1]][col[1]].owner())
				{
					if (oppositeFutureClone[row[2]][col[2]].owner() == playerNum)
					{
						count+=add[1];
					}
					else if (oppositeFutureClone[row[2]][col[2]].owner() == oppositeNum)
					{
						count-=subtract[1];
					}
				}
				else
				{
					continuous = false;
				}
			}
			
			// three slots away
			if (row[3] >= 0 && row[3] < 6 && col[3] >= 0 && col[3] < 7 && continuous)
			{
				if (oppositeFutureClone[row[3]][col[3]].owner() == oppositeFutureClone[row[2]][col[2]].owner())
				{
					if (oppositeFutureClone[row[3]][col[3]].owner() == playerNum)
					{
						count+=add[2];
					}
					else if (oppositeFutureClone[row[3]][col[3]].owner() == oppositeNum)
					{
						count-=subtract[2];
					}
				}
				else
				{
					continuous = false;
				}
			}
		}
		else if (boardNum == 2)
		{
			// beside slot
			if (row[1] >= 0 && row[1] < 6 && col[1] >= 0 && col[1] < 7 && continuous)
			{
				if (myFutureClone[row[1]][col[1]].owner() == playerNum && myFutureClone[row[1]][col[1]].owner() != 0)
				{
					count+=add[0];
				}
				else if (myFutureClone[row[1]][col[1]].owner() == oppositeNum && myFutureClone[row[1]][col[1]].owner() != 0)
				{
					count-=subtract[0];
				}
				else
				{
					continuous = false;
				}
			}
			
			//two slots away
			if (row[2] >= 0 && row[2] < 6 && col[2] >= 0 && col[2] < 7 && continuous)
			{
				if (myFutureClone[row[2]][col[2]].owner() == myFutureClone[row[1]][col[1]].owner())
				{
					if (myFutureClone[row[2]][col[2]].owner() == playerNum)
					{
						count+=add[1];
					}
					else if (myFutureClone[row[2]][col[2]].owner() == oppositeNum)
					{
						count-=subtract[1];
					}
				}
				else
				{
					continuous = false;
				}
			}
			
			// three slots away
			if (row[3] >= 0 && row[3] < 6 && col[3] >= 0 && col[3] < 7 && continuous)
			{
				if (myFutureClone[row[3]][col[3]].owner() == myFutureClone[row[2]][col[2]].owner())
				{
					if (myFutureClone[row[3]][col[3]].owner() == playerNum)
					{
						count+=add[2];
					}
					else if (myFutureClone[row[3]][col[3]].owner() == oppositeNum)
					{
						count-=subtract[2];
					}
				}
				else
				{
					continuous = false;
				}
			}
		}
		
		return count;
	}

	// calculates the weighted slots
	public static void weightCalc()
	{
		for(int clear = 0; clear < 14; clear++)
		{
			for (int i = 0; i < weightedSlot[clear].length; i++)
			{
				weightedSlot[clear][i] = 0;
				oppositeFutureWeightedSlot[clear][i] = 0;
				myFutureWeightedSlot[clear][i] = 0;
			}
		}
		for (int clear = 0; clear < overallWeight.length; clear++)
		{
			overallWeight[clear] = 0;
		}
		
		for (int col = 0; col < Game.getBoardCols(); col++)
		{
			// assigns a weight on every single value (49 + 21 values total)
			if (lowestSlotRow[col] > -1)
			{
				for (int i = 0; i < 7; i++)
					weightedSlot[col][i] = valCount(lowestSlotRow[col], col, i, 0);
				
				for (int i = 0; i < 3; i++)
					weightedSlot[col+7][i] = weightedSlot[col][i] + weightedSlot[col][i+4];
			}
			else
			{
				for (int i = 0; i < 7; i++)
					weightedSlot[col][i] = 0;
				
				for (int i = 0; i < 3; i++)
					weightedSlot[col+7][i] = weightedSlot[col][i] + weightedSlot[col][i+4];
			}
			
			// assigns a weight on every single future value (49 + 21 values total)
			if (secondLowestSlotRow[col] > -1)
			{
				oppositeFutureClone = Game.getBoardClone();
				Game.applyTurnToBoard(oppositeFutureClone, col, playerNum);
				for (int i = 0; i < 7; i++)
					oppositeFutureWeightedSlot[col][i] = valCount(secondLowestSlotRow[col], col, i, 1);
				
				for (int i = 0; i < 3; i++)
					oppositeFutureWeightedSlot[col+7][i] = oppositeFutureWeightedSlot[col][i] + oppositeFutureWeightedSlot[col][i+4];
			}
			else
			{
				oppositeFutureClone = Game.getBoardClone();
				Game.applyTurnToBoard(oppositeFutureClone, col, playerNum);
				for (int i = 0; i < 7; i++)
					oppositeFutureWeightedSlot[col][i] = 0;
				
				for (int i = 0; i < 3; i++)
					oppositeFutureWeightedSlot[col+7][i] = oppositeFutureWeightedSlot[col][i] + oppositeFutureWeightedSlot[col][i+4];
			}
			
			// assigns a weight on every single future value (49 + 21 values total)
			if (thirdLowestSlotRow[col] > -1)
			{
				myFutureClone = Game.getBoardClone();
				Game.applyTurnToBoard(myFutureClone, col, playerNum);
				for (int i = 0; i < 7; i++)
					myFutureWeightedSlot[col][i] = valCount(thirdLowestSlotRow[col], col, i, 2);
				
				for (int i = 0; i < 3; i++)
					myFutureWeightedSlot[col+7][i] = myFutureWeightedSlot[col][i] + myFutureWeightedSlot[col][i+4];
			}
			else
			{
				myFutureClone = Game.getBoardClone();
				Game.applyTurnToBoard(myFutureClone, col, playerNum);
				for (int i = 0; i < 7; i++)
					myFutureWeightedSlot[col][i] = 0;
				
				for (int i = 0; i < 3; i++)
					myFutureWeightedSlot[col+7][i] = myFutureWeightedSlot[col][i] + myFutureWeightedSlot[col][i+4];
			}
		}
		
		double max = add[0] + add[1] + add[2];
		double min = -(subtract[0] + subtract[1] + subtract[2]);
		
		for (int col = 0; col < overallWeight.length; col++)
		{
			// remaining numbers
			for (int i = 0; i < weightedSlot[col].length; i++)
				overallWeight[col] += weightedSlot[col][i];
			
			// my future win and loss
			for (int i = 0; i < 7; i++)
				if (myFutureWeightedSlot[col][i] >= max)
					overallWeight[col] += add[2]*3;
			for (int i = 0; i < 3; i++)
				if (myFutureWeightedSlot[col+7][i] > max - add[2])
					overallWeight[col] += add[2]*3;
			
			for (int i = 0; i < 7; i++)
				if (myFutureWeightedSlot[col][i] <= min)
					overallWeight[col] = 0;
			for (int i = 0; i < 3; i++)
				if (myFutureWeightedSlot[col+7][i] < min + subtract[2])
					overallWeight[col] = 0;
			
			// opposite future loss
			for (int i = 0; i < 7; i++)
				if (oppositeFutureWeightedSlot[col][i] <= min)
					overallWeight[col] = 0;
			for (int i = 0; i < 3; i++)
				if (oppositeFutureWeightedSlot[col+7][i] < min + subtract[2])
					overallWeight[col] = 0;
			
			// current win or loss
			for (int i = 0; i < 7; i++)
				if (weightedSlot[col][i] <= min)
					overallWeight[col] += add[2]*5;
			for (int i = 0; i < 3; i++)
				if (weightedSlot[col+7][i] < min + subtract[2])
					overallWeight[col] += add[2]*5;
			
			for (int i = 0; i < 7; i++)
				if (weightedSlot[col][i] >= max)
					overallWeight[col] += add[2]*10;
			for (int i = 0; i < 3; i++)
				if (weightedSlot[col+7][i] > max - add[2])
					overallWeight[col] += add[2]*10;
		}
	}
}

