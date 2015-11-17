package reversi.controllers;

import javax.persistence.*;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import reversi.game.MPBoard;

@Controller
public class MultiController {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("reversi");



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
		
		MPBoard  bord = new MPBoard();
		bord.addPlayer("Player1Session","Player1!");
		bord.addPlayer("Player2Session","Player2!");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(bord);
		t.commit();
		em.close();
		
		
		return "/multiplayer/lobby";
	}

	@RequestMapping("/multiplayer/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/multiplayer";
	}
}
