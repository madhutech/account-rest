package com.qa.accounts.service;

import com.qa.accounts.domain.Account;

import java.util.List;

@SuppressWarnings("unused")
public interface AccountsService {

    List<Account> getAccounts();

    Account addAccount(Account account);

    void deleteAccount(int id);

}
