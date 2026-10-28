package com.security.bank.cards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.security.bank.dto.CardDto;
import com.security.bank.entity.Card;
import com.security.bank.service.CardService;

@RestController
@RequestMapping("/card")
public class UserCardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/block")
    public String blockCard(@RequestParam Long accountNumber, @RequestParam Long cardNumber) {

        return cardService.blockCard(
                accountNumber,
                cardNumber);
    }

    @PostMapping("/apply/new")
    public String applyNewCard( @RequestParam Long accountNumber, @RequestBody CardDto cardDto) {

        return cardService.applyNewCard(accountNumber, cardDto);
    }

    @PutMapping("/setting")
    public Card updateCardSettings(@RequestBody Card card, @RequestParam Long cardNumber) {

        return cardService.updateCardSettings(card, cardNumber);
    }
}
