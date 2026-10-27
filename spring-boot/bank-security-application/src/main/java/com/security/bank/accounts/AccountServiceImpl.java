package com.security.bank.accounts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.security.bank.dto.AccountDto;
import com.security.bank.dto.KycDto;
import com.security.bank.dto.NomineeDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.Nominee;
import com.security.bank.entity.User;
import com.security.bank.repository.AccountRepository;
import com.security.bank.repository.UserRepository;
import com.security.bank.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Account createAccount(AccountDto accountDto, Long userId) {
        return null;
    }

    @Override
    public List<Account> getAllAccounts(Long userId) {
    	User user = userRepository.findById(userId).get();
    	return user.getAccountList();
    }

    @Override
    public double getBalance(Long accountNumber) {
    	Account account = accountRepository.findByAccountNumber(accountNumber).get();
        return account.getBalance();
    }

    @Override
    public Nominee getNominee(Long accountNumber) {
    	
    	Account account = accountRepository.findByAccountNumber(accountNumber).get();
    	
        return  account.getNominee();
    }

    @Override
    public Nominee updateNominee(NomineeDto nomineeDto, Long accountId) {
        return null;
    }

    @Override
    public User getKycDetails(Long accountNumber) {
        return null;
    }

    @Override
    public User updateKyc(KycDto kycDto, Long accountId) {
        return null;
    }

    @Override
    public Account getAccountSummary(Long accountNumber) {
    	Account account = accountRepository.findByAccountNumber(accountNumber).get();

    	account.setUser(null);

    	return account;
    }
}