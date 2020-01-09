package com.project.performance.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.performance.model.ContentInfo;
import com.project.performance.model.Scrap;
import com.project.performance.model.User;
import com.project.performance.repository.ContentRepository;
import com.project.performance.repository.ScrapRepository;
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
		return "redirect:/stage";
	}

	@Autowired
	ContentRepository contentRepository;

	@GetMapping("/content")
	public List<ContentInfo> content() {
		List<ContentInfo> content = contentRepository.findAll();
		return content;
	}

	@PostMapping("/content")
	public String contentPost(@ModelAttribute ContentInfo content) {
		contentRepository.save(content);

		return "redirect:/content";
	}

	@GetMapping("/signout")
	public String signout() {
		session.invalidate();
		return "redirect:/stage";
	}

	@GetMapping("/stage")
	public String stage(HttpServletRequest req , Model model) throws IOException {
	
		String genre = req.getParameter("genre");
		String category = req.getParameter("category");
		if(category==null || category.equals("")) category = "연극";
		if(genre==null || genre.equals("")) genre = "";
		List<ContentInfo> list = contentRepository.findAllByGenreContainingAndCategory(genre, category);
		System.out.println(list);
		if(genre==null || genre.equals("")) genre = "전체";
		// list, category, genre 전달
		model.addAttribute("list" , list);
		model.addAttribute("category" , category);
		model.addAttribute("genre" , genre);
		
		return "stage";
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

	@GetMapping("/detail")
	public String detail(
			Model model, 
			@RequestParam("title") String title,
			@RequestParam("period") String period,
			@RequestParam("location") String location,
			@RequestParam("category") String category,
			@RequestParam("thumb") String thumb
			) throws UnsupportedEncodingException {
		
		
		
		
		String result = "";
		for(int i = 0; i < title.length(); i++) {
			result += "%u" + Integer.toHexString(title.charAt(i) | 0x10000).substring(1);
		}
		String interparkTit = result;
		
		model.addAttribute("title", title);
		model.addAttribute("interparkTit", interparkTit);
		model.addAttribute("period", period);
		model.addAttribute("location", location);
		model.addAttribute("youtube", category + title);
		model.addAttribute("thumb", thumb);
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
	
	@Autowired
	ScrapRepository scrapRepository;
	
	@PostMapping("/test")
	@ResponseBody
	public String testPost(@ModelAttribute Scrap scrap, HttpSession session) {
		User user = (User) session.getAttribute("user_info");
		if(user == null) {
			return "0";
		}
		
		String email = user.getEmail();
		scrap.setEmail(email);
		scrapRepository.save(scrap);
		return "1";
	}
	
	@GetMapping("/mylist")
	public String mylist(Model model, HttpSession session) throws IOException {
		
		
		
		User user = (User) session.getAttribute("user_info");
		String email = user.getEmail();
		List<Scrap> scraplist = scrapRepository.findAllByEmail(email);
		for(int i = 0; i < scraplist.size(); i++) {
			Scrap s = scraplist.get(i);
			String title = s.getTitle();
			String result = "";
			for(int j = 0; j < title.length(); j++) {
				result += "%u" + Integer.toHexString(title.charAt(j) | 0x10000).substring(1);
			}
			String interparkTit = result;
			s.setInterparkTit(interparkTit);
		}
		
		model.addAttribute("list", scraplist);
		return "scrap";
	}
}









