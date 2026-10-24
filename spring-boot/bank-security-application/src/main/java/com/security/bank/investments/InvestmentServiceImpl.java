package com.security.bank.investments;

import org.springframework.stereotype.Service;

import com.security.bank.dto.InvestmentDto;
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
        return null;
    }
}