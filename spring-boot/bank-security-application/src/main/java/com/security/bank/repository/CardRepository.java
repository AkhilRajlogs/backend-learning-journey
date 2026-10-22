package com.security.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.bank.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

	Optional<Card> findByCardNumber(Long cardNumber);
}
