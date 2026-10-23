package com.security.bank.service;

import java.util.List;

import com.security.bank.dto.AdminDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.AccountType;
import com.security.bank.entity.BranchType;
import com.security.bank.entity.User;

public interface AdminService {

    User addAdmin(AdminDto adminDto);

    List<User> getAllUsers();

    User getUserByName(String username);

    String deleteUser(Long userId);

    String activateAccount(Long userId, Long accountId);

    String deactivateAccount(Long userId, Long accountId);

    List<Account> getActiveAccounts();

    List<Account> getInactiveAccounts();

    List<Account> getAccountsByAccountType(AccountType accType);

    List<Account> getAccountsByBranch(BranchType branchType);

}