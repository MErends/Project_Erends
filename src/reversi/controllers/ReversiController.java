package reversi.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	
	
	@RequestMapping("game")
	public String makeFirstMove(HttpSession session, Model model) {
		Board board = (Board) session.getAttribute("board");
		Color turn = (Color) session.getAttribute("turn");
		StringBuilder table = new StringBuilder();
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
					table.append("\t\t<td align=\"center\" style=\"background-image:url(images/None.png);background-repeat:no-repeat;\">\n");
					table.append("\t\t\t<input type=\"radio\" name=\"placeID\" value=\""+ (x * 8 + y) + "\" />\n");
					table.append("\t\t</td>\n");
				}
			}
			table.append("\t</tr>\n");
		}
		table.append("</table>\n");
		model.addAttribute("tableString", table.toString());
		return "showBoard";
	}
	
}
