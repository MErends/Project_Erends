package reversi.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import reversi.game.*;

@Controller
public class ReversiController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/newGame")
	public String newGame(Model model, HttpSession session) {
		session.setAttribute("board", new Board());
		session.setAttribute("ColorHasTurn", Color.White);
		return "showBoard";
	}
}
