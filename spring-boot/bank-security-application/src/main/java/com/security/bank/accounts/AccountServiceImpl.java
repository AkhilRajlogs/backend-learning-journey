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
    public Nominee updateNominee(NomineeDto nomineeDto, Long accountId) {

        Account account = accountRepository.findById(accountId).get();

        Nominee nominee = account.getNominee();

        nominee.setRelation(nomineeDto.getRelation());
        nominee.setName(nomineeDto.getName());
        nominee.setAccountNumber(nomineeDto.getAccountNumber());
        nominee.setGender(nomineeDto.getGender());
        nominee.setAge(nomineeDto.getAge());

        account.setNominee(nominee);

        accountRepository.save(account);

        return nominee;
    }

    @Override
    public User getKycDetails(Long accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber).get();

        User user = account.getUser();

        user.setAccountList(null);
        user.setInvestmentList(null);

        return user;
    }

    @Override
    public User updateKyc(KycDto kycDto, Long accountId) {

        Account account = accountRepository.findById(accountId).get();

        User user = account.getUser();

        user.setName(kycDto.getName());
        user.setAddress(kycDto.getAddress());
        user.setNumber(kycDto.getNumber());
        user.setIdentityProof(kycDto.getIdentityProof());

        userRepository.save(user);

        user.setAccountList(null);
        user.setInvestmentList(null);

        return user;
    }

    @Override
    public Account getAccountSummary(Long accountNumber) {
    	Account account = accountRepository.findByAccountNumber(accountNumber).get();

    	account.setUser(null);

    	return account;
    }
}