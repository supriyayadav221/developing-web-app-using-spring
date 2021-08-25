package com.expense.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.expense.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense,Integer> {

	
	@Query("from Expense as c where c.user.id=:userId")
	public List<Expense> findExpensesbyUser(@Param("userId")int userId);
}
