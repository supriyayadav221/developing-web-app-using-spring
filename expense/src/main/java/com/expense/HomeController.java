package com.expense;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.expense.dao.UserRepository;
import com.expense.helper.Message;
import com.expense.model.Expense;
import com.expense.model.User;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title", "Expenses Tracker");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title", "About Expense Tracker");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model)
	{
		
		model.addAttribute("title", " Register-Expense Tracker");
		model.addAttribute("user",new User());
		return "signup";
	}
//	@GetMapping("/login")
//	public String customLogin(Model model)
//	{
//	
//		model.addAttribute("title", "Login- Expense Tracker");
//		return "user_dahboard";
//	}
	

	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,@RequestParam(value="agreement",defaultValue="false") boolean agreement,Model model,
			HttpSession session)
	{
		try {
		if(!agreement)
		{
			System.out.println("not agreed t&c");
			throw new Exception();
		}
		
		
		user.setRole("ROLE_USER");
		user.setEnables(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		System.out.println("Agreement"+agreement);
		System.out.println("USER"+user);
		
		User result=this.userRepository.save(user);
		model.addAttribute("user",new User());
		//session.setAttribute(null, result);
		}catch(Exception e)
		{
			e.printStackTrace();
			model.addAttribute("user",user);
		//	session.setAttribute("message",new Message("something went wrong"+e.getMessage(),"Alert! error"));
		}
		return "signup";
	}

}
