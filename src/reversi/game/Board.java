package reversi.game;

import java.util.ArrayList;

public class Board {
	Stone[][] board;	
	
	public Board() {
		board = new Stone[8][8];
		
		for(int x = 0; x != 8; ++x)
			for(int y = 0; y != 8; ++y)
				board[x][y] = new Stone(Color.None);
		
		board[4][3].setColor(Color.Black);
		board[3][4].setColor(Color.Black);
		board[3][3].setColor(Color.White);
		board[4][4].setColor(Color.White);
	}
	
	public int potentialScoreFor(int x, int y, Color color) {
		if(board[x][y].getColor() != Color.None)
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
	private ArrayList<Stone> potentialNW(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--x > 0 && --y > 0) {
			if(board[x][y].getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(board[x][y].getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialW(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--y > 0) {
			if(board[x][y].getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(board[x][y].getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialSW(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++x < 8 && --y > 0) {
			if(board[x][y].getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(board[x][y].getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialS(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++x < 8) {
			if(board[x][y].getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(board[x][y].getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialSE(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++x < 8 && ++y < 8) {
			if(board[x][y].getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(board[x][y].getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialE(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++y < 8) {
			if(board[x][y].getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(board[x][y].getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialNE(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--x > 0 && ++y < 8) {
			if(board[x][y].getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(board[x][y].getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return new ArrayList<Stone>();
	}

	private ArrayList<Stone> potentialN(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--x > 0) {
			if(board[x][y].getColor() == Color.None) {
				return new ArrayList<Stone>();
			} else if(board[x][y].getColor() == color) {
				return toFlip;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return new ArrayList<Stone>();
	}
	
	public void printBoard() {
		System.out.println("\nBoard:\n+-------+-------+-------+-------+-------+-------+-------+-------+");
		for(int x = 0; x != 8; ++x) {
			System.out.println("|       |       |       |       |       |       |       |       |");
			for(int y = 0; y != 8; ++y) {
				System.out.print("| " + board[x][y].getColor() + "\t");
			}
			System.out.println("|\n|       |       |       |       |       |       |       |       |");
			System.out.println("+-------+-------+-------+-------+-------+-------+-------+-------+");
		}
	}

	public Color getColorAt(int x, int y) {
		return board[x][y].getColor();
	}
	
	public void addStone(int x, int y, Color color) {
		board[x][y].setColor(color);
		flipAll(x, y, color);
	}
	public void setStone(int x, int y, Color color) {
		board[x][y].setColor(color);
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

	private void flipNW(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--x > 0 && --y > 0) {
			if(board[x][y].getColor() == Color.None) {
				return;
			} else if(board[x][y].getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return;
	}

	private void flipW(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--y > 0) {
			if(board[x][y].getColor() == Color.None) {
				return;
			} else if(board[x][y].getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return;
	}

	private void flipSW(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++x < 8 && --y > 0) {
			if(board[x][y].getColor() == Color.None) {
				return;
			} else if(board[x][y].getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return;
	}

	private void flipS(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++x < 8) {
			if(board[x][y].getColor() == Color.None) {
				return;
			} else if(board[x][y].getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return;
	}

	private void flipSE(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++x < 8 && ++y < 8) {
			if(board[x][y].getColor() == Color.None) {
				return;
			} else if(board[x][y].getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return;
	}

	private void flipE(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(++y < 8) {
			if(board[x][y].getColor() == Color.None) {
				return;
			} else if(board[x][y].getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return;
	}

	private void flipNE(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--x > 0 && ++y < 8) {
			if(board[x][y].getColor() == Color.None) {
				return;
			} else if(board[x][y].getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return;		
	}

	private void flipN(int x, int y, Color color) {
		ArrayList<Stone> toFlip = new ArrayList<Stone>();
		while(--x > 0) {
			if(board[x][y].getColor() == Color.None) {
				return;
			} else if(board[x][y].getColor() == color) {
				for(Stone stone : toFlip) {
					stone.doFlip();
				}
				return;
			} else {
				toFlip.add(board[x][y]);
			}	
		}
		return;
	}
	
}
