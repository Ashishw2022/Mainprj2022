package com.example.vcare;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.vcare.register.Login;

public class ExampleUnitTest {
    @Test
    public void testExampleWithCorrectValues() {
        String validEmail = "ashish123@gmail.com";
        String validPassword = "user123456";
        boolean response =  Login.validate(validEmail,validPassword);
        Assert.assertEquals(true, response);
    }
    @Test
    public void testExampleWithIncorrectEmail() {
        String invalidEmail = "email1";
        String validPassword = "password";
        boolean response =   Login.validate(invalidEmail,validPassword);
        Assert.assertEquals(false, response);
    }
    @Test
    public void testExampleWithIncorrectPassword() {
        String validEmail = "email";
        String invalidPassword = "password1";
        boolean response =   Login.validate(validEmail,invalidPassword);
        Assert.assertEquals(false, response);
    }
    @Test
    public void testExampleWithIncorrectValues() {
        String invalidEmail = "email1";
        String invalidPassword = "password1";
        boolean response =   Login.validate(invalidEmail,invalidPassword);
        Assert.assertEquals(false, response);
    }
}

