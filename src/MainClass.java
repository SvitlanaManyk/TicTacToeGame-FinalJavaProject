// Svitlana Manyk --- final project 
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainClass {
	
	public static void main(String[] args) {
		
	    GameLogicRobot mGame = new GameLogicRobot(); 
		TicTacToeBoard mBoard  = new TicTacToeBoard();
		mBoard.printOpeningMessage();
		System.out.println("Do you want to go first? y/n");
		Scanner mScan = new Scanner(System.in); 
		String userResp = mScan.next();
		boolean humanPlayerFirst = userResp.equalsIgnoreCase("Y");
		int playerA, playerB;
		boolean lastMovedPlayer1 = false;
		boolean lastMovedPlayer2 = true;
		playerA = humanPlayerFirst == true? 1:2;
		playerB = humanPlayerFirst == false? 1:2;
		
		switch (playerA){
		case 1:
			while (!mGame.isDraw(mBoard)&&!mGame.isWin(mBoard)){
				if (lastMovedPlayer2 && !lastMovedPlayer1) {
					lastMovedPlayer1 = mBoard.move(playerA, userInSqrNumber(mBoard));
					lastMovedPlayer2 = false;
					mBoard.print();
				 }
				else {
				    lastMovedPlayer2 = mBoard.move(playerB, mGame.getBestNextMove(mBoard));
				    lastMovedPlayer1 = false;
			     	mBoard.print();
				 }
			}
			if (mGame.isDraw(mBoard))
						System.out.println("It's a drew");
			if (mGame.isWin(mBoard)){
				String winMessageA = new String("Congratulations, you've won!");
				String winMessageB = new String("The computer has won");
				String winMessage = new String();
				winMessage = lastMovedPlayer1 == true && lastMovedPlayer2 == false ? 
						winMessageA : winMessageB;
				System.out.println(winMessage);
			} 
		case 2:
			while (!mGame.isDraw(mBoard)&&!mGame.isWin(mBoard)){
				if (lastMovedPlayer2 && !lastMovedPlayer1) {
					lastMovedPlayer1 = mBoard.move(playerB, mGame.getBestNextMove(mBoard));
					lastMovedPlayer2 = false;
					mBoard.print();
				 }
				else {
				    lastMovedPlayer2 = mBoard.move(playerA, userInSqrNumber(mBoard));
				    lastMovedPlayer1 = false;
			     	mBoard.print();
				 }
			}
			if (mGame.isDraw(mBoard))
				System.out.println("It's a drew");
			if (mGame.isWin(mBoard)){
				String winMessageB = new String("Congratulations, you've won!");
				String winMessageA = new String("The computer has won");
				String winMessage = new String();
				winMessage = lastMovedPlayer1 == true && lastMovedPlayer2 == false ? 
						winMessageA : winMessageB;
				System.out.println(winMessage);
			}
		}
		mScan.close();
}
		
	 public static int userInSqrNumber (TicTacToeBoard board){ 
		while (true){
			try{
				System.out.println("Enter a square number (integer between 1-9)");
				Scanner mScan = new Scanner(System.in);
				int userInt = mScan.nextInt();
				if (userInt >= 1 && userInt <=9 && board.isVacant(userInt)==true) {
					int userSquareNumber = userInt;
					return userSquareNumber;}
				else {System.out.println("Your input is invalid!");
				System.out.println("Input Again");}
			}
			catch (InputMismatchException ex){
                System.out.println("Your input is invalid!");
				System.out.println("Input Again");		}
			}	 
}
}
