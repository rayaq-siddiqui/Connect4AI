package connectFour;
//Author: Connor Killingbeck

import java.util.Arrays;

//Version: 1.6

import java.util.Scanner;
import java.util.Stack;

public class Game
{
	//SETTINGS
	public static boolean player1IsHuman = false;
	public static boolean player2IsHuman = false;
	public static boolean displayGames = false;
	public static boolean debugErrorsOn = false;
	public static int delayBetweenTurns = 0; //milliseconds
	public static int games = 1000;

	//GAME DATAFIELDS
	private static Scanner scr = new Scanner(System.in);
	private static int boardRows = 6;
	private static int boardCols = 7;
	private static Slot[][] gameBoard = new Slot[boardRows][boardCols];
	private static int turn;
	private static String player1Colour = "RED";
	private static String player2Colour = "BLU";
	static int player1Wins = 0;
	static int player2Wins = 0;
	static int ties = 0;
	private static int turnCount = 0;
	private static int totalTurns = boardRows * boardCols;
	private static int lastTurn = -1;  //column of last turn, -1 means "no last turn"
	
	//================================================================================

	public static void main(String[] args) throws InterruptedException 
	{
		player1Wins = 0;
		player2Wins = 0;
		ties = 0;
				
		for(int g = 0; g < games; g++)
		{
			turn = 1;
			turnCount=0;
			lastTurn = -1;
			setupBoard();
			
			if(displayGames == true)
			{
				drawBoardInConsole();
				Thread.sleep(delayBetweenTurns);
			}
			boolean gameOver = false;
			while(gameOver == false)
			{
				int chosenColumn = playerTurn();
				
				if(isValidChoice(gameBoard, chosenColumn) == true)
				{
					applyTurnToBoard(gameBoard, chosenColumn, turn);
					lastTurn = chosenColumn;
				}
				else
				{
					if (debugErrorsOn == true)
						System.out.println("Illegal column value (" + chosenColumn + ") by player " + turn + ".  Your turn is skipped." );
					lastTurn = -1;
				}
				turnCount++;
				turnSwitch();

				if(displayGames == true)
				{
					drawBoardInConsole();
					Thread.sleep(delayBetweenTurns);
				}

				gameOver = gameWon(gameBoard) || boardIsFull(gameBoard);

			}//END OF GAME LOOP

			int winner = gameWonBy(gameBoard);
			if(winner == 1) //RED
			{
				player1Wins++;
			}
			else if(winner == 2)  //BLU
			{
				player2Wins++;
			}
			else //winner == 0
			{
				ties++;
			}
			
			if(displayGames == true)  //display game result
			{
				System.out.println("***************************************************************");
				System.out.println("*                                                             *");
				System.out.println("*                                                             *");

				if (winner == 0)  //tie
					System.out.println("* It's a tie game!                                            *");
				else 
					System.out.println("* The winner is Player " + winner + "!                                     *");
				System.out.println("*                                                             *");
				System.out.println("*                                                             *");
				System.out.println("***************************************************************");
				Thread.sleep(delayBetweenTurns*5);
			}

		}

		System.out.println("Player 1 wins: " + player1Wins);
		System.out.println("Player 2 wins: " + player2Wins);
		System.out.println("Tie games:" + ties);
	}


	//================================================================================
	//****************************************************
	//CALL YOUR AI DOWN BELOW
	//****************************************************
	private static int playerTurn() throws InterruptedException
	{
		int chosenColumn = -1;

		//make an if statement to see whose turn it is

		if(turn == 1 && player1IsHuman == false)
		{
			chosenColumn = CampeauAI02.playTurn(getBoardClone(), 1, player1Colour);    //CALL CORRECT AI HERE
		}

		if(turn == 2 && player2IsHuman == false)
		{
			chosenColumn = NewConnectRayaqAI.playTurn(getBoardClone(), 2, player2Colour);		//CALL CORRECT AI HERE
		}

		if(turn == 1 && player1IsHuman == true)
		{
			chosenColumn = getHumanInput(1, player1Colour);
		}

		if(turn == 2 && player2IsHuman == true)
		{
			chosenColumn = getHumanInput(2, player2Colour);
		}

		return chosenColumn;
	}
	
	//================================================================================

	private static void setupBoard()
	{
		for(int r = 0; r < boardRows; r++)
		{
			for (int c = 0; c < boardCols; c++)
			{
				gameBoard[r][c] = new Slot();
			}
		}
	}
	
	//================================================================================

	public static void drawBoardInConsole()
	{
		System.out.println("---------------------------------------------------------");
		int turnsLeft = totalTurns - turnCount;
		String colour = (turn == 1) ? player1Colour : player2Colour;
		System.out.println("Turns:" + turnCount + "       Turns Left:" + turnsLeft + "       Next:Player " + turn + "(" + colour + ")     Last Turn:Col #" + lastTurn);
		System.out.println("---------------------------------------------------------");

		System.out.println("");
		for(int r = 0; r < boardRows; r++)
		{
			for (int c = 0; c < boardCols; c++)
			{
				System.out.print("| " + gameBoard[r][c].value + " ");
			}
			System.out.println(" |");
		}
		System.out.println("");
	}

	//================================================================================

	private static int getHumanInput(int playerNumber, String colour)
	{
		
		String msg = "Player " + playerNumber + " (" + colour + "), input your column choice (0-6)";

		System.out.println(msg);
		int columnChoice = scr.nextInt();
		
		while(columnChoice < 0 || columnChoice > 6 || columnIsFull(gameBoard, columnChoice))
		{
			System.out.println("That was an invalid input value.  Try again.");
			System.out.println(msg);
			columnChoice = scr.nextInt();
		}
		return columnChoice;
	}
	
	//================================================================================

	private static void turnSwitch()
	{
		if(turn == 1)
		{
			turn = 2;
		}

		else if(turn == 2)
		{
			turn = 1;
		}		
	}

	//================================================================================	

    //returns true if all four values are the same and not the blank slot
	
	private static boolean quadCheck(String a, String b, String c, String d)
	{
		if (a.equals(b) && a.contentEquals(c) && a.equals(d) && !a.contentEquals(" o "))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

//================================================================================	
//================================================================================
//================================================================================
//================================================================================
//================================================================================
//================================================================================
//================================================================================
//================================================================================

	public static void applyTurnToBoard(Slot[][] gb, int chosenColumn, int currentTurn)
	{
		if(chosenColumn > -1 && chosenColumn < 7)
		{
			if (columnIsFull(gb, chosenColumn) == false)
			{					
				if(currentTurn == 1)
				{
					dropInColumn(gb, player1Colour, chosenColumn);  //RED
				}
				else if(currentTurn == 2)
				{
					dropInColumn(gb, player2Colour, chosenColumn);  //BLU
				}
			}
		}
	}

	//================================================================================

	public static void dropInColumn(Slot[][] gb, String colour, int column)
	{
		int currentRow = 0;
		while(currentRow < boardRows-1 && gb[currentRow+1][column].isOpen())
		{
			currentRow++;
		}	

		gb[currentRow][column].value = colour;
	}
	
//================================================================================


	public static boolean isValidChoice(Slot[][] gb, int chosenColumn)
	{
		return chosenColumn >= 0 && chosenColumn <= boardCols && !columnIsFull(gb, chosenColumn);
	}
	
//================================================================================
	
	//Returns a copy of the provided gameBoard object.
	
	public static Slot[][] getBoardClone(Slot[][] gb)
	{
		Slot[][] clone = new Slot[boardRows][boardCols];
		for(int r=0; r<boardRows; r++)
			for(int c=0; c<boardCols; c++)
				clone[r][c] = gb[r][c].clone();
		return clone;
	}
	
	//================================================================================

	//Returns a copy of the game's gameBoard object.
	
	public static Slot[][] getBoardClone()
	{
		Slot[][] clone = new Slot[boardRows][boardCols];
		for(int r=0; r<boardRows; r++)
			for(int c=0; c<boardCols; c++)
				clone[r][c] = gameBoard[r][c].clone();
		return clone;
	}
		
	//================================================================================

	public static boolean boardIsFull(Slot[][] gb)
	{
		int topRow = 0;
		return rowIsFull(gb, topRow);  //only need to check top row
	}
	
	//================================================================================
	
	public static boolean columnIsFull(Slot[][] gb, int column)
	{
		int topRow=0;
		if(gb[topRow][column].isOpen() == true)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	//================================================================================

	public static boolean rowIsFull(Slot[][] gb, int row)
	{
		boolean full = true;
		for(int c=0; c<boardCols; c++)
		{
			if (gb[row][c].isOpen() == true)
			{
				full = false;
			}
		}
		return full;
	}
	
	//================================================================================

	public static int slotOwner(Slot[][] gb, int row, int col)
	{
		return gb[row][col].owner();
	}

	//================================================================================

	public static String slotValue(Slot[][] gb, int row, int col)
	{
		return gb[row][col].value;
	}

	//================================================================================

	public static boolean slotOpen(Slot[][] gb, int row, int col)
	{
		return gb[row][col].isOpen();
	}

	//================================================================================

	public static int lowestEmptySlot(Slot[][] gb, int col)
	{
		int row=boardRows-1;  //5
		while(row >= 0 && gb[row][col].isOpen() == false)
		{
			row--;
		}
		if (row == boardRows)
		{
			return -1;
		}
		else
		{
			return row;
		}
	}

	//================================================================================	

	public static boolean gameWon(Slot[][] gb)
	{
		int winner = gameWonBy(gb);
		if (winner == 1 || winner == 2)
			return true;
		else
			return false;
	}
	
	//================================================================================	

	//Returns 0 when there no winner, 1 when player1 wins and 2 when player2 wins.

	public static int gameWonBy(Slot[][] gb)
	{
		for(int r = 0; r < 6; r++)
		{
			for(int c = 0; c < 7; c++)
			{
				//test - down
				try 
				{
					boolean test1 = quadCheck(gb[r][c].value, gb[r][c+1].value, gb[r][c+2].value, gb[r][c+3].value);
					if (test1 == true)
					{
						return gb[r][c].owner();
					}
				}
				catch(Exception e)
				{
					//Do Nothing
				}

				//test - up
				try
				{
					boolean test2 = quadCheck(gb[r][c].value, gb[r][c-1].value, gb[r][c-2].value, gb[r][c-3].value);
					if (test2 == true)
					{
						return gb[r][c].owner();
					}
				}
				catch(Exception e)
				{
					//Do Nothing
				}

				//test - right
				try 
				{
					boolean test3 = quadCheck(gb[r][c].value, gb[r+1][c].value, gb[r+2][c].value, gb[r+3][c].value);
					if (test3 == true)
					{
						return gb[r][c].owner();
					}
				}
				catch(Exception e)
				{
					//Do Nothing
				}

				//test - left
				try
				{
					boolean test4 = quadCheck(gb[r][c].value, gb[r-1][c].value, gb[r-2][c].value, gb[r-3][c].value);
					if (test4 == true)
					{
						return gb[r][c].owner();
					}
				}
				catch(Exception e)
				{
					//Do Nothing
				}

				//diagonal tests

				try 
				{
					boolean test5 = quadCheck(gb[r][c].value, gb[r+1][c+1].value, gb[r+2][c+2].value, gb[r+3][c+3].value);
					if (test5 == true)
					{
						return gb[r][c].owner();
					}					
				}
				catch(Exception e)
				{
					//Do Nothing
				}

				try 
				{
					boolean test6 = quadCheck(gb[r][c].value, gb[r+1][c-1].value, gb[r+2][c-2].value, gb[r+3][c-3].value);
					if (test6 == true)
					{
						return gb[r][c].owner();
					}					
				}
				catch(Exception e)
				{
					//Do Nothing
				}

				try 
				{
					boolean test7 = quadCheck(gb[r][c].value, gb[r-1][c+1].value, gb[r-2][c+2].value, gb[r-3][c+3].value);
					if (test7 == true)
					{
						return gb[r][c].owner();
					}
				}
				catch(Exception e)
				{
					//Do Nothing
				}

				try 
				{
					boolean test8 = quadCheck(gb[r][c].value, gb[r-1][c-1].value, gb[r-2][c-2].value, gb[r-3][c-3].value);
					if (test8 == true)
					{
						return gb[r][c].owner();
					}					
				}
				catch(Exception e)
				{
					//Do Nothing
				}
			}
		}
		return 0;
	}		

	
	//================================================================================	

	public static int getTurnCount()
	{
		return turnCount;
	}
	
	//================================================================================	

	public static int getTurnsLeft()
	{
		return totalTurns - turnCount;
	}
	
	//================================================================================	

	public static int getCurrentTurn()
	{
		return turn;
	}
	
	//================================================================================	

	public static int getLastTurn()
	{
		return lastTurn;
	}
	
	//================================================================================	

	public static String getPlayer1Colour()
	{
		return player1Colour;
	}

	//================================================================================	

	public static String getPlayer2Colour()
	{
		return player2Colour;
	}	
	
	//================================================================================	

	public static int getBoardRows()
	{
		return boardRows;
	}
	
	//================================================================================	

	public static int getBoardCols()
	{
		return boardCols;
	}	
	
	//================================================================================	

	//Returns an arrays of choices that are available.  
	//The index in the array is the column value and the value of the element is the row value.  
	//If choices[0] equals 4, that means that the slot with (r,c) = (4,0) is available this turn.  So the slots below, with (r,c) = (5,0) and (6,0) are already taken.
	
	public static int[] getListOfChoices(Slot[][] gb)
	{
		int choices[] = new int[boardCols];
		for (int c=0; c<boardCols; c++)
		{
			choices[c] = lowestEmptySlot(gb, c);
		}
		return choices;
	}
	
}
