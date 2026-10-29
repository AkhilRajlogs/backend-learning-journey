package com.security.bank.accounts;

import java.util.*;

import org.springframework.stereotype.Service;

import com.security.bank.dto.AccountDto;
import com.security.bank.dto.KycDto;
import com.security.bank.dto.NomineeDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.AccountType;
import com.security.bank.entity.BranchType;
import com.security.bank.entity.Card;
import com.security.bank.entity.CardType;
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

        User user = userRepository.findById(userId).get();

        Account account = new Account();

        account.setAccountType(AccountType.valueOf(accountDto.getAccountType()));

        account.setBalance(accountDto.getBalance());
        account.setProof(accountDto.getProof());
        account.setNominee(accountDto.getNominee());

        account.setStatus("ACTIVE");
        account.setOpeningDate(new Date());
        
        Random random = new Random();

        account.setAccountNumber(Math.abs(random.nextLong()));

        account.setUser(user);

        Card card = new Card();

        card.setCardHolderName(user.getName());
        card.setCardNumber(Math.abs(random.nextLong()));

        card.setCvv(100 + random.nextInt(900));

        card.setAllocationDate(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5);

        card.setExpiryDate(calendar.getTime());

        card.setPin(1000L + random.nextInt(9000));

        card.setStatus("ACTIVE");

        switch (account.getAccountType()) {

        case SAVINGS:

            account.setInterestRate(2.7f);
            account.setBranch(BranchType.BOB);

            card.setCardType(CardType.DEBIT_GLOBAL);
            card.setDailyLimit(40000);

            account.setCard(card);

            break;

        case CURRENT:

            account.setInterestRate(5.2f);
            account.setBranch(BranchType.ICIC);

            card.setCardType(CardType.CREDIT_PREMIUM);
            card.setDailyLimit(50000);

            account.setCard(card);

            break;

        case PPF:

            account.setInterestRate(7.4f);
            account.setBranch(BranchType.SBI);

            break;

        case SALARY:

            account.setInterestRate(4.1f);
            account.setBranch(BranchType.HDFC);

            card.setCardType(CardType.CREDIT_MASTER);
            card.setDailyLimit(75000);

            account.setCard(card);

            break;
        }

        return accountRepository.save(account);
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

        return account.getNominee();
    }

    @Override
    public Nominee updateNominee(NomineeDto nomineeDto, Long accountId) {

        Account account = accountRepository.findById(accountId).get();

        Nominee nominee = account.getNominee();

        if (nominee == null) {
            nominee = new Nominee();
        }

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