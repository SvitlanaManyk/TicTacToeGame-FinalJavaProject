import java.util.Random;

public class GameLogicRobot {
	
	// written by Dan Rothman, Java instructor, Forte Knowledge -  September 2015

	private final static int CENTER_SQUARE = 5;
	private final static int[] CORNERS = {1,3,7,9};
	private final static int[] SIDES = {2,4,6,8};
   
	private final static String diag1 = "1/5/9";
	private final static String diag2 = "3/5/7";
	
	private final static int[][] OPPOSITE_CORNERS = {{-1, -1},{9, 9}, {7,9}, {7,7},{3,9}, {-1,-1}, {1,7},{3, 3},{1,3}, {1,1}};

	private final static int[][] ADJACENT_CORNERS = {{-1, -1},{3, 7}, {1, 3}, {1, 9},{1, 7},{-1, -1}, {3, 9}, {1,9},   {7, 9}, {3,  7}};

	
	// Public static methods of GameLogicRobot class
	
	// getBestNextMove
	// returns a square number for the calculated "best next move" based on the state of the TicTacToeBoard 
	/////////////////////////////////////////////////////////////////////////////////
	public static int getBestNextMove(TicTacToeBoard board)
	{
	    int moveNumber = getMoveNumber(board);
	    
		int squareToMoveTo = -1; //initialize it to -1. It will never be returned as this value.

		switch(moveNumber)
		{
		case 1:
			// randomly move to center or a corner square
			
			return  (generateRandomInt(1,4) <= 2?CENTER_SQUARE:getRandomCorner());

		case 2:
			// here is a simple strategy, occupy the center square if it's open, if it isn't occupy one of the corners
			return (board.isVacant(CENTER_SQUARE)?CENTER_SQUARE:getRandomCorner());

		case 3:
			return get3rdMove(board);

		case 4:
			if (isDiagTrap(board)) {return SIDES[0];}
	
			
		default:
			int ourPlayer = moveNumber%2==0?2:1;
			int oppPlayer = moveNumber%2==0?1:2;
			 
			int ourLastMoveRow = board.getRow(board.getLastMoveForPlayer(ourPlayer));
			int ourLastMoveCol = board.getCol(board.getLastMoveForPlayer(ourPlayer));
			int oppLastMoveRow = board.getRow(board.getLastMoveForPlayer(oppPlayer));	
			int oppLastMoveCol = board.getCol(board.getLastMoveForPlayer(oppPlayer));
			
			
			squareToMoveTo = getNearWins(moveNumber,board,ourLastMoveRow,ourLastMoveCol,false);// see if we can win 
			if (squareToMoveTo > 0){return squareToMoveTo;}
			
			squareToMoveTo = getNearWins(moveNumber+1,board,oppLastMoveRow,oppLastMoveCol,false);// see if we need to block
			if (squareToMoveTo > 0){return squareToMoveTo;}
			
			squareToMoveTo = getNearWinningMove(moveNumber,2, board); //see if we can set up for a double win
			if (squareToMoveTo > 0){return squareToMoveTo;}
			
			squareToMoveTo = getNearWinningMove(moveNumber,1, board);  // see if we can set up for a single win
			if (squareToMoveTo > 0){return squareToMoveTo;}
		}
		return getUnoccupiedSquare(board);
	}
	
	

	
	// isWin
	/////////////////////////////////
	public static boolean isWin( TicTacToeBoard board) {

		//check rows
		int lastMove = board.getLastMove();
		if (lastMove < 0)
			{
				return false;
			}
		int winRow = board.getRow(lastMove);
		int winCol = board.getCol(lastMove);
		
		String resultString = board.squares[winRow][0] + board.squares[winRow][1] + board.squares[winRow][2];
		if (isWinString(resultString)){return true;}
		resultString = board.squares[0][winCol] + board.squares[1][winCol] + board.squares[2][winCol];
		if (isWinString(resultString)){return true;}

		//check the diags
		resultString = board.squares[0][0] + board.squares[1][1] + board.squares[2][2];
	    if (diag1.contains(String.valueOf(lastMove)) && isWinString(resultString)){return true;}
	
	    resultString = board.squares[0][2] + board.squares[1][1] + board.squares[2][0];
	    if (diag2.contains(String.valueOf(lastMove)) && isWinString(resultString)){return true;}
		
		return false;
	}
	
	// isDraw
	////////////////////////
	public static boolean isDraw(TicTacToeBoard board) {
		return getUnoccupiedSquare(board) <= 0 && !isWin(board);
	}

	
	// private utility methods
	////////////////////////////////////////////////////////
	
	private static int getUnoccupiedSquare(TicTacToeBoard board) {
		for (int row = 0; row < board.squares.length;row++)
		{
			for (int col = 0; col < board.squares[row].length;col++)
			{
				int squareNumber = board.getSquareNumber(row,col);
				if (board.isVacant(squareNumber)){return squareNumber;}
			}
		}
		return -1;
	}
	
	private static int getMoveNumber(TicTacToeBoard board)
	{
		int moveCount = 1;
		for (String[] mStringArray:board.squares)
		{
			for (String mString:mStringArray)
			{
				moveCount = !mString.equals(TicTacToeBoard.VACANT_CHAR)?moveCount+1:moveCount;
			}
		}
		return moveCount;
	}
	private static int getNearWinningMove( int moveNumber, int numberOfWinsNeeded, TicTacToeBoard board) {

		//trys to find either a double or a single near win, depending on argument numberOfWinsNeeded
		String assignVal = (moveNumber%2 == 0?TicTacToeBoard.PLAYER2_CHAR:TicTacToeBoard.PLAYER1_CHAR);

		    int suggestedSquare = 0;
			for (int squareNumber = 1; squareNumber <= board.squares.length * board.squares[0].length ;squareNumber++)
			{   
				if (board.isVacant(squareNumber))  //vacant square is a possible move
				{
					int row = board.getRow(squareNumber);
					int col = board.getCol(squareNumber);
					board.squares[row][col] = assignVal;    //fill the square with x or o temporarily

					// see if we can find a double near win

					if (getNearWins(moveNumber,board,row,col,true) == numberOfWinsNeeded)
					{
						board.squares[row][col] = TicTacToeBoard.VACANT_CHAR;
						// we should prefer the corner over the side
						if (isCorner(squareNumber))
					    {
					    	return squareNumber;
					    }
						else
						{
							suggestedSquare = squareNumber;
						}
					}
					//restore value for square
					board.squares[row][col] = TicTacToeBoard.VACANT_CHAR; 
				}
			}
		
		return suggestedSquare == 0? -1:suggestedSquare;
	}

	private static boolean isCorner(int squareNumber) {
		for (int corner:CORNERS)
			if (corner == squareNumber)
				return true;
		return false;
	}




	private static int getNearWins(int moveNumber,TicTacToeBoard board, int row, int col, boolean doReturnCount)
	{
		// counts the number of near wins on the board for a given player (dependent on movenumber)
    	int numNearWins = 0;
    	
    	//check rows
    	String resultString =  board.squares[row][0] + board.squares[row][1] + board.squares[row][2];
    	if (isNearWinString(moveNumber,resultString))
		{
 			if (!doReturnCount) {return board.getSquareNumber(row, resultString.indexOf(TicTacToeBoard.VACANT_CHAR));} 
			numNearWins++ ;
		}
    	
    	//check cols
    	 resultString =  board.squares[0][col] + board.squares[1][col] + board.squares[2][col];
    	if (isNearWinString(moveNumber,resultString))
		{
 			if (!doReturnCount) {return board.getSquareNumber(resultString.indexOf(TicTacToeBoard.VACANT_CHAR),col);} 
			numNearWins++ ;
		}
    	
    	//check diag1
    	 resultString =  board.squares[0][0] + board.squares[1][1] + board.squares[2][2];
     	if (isNearWinString(moveNumber,resultString) && diag1.contains(String.valueOf(board.getSquareNumber(row, col))))
 		{
  			if (!doReturnCount) {return board.getSquareNumber(resultString.indexOf(TicTacToeBoard.VACANT_CHAR), resultString.indexOf(TicTacToeBoard.VACANT_CHAR));} 
 			numNearWins++ ;
 		}
     	
     	 resultString =  board.squares[0][2] + board.squares[1][1] + board.squares[2][0];
     	if (isNearWinString(moveNumber,resultString) && diag2.contains(String.valueOf(board.getSquareNumber(row, col))))
 		{
  			if (!doReturnCount) {return board.getSquareNumber(resultString.indexOf(TicTacToeBoard.VACANT_CHAR),
  					                                          (board.squares.length - 1)-resultString.indexOf(TicTacToeBoard.VACANT_CHAR));} 
 			numNearWins++ ;
 		}		
   	
    	return (doReturnCount?numNearWins:-1);
	}

	private static boolean isDiagTrap(TicTacToeBoard board)
	{
	// used only the forth move if board is
	/*
	 *   x _ _        _ _ x
	 *   _ O _  or    _ O _
	 *   _ _ x        x _ _
	 */
	
		return board.getSquareValue(CENTER_SQUARE).equals(TicTacToeBoard.PLAYER2_CHAR) &&

				((board.getSquareValue(1).equals(TicTacToeBoard.PLAYER1_CHAR) &&
						board.getSquareValue(9).equals(TicTacToeBoard.PLAYER1_CHAR))
						||
						(board.getSquareValue(3).equals(TicTacToeBoard.PLAYER1_CHAR) &&
								board.getSquareValue(7).equals(TicTacToeBoard.PLAYER1_CHAR)));
	}



	private static int get3rdMove(TicTacToeBoard board) {
		// 
		// these are the cases where on move 1 we opted to occupy the center square.
		if (board.getSquareValue(CENTER_SQUARE).equals(TicTacToeBoard.PLAYER1_CHAR))
		{
			return getCenterAndCornerOrSideMove(board);
		}

		// In these cases our first move was a corner
		int ourFirstMove = getFirstOccupiedCorner(TicTacToeBoard.PLAYER1_CHAR,board);

		//opponent moved to  center??
		if (!board.isVacant(CENTER_SQUARE))
		{
			return OPPOSITE_CORNERS[ourFirstMove][0];
		}

		//opponent moved to corner??
		int OpponentsMove = getFirstOccupiedCorner(TicTacToeBoard.PLAYER2_CHAR,board);

		if (OpponentsMove > 0)
		{
			// move to an adjacent corner
			return getUnoccupiedAdjCorner(ourFirstMove,board);
		}

		//opponent moved to side
		OpponentsMove = getFirstOccupiedSide(TicTacToeBoard.PLAYER2_CHAR,board);
		if (OpponentsMove > 0)
			//go to the corner square that is adjacent to your move and opposite your opponent's move.
			for (int adjCorner:ADJACENT_CORNERS[ourFirstMove])
				for (int oppCorner:OPPOSITE_CORNERS[OpponentsMove])	
					if (oppCorner == adjCorner)
						return oppCorner;

		return -1;
	}

	private static int getCenterAndCornerOrSideMove(TicTacToeBoard board) {
		int[] lookupArray1;
		int[][] lookupArray2;
         
		for (int i = 0; i<2;i++)
		{
			lookupArray1 = i==0?CORNERS:SIDES;
			lookupArray2 = i==0?OPPOSITE_CORNERS:ADJACENT_CORNERS;

			for (int square:lookupArray1)
			{
				if (!board.isVacant(square))
				{
					return lookupArray2[square][0];
				}
			}
		}
		return -1;
	}

	private static int getUnoccupiedAdjCorner(int squareNumber, TicTacToeBoard board) {
		return searchLookupArray(TicTacToeBoard.VACANT_CHAR, board,ADJACENT_CORNERS[squareNumber]);
	}

	private static int getFirstOccupiedCorner(String playerMark, TicTacToeBoard board) {
		int retval = searchLookupArray(playerMark,board,CORNERS);
		return retval;
	}

	private static int getFirstOccupiedSide(String playerMark, TicTacToeBoard board) {

		return searchLookupArray(playerMark,board,SIDES);
	}

	private static int searchLookupArray(String playerMark,TicTacToeBoard board,int[] searchArray)
	{
		for (int square:searchArray)
		{
			if (board.getSquareValue(square).equals(playerMark))  //  there's a corner occupied
			{
				return square;
			}
		}
		return -1;
	}

	//************************************************************************************************
	
	
	private static boolean isWinString(String resultString)
	{
		
		return (resultString.equals(TicTacToeBoard.PLAYER2_CHAR+TicTacToeBoard.PLAYER2_CHAR+TicTacToeBoard.PLAYER2_CHAR) ||
				                    resultString.equals(TicTacToeBoard.PLAYER1_CHAR+TicTacToeBoard.PLAYER1_CHAR+TicTacToeBoard.PLAYER1_CHAR)); 
			
	}
	
	private static boolean isNearWinString(int moveNumber,String resultString) 
	{
		String charToFind = (moveNumber%2==0?TicTacToeBoard.PLAYER2_CHAR:TicTacToeBoard.PLAYER1_CHAR);
		
		return resultString.contains(TicTacToeBoard.VACANT_CHAR) &&
				(resultString.contains(charToFind+charToFind) ||
				resultString.contains(charToFind+TicTacToeBoard.VACANT_CHAR+charToFind));
	}
	


	//***********************************************************************************************
	private static int getRandomCorner()
	{
		return CORNERS[generateRandomInt(0,3)];
	}

	private static int generateRandomInt(int minValue,int maxValue)
	{
		Random rn = new Random();
		return rn.nextInt(maxValue - minValue + 1) + minValue;
	}


	
}