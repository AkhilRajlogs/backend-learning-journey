package com.security.bank.investments;

import org.springframework.stereotype.Service;

import com.security.bank.dto.InvestmentDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.Investment;
import com.security.bank.entity.InvestmentType;
import com.security.bank.repository.AccountRepository;
import com.security.bank.repository.InvestmentRepository;
import com.security.bank.service.InvestmentService;

@Service
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final AccountRepository accountRepository;

    public InvestmentServiceImpl(InvestmentRepository investmentRepository,
                                 AccountRepository accountRepository) {
        this.investmentRepository = investmentRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public String investNow(Long accountId, InvestmentDto investmentDto) {

        Account account = accountRepository.findById(accountId).get();

        if (account.getBalance() < investmentDto.getAmount()) {

            throw new RuntimeException("Error in Investment");
        }

        Investment investment = new Investment();

        investment.setInvestmentType(InvestmentType.valueOf(investmentDto.getInvestmentType()));

        investment.setAmount(investmentDto.getAmount());

        investment.setDuration(  investmentDto.getDuration());

        investment.setUser(account.getUser());

        account.setBalance(account.getBalance() - investmentDto.getAmount());

        investmentRepository.save(investment);

        accountRepository.save(account);

        return "Investment successful";
    }
}