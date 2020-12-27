package connectFour;

import java.util.Arrays;

public class ConnectRayaqAI 
{
	/* DATA FIELDS */
	// weights all of the values of the bottom-most slot (starts top left and goes counter clockwise)
	public static int[][] 
			weightedSlot = new int[7][7],			// top left, mid left, bottom left, bottom middle...
			futureWeightedSlot = new int[7][7],		// top left, mid left, bottom left, bottom middle...
			diagnolWeightSlot = new int[7][3],		// topleft-bottomright, mid(horizonatal), bottomleft-topright
			futureDiagnolWeightSlot = new int[7][3];// topleft-bottomright, mid(horizonatal), bottomleft-topright
	// open slots (starting at column 0 until 6)
	public static int[] 
			lowestOpenSlotRow = {5, 5, 5, 5, 5, 5, 5},
			secondLowestSlotRow = {5, 5, 5, 5, 5, 5, 5},
			overallWeight = {0, 0, 0, 0, 0, 0, 0};
	// what is the value of your piece
	public static String yourPiece = "";
	// clone of the game board
	public static Slot[][] 
			originalClone, 
			futureClone;
	// player turn number
	public static int 
			playerNum,
			oppositeNum;
	
	/* METHODS */
	// required method
	public static int playTurn(Slot[][] gb, int playerNumber, String playerColour) throws InterruptedException
	{
		originalClone = gb;
		futureClone = Game.getBoardClone();
		yourPiece = playerColour;
		playerNum = playerNumber;
		oppositeNum = playerNum == 1 ? 2 : 1;
			
		weightCalc();
		
		//************************************************************
		
//		System.out.println(Arrays.toString(lowestOpenSlotRow));
//		for (int[] a : weightedSlot)
//			System.out.println("    " + Arrays.toString(a));
//		for (int[] a : diagnolWeightSlot)
//			System.out.println("         " + Arrays.toString(a));
//		
//		System.out.println(Arrays.toString(secondLowestSlotRow));
//		for (int[] a : futureWeightedSlot)
//			System.out.println("    " + Arrays.toString(a));
//		for (int[] a : futureDiagnolWeightSlot)
//			System.out.println("         " + Arrays.toString(a));
		
		//************************************************************
		
		int position = positionDecide();
		System.out.println(Arrays.toString(overallWeight));
		System.out.println(position);
		return position;
	}
	
	// decides the position that should be played
	public static int positionDecide() throws InterruptedException
	{
		int tmpPosition = 0;
		int combinationNum = 0;
		int selectedVal = 0;		// the weight of the selected postion
		boolean positionSelected = false;
		boolean diagnol = false;
		
		while(!positionSelected)
		{
			tmpPosition = 0;
			combinationNum = 0;
			selectedVal = 0;
			positionSelected = false;
			diagnol = false;

// ***************************************************************************************************************************************************
//WINNING / PREVENTING LOSS
			// diagnol slot weights extremes win
			for (int col = 0; col < diagnolWeightSlot.length; col++)
			{
				for (int combo = 0; combo < diagnolWeightSlot[col].length && lowestOpenSlotRow[col] != -1; combo++)
				{
					if (diagnolWeightSlot[col][combo] >= 4)
					{
						tmpPosition = col;
						combinationNum = combo;
						positionSelected = true;
						diagnol = true;
						selectedVal = diagnolWeightSlot[col][combo];
					}
				}
			}
			
			// normal slot weights extremes win
			if (!positionSelected)
			{
				for (int col = 0; col < weightedSlot.length; col++)
				{
					for (int combo = 0; combo < weightedSlot[col].length && lowestOpenSlotRow[col] != -1; combo++)
					{
						if (weightedSlot[col][combo] >= 6)
						{
							tmpPosition = col;
							combinationNum = combo;
							positionSelected = true;
							diagnol = false;
							selectedVal = weightedSlot[col][combo];
						}
					}
				}
			}
			
			// diagnol slot weights extremes prevents loss
			for (int col = 0; col < diagnolWeightSlot.length; col++)
			{
				for (int combo = 0; combo < diagnolWeightSlot[col].length && lowestOpenSlotRow[col] != -1; combo++)
				{
					if (diagnolWeightSlot[col][combo] <= -4)
					{
						tmpPosition = col;
						combinationNum = combo;
						positionSelected = true;
						diagnol = true;
						selectedVal = diagnolWeightSlot[col][combo];
					}
				}
			}
			
			// normal slot weights extremes prevents loss
			if (!positionSelected)
			{
				for (int col = 0; col < weightedSlot.length; col++)
				{
					for (int combo = 0; combo < weightedSlot[col].length && lowestOpenSlotRow[col] != -1; combo++)
					{
						if (weightedSlot[col][combo] <= -6)
						{
							tmpPosition = col;
							combinationNum = combo;
							positionSelected = true;
							diagnol = false;
							selectedVal = weightedSlot[col][combo];
						}
					}
				}
			}
			
// ***************************************************************************************************************************************************
// STRATEGIC TMPPOSITION METHODS - REMOVES ANY FUTURE LOSS COMBOS
			// future move check remover
			if (!positionSelected)
			{
				for (int position = 0; position < futureDiagnolWeightSlot.length; position++)
				{
					// diagnol for loop
					for (int combo = 0; combo < futureDiagnolWeightSlot[position].length && secondLowestSlotRow[position] > -1 && lowestOpenSlotRow[position] > -1; combo++)
					{
						if (futureDiagnolWeightSlot[position][combo] <= -4)
						{
							for (int i = 0; i < diagnolWeightSlot[position].length; i++)
								diagnolWeightSlot[position][i] = 0;
							for (int i = 0; i < weightedSlot[position].length; i++)
								weightedSlot[position][i] = 0;
							overallWeight[position] = 0;
							
							System.out.println("DIAGNOL FOR LOOP REMOVE: " + position);
			//								Thread.sleep(10);
						}
					}
					
					// normal for loop
					for (int combo = 0; combo < futureWeightedSlot[position].length && secondLowestSlotRow[position] > -1 && lowestOpenSlotRow[position] > -1; combo++)
					{
						if (futureWeightedSlot[position][combo] <= -6)
						{
							for (int i = 0; i < diagnolWeightSlot[position].length; i++)
								diagnolWeightSlot[position][i] = 0;
							for (int i = 0; i < weightedSlot[position].length; i++)
								weightedSlot[position][i] = 0;
							overallWeight[position] = 0;
							
							System.out.println("NORMAL FOR LOOP REMOVE: " + position);
			//								Thread.sleep(10);
						}
					}
				}
			}
			
// ***************************************************************************************************************************************************
// STRATEGIC TMPPOSITION METHODS - DETERMINES ANY FUTURE WINS
			// future move check remover
			if (!positionSelected)
			{
				weightCalc();
				for (int position = 0; position < futureDiagnolWeightSlot.length; position++)
				{
					// diagnol for loop
					for (int combo = 0; combo < futureDiagnolWeightSlot[position].length && secondLowestSlotRow[position] > -1 && lowestOpenSlotRow[position] > -1; combo++)
					{
						if (futureDiagnolWeightSlot[position][combo] >= 4)
						{
							overallWeight[position] += 10;
							
							System.out.println("DIAGNOL FOR LOOP WIN ADD: " + position);
//							Thread.sleep(100);
						}
					}
					
					// normal for loop
					for (int combo = 0; combo < futureWeightedSlot[position].length && secondLowestSlotRow[position] > -1 && lowestOpenSlotRow[position] > -1; combo++)
					{
						if (futureWeightedSlot[position][combo] >= 6)
						{
							overallWeight[position] += 10;
							
							System.out.println("NORMAL FOR LOOP WIN ADD: " + position);
//							Thread.sleep(100);
						}
					}
				}
				
				int highestWeight = 0;
				int lowestWeight = 0;
				int highPosition = 0;
				int lowPosition = 0;
				for (int col = 0; col < overallWeight.length; col++)
				{
					if (lowestOpenSlotRow[col] > -1)
					{
						if (overallWeight[col] > highestWeight)
						{
							highPosition = col;
							highestWeight = overallWeight[col];
							positionSelected = true;
						}
						if (overallWeight[col] < lowestWeight)
						{
							lowPosition = col;
							lowestWeight = overallWeight[col];
							positionSelected = true;
						}
					}
				}
				if (positionSelected)
				{
					tmpPosition = overallWeight[highPosition] > Math.abs(overallWeight[lowPosition]) ? highPosition : lowPosition;
					selectedVal = overallWeight[tmpPosition];
					System.out.println(Arrays.toString(overallWeight));
					System.out.println(highPosition + ", " + lowPosition);
				}
			}
						
// ***************************************************************************************************************************************************			
// defense/ offensive avg
//			// diagnol developing positions normal preventing loss
//			if (!positionSelected)
//			{
//				for (int col = 0; col < diagnolWeightSlot.length; col++)
//				{
//					for (int combo = 0; combo < diagnolWeightSlot[col].length && lowestOpenSlotRow[col] != -1; combo++)
//					{
//						if (diagnolWeightSlot[col][combo] <= -3)
//						{
//							tmpPosition = col;
//							combinationNum = combo;
//							positionSelected = true;
//							diagnol = true;
//							selectedVal = diagnolWeightSlot[col][combo];
//						}
//						
////						if (!positionSelected && diagnolWeightSlot[col][combo] >= 3)
////						{
////							tmpPosition = col;
////							combinationNum = combo;
////							positionSelected = true;
////							diagnol = true;
////							selectedVal = diagnolWeightSlot[col][combo];
////						}
//					}
//				}
//			}
//			
//			// normal developing positions normal
//			if (!positionSelected)
//			{
//				for (int col = 0; col < weightedSlot.length; col++)
//				{
//					for (int combo = 0; combo < weightedSlot[col].length && lowestOpenSlotRow[col] != -1; combo++)
//					{
//						if (weightedSlot[col][combo] <= -3)
//						{
//							tmpPosition = col;
//							combinationNum = combo;
//							positionSelected = true;
//							diagnol = false;
//							selectedVal = weightedSlot[col][combo];
//						}
//						
//						if (!positionSelected && weightedSlot[col][combo] >= 3)
//						{
//							tmpPosition = col;
//							combinationNum = combo;
//							positionSelected = true;
//							diagnol = false;
//							selectedVal = weightedSlot[col][combo];
//						}
//					}
//				}
//			}

// ***************************************************************************************************************************************************		
			
//			if (!positionSelected)
//			{
//				int highestWeight = 0;
//				int lowestWeight = 0;
//				for (int col = 0; col < overallWeight.length; col++)
//				{
//					if (overallWeight[col] > highestWeight)
//					{
//						tmpPosition = col;
//						positionSelected = true;
//						selectedVal = overallWeight[col];
//					}
//				}
//				
//				// for loop lowest
//				
//				// higher abs value go
//			}
			
//			if (diagnol)
//			{
//				System.out.println(Arrays.toString(diagnolWeightSlot[tmpPosition]));
//				Thread.sleep(10);
//			}
//			else
//			{
//				System.out.println(Arrays.toString(weightedSlot[tmpPosition]));
//				Thread.sleep(10);
//			}
			
			// random - this means the board is empty
			if (!positionSelected && (lowestOpenSlotRow[3] >= 0 && Game.slotOpen(originalClone, lowestOpenSlotRow[3], 3)))
			{
				tmpPosition = 3;
				positionSelected = true;
			}
			else if (!positionSelected && (lowestOpenSlotRow[0] >= 0 && Game.slotOpen(originalClone, lowestOpenSlotRow[0], 0)))
			{
				tmpPosition = 0;
				positionSelected = true;
			}
			else if (!positionSelected && (lowestOpenSlotRow[6] >= 0 && Game.slotOpen(originalClone, lowestOpenSlotRow[6], 6)))
			{
				tmpPosition = 6;
				positionSelected = true;
			}
			
			// check if position is valid
			if (lowestOpenSlotRow[tmpPosition] == -1 && positionSelected)
			{
				positionSelected = false;
				if (!diagnol)
					weightedSlot[tmpPosition][combinationNum] = 0;
				else
					diagnolWeightSlot[tmpPosition][combinationNum] = 0;
				overallWeight[tmpPosition] = 0;
			}
			else if (positionSelected && !Game.slotOpen(originalClone, lowestOpenSlotRow[tmpPosition], tmpPosition))
			{
				positionSelected = false;
				if (!diagnol)
					weightedSlot[tmpPosition][combinationNum] = 0;
				else
					diagnolWeightSlot[tmpPosition][combinationNum] = 0;
				overallWeight[tmpPosition] = 0;
			}
		}
				
		return tmpPosition;
	}
	
	// calculates the weighted slots
	public static void weightCalc()
	{
		lowestOpenSlotFinder();
		secondLowestOpenSlotFinder();
		
		for (int col = 0; col < Game.getBoardCols(); col++)
		{
			// assigns a weight on every single value (49 + 21 values total)
			if (lowestOpenSlotRow[col] != -1)
			{
				for (int i = 0; i < 7; i++)
					weightedSlot[col][i] = valCount(lowestOpenSlotRow[col], col, i, true);
				
				for (int i = 0; i < 3; i++)
					diagnolWeightSlot[col][i] = weightedSlot[col][i] + weightedSlot[col][i+4];
			}
			else
			{
				for (int i = 0; i < 7; i++)
					weightedSlot[col][i] = 0;
				
				for (int i = 0; i < 3; i++)
					diagnolWeightSlot[col][i] = weightedSlot[col][i] + weightedSlot[col][i+4];
			}
			
			// assigns a weight on every single future value (49 + 21 values total)
			if (secondLowestSlotRow[col] != -1)
			{
				futureClone = Game.getBoardClone();
				Game.applyTurnToBoard(futureClone, col, playerNum);
				for (int i = 0; i < 7; i++)
					futureWeightedSlot[col][i] = valCount(secondLowestSlotRow[col], col, i, false);
				
				for (int i = 0; i < 3; i++)
					futureDiagnolWeightSlot[col][i] = futureWeightedSlot[col][i] + futureWeightedSlot[col][i+4];
			}
			else
			{
				futureClone = Game.getBoardClone();
				Game.applyTurnToBoard(futureClone, col, playerNum);
				for (int i = 0; i < 7; i++)
					futureWeightedSlot[col][i] = 0;
				
				for (int i = 0; i < 3; i++)
					futureDiagnolWeightSlot[col][i] = futureWeightedSlot[col][i] + futureWeightedSlot[col][i+4];
			}
			overallWeight[col] = 0;
			// overall weight calc
			for (int i = 0; i < weightedSlot[col].length; i++)
				overallWeight[col] += weightedSlot[col][i];
//			for (int i = 0; i < diagnolWeightSlot[col].length; i++)
//				overallWeight[col] += diagnolWeightSlot[col][i];
		}
	}
	
	// count the values of each of the given values
	public static int valCount(int r, int c, int type, boolean original)
	{
		int count = 0;
		boolean continuous = true;
		
		int[] row = {r, 0, 0, 0};
		int[] col = {c, 0, 0, 0};
		
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
		
		if (original)
		{
			// beside slot
			if (row[1] >= 0 && row[1] < 6 && col[1] >= 0 && col[1] < 7 && continuous)
			{
				if (originalClone[row[1]][col[1]].owner() == playerNum && originalClone[row[1]][col[1]].owner() != 0) 
				{
					count++;
				}
				else if (originalClone[row[1]][col[1]].owner() == oppositeNum && originalClone[row[1]][col[1]].owner() != 0)
				{
					count--;
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
						count+=2;
					}
					else if (originalClone[row[2]][col[2]].owner() == oppositeNum)
					{
						count-=2;
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
						count+=3;
					}
					else if (originalClone[row[3]][col[3]].owner() == oppositeNum)
					{
						count-=3;
					}
				}
				else
				{
					continuous = false;
				}
			}
		}
		else if (!original)
		{
			// beside slot
			if (row[1] >= 0 && row[1] < 6 && col[1] >= 0 && col[1] < 7 && continuous)
			{
				if (futureClone[row[1]][col[1]].owner() == playerNum && futureClone[row[1]][col[1]].owner() != 0)
				{
					count++;
				}
				else if (futureClone[row[1]][col[1]].owner() == oppositeNum && futureClone[row[1]][col[1]].owner() != 0)
				{
					count--;
				}
				else
				{
					continuous = false;
				}
			}
			
			//two slots away
			if (row[2] >= 0 && row[2] < 6 && col[2] >= 0 && col[2] < 7 && continuous)
			{
				if (futureClone[row[2]][col[2]].owner() == futureClone[row[1]][col[1]].owner())
				{
					if (futureClone[row[2]][col[2]].owner() == playerNum)
					{
						count+=2;
					}
					else if (futureClone[row[2]][col[2]].owner() == oppositeNum)
					{
						count-=2;
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
				if (futureClone[row[3]][col[3]].owner() == futureClone[row[2]][col[2]].owner())
				{
					if (futureClone[row[3]][col[3]].owner() == playerNum)
					{
						count+=3;
					}
					else if (futureClone[row[3]][col[3]].owner() == oppositeNum)
					{
						count-=3;
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
	
	// fills in the lowest open slot data field with relevant values (works perfectly)
	public static void lowestOpenSlotFinder()
	{
		lowestOpenSlotRow = Game.getListOfChoices(originalClone);
	}
	
	// fills the second lowest open slot data field with information
	public static void secondLowestOpenSlotFinder()
	{
		for (int c = 0; c < Game.getBoardCols(); c++)
		{
			Game.applyTurnToBoard(futureClone, c, playerNum);
		}
		
		secondLowestSlotRow = Game.getListOfChoices(futureClone);
	}
}
