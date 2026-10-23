package com.security.bank.service;

import com.security.bank.dto.InvestmentDto;

public interface InvestmentService {

    String investNow(Long accountId, InvestmentDto investmentDto);

}