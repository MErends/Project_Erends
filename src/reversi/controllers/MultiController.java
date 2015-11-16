package reversi.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MultiController {

	@RequestMapping("multiplayer/")
	public String loggedIn(HttpSession session) {
		String name = (String) session.getAttribute("name");
		if (name == null || name == "")
			return "multiplayer/login";
		return "multiplayer/lobby";
	}
	
	@RequestMapping("multiplayer/login")
	public String logIn(Model model, HttpSession session) {
		return "multiplayer/lobby";
	}
}
