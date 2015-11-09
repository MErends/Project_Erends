package reversi.game;

public class Board {
	Stone[][] board;	
	
	public Board() {
		board = new Stone[8][8];
		board[5-1][4-1] = new Stone(Color.Black);
		board[4-1][5-1] = new Stone(Color.Black);
		board[4-1][4-1] = new Stone(Color.White);
		board[5-1][5-1] = new Stone(Color.White);
	}
	
	
}
