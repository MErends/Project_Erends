package reversi.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import reversi.game.Board;
import reversi.game.Color;
@Controller
public class ReversiController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/newGame")
	public String newGame(HttpSession session) {
		Board board = new Board();
		Color color = Color.Black;
		session.setAttribute("board", board);
		session.setAttribute("turn", color);
		return "redirect:/game";
	}
	
	
	@RequestMapping(value="/game", method=RequestMethod.POST)
	public String makeMove(HttpSession session, int placeID) {
		Board board = (Board) session.getAttribute("board");
		Color turn = (Color) session.getAttribute("turn");
		int x = placeID / 8;
		int y = placeID % 8;
		board.addStone(x, y, turn);
		turn = turn == Color.Black ? Color.White : Color.Black;
		session.setAttribute("board", board);
		session.setAttribute("turn", turn);
		return "redirect:/game";
	}
	
	@RequestMapping("/game")
	public String showGame(HttpSession session, Model model) {
		Board board = (Board) session.getAttribute("board");
		Color turn = (Color) session.getAttribute("turn");
		StringBuilder table = new StringBuilder();
		//String checked = "";
		String checked = "checked";
		table.append("<table>\n");
		for (int x = 0; x != 8; ++x) {
			table.append("\t<tr>\n");
			for (int y = 0; y != 8; ++y) {
				Color color = board.getColorAt(x, y);
				if (color != Color.None) {
					table.append("\t\t<td><img src=\"images/" + color + ".png\" /></td>\n");
				} else if (board.potentialScoreFor(x, y, turn) == 0 ) {
					table.append("\t\t<td><img src=\"images/None.png\" /></td>\n");
				} else {
					table.append("\t\t<td height=\"44\" align=\"center\" style=\"background-image:url(images/None.png);background-repeat:no-repeat;\">\n");
					table.append("\t\t\t<input type=\"radio\" name=\"placeID\" value=\""+ (x * 8 + y) + "\" " + checked + "/>\n");
					table.append("\t\t</td>\n");
					checked = "";
				}
			}
			table.append("\t</tr>\n");
		}
		table.append("</table>\n");
		model.addAttribute("tableString", table.toString());
		
		if (board.hasNoMoves(turn)) {
			turn = turn == Color.Black ? Color.White : Color.Black;
			session.setAttribute("turn", turn);
			model.addAttribute("skippable", true);
		}
		
		if (board.noMoreMoves()) {
			String message;
			int blackScore = board.numStones(Color.Black);
			int whiteScore = board.numStones(Color.White);
			model.addAttribute("blackScore", blackScore);
			model.addAttribute("whiteScore", whiteScore);
			if (blackScore > whiteScore)
				message = "Black has won the game!";
			else if (blackScore < whiteScore)
				message = "White has won the game!";
			else
				message = "It is a draw!!!";
			model.addAttribute("message", message);
			return "endGame";
		}
		return "showBoard";
	}
	
}
