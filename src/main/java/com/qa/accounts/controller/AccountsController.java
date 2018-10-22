
package com.qa.accounts.controller;

import com.qa.accounts.domain.Account;
import com.qa.accounts.domain.ResponseText;
import com.qa.accounts.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unused")
@RestController
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @GetMapping ("/accounts")
  //  @ResponseBody
    public List<Account> getAccounts() {
        return accountsService.getAccounts();
    }

    @PostMapping(value = "/accounts")
 //   @ResponseBody
    public ResponseEntity addAccount(final @Valid @RequestBody Account account) {
        try {
            final Account savedAccount = accountsService.addAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseText(savedAccount.toString() +
                    "account has been successfully added"));
        } catch (DataAccessException daE) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseText(daE.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseText(e.getMessage()));
        }

    }

    @DeleteMapping(value = "/accounts/{id}")
  //  @ResponseBody
    public ResponseEntity deleteAccount(final @PathVariable(value = "id") int id) {

        String message;
        try {
            accountsService.deleteAccount(id);
            message = "account has been successfully deleted";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseText(message));
        } catch (DataAccessException e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseText(message));
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseText(message));
        } catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseText(message));
        }
    }

}
