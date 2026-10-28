package com.security.bank.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.bank.dto.AdminDto;
import com.security.bank.entity.Account;
import com.security.bank.entity.AccountType;
import com.security.bank.entity.BranchType;
import com.security.bank.entity.Role;
import com.security.bank.entity.User;
import com.security.bank.repository.AccountRepository;
import com.security.bank.repository.UserRepository;
import com.security.bank.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(UserRepository userRepository,
                            AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;

    }
    
    @Override
    public User addAdmin(AdminDto adminDto) {

        User user = new User();

        user.setName(adminDto.getName());
        user.setUsername(adminDto.getUsername());
        user.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        user.setAddress(adminDto.getAddress());
        user.setNumber(adminDto.getNumber());
        user.setIdentityProof(adminDto.getIdentityProof());

        Role role = new Role();
        role.setRoleName("ROLE_ADMIN");

        user.setRoles(role);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User getUserByName(String username) {

        return userRepository.findByUsername(username).get();
    }

    @Override
    public String deleteUser(Long userId) {

        if(userRepository.existsById(userId)) {

            userRepository.deleteById(userId);

            return "Deleted Successfully";
        }

        return "Error in deletion";
    }
    
    @Override
    public String activateAccount(Long userId, Long accountId) {

        if(userRepository.existsById(userId)
                && accountRepository.existsById(accountId)) {

            Account account =
                    accountRepository.findById(accountId).get();

            account.setStatus("ACTIVE");

            accountRepository.save(account);

            return "Activated Account for User with id: " + userId;
        }

        return "ERROR";
    }

    @Override
    public String deactivateAccount(Long userId, Long accountId) {

        if(userRepository.existsById(userId)
                && accountRepository.existsById(accountId)) {

            Account account =
                    accountRepository.findById(accountId).get();

            account.setStatus("INACTIVE");

            accountRepository.save(account);

            return "Deactivated Account for User with id: " + userId;
        }

        return "ERROR";
    }

    @Override
    public List<Account> getActiveAccounts() {

        return accountRepository.findAllByStatus("ACTIVE");
    }

    @Override
    public List<Account> getInactiveAccounts() {

        return accountRepository.findAllByStatus("INACTIVE");
    }

    @Override
    public List<Account> getAccountsByAccountType(AccountType accType) {

        return accountRepository.findAllByAccountType(accType);
    }

    @Override
    public List<Account> getAccountsByBranch(BranchType branchType) {

        return accountRepository.findAllByBranch(branchType);
    }
}