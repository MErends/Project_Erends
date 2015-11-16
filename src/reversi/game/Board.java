package reversi.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Board {
	
	private long id;
	private List<Stone> stones = new ArrayList<Stone>();
	private Color turn;
	
	public Board() {
		for (int x = 0; x != 64; ++x)
			stones.add(new Stone(Color.None));

		stones.get(4 * 8 + 3).setColor(Color.Black);
		stones.get(3 * 8 + 4).setColor(Color.Black);
		stones.get(3 * 8 + 3).setColor(Color.White);
		stones.get(4 * 8 + 4).setColor(Color.White);
		turn = Color.Black;
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
	public List<Stone> getStones() {
		return stones;
	}
	
	public void switchTurn() {
		turn = turn == Color.Black ? Color.White : Color.Black; 
	}

	public Color getTurn() {
		return turn;
	}

	public int bestMove(Color turn) {
		int[] corners = {0, 7, 56, 63};
		int[] topleft = {1, 8, 9};
		int[] topright = {6, 14, 15};
		int[] bottomleft = {48, 49, 57};
		int[] bottomright = {54, 55, 62};
		int bestScore = 0;
		List<Integer> moves = new ArrayList<Integer>();
		List<Integer> cornerMoves = new ArrayList<Integer>();
		List<Integer> subcornerMoves = new ArrayList<Integer>();
		
		for (int x = 0; x != 8; ++x) {
			for (int y = 0; y != 8; ++y) {
				int score = potentialScoreFor(x, y, turn);
				if (score == 0) continue;
				int position = 8 * x + y;
				if (hasArrayGot(corners, position)) {
					cornerMoves.add(position);
					continue;
				}
				if (hasArrayGot(topleft, position) && stones.get(0).getColor() != turn) {
					subcornerMoves.add(position);
					continue;
				}
				
				if (hasArrayGot(topright, position) && stones.get(7).getColor() != turn) {
					subcornerMoves.add(position);
					continue;
				}
				
				if (hasArrayGot(bottomleft, position) && stones.get(56).getColor() != turn) {
					subcornerMoves.add(position);
					continue;
				}
				
				if (hasArrayGot(bottomright, position) && stones.get(63).getColor() != turn) {
					subcornerMoves.add(position);
					continue;
				}
				if (score >= bestScore) {
					if (score > bestScore)
						moves = new ArrayList<Integer>();
					moves.add(position);
					bestScore = score;
				}
			}
		}
		Random random = new Random();
		if (cornerMoves.size() != 0) {
			int index = random.nextInt(cornerMoves.size());
			int move = cornerMoves.get(index);
			return move;
		}
		if (moves.size() != 0) {
			int index = random.nextInt(moves.size());
			int move = moves.get(index);
			return move;
		}
		if (subcornerMoves.size() != 0) {
			int index = random.nextInt(subcornerMoves.size());
			int move = subcornerMoves.get(index);
			return move;
		}
		return -1;
	}

	private boolean hasArrayGot(int[] arr, int value) {
		int len = arr.length;
		for (int x = 0; x != len; ++x) {
			if (arr[x] == value)
				return true;
		}
		return false;
	}
	
	public boolean hasNoMoves(Color color) {
		for (int x = 0; x != 8; ++x) {
			for (int y = 0; y != 8; ++y) {
				if (potentialScoreFor(x, y, color) != 0)
					return false;
			}
		}
		return true;
	}

	public boolean noMoreMoves() {
		return (hasNoMoves(Color.Black) && hasNoMoves(Color.White));
	}

	public int numStones(Color color) {
		int num = 0;
		for(Stone stone : stones)
			num += stone.getColor() == color ? 1 : 0;
		
		return num;
	}

	public int potentialScoreFor(int x, int y, Color color) {
		if(stones.get(8 * x + y).getColor() != Color.None)
			return 0;
		
		ArrayList<Stone> flippable = new ArrayList<Stone>();
		flippable.addAll(potentialNW(x, y, color));
		flippable.addAll(potentialW(x, y, color));
		flippable.addAll(potentialSW(x, y, color));
		flippable.addAll(potentialS(x, y, color));
		flippable.addAll(potentialSE(x, y, color));
		flippable.addAll(potentialE(x, y, color));
		flippable.addAll(potentialNE(x, y, color));
		flippable.addAll(potentialN(x, y, color));
		return flippable.size();
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setStone(int x, int y, Color color) {
		stones.get(8 * x + y).setColor(color);
	}

	
	public void setStones(List<Stone> stones) {
		this.stones = stones;
	}

	public void setTurn(Color turn) {
		this.turn = turn;
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

	private ArrayList<Stone> potentialE(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++y < 8) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(stones.get(8 * x + y).getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialN(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--x > -1) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(stones.get(8 * x + y).getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialNE(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--x > -1 && ++y < 8) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(stones.get(8 * x + y).getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialNW(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--x > -1 && --y > -1) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(stones.get(8 * x + y).getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialS(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++x < 8) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(stones.get(8 * x + y).getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialSE(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++x < 8 && ++y < 8) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(stones.get(8 * x + y).getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialSW(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++x < 8 && --y > -1) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(stones.get(8 * x + y).getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialW(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--y > 0) {
			if(stones.get(8 * x + y).getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(stones.get(8 * x + y).getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(stones.get(8 * x + y));
			}	
		}
		return new ArrayList<Stone>();
	}	
}
