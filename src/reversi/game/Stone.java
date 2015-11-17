package reversi.game;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Stone {
	private long id;
	private Color color;
	
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public Stone() {
		
	}
	
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
