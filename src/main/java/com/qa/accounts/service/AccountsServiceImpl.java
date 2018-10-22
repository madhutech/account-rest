package com.qa.accounts.service;

import com.qa.accounts.domain.Account;
import com.qa.accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressWarnings("unused")
@Service
public class AccountsServiceImpl implements AccountsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAccounts() {
        List<Account> accountList = new ArrayList<>();
        accountRepository.findAll().forEach(accountList::add);
        return accountList;
    }

    @Override
    @Transactional
    public Account addAccount(final Account account) {
        checkNotNull(account);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccount(final int id) {
        accountRepository.deleteById(id);
    }
}
