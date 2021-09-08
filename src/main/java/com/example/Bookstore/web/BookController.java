package com.example.Bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {
	@GetMapping("/index")
	public String books(Model model, @RequestParam(name = "book", required = false) String book) {
		model.addAttribute("book", book);
		return "index";
	}

}
