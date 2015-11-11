package reversi.game;

public class ReversiTester {

	public static void main(String[] args) {
		Board speelbord = new Board();
		speelbord.setStone(0, 0, Color.White);
		speelbord.setStone(7, 7, Color.White);
		System.out.println(speelbord);
	}

}
