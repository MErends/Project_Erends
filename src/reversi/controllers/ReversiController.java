package reversi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReversiController {

	@RequestMapping("/")
	public String simple() {
		return "index";
	}

}
