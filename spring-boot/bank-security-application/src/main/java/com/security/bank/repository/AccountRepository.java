package com.security.bank.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.bank.entity.Account;
import com.security.bank.entity.AccountType;
import com.security.bank.entity.BranchType;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>  {
	
	Optional<Account> findByAccountNumber(Long accountNumber);
	List<Account> findAllByStatus(String status);
    List<Account> findAllByAccountType(AccountType accountType);
    List<Account> findAllByBranch(BranchType branchType);
    
}
