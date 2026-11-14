package com.security.bank.accounts;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.security.bank.dto.AccountDto;
import com.security.bank.dto.KycDto;
import com.security.bank.dto.NomineeDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.Nominee;
import com.security.bank.entity.User;
import com.security.bank.service.AccountService;

@RestController
@RequestMapping("/account")
public class UserAccountController {

	@Autowired
	AccountService accountService;

	@PostMapping("/create/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public Account createAccount(@RequestBody AccountDto accountDto, @PathVariable Long userId) {

		return accountService.createAccount(accountDto, userId);
	}

	@GetMapping("/all/{userId}")
	public List<Account> getAllAccounts(@PathVariable Long userId) {
		return accountService.getAllAccounts(userId);
	}

	@GetMapping("/balance")
	public double checkAccountBalance(@RequestParam Long accountNumber) {
		return accountService.getBalance(accountNumber);
	}

	@GetMapping("/nominee")
	public Nominee getNominee(@RequestParam Long accountNumber) {
		return accountService.getNominee(accountNumber);
	}

	@PutMapping("/updateNominee/{accountId}")
	public Nominee updateNominee(
	        @RequestBody NomineeDto nomineeDto,
	        @PathVariable Long accountId) {

	    return accountService.updateNominee(nomineeDto, accountId);
	}

	@GetMapping("/getKycDetails")
	public User getUserByAcountNumber(@RequestParam Long accountNumber) {
		return accountService.getKycDetails(accountNumber);
	}

	@PutMapping("/updateKyc/{accountId}")
	public User updateKyc(
	        @RequestBody KycDto kycDto,
	        @PathVariable Long accountId) {

	    return accountService.updateKyc(kycDto, accountId);
	}

	@GetMapping("/getAccount/summary")
	public Account getAccountSummary(@RequestParam Long accountNumber) {
		return accountService.getAccountSummary(accountNumber);
	}
}
