package reversi.game;

public class ReversiTester {

	public static void main(String[] args) {
		Board speelbord = new Board();
		speelbord.setStone(0, 7, Color.Black);
		speelbord.setStone(1, 7, Color.White);
		speelbord.setStone(2, 7, Color.White);
		speelbord.setStone(3, 7, Color.White);
		speelbord.setStone(4, 7, Color.White);
		System.out.println(speelbord.potentialScoreFor(5, 7, Color.Black));
		System.out.println(speelbord);
	}

}
