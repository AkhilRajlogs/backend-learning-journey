package com.security.bank.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.security.bank.dto.AdminDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.AccountType;
import com.security.bank.entity.BranchType;
import com.security.bank.entity.User;
import com.security.bank.repository.AccountRepository;
import com.security.bank.repository.UserRepository;
import com.security.bank.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AdminServiceImpl(UserRepository userRepository,
                            AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public User addAdmin(AdminDto adminDto) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserByName(String username) {
        return null;
    }

    @Override
    public String deleteUser(Long userId) {
        return null;
    }

    @Override
    public String activateAccount(Long userId, Long accountId) {
        return null;
    }

    @Override
    public String deactivateAccount(Long userId, Long accountId) {
        return null;
    }

    @Override
    public List<Account> getActiveAccounts() {
        return null;
    }

    @Override
    public List<Account> getInactiveAccounts() {
        return null;
    }

    @Override
    public List<Account> getAccountsByAccountType(AccountType accType) {
        return null;
    }

    @Override
    public List<Account> getAccountsByBranch(BranchType branchType) {
        return null;
    }
}