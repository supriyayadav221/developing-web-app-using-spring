package com.expense;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.expense.dao.ExpenseRepository;
import com.expense.dao.UserRepository;
import com.expense.helper.Message;
import com.expense.model.Expense;
import com.expense.model.User;

@Controller
@RequestMapping("/")
public class UserController {
	

	@Autowired
	private ExpenseRepository  expenseRepository;
	
	@Autowired
	private UserRepository  userRepository;
	@ModelAttribute
	public void addCommonData(Model model,Principal principal)
	{
		String userName=principal.getName();
		System.out.print("username "+userName);
		User user=userRepository.getUserByUserName(userName);
		
		System.out.println("USER"+user);
		model.addAttribute("user",user);
		
		
	}
	
	@RequestMapping("/user/index")
	public String dashboard(Model model,Principal principal)
	{
		
		model.addAttribute("title","User Dashboard");
		return "user_dahboard";
	}
	
	@GetMapping("/user/add-expense")
	public String openAddExpenseForm(Model model)
	{
		model.addAttribute("title","Add Expense");
		model.addAttribute("expense",new Expense());
		return "add_expense_form";
	}
	
	@PostMapping("/user/process-expense")
	public String processExpense(@ModelAttribute Expense expense,Principal principal)
	{
	try {
		String name=principal.getName();
		User user=this.userRepository.getUserByUserName(name);
		expense.setUser(user);
		user.getExpenses().add(expense);
		this.userRepository.save(user);
		
		System.out.println("Added to database");
		System.out.println("Data" + expense.toString());
	}
	catch(Exception e)
	{
		System.out.print("Errro"+e.getMessage());
		e.printStackTrace();
		
	}

	
		return "add_expense_form";
	}
	
	@GetMapping("/user/show-expenses")
	public String showExpenses(Model m,Principal principal)
	{    m.addAttribute("title","View Expense");
	String userName=principal.getName();
	User user=this.userRepository.getUserByUserName(userName);
		
		List<Expense> expenses=this.expenseRepository.findExpensesbyUser(user.getId());
		m.addAttribute("expenses",expenses);
//		
//		String userName=principal.getName();
//				User user=this.userRepository.getUserByUserName(userName);
//				List<Expense> expenses=user.getExpenses();
		
		return "show_expenses";
	}
	
	@GetMapping("/user/delete/{eID}")
	public String deleteContact(@PathVariable("eID") Integer eID,Model model)
	{
Optional<Expense> expenseOpt=this.expenseRepository.findById(eID);
	Expense expense=expenseOpt.get();

		expense.setUser(null);
		this.expenseRepository.delete(expense);
		
		
		return "redirect:/user/show-expenses/";
		
	
	}
	
	

}
