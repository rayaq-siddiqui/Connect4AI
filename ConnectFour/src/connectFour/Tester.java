package connectFour;

public class Tester 
{
	public static int boardRows = 6;
	
	
	
	public static void main(String[] args) 
	{
		ConnectRayaqAI.lowestOpenSlotFinder();
		System.out.println(ConnectRayaqAI.lowestOpenSlotRow);
	}
	
}
