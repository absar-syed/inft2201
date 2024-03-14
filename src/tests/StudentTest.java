package tests;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hamcrest.CoreMatchers;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import inft2202.kunza.InvalidUserDataException;
//import inft2202.kunza.Student;
import syeda.Student;
import exceptions.InvalidUserDataException;

public class StudentTest {

    public Student testStudent;
    public static Date lastAccess;
    public static Date enrol;


    @BeforeAll
    public static void setTestDates() {
	    GregorianCalendar cal = new GregorianCalendar();
		cal.set(2016, Calendar.SEPTEMBER, 3);
		lastAccess = cal.getTime();
		enrol = cal.getTime();
    }

    @BeforeEach
    public void resetStudent() {
        testStudent = null;
    }

    @Test
    public void itShouldDisallowEmptyFirstName() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testStudent = new Student(
                    100456789L,
                    "password",
                    "",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    's',
                    "CPA",
                    "Computer Programmer Analyst",
                    3);
        });
        assert (exception.getMessage().toLowerCase().contains("first"));
    }

    @Test
    public void itShouldDisallowEmptyLastName() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testStudent = new Student(
                    100456789L,
                    "password",
                    "Robert",
                    "",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    's',
                    "CPA",
                    "Computer Programmer Analyst",
                    3);
        });
        assert (exception.getMessage().toLowerCase().contains("last"));
    }

    @Test
    public void itShouldDisallowShortUserId() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testStudent = new Student(
                    123L,
                    "password",
                    "Robert",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    's',
                    "CPA",
                    "Computer Programmer Analyst",
                    3);
        });
        assert(exception.getMessage().toLowerCase().contains("id"));
    }

    @Test
    public void itShouldDisallowLongUserId() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testStudent = new Student(
                    10012345678L,
                    "password",
                    "Robert",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    's',
                    "CPA",
                    "Computer Programmer Analyst",
                    3);
        });
        assert(exception.getMessage().toLowerCase().contains("id"));
    }

    @Test
    public void itShouldDisallowBadUserId() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testStudent = new Student(
                    -100123456L,
                    "password",
                    "Robert",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    's',
                    "CPA",
                    "Computer Programmer Analyst",
                    3);
        });
        assert(exception.getMessage().toLowerCase().contains("id"));
    }

    @Test
    public void itShouldDisallowShortPassword() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testStudent = new Student(
                    100123456L,
                    "tiny",
                    "Robert",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    's',
                    "CPA",
                    "Computer Programmer Analyst",
                    3);
        });
        assert(exception.getMessage().toLowerCase().contains("password"));
    }

    @Test
    public void itShouldDisallowLongPassword() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testStudent = new Student(
                    100123456L,
                    "supercrazylongpasswordthatisactuallyreallylongerthanwhatweaskedfor",
                    "Robert",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    's',
                    "CPA",
                    "Computer Programmer Analyst",
                    3);
        });
        assert(exception.getMessage().toLowerCase().contains("password"));
    }

    @Test
    public void itShouldToString() {
        try {
            testStudent = new Student(
                    100123456L,
                    "password",
                    "Robert",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    's',
                    "CPA",
                    "Computer Programmer Analyst",
                    3);

            String value = testStudent.toString();
            System.out.println(value);
            assertThat("string has Student label", value, CoreMatchers.containsString("Student"));
            assertThat("string has student id", value, CoreMatchers.containsString(testStudent.getId().toString()));
            assertThat("string has first name", value, CoreMatchers.containsString(testStudent.getFirstName()));
            assertThat("string has last name", value, CoreMatchers.containsString(testStudent.getLastName()));
            assertThat("string has date", value, CoreMatchers.containsString("3-Sep-2016"));
            assertThat("string has year number text", value, CoreMatchers.containsString("3rd"));
            assertThat("string has program code", value, CoreMatchers.containsString(testStudent.getProgramCode()));
            assertThat("string has program description", value, CoreMatchers.containsString(testStudent.getProgramDescription()));
        } catch (Exception ex) {
            assert(false);
        }
    }
}
