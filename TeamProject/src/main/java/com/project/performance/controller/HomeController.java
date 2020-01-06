package com.project.performance.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.performance.model.User;
import com.project.performance.repository.UserRepository;

@Controller
public class HomeController {

	@Autowired
	HttpSession session;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String signinPost(@ModelAttribute User user) {
		User dbUser = userRepository.findByEmailAndPwd(user.getEmail(), user.getPwd());
		if (dbUser != null) {
			session.setAttribute("user_info", dbUser);
		}
		return "redirect:/main";
	}
	
	@GetMapping("/signout")
	public String signout() {
	session.invalidate();
	return "redirect:/main";
	}

	@RequestMapping("/main")
	public String main() {
		return "main";
	}

	@RequestMapping("/map")
	public String map() {
		return "map";
	}

	@RequestMapping("/mylist")
	public String mylist() {
		return "mylist";
	}

	@Autowired
	UserRepository userRepository;

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/register")
	public String registerPost(@ModelAttribute User user) {
		userRepository.save(user);
		return "redirect:/login";
	}

	@RequestMapping("/forgotpassword")
	public String forgotpassword() {
		return "forgotpassword";
	}

	@RequestMapping("/detail")
	public String detail() {
		return "detail";
	}

	@RequestMapping("/bigdoor")
	public String bigdoor() {
		return "bigdoor";
	}

	@RequestMapping("/error404")
	public String error404() {
		return "error404";
	}
}
