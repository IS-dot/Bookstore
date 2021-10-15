package com.example.Bookstore.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.Bookstore.domain.SignUpForm;
import com.example.Bookstore.domain.User;
import com.example.Bookstore.domain.UserRepository;

// ei tehdä tätä nyt loppuun tähän hätään
@Controller
public class SignUpController {
	@Autowired
	private UserRepository repository;

//	@RequestMapping(value = "signup")
//	public String addStudent(Model model) {
//		model.addAttribute("signupform", new SignUpForm());
//		return "signup";
//	}

	// luodaan uusi käyttäjä
	// tarkistetaan onko olemassa
	// rooliksi user
	// palautetaan login-sivulle
	@RequestMapping(value = "saveuser", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("signupform") SignUpForm signUpForm, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) { // validation errors
			if (signUpForm.getPassword().equals(signUpForm.getPasswordCheck())) { // check password match
				String pwd = signUpForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd);

				User newUser = new User();
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(signUpForm.getUsername());
				newUser.setRole("USER"); // itseään ei voi asettaa adminiksi
				if (repository.findByUsername(signUpForm.getUsername()) == null) { // tarkistetaanko onko olemassa
					repository.save(newUser);
				} else {
					bindingResult.rejectValue("username", "err.username", "Username already exists");
					return "signup";
				}
			} else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");
				return "signup";
			}
		} else {
			return "signup";
		}
		return "redirect:/login"; // palautetaan login-sivulle
	}
}
