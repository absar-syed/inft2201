package tests;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import exceptions.InvalidUserDataException;
import exceptions.NotFoundException;
import syeda.*;

import static syeda.Student.retrieve;

public class Lab3Tester {


    public static void main(String[] args) throws SQLException, NotFoundException, ParseException, InvalidUserDataException {

        Connection c = null;
        c = DatabaseConnect.initialize();
        Student.initialize(c);

        System.out.println("AUTHENTICATION TESTS \n");

        // test student authenticator
        System.out.println("*******************************************");
        System.out.println("Should be Successful - Mike Jones");
        testAuth(100111111, "password");
        System.out.println("*******************************************");
        System.out.println("Should Fail - Wrong PW");
        testAuth(100111111, "mypassword2");
        System.out.println("*******************************************");
        System.out.println("Should Fail - User not found");
        testAuth(100000000, "mypassword");
        System.out.println("*******************************************");
        System.out.println("Should be Successul - Absar");
        testAuth(100706764, "password");
        System.out.println("*******************************************");

        System.out.println("\nUPDATE TEST");
        System.out.println("Should Update Last Access of Absar");
        Student Absar = Student.retrieve(100706764);
        System.out.println("Current Last Access: " + Absar.getLastAccess());
        Absar.setLastAccess(new Date());
        Absar.update();
        System.out.println("Updated Last Access: " + Absar.getLastAccess());

    }

    /**
     * method used to test authenticate method
     * @param id user ID
     * @param pw user password
     */
    public static void testAuth(long id, String pw) {

        try {
            Student stud = Student.authenticate(id, pw);
            System.out.println("Authentication Successful");
            System.out.println(stud.getFirstName() + " " + stud.getLastName());
        } catch (NotFoundException nfe) {
            System.out.println("Authentication Failed: " + nfe.getMessage());
        }

    }

}
