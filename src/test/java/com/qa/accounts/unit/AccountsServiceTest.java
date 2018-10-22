package com.qa.accounts.unit;

import com.qa.accounts.domain.Account;
import com.qa.accounts.repository.AccountRepository;
import com.qa.accounts.service.AccountsServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AccountsServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountsServiceImpl accountsService;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();


    @Test
    public void retrieveMultipleAccountsFromRepository() {

        when(accountRepository.findAll()).thenReturn(prepareAccountsForTest());
        final List<Account> accounts = accountsService.getAccounts();
        assertEquals(3, accounts.size());

        assertEquals("firstAccount_FirstName", accounts.get(0).getFirstName());
        assertEquals("firstAccount_SecondName", accounts.get(0).getSecondName());
        assertEquals(111, accounts.get(0).getAccountNumber());

        assertEquals("secondAccount_FirstName", accounts.get(1).getFirstName());
        assertEquals("secondAccount_SecondName", accounts.get(1).getSecondName());
        assertEquals(222, accounts.get(1).getAccountNumber());

        assertEquals("thirdAccount_FirstName", accounts.get(2).getFirstName());
        assertEquals("thirdAccount_SecondName", accounts.get(2).getSecondName());
        assertEquals(333, accounts.get(2).getAccountNumber());

        verify(accountRepository).findAll();
    }

    @Test
    public void addAccountsToRespository() {
        final Account expectedAccount = new Account("1name", "2name", 1111);
        when(accountRepository.save(expectedAccount)).thenReturn(expectedAccount);
        Account savedAccount = accountsService.addAccount(expectedAccount);
        assertEquals(expectedAccount, savedAccount);
        assertEquals(1111, savedAccount.getAccountNumber());
        assertEquals("1name", savedAccount.getFirstName());
        assertEquals("2name", savedAccount.getSecondName());
        verify(accountRepository).save(expectedAccount);

    }

    @Test
    public void addNullAccountToRepositoryThrowsNPE() {
        this.thrown.expect(NullPointerException.class);
        accountsService.addAccount(null);
    }

    private static List<Account> prepareAccountsForTest() {
        return Arrays.asList(
                new Account("firstAccount_FirstName", "firstAccount_SecondName", 111),
                new Account("secondAccount_FirstName", "secondAccount_SecondName", 222),
                new Account("thirdAccount_FirstName", "thirdAccount_SecondName", 333));
    }

}
