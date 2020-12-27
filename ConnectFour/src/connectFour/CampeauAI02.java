package connectFour;

//This AI checks if one of the possible moves leads to a win.  If it does, then
//it chooses that move.  Otherwise, it chooses a random column.

//Issue: The random column, just like in RandomAI, might be full which leads to
//the AI missing its turn.  This is easy to fix but leaving it like this allows
//us to compare the impact of checking for a win when playing against the RandomAI.

//As player 1, 82% win% against RandomAI
//As player 2, 70% win% against RandomAI

public class CampeauAI02 
{
	
	public static int playTurn(Slot[][] gb, int playerNumber, String playerColour)
	{
		int winningChoice = winningChoice(gb, playerNumber, playerColour);
		if (winningChoice != -1)
		{
			return winningChoice;
		}
		else //return a random column (same as RandomAI)
		{
			int column = (int) (Math.random() * 7);
			return column;
		}
	}

	public static int winningChoice(Slot[][] gb, int playerNumber, String playerColour)
	{
		for(int c=0; c< 7; c++)
		{
			Slot[][] gbnew = Game.getBoardClone(gb);
			Game.applyTurnToBoard(gbnew, c, playerNumber);  //add chip to column c
			boolean winningState = Game.gameWon(gbnew);		//check if above move leads to a win
			if (winningState == true)
			{
				return c;
			}	
		}	
		return -1;
	}
	
}
