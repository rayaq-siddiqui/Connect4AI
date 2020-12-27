package connectFour;

//Out of the 7 available spots, this AI picks the spot with the highest value according
//to the array spotValues.  

//Issue: It works from left to right and therefore tends to pick more from the left side
//as tie breakers go to the first spot found with the value.

//As player 1, 97% win% against RandomAI
//As player 2, 87% win% against RandomAI

public class CampeauAI01 
{
	public static int[][] spotValues = {{2, 3, 4, 5, 4, 3, 2},    //as player1, 97% win% against RandomAI
										{3, 4, 5, 6, 5, 4, 3},    //as player2, 87% win% against RandomAI
										{4, 5, 6, 7, 6, 5, 4},
										{4, 5, 6, 7, 6, 5, 4},
										{3, 4, 5, 6, 5, 4, 3},
										{2, 3, 4, 5, 4, 3, 2}};
	
	public static int playTurn(Slot[][] gb, int playerNumber, String playerColour)
	{
		int[] choices = Game.getListOfChoices(gb);
		int maxValue = 0;
		int maxRow = -1;
		int maxCol = -1;
		for (int c=0; c<Game.getBoardCols(); c++)
		{
			if (choices[c] != -1)  //if the column is not full
			{
				int spotRow = choices[c];
				int spotCol = c;
				int value = spotValues[spotRow][spotCol];
				if(value > maxValue)
				{
					maxValue = value;
					maxRow = spotRow;
					maxCol = spotCol;
				}
			}
			
		}
		return maxCol;
	}

}
