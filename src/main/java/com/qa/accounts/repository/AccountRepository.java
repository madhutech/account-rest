package com.qa.accounts.repository;

import com.qa.accounts.domain.Account;
import org.springframework.data.repository.CrudRepository;

@SuppressWarnings("ALL")
public interface AccountRepository extends CrudRepository<Account,Integer>{
}
