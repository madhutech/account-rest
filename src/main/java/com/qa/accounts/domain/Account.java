package com.qa.accounts.domain;

import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;

import static com.google.common.base.Objects.equal;

@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "firstName is required field")
   // @Pattern(regexp = "^[A-Za-z]+$", message = "Only alphabet letters allowed for firstName")
    private String firstName;

    @NotBlank(message = "secondName is required field")
   // @Pattern(regexp = "^[A-Za-z]+$", message = "Only alphabet letters allowed for secondName")
    private String secondName;

    @Column(unique = true)
    @Positive(message = "positive integer should be supplied for accountNumber")
    private int accountNumber;

    public Account() {
    }

    public Account(String firstName, String secondName, int accountNumber) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.accountNumber = accountNumber;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getAccountNumber() {
        return accountNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account accounts = (Account) o;
        return id == accounts.id &&
                accountNumber == accounts.accountNumber &&
                equal(firstName, accounts.firstName) &&
                equal(secondName, accounts.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, firstName, secondName, accountNumber);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", accountNumber=" + accountNumber +
                '}';
    }
}
