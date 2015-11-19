package reversi.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import reversi.game.*;


@Controller
@RequestMapping("/multiplayer")
public class MultiController {

	private static Player waitingPlayer;
	
	public static Player getWaitingPlayer() {
		return waitingPlayer;
	}
	
	@RequestMapping("/")
	public String loggedIn(HttpSession session) {
		String name = (String) session.getAttribute("name");
		if (name == null || name == "")
			return "/multiplayer/login";
		return "redirect:/multiplayer/lobby";
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	public String getLoggedIn(HttpSession session, String name) {
		if (name == null || name.equals("") || name.equals("Enter your name"))
			return "/login";
		
		System.out.println("Logging in " + name + ". Entering into DB.");
		Player player = PlayerDAO.create(name);
		session.setAttribute("player", player);
		session.setAttribute("name", name);
		return "redirect:/multiplayer/lobby";
	}

	@RequestMapping("/lobby")
	public String lobby(HttpSession session) {
		String name = (String) session.getAttribute("name");
		if (name == null || name == "")
			return "/multiplayer/login";
	
		return "/multiplayer/lobby";
	} 	
	

	@RequestMapping("/findOpponent")
	public synchronized String findOpponent(HttpSession session) {
		Player player = (Player) session.getAttribute("player");
		
		if (waitingPlayer == null) {
			System.out.println("adding player to waiting: " + (String) session.getAttribute("name"));
			waitingPlayer = player;
			return "redirect:/multiplayer/waitingRoom";
		}
		System.out.println("There was a waiting player. Launching game");
		MPBoardDAO.create(player, waitingPlayer);
		PlayerDAO.update(player);
		PlayerDAO.update(waitingPlayer);
		
		waitingPlayer = null;
		return "redirect:/multiplayer/game";
	}
	
	@RequestMapping("/waitingRoom")
	public String waiting(HttpSession session, Model model) {
		Player player = (Player) session.getAttribute("player");
		model.addAttribute("playerID", player.getId());
		return "/multiplayer/waitingRoom";
	}
	
	@RequestMapping("/game")
	public String showGame(HttpSession session, Model model) {
		Player player = (Player) session.getAttribute("player");
		MPBoard board = MPBoardDAO.getByPlayerID(player.getId());
		Color turn = board.getTurn();
		model.addAttribute("playerColor", player.getColor());
		model.addAttribute("turn", turn);
		model.addAttribute("boardID", board.getId());
		StringBuilder table = new StringBuilder();
		table.append("<table>\n");
		for (int x = 0; x != 8; ++x) {
			table.append("\t<tr>\n");
			for (int y = 0; y != 8; ++y) {
				Color color = board.getColorAt(x, y);
				if (color != Color.None) {
					table.append("\t\t<td><img src=\"/Reversi/images/" + color + ".png\" ></td>\n");
				} else {
					if (player.getColor() == turn && board.validMove(x, y, turn) ) {
						table.append("\t\t<td id=\""+ (x * 8 + y) + "\" class=\"clickable\"><img id=\""+ (x * 8 + y) + "\" src=\"/Reversi/images/None.png\" class=\"clickable\"></td>\n");
					} else {
						table.append("\t\t<td><img src=\"/Reversi/images/None.png\" ></td>\n");
					}
				}
			}
			table.append("\t</tr>\n");
		}
		table.append("</table>\n");
		model.addAttribute("tableString", table.toString());

		if (board.hasNoMoves(turn)) {
			model.addAttribute("skippable", true);
		}

		if (board.noMoreMoves()) {
			String message;
			int blackScore = board.score(Color.Black);
			int whiteScore = board.score(Color.White);
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
		return "/multiplayer/game";
	}
	
	@RequestMapping(value="/game", method=RequestMethod.POST)
	public String makeMove(HttpSession session, Integer placeID, String skipTurn) {
		Player player = (Player) session.getAttribute("player");
		MPBoard board = MPBoardDAO.getByPlayerID(player.getId());
		Color turn = board.getTurn();
		
		if (skipTurn != null) {
			board.switchTurn();
			MPBoardDAO.update(board);
			return "redirect:/game";
		}
		if (placeID == null)
			return "redirect:/game";

		int x = placeID / 8;
		int y = placeID % 8;
		board.addStone(x, y, turn);
		board.switchTurn();
		MPBoardDAO.update(board);
		return "redirect:/multiplayer/game";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		Player player = (Player) session.getAttribute("player");
		if (player != null)
			PlayerDAO.remove(player.getId());
		session.invalidate();
		return "redirect:/multiplayer/";
	}
}
