package com.security.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.bank.entity.Investment;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {

	
	
}
