//package syeda;
//
//import exceptions.DuplicateException;
//import exceptions.InvalidUserDataException;
//import exceptions.NotFoundException;
//
//import java.sql.*;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class UserDA {
//
//    /**
//     * an instance of SimpleDateFormat that formats the date
//     */
//    private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");
//
//
//    /**
//     * Variable that hold an instance of a connection
//     */
//    static Connection aConnection;
//
//    /**
//     * Variable that hold an instance of a statement
//     */
//    static Statement aStatement;
//
//    /**
//     * Variable that holds an instance of a User
//     */
//    static User aUser;
//
//    /**
//     * long variable that holds the user id
//     */
//    static Long id;
//
//    /**
//     * String variable that holds Faculty's password
//     */
//    static String password;
//
//    /**
//     * String variable that holds Faculty's first name
//     */
//    static String firstName;
//
//    /**
//     * String variable that holds Faculty's last name
//     */
//    static String lastName;
//
//    /**
//     * String variable that holds Faculty's email address
//     */
//    static String emailAddress;
//
//    /**
//     * date variable that holds Faculty's last access
//     */
//    static Date lastAccess;
//
//    /**
//     * date variable that holds Faculty's enrol date
//     */
//    static Date enrolDate;
//
//    /**
//     * boolean variable that holds Faculty's account status
//     */
//    static boolean enabled;
//
//    /**
//     * char variable that holds Faculty's account type
//     */
//    static char type;
//
//
//
//    /**
//     * method that initializes the database connection
//     * @param c is an instance of a connection
//     */
//
//    public static void initialize(Connection c)
//    {
//        try {
//            aConnection=c;
//            aStatement=aConnection.createStatement();
//        }
//        catch (SQLException e)
//        { System.out.println(e.getMessage());	}
//    }
//
//    // close the database connection
//
//    /**
//     * method that terminates the database connection
//     */
//    public static void terminate()
//    {
//        try
//        { 	// close the statement
//            aStatement.close();
//        }
//        catch (SQLException e)
//        { System.out.println(e.getMessage());	}
//    }
//
//    /**
//     * method that creates a record and inserts it into the database
//     * @param aUser an  instance of Student
//     * @return a boolean
//     * @throws DuplicateException when a duplicate record is found
//     * @throws SQLException when an error in the SQL is found
//     * @throws ParseException when string to date parse is unsuccessful
//     */
//    public static boolean create(User aUser) throws DuplicateException, SQLException, ParseException {
//
//        boolean inserted = false; //insertion success flag
//
//        // retrieve the student attribute values
//        id = aUser.getId();
//        password = aUser.getPassword();
//        firstName = aUser.getFirstName();
//        lastName = aUser.getLastName();
//        emailAddress = aUser.getEmailAddress();
//        lastAccess =  aUser.getLastAccess();
//        enrolDate =  aUser.getEnrolDate();
//        enabled = aUser.isEnabled();
//        type = aUser.getType();
//
//        PasswordHasher pass = new PasswordHasher(password);
//        String lastAccessAsStr = SQL_DF.format(lastAccess);
//        String enrolDateAsStr = SQL_DF.format(enrolDate);
//
//        // creating the PreparedStatements for the insert statements
//        PreparedStatement psCreateUser = aConnection.prepareStatement(
//                "INSERT INTO Users (userid, password, firstname, lastname, emailaddress, lastaccess, enroldate, enabled, type) " +
//                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); ");
//
//        psCreateUser.setLong(1, id);
//        psCreateUser.setString(2, pass.Hash());
//        psCreateUser.setString(3, firstName);
//        psCreateUser.setString(4, lastName);
//        psCreateUser.setString(5, emailAddress);
//        psCreateUser.setString(6, lastAccessAsStr);
//        psCreateUser.setString(7, enrolDateAsStr);
//        psCreateUser.setBoolean(8, enabled);
//        psCreateUser.setString(9, String.valueOf(type));
//
//
//        //see if this User/Student already exists in the database
//        try
//        {
//            retrieve(id);
//            throw new DuplicateException("Problem with creating Student record, " + id +" already exists in the system.");
//        }
//        // if NotFoundException, add User/Student to database
//        catch(NotFoundException e)
//        {
//            try
//            {  // execute the SQL update statement
//                boolean Query1 = psCreateUser.execute();
//
//                inserted = true;
//
//            }
//            catch (SQLException ee) {
//
//                System.out.println(ee);
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return inserted;
//    }
//
//    /**
//     * method to retreive a record from the database
//     * @param userid the id of the student
//     * @return an instance of student
//     * @throws NotFoundException when a record cannot be found
//     * @throws SQLException when an error in the SQL is found
//     */
//    public static Student retrieve(long userid) throws NotFoundException, SQLException {
//
//        // retrieve Student data
//        Student aStudent = null;
//
//        // define the SQL query statement using the studentid key
//        PreparedStatement sqlSelect = aConnection.prepareStatement(
//        "SELECT * FROM Students JOIN Users ON Students.userid = Users.userid WHERE students.userid = ?;");
//        sqlSelect.setLong(1, userid);
//        ResultSet rs = sqlSelect.executeQuery();
//
//        //process the result set like normal
//        try {
//
//            // next method sets cursor & returns true if there is data
//            boolean gotIt = rs.next();
//
//            if (gotIt) {
//
//                // extract the data
//                id = Long.valueOf(rs.getString("userid"));
//                programCode = String.valueOf(rs.getString("programcode"));
//                programDescription = String.valueOf(rs.getString("programdescription"));
//                year = rs.getInt("year");
//                password = String.valueOf(rs.getString("password"));
//                firstName = String.valueOf(rs.getString("firstname"));
//                lastName = String.valueOf(rs.getString("lastname"));
//                emailAddress = String.valueOf(rs.getString("emailaddress"));
//                lastAccess = SQL_DF.parse(rs.getString("lastaccess"));
//                enrolDate = SQL_DF.parse(rs.getString("enroldate"));
//                enabled = rs.getBoolean("enabled");
//                type = rs.getString("type").charAt(0); //=============================================================================
//
//                // create student
//                try{
//                    aStudent = new Student(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate,
//                            enabled, type, programCode, programDescription, year);
////					System.out.println(aStudent.toString());
//
//                }catch (InvalidUserDataException e)
//                { System.out.println("Error making a record: " + e.getMessage());}
//
//            } else	{// nothing was retrieved
//
//                throw new NotFoundException("Problem retrieving Student record, Student ID " + userid +" does not exist in the system.");
//
//            }
//
//            rs.close();
//
//        }catch (ParseException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//
//        return aStudent;
//    }
//
//
//}
//
