package syeda;

import syeda.*;
import exceptions.DuplicateException;
import exceptions.InvalidUserDataException;
import exceptions.NotFoundException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Student extends User {

    /**
     * A constant that holds the default program code; UNDC
     */
    public static final String DEFAULT_PROGRAM_CODE = "UNDC";

    /**
     * A constant that holds the default program description; Undeclared
     */
    public static final String DEFAULT_PROGRAM_DESCRIPTION = "Undeclared";

    /**
     * A constant that holds the default year of a student; 1
     */
    public static final int DEFAULT_YEAR = 1;

    /**
     * an instance of SimpleDateFormat that formats the date
     */
    private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Variable that hold an instance of Student
     */
    static Student aStudent;

    /**
     * Variable that hold an instance of a connection
     */
    static Connection aConnection;

    /**
     * Variable that hold an instance of a statement
     */
    static Statement aStatement;

    // declare static variables for all Student instance attribute values
    /**
     * long variable that holds the student id
     */
    static Long id;

    /**
     * String variable that holds student's password
     */
    static String password;

    /**
     * String variable that holds student's first name
     */
    static String firstName;

    /**
     * String variable that holds Student's last name
     */
    static String lastName;

    /**
     * String variable that holds Student's email address
     */
    static String emailAddress;

    /**
     * date variable that holds Student's last access
     */
    static Date lastAccess;

    /**
     * date variable that holds Student's enrol date
     */
    static Date enrolDate;

    /**
     * boolean variable that holds Student's account status
     */
    static boolean enabled;

    /**
     * char variable that holds Student's account type
     */
    static char type;


    /**
     * a string variable that holds the program code for the Student object
     */
    private static String programCode;

    /**
     * A string variable that will store the program description for the Student object
     */
    private static String programDescription;

    /**
     * A string variable that will hold the year of the program they are currently enrolled in
     */
    private static int year;

    /**
     * A Vector of Mark objects named marks
     */
    private static Vector<Mark> marks;

    /**
     * programCode Accessor
     * @return programCode
     */
    public String getProgramCode() {
        return programCode;
    }

    /**
     * programCode mutator
     * @param programCode name the program's code
     */
    public static void setProgramCode(String programCode) {
        Student.programCode = programCode;
    }

    /**
     * programDescription accessor
     * @return programDescription
     */
    public String getProgramDescription() {
        return programDescription;
    }

    /**
     * programDescription mutator
     * @param programDescription name the program description
     */
    public static void setProgramDescription(String programDescription) {
        Student.programDescription = programDescription;
    }

    /**
     * year accessor
     * @return year
     */
    public  int getYear() {
        return year;
    }

    /**
     * year mutator
     * @param year name the student's year of study
     */
    public static void setYear(int year) {
        Student.year = year;
    }

    /**
     * marks accessor
     * @return marks
     */
    public static Vector<Mark> getMarks() {
        return marks;
    }

    /**
     * marks mutator
     * @param marks record all grades of student
     */
    public static void setMarks(Vector<Mark> marks) {

        Student.marks = marks;
    }

    /**
     * Overloaded student constructor that takes all attributes of User and Student class
     * @param id is the identification of the student
     * @param password is the password of the student
     * @param firstName the first name of the student
     * @param lastName the last name of the student
     * @param emailAddress the email address of the student
     * @param lastAccess the last time the account was accessed
     * @param enrolDate the time of account creation
     * @param enabled the status of the student's account
     * @param type the type of account of the student
     * @param programCode the code of the program
     * @param programDescription the description of the program
     * @param year the year of study of the student
     * @throws InvalidUserDataException An exception that encapsulates all the other exceptions
     */
    public Student(Long id,
                   String password,
                   String firstName,
                   String lastName,
                   String emailAddress,
                   Date lastAccess,
                   Date enrolDate,
                   boolean enabled,
                   char type,
                   String programCode,
                   String programDescription,
                   int year) throws InvalidUserDataException {

        super(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, enabled, type);

        setProgramCode(programCode);
        setProgramDescription(programDescription);
        setYear(year);

    }

    /**
     * Student constructor that takes all attributes of User and Student class
     * @param id is the identification of the student
     * @param password is the password of the student
     * @param firstName the first name of the student
     * @param lastName the last name of the student
     * @param emailAddress the email address of the student
     * @param lastAccess the last time the account was accessed
     * @param enrolDate the time of account creation
     * @param enabled the status of the student's account
     * @param type the type of account of the student
     * @param programCode the code of the program
     * @param programDescription the description of the program
     * @param year the year of study of the student
     * @param marks the marks of the student
     * @throws InvalidUserDataException An exception that encapsulates all the other exceptions
     */
    public Student(Long id,
                   String password,
                   String firstName,
                   String lastName,
                   String emailAddress,
                   Date lastAccess,
                   Date enrolDate,
                   boolean enabled,
                   char type,
                   String programCode,
                   String programDescription,
                   int year,
                   Vector<Mark> marks) throws InvalidUserDataException {

        super(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, enabled, type);

        setProgramCode(programCode);
        setProgramDescription(programDescription);
        setYear(year);
        setMarks(marks);

    }

    /**
     * Overloaded constructor for Student method takes arguments for all attributes for a Student object EXCEPT for a
     * Vector of Mark objects.
     * @param programCode the code of the program
     * @param programDescription the description of the program
     * @param year the year of study of the student
     * @throws InvalidUserDataException An exception that encapsulates all the other exceptions
     */
    public Student( String programCode, String programDescription, int year) throws InvalidUserDataException {

        super();

        Student.programCode = programCode;
        Student.programDescription = programDescription;
        Student.year = year;
        marks = new Vector<>();


    }

    /**
     * Default constructor that sets all defaults for student method
     * @throws InvalidUserDataException An exception that encapsulates all the other exceptions
     */
    public Student() throws InvalidUserDataException {

        super();

        setProgramCode(DEFAULT_PROGRAM_CODE);
        setProgramDescription(DEFAULT_PROGRAM_DESCRIPTION);
        setYear(DEFAULT_YEAR);
        setMarks(new Vector<>());

    }

    @Override
    public String toString() {

        String name = getFirstName() + " " + getLastName();
        long id = getId();
        int year = getYear();
        String description = getProgramDescription();
        String code = getProgramCode();
        String enroll = DF.format(getEnrolDate());
        String yearSuffix = null;

        if (year == 1) {
            yearSuffix = "1st";
        }
        if (year == 2) {
            yearSuffix = "2nd";
        }
        if (year == 3) {
            yearSuffix = "3rd";
        }
        if (year == 4) {
            yearSuffix = "4th";
        }

        return "Student Info for:" +
                "\n\t" + name + "( " + id + " )" +
                "\n\tCurrently in " + yearSuffix + " year of " + "'" + description + "'" + " (" + code + ")" +
                "\n\tEnrolled: " + enroll + "\n";
    }

    /**
     * @return null
     */
    @Override
    public String getTypeForDisplay() {
        return null;
    }

    public static void initialize(Connection c) {
        StudentDA.initialize(c);
    }

    public static void terminate() {
        StudentDA.terminate();
    }

    public static Student retrieve(long studentid) throws NotFoundException, SQLException {
        return StudentDA.retrieve(studentid);
    }

    public void create() throws DuplicateException, ParseException, SQLException {
        StudentDA.create(this);
    }

    public int update() throws NotFoundException, ParseException, SQLException {
       return StudentDA.update(this);
    }

    public void delete() throws NotFoundException, SQLException {

        StudentDA.delete(this);
    }

    public static Student authenticate(long studentid, String password) throws NotFoundException {
        return StudentDA.authenticate(studentid, password);
    }

}
