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
//import inft2202.kunza.Faculty;
import syeda.Faculty;
import exceptions.InvalidUserDataException;

public class FacultyTest {

    public Faculty testFaculty;
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
    public void resetFaculty() {
        testFaculty = null;
    }

    @Test
    public void itShouldDisallowEmptyFirstName() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testFaculty = new Faculty(
                    100456789L,
                    "password",
                    "",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    'f',
                    "BITM",
                    "Business, IT, and Management",
                    "R2-D2",
                    5555);
        });
        assert (exception.getMessage().toLowerCase().contains("first"));
    }

    @Test
    public void itShouldDisallowEmptyLastName() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testFaculty = new Faculty(
                    100456789L,
                    "password",
                    "Robert",
                    "",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    'f',
                    "BITM",
                    "Business, IT, and Management",
                    "R2-D2",
                    5555);
        });
        assert (exception.getMessage().toLowerCase().contains("last"));
    }

    @Test
    public void itShouldDisallowShortUserId() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testFaculty = new Faculty(
                    123L,
                    "password",
                    "Robert",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    'f',
                    "BITM",
                    "Business, IT, and Management",
                    "R2-D2",
                    5555);
        });
        assert (exception.getMessage().toLowerCase().contains("id"));
    }

    @Test
    public void itShouldDisallowLongUserId() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testFaculty = new Faculty(
                    10012345678L,
                    "password",
                    "Robert",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    'f',
                    "BITM",
                    "Business, IT, and Management",
                    "R2-D2",
                    5555);
        });
        assert (exception.getMessage().toLowerCase().contains("id"));
    }

    @Test
    public void itShouldDisallowBadUserId() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testFaculty = new Faculty(
                    -100123456L,
                    "password",
                    "Robert",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    'f',
                    "BITM",
                    "Business, IT, and Management",
                    "R2-D2",
                    5555);
        });
        assert (exception.getMessage().toLowerCase().contains("id"));
    }

    @Test
    public void itShouldDisallowShortPassword() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testFaculty = new Faculty(
                    100123456L,
                    "tiny",
                    "Robert",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    'f',
                    "BITM",
                    "Business, IT, and Management",
                    "R2-D2",
                    5555);
        });
        assert (exception.getMessage().toLowerCase().contains("password"));
    }

    @Test
    public void itShouldDisallowLongPassword() {
        Exception exception = Assert.assertThrows(InvalidUserDataException.class, () -> {
            testFaculty = new Faculty(
                    100123456L,
                    "supercrazylongpasswordthatisactuallyreallylongerthanwhatweaskedfor",
                    "Robert",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    'f',
                    "BITM",
                    "Business, IT, and Management",
                    "R2-D2",
                    5555);
        });
        assert (exception.getMessage().toLowerCase().contains("password"));
    }

    @Test
    public void itShouldToString() {
        try {
            testFaculty = new Faculty(
                    100123456L,
                    "password",
                    "Robert",
                    "McReady",
                    "bob.mcready@dcmail.ca",
                    enrol,
                    lastAccess,
                    true,
                    'f',
                    "BITM",
                    "Business, IT, and Management",
                    "R2-D2",
                    5555);

            String value = testFaculty.toString();
            System.out.println(value);
            assertThat("string has Faculty label", value, CoreMatchers.containsString("Faculty"));
            assertThat("string has Faculty id", value, CoreMatchers.containsString(testFaculty.getId().toString()));
            assertThat("string has first name", value, CoreMatchers.containsString(testFaculty.getFirstName()));
            assertThat("string has last name", value, CoreMatchers.containsString(testFaculty.getLastName()));
            assertThat("string has email", value, CoreMatchers.containsString(testFaculty.getEmailAddress()));
            assertThat("string has date", value, CoreMatchers.containsString("3-Sep-2016"));
            assertThat("string has school name", value, CoreMatchers.containsString(testFaculty.getSchoolDescription()));
            assertThat("string has school code", value, CoreMatchers.containsString(testFaculty.getSchoolCode()));
            assertThat("string has phone", value, CoreMatchers.containsString(Faculty.PHONE_NUMBER));
            assertThat("string has extension", value, CoreMatchers.containsString(String.valueOf(testFaculty.getExtension())));
        } catch (Exception ex) {
            assert (false);
        }
    }
}
