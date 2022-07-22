package com.example.vcare;

import org.junit.Assert;
import org.junit.Test;
import com.example.vcare.register.Signup;

public class registerTest {
    @Test
    public void testExampleWithCorrectValues() {
        String lname ="anu"; String emailMain ="ashish123@gmail.com";String phMain="9878945156";String passwordMain="user1234";String cpasswordMain ="user1234";
        boolean response =  Signup.validate(lname,emailMain,phMain,passwordMain,cpasswordMain);
        Assert.assertEquals(true, response);
    }
    @Test
    public void testExampleWithIncorrectEmail() {
        String lname =""; String emailMain ="user@gmail.com";String phMain="";String passwordMain="user";String cpasswordMain ="user";
        boolean response =  Signup.validate(lname,emailMain,phMain,passwordMain,cpasswordMain);
        Assert.assertEquals(false, response);
    }
    @Test
    public void testExampleWithIncorrectPassword() {
        String lname ="anu"; String emailMain ="user@gmail.com";String phMain="9878945156";String passwordMain="";String cpasswordMain ="";
        boolean response =  Signup.validate(lname,emailMain,phMain,passwordMain,cpasswordMain);
        Assert.assertEquals(false, response);
    }

    @Test
    public void testExampleWithIncorrectValues() {
        String lname ="anu"; String emailMain ="use123r@gmail.com";String phMain="9878945156";String passwordMain="";String cpasswordMain ="";
        boolean response =  Signup.validate(lname,emailMain,phMain,passwordMain,cpasswordMain);
        Assert.assertEquals(false, response);
    }
}

