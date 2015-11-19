package reversi.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import reversi.game.*;


@Controller
@RequestMapping("/multiplayer")
public class MultiController {

	private static Player waitingPlayer;
	
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
			return "/multiplayer/waitingRoom";
		}
		System.out.println("There was a waiting player. Launching game");
		MPBoardDAO.create(player, waitingPlayer);
		waitingPlayer = null;
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
