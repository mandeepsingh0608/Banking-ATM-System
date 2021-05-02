package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest {

    @Test
    public void checkUserName() {
        Bank account=new Bank();
        assertTrue(account.CheckUserName("Rahul"));//user exist in database
        assertFalse(account.CheckUserName("Tania"));//user does not exist in database
    }

    @Test
    public void checkPassword() {
        Bank account=new Bank();
        assertTrue(account.CheckPassword("A102"));//user exist in database
        assertFalse(account.CheckPassword("T3016"));//user does not exist in database
    }

    @Test
    public void depositAmount() {
        Bank account=new Bank();
        assertEquals(50,account.depositAmount(50),0);

    }


}