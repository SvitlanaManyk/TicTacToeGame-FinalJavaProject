# TicTacToeGame-FinalJavaProject
TicTacToeGame-FinalJavaProject

1. Ask the user whether he/she want to go first or second.

2. If the user wants to go first, he/she is player 1, and the computer is player 2. Otherwise, the computer is player 1 and the user is player 2.

3. If the user is player 1, prompt the user for a  move, make the move on the board,  and print the board  showing the move the user chose to make to the console. Then execute the computer's move, outputting what the computer's move was and print the board showing the move the computer made to the console as well.

*The user will input an integer between 1 - 9 corresponding to the squares of a tic tac toe board, starting from the upper left corner and moving from left to right, top to bottom

4. If the computer is player 1, and the user is player 2, reverse the steps in (3)

5. Continue to make moves until one side has won (3 X's or 3 Y's in a row, column, or diagonally), or until the game is a draw(all squares on the board are occupied). Your app must be able to know when either one side was won or if there is a draw situation. When one of these situations occur, the app will spit out the appropriate message (either "Congratulations, you've won!", or "The computer has won.", or "It's a drew")

6. You must validate the user input. This means making sure that the user has entered an integer (handling the InputMismatchException), that the integer entered is between 1 and 9, and that the square is vacant. Notify the user in the case of invalid input.


class TicTacToeBoard
 ------------------------------------------------------------------------------------------------------------------------------

public boolean move(int playerNumber, int row, int col) - Puts player's symbol ("X" for playerNumber == 1, "0" for playerNumber == 2). Records the move in the lastMovePlayer1, lastMovePlayer2 and lastMove variables.

public boolean move(int playerNumber, int squareNumber) - Overloaded method, converts squareNumber to a row and column then executes move(playerNumber,row,col

public int numberOfRows() - Returns number of rows in the board

public int numberOfCols()  - Returns number of columns in the board

public int getLastMoveForPlayer(int playerNumber) - Returns the square number of the last move made by the player passed through the argument (1 or 2).

public int getLastMove() - Returns the square number of the last move made in the game.

public boolean isVacant(int squareNumber)  - Returns true if there is a "vacant" symbol on that square, false if the square is marked with a player's symbol (X or O).

public void printOpeningMessage()  - Prints the opening message for the game program. For example "Welcome to The GAME!! Good Luck".

public void print() - Prints out gameBoard.squares array with all the current moves on it.



class GameLogicRobot
------------------------------------------------------------------------------------------------------------------------------

public static int getBestNextMove(TicTacToeBoard board) - Returns the computer's calculated best next move based on the state of the argument (which is a TicTacToeBoard) in the form of an integer representing square 1 = 9.

public static boolean isWin( TicTacToeBoard board) - Returns true if either player has won, based on the state of the argument board.

public static boolean isDraw(TicTacToeBoard board)  - Returns true if all squares on the board are occupied and there is no winner.
