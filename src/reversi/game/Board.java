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
	
	public void addStone(int x, int y, Color color) {
		board[x][y].setColor(color);
		flipAll(x, y, color);
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
