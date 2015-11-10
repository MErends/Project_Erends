package reversi.game;

public class ReversiTester {

	public static void main(String[] args) {
		Board speelbord = new Board();
		speelbord.setStone(4, 2, Color.White);
		speelbord.setStone(4, 5, Color.White);
		speelbord.printBoard();
		
	}

}
