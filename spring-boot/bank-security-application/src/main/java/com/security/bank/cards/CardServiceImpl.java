package com.security.bank.cards;

import java.util.*;

import org.springframework.stereotype.Service;

import com.security.bank.dto.CardDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.Card;
import com.security.bank.entity.CardType;
import com.security.bank.repository.AccountRepository;
import com.security.bank.repository.CardRepository;
import com.security.bank.service.CardService;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    public CardServiceImpl(CardRepository cardRepository, AccountRepository accountRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public String blockCard(Long accountNumber, Long cardNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber).get();

        Card card = account.getCard();

        if (!card.getCardNumber().equals(cardNumber)) {
            return "Card Not Found";
        }

        card.setStatus("BLOCKED");

        cardRepository.save(card);

        return "Card Blocked Successfully";
    }

    @Override
    public String applyNewCard(Long accountNumber, CardDto cardDto) {

        Account account = accountRepository.findByAccountNumber(accountNumber).get();

        Random random = new Random();

        Card card = new Card();

        card.setCardHolderName(cardDto.getCardHolderName());

        card.setCardType(CardType.valueOf(cardDto.getCardType()));

        card.setPin(cardDto.getPin());

        card.setCardNumber(Math.abs(random.nextLong()));

        card.setCvv(100 + random.nextInt(900));

        card.setAllocationDate(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5);

        card.setExpiryDate(calendar.getTime());

        card.setStatus("ACTIVE");

        account.setCard(card);

        accountRepository.save(account);

        return "Card Applied Successfully";
    }

    @Override
    public Card updateCardSettings(Card card, Long cardNumber) {

        Card existingCard = cardRepository.findByCardNumber(cardNumber).get();

        existingCard.setCardHolderName( card.getCardHolderName());

        existingCard.setCardType(card.getCardType());

        existingCard.setPin(card.getPin());

        existingCard.setStatus(card.getStatus());

        existingCard.setDailyLimit(card.getDailyLimit());

        return cardRepository.save(existingCard);
    }
}