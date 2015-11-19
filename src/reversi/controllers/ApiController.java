package reversi.controllers;

import org.springframework.web.bind.annotation.*;

import reversi.game.*;

@RestController
public class ApiController {

	@RequestMapping(value="/api/turn/{id}")
	public Color getTurn(@PathVariable int id) {
		System.out.println("Turn requested!");
		MPBoard board = MPBoardDAO.getByID(id);
		if (board != null) {
			return board.getTurn();
		}
		return Color.None;
	}
	
	@RequestMapping(value="/api/waiter/")
	public Long getWaiter() {
		Player player = MultiController.getWaitingPlayer();
		if (player != null) {
			return player.getId();
		}
		return -1L;
	}
	
}
