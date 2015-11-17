package reversi.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MultiController {

	@RequestMapping("/multiplayer")
	public String loggedIn(HttpSession session) {
		String name = (String) session.getAttribute("name");
		if (name == null || name == "")
			return "/multiplayer/login";
		return "redirect:/multiplayer/lobby";
	}

	@RequestMapping(value="multiplayer", method=RequestMethod.POST)
	public String getLoggedIn(HttpSession session, String name) {
		if (name == null || name.equals("") || name.equals("Enter your name"))
			return "/multiplayer/login";
		session.setAttribute("name", name);
		return "redirect:/multiplayer/lobby";
	}

	@RequestMapping("/multiplayer/lobby")
	public String lobby(HttpSession session) {
		String name = (String) session.getAttribute("name");
		if (name == null || name == "")
			return "/multiplayer/login";
	
		return "/multiplayer/lobby";
	}

	@RequestMapping("/multiplayer/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/multiplayer";
	}
}
