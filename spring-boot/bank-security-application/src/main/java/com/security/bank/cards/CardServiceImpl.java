package com.security.bank.cards;

import org.springframework.stereotype.Service;

import com.security.bank.dto.CardDto;
import com.security.bank.entity.Card;
import com.security.bank.repository.AccountRepository;
import com.security.bank.repository.CardRepository;
import com.security.bank.service.CardService;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    public CardServiceImpl(CardRepository cardRepository,
                           AccountRepository accountRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public String blockCard(Long accountNumber, Long cardNumber) {
        return null;
    }

    @Override
    public String applyNewCard(Long accountNumber, CardDto cardDto) {
        return null;
    }

    @Override
    public Card updateCardSettings(Card card, Long cardNumber) {
        return null;
    }
}