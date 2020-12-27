public class ChadAI {
	
	//Can Currently run 300 000 games/minute(Against RandomAI being Player 2)
	//((Wins As P1 + Wins AS P2)/2) / Amount of Games) * 100%
	//Average Win Rate Random AI:	99.4875%	(P1: 99.713%	P2: 99.262%)
	//Average Win Rate CampeauAI02:	96.9210%	(P1: 98.393%	P2: 95.449%)
	//Average Win Rate CampeauAI01:	100.0000%	(P1: 100.000%	P2: 100.000%)
	
	
	public static int playTurn(Slot[][] gb, int playerNumber, String playerColour) {
		int winChoice = win(gb, playerNumber, playerColour);
		int notLoseChoice = notLose(gb, opponentNumber(playerNumber), playerColour);
		//========================================================================================================================
		if (winChoice != -1) {
			return winChoice;
		}
		//========================================================================================================================
		else if(notLoseChoice != -1) {
			return notLoseChoice;
		}
		//========================================================================================================================
		else if(Game.rowIsFull(gb, 5) == false && Game.slotValue(gb, 5, 3) == playerColour) {
			int ans = checkRowTrickOff(gb, playerColour, 5);
			if(ans != -1) {
				return ans;
			}
		}
		else if(Game.rowIsFull(gb, 4) == false && Game.slotValue(gb, 4, 3) == playerColour) {
			int ans = checkRowTrickOff(gb, playerColour, 4);
			if(ans != -1) {
				return ans;
			}
		}
		else if(Game.rowIsFull(gb, 3) == false && Game.slotValue(gb, 3, 3) == playerColour) {
			int ans = checkRowTrickOff(gb, playerColour, 3);
			if(ans != -1) {
				return ans;
			}
		}
		else if(Game.rowIsFull(gb, 2) == false && Game.slotValue(gb, 2, 3) == playerColour) {
			int ans = checkRowTrickOff(gb, playerColour, 2);
			if(ans != -1) {
				return ans;
			}
		}
		else if(Game.rowIsFull(gb, 1) == false && Game.slotValue(gb, 1, 3) == playerColour) {
			int ans = checkRowTrickOff(gb, playerColour, 1);
			if(ans != -1) {
				return ans;
			}
		}
		else if(Game.rowIsFull(gb, 0) == false && Game.slotValue(gb, 0, 3) == playerColour) {
			int ans = checkRowTrickOff(gb, playerColour, 0);
			if(ans != -1) {
				return ans;
			}
		}
		//========================================================================================================================
		int ansLDTOff = checkLeftDiagonalTrickOff(gb, playerColour);
		if(ansLDTOff != -1) {
			return ansLDTOff;
		}
		//========================================================================================================================
		int ansRDTOff = checkRightDiagonalTrickOff(gb, playerColour);
		if(ansRDTOff != -1) {
			return ansRDTOff;
		}		
		//========================================================================================================================
		else if(Game.rowIsFull(gb, 5) == false && Game.slotValue(gb, 5, 3) != playerColour) {
			int ans = checkRowTrick(gb, playerColour, 5);
			if(ans != -1) {
				return ans;
			}
		}
		else if(Game.rowIsFull(gb, 4) == false && Game.slotValue(gb, 4, 3) != playerColour) {
			int ans = checkRowTrick(gb, playerColour, 4);
			if(ans != -1) {
				return ans;
			}
		}
		else if(Game.rowIsFull(gb, 3) == false && Game.slotValue(gb, 3, 3) != playerColour) {
			int ans = checkRowTrick(gb, playerColour, 3);
			if(ans != -1) {
				return ans;
			}
		}
		else if(Game.rowIsFull(gb, 2) == false && Game.slotValue(gb, 2, 3) != playerColour) {
			int ans = checkRowTrick(gb, playerColour, 2);
			if(ans != -1) {
				return ans;
			}
		}
		else if(Game.rowIsFull(gb, 1) == false && Game.slotValue(gb, 1, 3) != playerColour) {
			int ans = checkRowTrick(gb, playerColour, 1);
			if(ans != -1) {
				return ans;
			}
		}
		else if(Game.rowIsFull(gb, 0) == false && Game.slotValue(gb, 0, 3) != playerColour) {
			int ans = checkRowTrick(gb, playerColour, 0);
			if(ans != -1) {
				return ans;
			}
		}
		//========================================================================================================================
		int ansLDT = checkLeftDiagonalTrick(gb, playerColour);
		if(ansLDT != -1) {
			return ansLDT;
		}
		//========================================================================================================================
		int ansRDT = checkRightDiagonalTrick(gb, playerColour);
		if(ansRDT != -1) {
			return ansRDT;
		}
		//========================================================================================================================
		int[] options = Game.getListOfChoices(gb);
		int maxVal = 0;
		int maxCol = -1;
		for (int i = 0; i < Game.getBoardCols(); i++) {
			if (options[i] != -1) {
				int spotRow = options[i];
				int spotCol = i;
				int value = spotValues[spotRow][spotCol];
				if(value > maxVal) {
					maxVal = value;
					maxCol = spotCol;
				}
			}
		}
		return maxCol;
	}
	//================================================================================================================================
	public static int[][] spotValues = {{3, 4, 5, 7, 5, 4, 3}, 
			{3, 5, 7, 9, 7, 5, 3}, 
			{3, 6, 9, 11, 9, 6, 3},
			{2, 5, 8, 10, 8, 5, 2},
			{2, 4, 6, 8, 6, 4, 2},
			{2, 3, 4, 6, 4, 3, 2}};
	//================================================================================================================================
	public static int opponentNumber(int playerNumber) {
		if(playerNumber == 1) {
			return 2;
		}
		else {
			return 1;
		}
	}
	//================================================================================================================================
		public static String opponentColour(String playerColour) {
			if(playerColour == "RED") {
				return "BLU";
			}
			else {
				return "RED";
			}
		}
	//================================================================================================================================
	public static int notLose(Slot[][] gb, int opponentNumber, String playerColour) {
		
		for(int i = 0; i < 7; i++) {
			Slot[][] gbnew = Game.getBoardClone(gb);
			Game.applyTurnToBoard(gbnew, i, opponentNumber);
			boolean won = Game.gameWon(gbnew);	
			if (won == true) {
				return i;
			}	
		}	
		return -1;
	}
	//================================================================================================================================
	public static int win(Slot[][] gb, int playerNumber, String playerColour) {
		for(int i = 0; i < 7; i++) {
			Slot[][] gbnew = Game.getBoardClone(gb);
			Game.applyTurnToBoard(gbnew, i, playerNumber);
			boolean won = Game.gameWon(gbnew);	
			if (won == true) {
				return i;
			}	
		}	
		return -1;
	}
	//================================================================================================================================
	public static int checkRowTrick(Slot[][] gb, String playerColour, int row) {
		if(row == 5) {
			if(Game.slotValue(gb, row, 0) == " o " && Game.slotValue(gb, row, 1) == opponentColour(playerColour) && Game.slotValue(gb, row, 2) == opponentColour(playerColour) && Game.slotValue(gb, row, 3) == " o " && Game.slotValue(gb, row, 4) == " o " || 
					Game.slotValue(gb, row, 2) == " o " && Game.slotValue(gb, row, 3) == " o " && Game.slotValue(gb, row, 4) == opponentColour(playerColour) && Game.slotValue(gb, row, 5) == opponentColour(playerColour) && Game.slotValue(gb, row, 6) == " o ") {
				return 3;
			}
			else if(Game.slotValue(gb, row, 0) == " o " && Game.slotValue(gb, row, 1) == " o " && Game.slotValue(gb, row, 2) == opponentColour(playerColour) && Game.slotValue(gb, row, 3) == opponentColour(playerColour) && Game.slotValue(gb, row, 4) == " o " || 
					Game.slotValue(gb, row, 1) == " o " && Game.slotValue(gb, row, 2) == opponentColour(playerColour) && Game.slotValue(gb, row, 3) == opponentColour(playerColour) && Game.slotValue(gb, row, 4) == " o " && Game.slotValue(gb, row, 5) == " o ") {
				return 4;
			}
			else if(Game.slotValue(gb, row, 1) == " o " && Game.slotValue(gb, row, 2) == " o " && Game.slotValue(gb, row, 3) == opponentColour(playerColour) && Game.slotValue(gb, row, 4) == opponentColour(playerColour) && Game.slotValue(gb, row, 5) == " o " || 
					Game.slotValue(gb, row, 2) == " o " && Game.slotValue(gb, row, 3) == opponentColour(playerColour) && Game.slotValue(gb, row, 4) == opponentColour(playerColour) && Game.slotValue(gb, row, 5) == " o " && Game.slotValue(gb, row, 6) == " o ") {
				return 2;
			}
		}
		
		else {
			if(Game.slotValue(gb, row, 0) == " o " && Game.lowestEmptySlot(gb, 0) == row && Game.slotValue(gb, row, 1) == opponentColour(playerColour) && Game.slotValue(gb, row, 2) == opponentColour(playerColour) && Game.slotValue(gb, row, 3) == " o " && Game.lowestEmptySlot(gb, 3) == row && Game.slotValue(gb, row, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row || 
					Game.slotValue(gb, row, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row && Game.slotValue(gb, row, 3) == " o " && Game.lowestEmptySlot(gb, 3) == row && Game.slotValue(gb, row, 4) == opponentColour(playerColour) && Game.slotValue(gb, row, 5) == opponentColour(playerColour) && Game.slotValue(gb, row, 6) == " o " && Game.lowestEmptySlot(gb, 6) == row) {
				return 3;
			}
			else if(Game.slotValue(gb, row, 0) == " o " && Game.lowestEmptySlot(gb, 0) == row && Game.slotValue(gb, row, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row && Game.slotValue(gb, row, 2) == opponentColour(playerColour) && Game.slotValue(gb, row, 3) == opponentColour(playerColour) && Game.slotValue(gb, row, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row || 
					Game.slotValue(gb, row, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row && Game.slotValue(gb, row, 2) == opponentColour(playerColour) && Game.slotValue(gb, row, 3) == opponentColour(playerColour) && Game.slotValue(gb, row, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row && Game.slotValue(gb, row, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row) {
				return 4;
			}
			else if(Game.slotValue(gb, row, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row && Game.slotValue(gb, row, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row && Game.slotValue(gb, row, 3) == opponentColour(playerColour) && Game.slotValue(gb, row, 4) == opponentColour(playerColour) && Game.slotValue(gb, row, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row || 
					Game.slotValue(gb, row, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row && Game.slotValue(gb, row, 3) == opponentColour(playerColour) && Game.slotValue(gb, row, 4) == opponentColour(playerColour) && Game.slotValue(gb, row, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row && Game.slotValue(gb, row, 6) == " o " && Game.lowestEmptySlot(gb, 6) == row) {
				return 2;
			}
		}
		return -1;
	}
	//================================================================================================================================
	public static int checkLeftDiagonalTrick(Slot[][] gb, String playerColour) {
		for(int row = 5; row > 3; row--) {
			if(Game.slotValue(gb, row, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row && Game.slotValue(gb, row - 1, 3) == " o " && Game.lowestEmptySlot(gb, 3) == row - 1 && Game.slotValue(gb, row - 2, 2) == opponentColour(playerColour) && Game.slotValue(gb, row - 3, 1) == opponentColour(playerColour) && Game.slotValue(gb, row - 4, 0) == " o " && Game.lowestEmptySlot(gb, 0) == row - 4 || 
					Game.slotValue(gb, row, 6) == " o " && Game.lowestEmptySlot(gb, 6) == row && Game.slotValue(gb, row - 1, 5) == opponentColour(playerColour) && Game.slotValue(gb, row - 2, 4) == opponentColour(playerColour) && Game.slotValue(gb, row - 3, 3) == " o " && Game.lowestEmptySlot(gb, 3) == row - 3 && Game.slotValue(gb, row - 4, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row - 4) {
				return 3;
			}
			else if(Game.slotValue(gb, row, 6) == " o " && Game.lowestEmptySlot(gb, 6) == row && Game.slotValue(gb, row - 1, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row - 1 && Game.slotValue(gb, row - 2, 4) == opponentColour(playerColour) && Game.slotValue(gb, row - 3, 3) == opponentColour(playerColour) && Game.slotValue(gb, row - 4, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row - 4 || 
					Game.slotValue(gb, row, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row && Game.slotValue(gb, row - 1, 4) == opponentColour(playerColour) && Game.slotValue(gb, row - 2, 3) == opponentColour(playerColour) && Game.slotValue(gb, row - 3, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row - 3 && Game.slotValue(gb, row - 4, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row - 4) {
				return 2;
			}
			else if(Game.slotValue(gb, row, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row && Game.slotValue(gb, row - 1, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row - 1 && Game.slotValue(gb, row - 2, 3) == opponentColour(playerColour) && Game.slotValue(gb, row - 3, 2) == opponentColour(playerColour) && Game.slotValue(gb, row - 4, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row - 4 || 
					Game.slotValue(gb, row, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row && Game.slotValue(gb, row - 1, 3) == opponentColour(playerColour) && Game.slotValue(gb, row - 2, 2) == opponentColour(playerColour) && Game.slotValue(gb, row - 3, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row - 3 && Game.slotValue(gb, row - 4, 0) == " o " && Game.lowestEmptySlot(gb, 0) == row - 4) {
				return 4;
			}
		}
		return -1;
	}
	//================================================================================================================================
	public static int checkRightDiagonalTrick(Slot[][] gb, String playerColour) {
		for(int row = 5; row > 3; row--) {
			if(Game.slotValue(gb, row, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row && Game.slotValue(gb, row - 1, 3) == " o " && Game.lowestEmptySlot(gb, 3) == row - 1 && Game.slotValue(gb, row - 2, 4) == opponentColour(playerColour) && Game.slotValue(gb, row - 3, 5) == opponentColour(playerColour) && Game.slotValue(gb, row - 4, 6) == " o " && Game.lowestEmptySlot(gb, 6) == row - 4 || 
					Game.slotValue(gb, row, 0) == " o " && Game.lowestEmptySlot(gb, 0) == row && Game.slotValue(gb, row - 1, 1) == opponentColour(playerColour) && Game.slotValue(gb, row - 2, 2) == opponentColour(playerColour) && Game.slotValue(gb, row - 3, 3) == " o " && Game.lowestEmptySlot(gb, 3) == row - 3 && Game.slotValue(gb, row - 4, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row - 4) {
				return 3;
			}
			else if(Game.slotValue(gb, row, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row && Game.slotValue(gb, row - 1, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row - 1 && Game.slotValue(gb, row - 2, 3) == opponentColour(playerColour) && Game.slotValue(gb, row - 3, 4) == opponentColour(playerColour) && Game.slotValue(gb, row - 4, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row - 4 || 
					Game.slotValue(gb, row, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row && Game.slotValue(gb, row - 1, 3) == opponentColour(playerColour) && Game.slotValue(gb, row - 2, 4) == opponentColour(playerColour) && Game.slotValue(gb, row - 3, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row - 3 && Game.slotValue(gb, row - 4, 6) == " o " && Game.lowestEmptySlot(gb, 6) == row - 4) {
				return 2;
			}
			else if(Game.slotValue(gb, row, 0) == " o " && Game.lowestEmptySlot(gb, 0) == row && Game.slotValue(gb, row - 1, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row - 1 && Game.slotValue(gb, row - 2, 2) == opponentColour(playerColour) && Game.slotValue(gb, row - 3, 3) == opponentColour(playerColour) && Game.slotValue(gb, row - 4, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row - 4 || 
					Game.slotValue(gb, row, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row && Game.slotValue(gb, row - 1, 2) == opponentColour(playerColour) && Game.slotValue(gb, row - 2, 3) == opponentColour(playerColour) && Game.slotValue(gb, row - 3, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row - 3 && Game.slotValue(gb, row - 4, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row - 4) {
				return 4;
			}
		}
		return -1;
	}
	//================================================================================================================================
		public static int checkRowTrickOff(Slot[][] gb, String playerColour, int row) {
			if(row == 5) {
				if(Game.slotValue(gb, row, 0) == " o " && Game.slotValue(gb, row, 1) == playerColour && Game.slotValue(gb, row, 2) == playerColour && Game.slotValue(gb, row, 3) == " o " && Game.slotValue(gb, row, 4) == " o " || 
						Game.slotValue(gb, row, 2) == " o " && Game.slotValue(gb, row, 3) == " o " && Game.slotValue(gb, row, 4) == playerColour && Game.slotValue(gb, row, 5) == playerColour && Game.slotValue(gb, row, 6) == " o ") {
					return 3;
				}
				else if(Game.slotValue(gb, row, 0) == " o " && Game.slotValue(gb, row, 1) == " o " && Game.slotValue(gb, row, 2) == playerColour && Game.slotValue(gb, row, 3) == playerColour && Game.slotValue(gb, row, 4) == " o " || 
						Game.slotValue(gb, row, 1) == " o " && Game.slotValue(gb, row, 2) == playerColour && Game.slotValue(gb, row, 3) == playerColour && Game.slotValue(gb, row, 4) == " o " && Game.slotValue(gb, row, 5) == " o ") {
					return 4;
				}
				else if(Game.slotValue(gb, row, 1) == " o " && Game.slotValue(gb, row, 2) == " o " && Game.slotValue(gb, row, 3) == playerColour && Game.slotValue(gb, row, 4) == playerColour && Game.slotValue(gb, row, 5) == " o " || 
						Game.slotValue(gb, row, 2) == " o " && Game.slotValue(gb, row, 3) == playerColour && Game.slotValue(gb, row, 4) == playerColour && Game.slotValue(gb, row, 5) == " o " && Game.slotValue(gb, row, 6) == " o ") {
					return 2;
				}
			}
			
			else {
				if(Game.slotValue(gb, row, 0) == " o " && Game.lowestEmptySlot(gb, 0) == row && Game.slotValue(gb, row, 1) == playerColour && Game.slotValue(gb, row, 2) == playerColour && Game.slotValue(gb, row, 3) == " o " && Game.lowestEmptySlot(gb, 3) == row && Game.slotValue(gb, row, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row || 
						Game.slotValue(gb, row, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row && Game.slotValue(gb, row, 3) == " o " && Game.lowestEmptySlot(gb, 3) == row && Game.slotValue(gb, row, 4) == playerColour && Game.slotValue(gb, row, 5) == playerColour && Game.slotValue(gb, row, 6) == " o " && Game.lowestEmptySlot(gb, 6) == row) {
					return 3;
				}
				else if(Game.slotValue(gb, row, 0) == " o " && Game.lowestEmptySlot(gb, 0) == row && Game.slotValue(gb, row, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row && Game.slotValue(gb, row, 2) == playerColour && Game.slotValue(gb, row, 3) == playerColour && Game.slotValue(gb, row, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row || 
						Game.slotValue(gb, row, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row && Game.slotValue(gb, row, 2) == playerColour && Game.slotValue(gb, row, 3) == playerColour && Game.slotValue(gb, row, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row && Game.slotValue(gb, row, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row) {
					return 4;
				}
				else if(Game.slotValue(gb, row, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row && Game.slotValue(gb, row, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row && Game.slotValue(gb, row, 3) == playerColour && Game.slotValue(gb, row, 4) == playerColour && Game.slotValue(gb, row, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row || 
						Game.slotValue(gb, row, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row && Game.slotValue(gb, row, 3) == playerColour && Game.slotValue(gb, row, 4) == playerColour && Game.slotValue(gb, row, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row && Game.slotValue(gb, row, 6) == " o " && Game.lowestEmptySlot(gb, 6) == row) {
					return 2;
				}
			}
			return -1;
		}
		//================================================================================================================================
		public static int checkLeftDiagonalTrickOff(Slot[][] gb, String playerColour) {
			for(int row = 5; row > 3; row--) {
				if(Game.slotValue(gb, row, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row && Game.slotValue(gb, row - 1, 3) == " o " && Game.lowestEmptySlot(gb, 3) == row - 1 && Game.slotValue(gb, row - 2, 2) == playerColour && Game.slotValue(gb, row - 3, 1) == playerColour && Game.slotValue(gb, row - 4, 0) == " o " && Game.lowestEmptySlot(gb, 0) == row - 4 || 
						Game.slotValue(gb, row, 6) == " o " && Game.lowestEmptySlot(gb, 6) == row && Game.slotValue(gb, row - 1, 5) == playerColour && Game.slotValue(gb, row - 2, 4) == playerColour && Game.slotValue(gb, row - 3, 3) == " o " && Game.lowestEmptySlot(gb, 3) == row - 3 && Game.slotValue(gb, row - 4, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row - 4) {
					return 3;
				}
				else if(Game.slotValue(gb, row, 6) == " o " && Game.lowestEmptySlot(gb, 6) == row && Game.slotValue(gb, row - 1, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row - 1 && Game.slotValue(gb, row - 2, 4) == playerColour && Game.slotValue(gb, row - 3, 3) == playerColour && Game.slotValue(gb, row - 4, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row - 4 || 
						Game.slotValue(gb, row, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row && Game.slotValue(gb, row - 1, 4) == playerColour && Game.slotValue(gb, row - 2, 3) == playerColour && Game.slotValue(gb, row - 3, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row - 3 && Game.slotValue(gb, row - 4, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row - 4) {
					return 2;
				}
				else if(Game.slotValue(gb, row, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row && Game.slotValue(gb, row - 1, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row - 1 && Game.slotValue(gb, row - 2, 3) == playerColour && Game.slotValue(gb, row - 3, 2) == playerColour && Game.slotValue(gb, row - 4, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row - 4 || 
						Game.slotValue(gb, row, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row && Game.slotValue(gb, row - 1, 3) == playerColour && Game.slotValue(gb, row - 2, 2) == playerColour && Game.slotValue(gb, row - 3, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row - 3 && Game.slotValue(gb, row - 4, 0) == " o " && Game.lowestEmptySlot(gb, 0) == row - 4) {
					return 4;
				}
			}
			return -1;
		}
		//================================================================================================================================
		public static int checkRightDiagonalTrickOff(Slot[][] gb, String playerColour) {
			for(int row = 5; row > 3; row--) {
				if(Game.slotValue(gb, row, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row && Game.slotValue(gb, row - 1, 3) == " o " && Game.lowestEmptySlot(gb, 3) == row - 1 && Game.slotValue(gb, row - 2, 4) == playerColour && Game.slotValue(gb, row - 3, 5) == playerColour && Game.slotValue(gb, row - 4, 6) == " o " && Game.lowestEmptySlot(gb, 6) == row - 4 || 
						Game.slotValue(gb, row, 0) == " o " && Game.lowestEmptySlot(gb, 0) == row && Game.slotValue(gb, row - 1, 1) == playerColour && Game.slotValue(gb, row - 2, 2) == playerColour && Game.slotValue(gb, row - 3, 3) == " o " && Game.lowestEmptySlot(gb, 3) == row - 3 && Game.slotValue(gb, row - 4, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row - 4) {
					return 3;
				}
				else if(Game.slotValue(gb, row, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row && Game.slotValue(gb, row - 1, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row - 1 && Game.slotValue(gb, row - 2, 3) == playerColour && Game.slotValue(gb, row - 3, 4) == playerColour && Game.slotValue(gb, row - 4, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row - 4 || 
						Game.slotValue(gb, row, 2) == " o " && Game.lowestEmptySlot(gb, 2) == row && Game.slotValue(gb, row - 1, 3) == playerColour && Game.slotValue(gb, row - 2, 4) == playerColour && Game.slotValue(gb, row - 3, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row - 3 && Game.slotValue(gb, row - 4, 6) == " o " && Game.lowestEmptySlot(gb, 6) == row - 4) {
					return 2;
				}
				else if(Game.slotValue(gb, row, 0) == " o " && Game.lowestEmptySlot(gb, 0) == row && Game.slotValue(gb, row - 1, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row - 1 && Game.slotValue(gb, row - 2, 2) == playerColour && Game.slotValue(gb, row - 3, 3) == playerColour && Game.slotValue(gb, row - 4, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row - 4 || 
						Game.slotValue(gb, row, 1) == " o " && Game.lowestEmptySlot(gb, 1) == row && Game.slotValue(gb, row - 1, 2) == playerColour && Game.slotValue(gb, row - 2, 3) == playerColour && Game.slotValue(gb, row - 3, 4) == " o " && Game.lowestEmptySlot(gb, 4) == row - 3 && Game.slotValue(gb, row - 4, 5) == " o " && Game.lowestEmptySlot(gb, 5) == row - 4) {
					return 4;
				}
			}
			return -1;
		}
}