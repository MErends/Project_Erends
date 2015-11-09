package reversi.game;

public class Stone {
	Color color;
	
	public Stone(Color color) {
			this.color = color;
	}

	
	public void doFlip() {
		color = color == Color.Black ? Color.White : Color.Black;
	}


	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}
	
	
	
}
