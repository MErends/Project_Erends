package reversi.controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import reversi.game.*;

@Controller
public class ReversiController {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("reversi");

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/newGame")
	public String newGame(Model model, HttpSession session) {
		Board board = new Board();
		Color color = Color.White;
		session.setAttribute("board", board);
		session.setAttribute("ColorHasTurn", color);
		int[][] potentialScore = new int[8][8];
		for(int x = 0; x != 8; ++x) 
			for(int y = 0; y != 8; ++y) 
				potentialScore[x][y] = board.potentialScoreFor(x, y, color);
		
		session.setAttribute("potential", potentialScore);
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist( new Stone(Color.Black) );
		t.commit();
		em.close();
		return "showBoard";
	}
}
