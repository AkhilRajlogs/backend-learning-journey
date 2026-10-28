package com.security.bank.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.bank.dto.AdminDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.AccountType;
import com.security.bank.entity.BranchType;
import com.security.bank.entity.User;
import com.security.bank.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/add")
	public User addAdmin(@RequestBody AdminDto adminDto) {

		return adminService.addAdmin(adminDto);
	}

	@GetMapping("/getAllUser")
	public List<User> getAllUsers() {

		return adminService.getAllUsers();
	}

	@GetMapping("/getUserByName/{username}")
	public User getUserByName(@PathVariable String username) {

		return adminService.getUserByName(username);
	}

	@DeleteMapping("/deleteUser/{userId}")
	public String deleteUser(@PathVariable Long userId) {

		return adminService.deleteUser(userId);
	}

	@PutMapping("/account/deactivate")
	public String deactivateAccount(@RequestParam Long userId, @RequestParam Long accountId) {

		return adminService.deactivateAccount(userId, accountId);
	}

	@PutMapping("/account/activate")
	public String activateAccount(@RequestParam Long userId, @RequestParam Long accountId) {

		return adminService.activateAccount(userId, accountId);
	}

	@GetMapping("/account/getActiveAccountsList")
	public List<Account> getActiveAccountsList() {

		return adminService.getActiveAccounts();
	}

	@GetMapping("/account/getInActiveAccountsList")
	public List<Account> getInActiveAccountsList() {

		return adminService.getInactiveAccounts();
	}

	@GetMapping("/accountList/ByAccountType/{accType}")
	public List<Account> getAccountsByAccountType(@PathVariable AccountType accType) {

		return adminService.getAccountsByAccountType(accType);
	}

	@GetMapping("/accountList/ByBranchType/{branchType}")
	public List<Account> getAccountsByBranchType(@PathVariable BranchType branchType) {

		return adminService.getAccountsByBranch(branchType);
	}
}