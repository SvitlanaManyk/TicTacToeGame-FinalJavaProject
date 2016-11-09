
public abstract class AbstractBoard {

	// written by Dan Rothman, Java instructor, Forte Knowledge -  September 2015
	
	public String[][] squares = {{}}; 
	
	//
	// abstract methods
	//
	public abstract void initialize();
	public abstract boolean move(int playerNumber, int row, int col); // returns false if not a valid move
	public abstract boolean move(int playerNumber, int squareNumber); // move that converts a square number to row and col
	
	public abstract int  getLastMove();  
	public abstract int  getLastMoveForPlayer(int playerNumber); // get the last move made
	
	public abstract boolean isVacant(int squareNumber);
	
	public abstract void printOpeningMessage();
	public abstract void print();


}