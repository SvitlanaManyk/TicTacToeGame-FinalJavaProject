public class TicTacToeBoard extends AbstractBoard {
	//
	// written by Dan Rothman, Java instructor, Forte Knowledge -  September 2015

	// override values in the abstract class
	//
	public final static String VACANT_CHAR = "_";
	public final static String PLAYER1_CHAR = "X";
	public final static String PLAYER2_CHAR = "O";
	public final static String NULL_CHAR = "NULL";
	
	public String[][] squares = {{VACANT_CHAR, VACANT_CHAR, VACANT_CHAR},
			                    {VACANT_CHAR, VACANT_CHAR, VACANT_CHAR},
			                    {VACANT_CHAR, VACANT_CHAR, VACANT_CHAR}};
	
	private final int ROW_COUNT = 3;
	private final int COL_COUNT = 3;

	private int lastMovePlayer1 = -1;
	private int lastMovePlayer2 = -1;
	private int lastMove = -1;

	public TicTacToeBoard()
	{
		initialize();
	}
	
	
     // move
	//puts player's symbol ("X" for playerNumber == 1, "0" for playerNumber == 2)
	// records the move int eh lastMovePlayer1, lastMovePlayer2 and lastMove variables.
	/////////////////////////////////////////////////////////////////////////////////////
	public boolean move(int playerNumber, int row, int col) {
		try {
			if (squares[row][col].equals(VACANT_CHAR)) {
				int squareNumber = getSquareNumber(row, col);
				squares[row][col] = (playerNumber == 1 ? TicTacToeBoard.PLAYER1_CHAR
						: TicTacToeBoard.PLAYER2_CHAR);
				lastMovePlayer1 = playerNumber == 1 ? squareNumber
						: lastMovePlayer1;
				lastMovePlayer2 = playerNumber == 2 ? squareNumber
						: lastMovePlayer2;
				lastMove = squareNumber;
				return true;
			}
		} 
		catch (ArrayIndexOutOfBoundsException ex) 
		{
           return false;
		}
		return false;
	}
	
	// move
    // overloaded method, converts squareNumber to a row and column then executes move(playerNumber,row,col)
	////////////////////////////////////////////////////////////////////////////////////////////
	public boolean move(int playerNumber, int squareNumber) {
		return move(playerNumber, getRow(squareNumber), getCol(squareNumber));
	}

	public int numberOfRows() {
		// TODO Auto-generated method stub
		return this.squares.length;
	}

	public int numberOfCols() {
		return this.squares[0].length;
	}
	
	// overridden methods from the abstract parent class
    //////////////////////////////////////////////////////////
	
	@Override
	public void initialize() {
		for (int i = 0; i < squares.length * squares[0].length;i++)
		{
			squares[i/squares.length][i%squares[0].length] = VACANT_CHAR;
		}

	}
    
	@Override
	public int getLastMoveForPlayer(int playerNumber) {
		return playerNumber == 1 ? lastMovePlayer1 : lastMovePlayer2;
	}

	@Override
	public int getLastMove() {
		return lastMove;
	}
	
	@Override
	public boolean isVacant(int squareNumber) {
		return getSquareValue(squareNumber).equals(VACANT_CHAR);
	}
	
	@Override
	public void printOpeningMessage() {
		// System out println the opening message for the game program. For
		// example "Welcome to The GAME!! Good Luck".
		System.out.println("Welcome To Tic Tac Toe!!!");
		System.out.println();
	}

	@Override
	public void print() {
		/*
		 * print out gameBoard.squares array with all the current moves on it.
		 * And example would be:
		 * 
		 * X O _ _ _ _ _ _ X
		 */

		for (int row = 0; row < this.squares.length; row++) {
			String printLine = "";
			for (int col = 0; col < this.squares[row].length; col++) {
				printLine += this.squares[row][col].toString() + " ";
			}
			System.out.println(printLine);
		}

		System.out.println();

	}
	
	//////////////////////////////////////
	
	// protected utility classes
	//////////////////////////////////////
	
	protected String getSquareValue(int squareNumber) {
		try
		{
		   return squares[getRow(squareNumber)][getCol(squareNumber)];
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			return NULL_CHAR;
		}
		
	}
	
	protected int getSquareNumber(int row, int col) {
		return (row * squares.length) + (col + 1);
	}

	protected int getRow(int squareNumber) {
		return (squareNumber - 1) / squares.length;
	}

	protected int getCol(int squareNumber) {
		return (squareNumber - 1) % squares[0].length;
	}

}