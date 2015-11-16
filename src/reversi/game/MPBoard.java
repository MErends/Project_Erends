package reversi.game;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class MPBoard {
	
	private long id;
	private List<String> players = new ArrayList<String>();
	private List<Stone> stones = new ArrayList<Stone>();
	private Color turn;
	
	public MPBoard() {
		for (int x = 0; x != 64; ++x)
			stones.add(new Stone(Color.None));
		stones.get(4 * 8 + 3).setColor(Color.Black);
		stones.get(3 * 8 + 4).setColor(Color.Black);
		stones.get(3 * 8 + 3).setColor(Color.White);
		stones.get(4 * 8 + 4).setColor(Color.White);
		turn = Color.Black;
	}

	public Color addPlayer(String sessionID) {
		players.add(sessionID);
		return players.size() == 1 ? Color.Black : Color.White;
	}
	
	public void addStone(int x, int y, Color color) {
		stones.get(8 * x + y).setColor(color);
		flipAll(x, y, color);
	}
	
	public Color getColorAt(int x, int y) {
		return stones.get(8 * x + y).getColor();
	}
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public long getId() {
		return id;
	}
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<String> getPlayers() {
		return players;
	}
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Stone> getStones() {
		return stones;
	}
	
	public Color getTurn() {
		return turn;
	}

	public boolean hasNoMoves(Color color) {
		for (int x = 0; x != 8; ++x) {
			for (int y = 0; y != 8; ++y) {
				if (validMove(x, y, color))
					return false;
			}
		}
		return true;
	}
	
	public boolean noMoreMoves() {
		return (hasNoMoves(Color.Black) && hasNoMoves(Color.White));
	}

	public int score(Color color) {
		int num = 0;
		for(Stone stone : stones)
			num += stone.getColor() == color ? 1 : 0;
		
		return num;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	
	public void setStones(List<Stone> stones) {
		this.stones = stones;
	}

	public void setTurn(Color turn) {
		this.turn = turn;
	}

	public void switchTurn() {
		turn = turn == Color.Black ? Color.White : Color.Black; 
	}
	
	@Override
	public String toString() {
		String output = "\nBoard:\n+-------+-------+-------+-------+-------+-------+-------+-------+\n";
		for(int x = 0; x != 8; ++x) {
			output = output.concat("|       |       |       |       |       |       |       |       |\n");
			for(int y = 0; y != 8; ++y) {
				//output = output.concat("| " + stones.get(8 * x + y).getColor() + "\t");
				output = output.concat("|" + (8 * x + y) + "\t");
			}
			output = output.concat("|\n|       |       |       |       |       |       |       |       |\n");
			output = output.concat("+-------+-------+-------+-------+-------+-------+-------+-------+\n");
		}
		return output;
	}

	public boolean validMove(int x, int y, Color color) {
		if(stones.get(8 * x + y).getColor() != Color.None)
			return false;
		return validToN(x, y, color) ||
				validToNE(x, y, color) ||
				validToE(x, y, color) ||
				validToSE(x, y, color) ||
				validToS(x, y, color) ||
				validToSW(x, y, color) ||
				validToW(x, y, color) ||
				validToNW(x, y, color);
		}

	private void flipAll(int x, int y, Color color) {
		flipN(x, y, color);
		flipNE(x, y, color);
		flipE(x, y, color);
		flipSE(x, y, color);
		flipS(x, y, color);
		flipSW(x, y, color);
		flipW(x, y, color);
		flipNW(x, y, color);
	}


	private void flipE(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++y < 8) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return;
			} else if(stones.get(8 * x + y).getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return;
	}
	
	private void flipN(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--x > -1) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return;
			} else if(stones.get(8 * x + y).getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return;
	}
	private void flipNE(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--x > -1 && ++y < 8) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return;
			} else if(stones.get(8 * x + y).getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return;		
	}
	private void flipNW(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--x > -1 && --y > -1) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return;
			} else if(stones.get(8 * x + y).getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return;
	}

	private void flipS(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++x < 8) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return;
			} else if(stones.get(8 * x + y).getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return;
	}

	private void flipSE(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++x < 8 && ++y < 8) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return;
			} else if(stones.get(8 * x + y).getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return;
	}

	private void flipSW(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++x < 8 && --y > -1) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return;
			} else if(stones.get(8 * x + y).getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return;
	}

	private void flipW(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--y > -1) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return;
			} else if(stones.get(8 * x + y).getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return;
	}

	
	private boolean validToE(int x, int y, Color turn) {
		boolean streak = false;
		while(++y < 8) {
			Color position = stones.get(8 * x + y).getColor();
			if (position == Color.None)
				return false;
			else if(position == turn) {
				return streak;
			} else {
				streak = true;
			}	
		}
		return false;
	}
	
	private boolean validToN(int x, int y, Color turn) {
		boolean streak = false;
		while(--x > 0) {
			Color position = stones.get(8 * x + y).getColor();
			if (position == Color.None)
				return false;
			else if(position == turn) {
				return streak;
			} else {
				streak = true;
			}	
		}
		return false;
	}
	
	private boolean validToNE(int x, int y, Color turn) {
		boolean streak = false;
		while(--x > 0 && ++y < 8) {
			Color position = stones.get(8 * x + y).getColor();
			if (position == Color.None)
				return false;
			else if(position == turn) {
				return streak;
			} else {
				streak = true;
			}	
		}
		return false;
	}
	
	private boolean validToNW(int x, int y, Color turn) {
		boolean streak = false;
		while(--y > 0 && --x > 0) {
			Color position = stones.get(8 * x + y).getColor();
			if (position == Color.None)
				return false;
			else if(position == turn) {
				return streak;
			} else {
				streak = true;
			}	
		}
		return false;
	}
	
	private boolean validToS(int x, int y, Color turn) {
		boolean streak = false;
		while(++y < 8) {
			Color position = stones.get(8 * x + y).getColor();
			if (position == Color.None)
				return false;
			else if(position == turn) {
				return streak;
			} else {
				streak = true;
			}	
		}
		return false;
	}
	
	private boolean validToSE(int x, int y, Color turn) {
		boolean streak = false;
		while(++x < 8 && ++y < 8) {
			Color position = stones.get(8 * x + y).getColor();
			if (position == Color.None)
				return false;
			else if(position == turn) {
				return streak;
			} else {
				streak = true;
			}	
		}
		return false;
	}
	
	private boolean validToSW(int x, int y, Color turn) {
		boolean streak = false;
		while(--x > 0 && ++y < 8) {
			Color position = stones.get(8 * x + y).getColor();
			if (position == Color.None)
				return false;
			else if(position == turn) {
				return streak;
			} else {
				streak = true;
			}	
		}
		return false;
	}
	
	private boolean validToW(int x, int y, Color turn) {
		boolean streak = false;
		while(--y > 0) {
			Color position = stones.get(8 * x + y).getColor();
			if (position == Color.None)
				return false;
			else if(position == turn) {
				return streak;
			} else {
				streak = true;
			}	
		}
		return false;
	}
}
