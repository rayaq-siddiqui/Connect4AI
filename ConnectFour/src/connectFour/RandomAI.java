package connectFour;

public class RandomAI 
{
	public static int playTurn(Slot[][] gb, int playerNumber, String playerColour)
	{
		int column = (int) (Math.random() * 7);
		return column;
	}
}
