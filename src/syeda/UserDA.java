package syeda;

import exceptions.DuplicateException;
import exceptions.InvalidUserDataException;
import exceptions.NotFoundException;
import syeda.StudentDA;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class UserDA {

    /**
     * an instance of SimpleDateFormat that formats the date
     */
    private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * Variable that hold an instance of a connection
     */
    static Connection aConnection;

    /**
     * Variable that hold an instance of a statement
     */
    static Statement aStatement;

    /**
     * Variable that holds an instance of a User
     */
    static User aUser;

    /**
     * long variable that holds the user id
     */
    static Long id;

    /**
     * String variable that holds Faculty's password
     */
    static String password;

    /**
     * String variable that holds Faculty's first name
     */
    static String firstName;

    /**
     * String variable that holds Faculty's last name
     */
    static String lastName;

    /**
     * String variable that holds Faculty's email address
     */
    static String emailAddress;

    /**
     * date variable that holds Faculty's last access
     */
    static Date lastAccess;

    /**
     * date variable that holds Faculty's enrol date
     */
    static Date enrolDate;

    /**
     * boolean variable that holds Faculty's account status
     */
    static boolean enabled;

    /**
     * char variable that holds Faculty's account type
     */
    static char type;



    /**
     * method that initializes the database connection
     * @param c is an instance of a connection
     */

    public static void initialize(Connection c)
    {
        try {
            aConnection=c;
            aStatement=aConnection.createStatement();
        }
        catch (SQLException e)
        { System.out.println(e.getMessage());	}
    }

    // close the database connection

    /**
     * method that terminates the database connection
     */
    public static void terminate()
    {
        try
        { 	// close the statement
            aStatement.close();
        }
        catch (SQLException e)
        { System.out.println(e.getMessage());	}
    }

    /**
     * method that creates a record and inserts it into the database
     * @param aUser an  instance of Student
     * @return a boolean
     * @throws DuplicateException when a duplicate record is found
     * @throws SQLException when an error in the SQL is found
     * @throws ParseException when string to date parse is unsuccessful
     */
    public static boolean create(User aUser) throws DuplicateException, SQLException, ParseException {

        boolean inserted = false; //insertion success flag

        // retrieve the student attribute values
        id = aUser.getId();
        password = aUser.getPassword();
        firstName = aUser.getFirstName();
        lastName = aUser.getLastName();
        emailAddress = aUser.getEmailAddress();
        lastAccess =  aUser.getLastAccess();
        enrolDate =  aUser.getEnrolDate();
        enabled = aUser.isEnabled();
        type = aUser.getType();

        PasswordHasher pass = new PasswordHasher(password);
        String lastAccessAsStr = SQL_DF.format(lastAccess);
        String enrolDateAsStr = SQL_DF.format(enrolDate);

        Connection c = DatabaseConnect.initialize();
        User.initialize(c);

        // creating the PreparedStatements for the insert statements
        PreparedStatement psCreateUser = aConnection.prepareStatement(
                "INSERT INTO Users (userid, password, firstname, lastname, emailaddress, lastaccess, enroldate, enabled, type) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); ");

        psCreateUser.setLong(1, id);
        psCreateUser.setString(2, pass.Hash());
        psCreateUser.setString(3, firstName);
        psCreateUser.setString(4, lastName);
        psCreateUser.setString(5, emailAddress);
        psCreateUser.setString(6, lastAccessAsStr);
        psCreateUser.setString(7, enrolDateAsStr);
        psCreateUser.setBoolean(8, enabled);
        psCreateUser.setString(9, String.valueOf(type));


        //see if this User/Student already exists in the database
        try
        {
            retrieve(id);
            throw new DuplicateException("Problem with creating User record, " + id +" already exists in the system.");
        }
        // if NotFoundException, add User/Student to database
        catch(NotFoundException e)
        {
            try
            {  // execute the SQL update statement
                boolean Query1 = psCreateUser.execute();

                inserted = true;

            }
            catch (SQLException ee) {

                System.out.println(ee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inserted;
    }

    /**
     * method to retreive a record from the database
     * @param userid the id of the student
     * @return an instance of student
     * @throws NotFoundException when a record cannot be found
     * @throws SQLException when an error in the SQL is found
     */
    public static User retrieve(long userid) throws NotFoundException, SQLException {

        // retrieve Student data
        User aUser = null;

        Connection c = DatabaseConnect.initialize();
        User.initialize(c);

        // define the SQL query statement using the studentid key
        PreparedStatement sqlSelect = aConnection.prepareStatement(
        "SELECT * FROM users WHERE userid = ?;");
        sqlSelect.setLong(1, userid);
        ResultSet rs = sqlSelect.executeQuery();

        //process the result set like normal
        try {

            // next method sets cursor & returns true if there is data
            boolean gotIt = rs.next();

            if (gotIt) {

                // extract the data
                id = Long.valueOf(rs.getString("userid"));
                password = String.valueOf(rs.getString("password"));
                firstName = String.valueOf(rs.getString("firstname"));
                lastName = String.valueOf(rs.getString("lastname"));
                emailAddress = String.valueOf(rs.getString("emailaddress"));
                lastAccess = SQL_DF.parse(rs.getString("lastaccess"));
                enrolDate = SQL_DF.parse(rs.getString("enroldate"));
                enabled = rs.getBoolean("enabled");
                type = rs.getString("type").charAt(0); //=============================================================================

                // create student
                try{
                    aUser = new User(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate,
                            enabled, type);

//					System.out.println(aUser.toString());

                }catch (InvalidUserDataException e)
                { System.out.println("Error making a record: " + e.getMessage());}

            } else	{// nothing was retrieved

                throw new NotFoundException("Problem retrieving User record, User ID " + userid +" does not exist in the system.");

            }

            rs.close();

        }catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }

        return aUser;
    }

    /**
     * method that updates a record in the database
     * @param aUser an instance of student
     * @return an integer, number of records updated
     * @throws NotFoundException when a record is not found
     * @throws SQLException when an error in the SQL is found
     */
    public static int update(User aUser) throws NotFoundException, SQLException {

        int records = 0;  //records updated in method

        // retrieve the student argument attribute values
        id = aUser.getId();
        firstName = aUser.getFirstName();
        lastName = aUser.getLastName();
        emailAddress = aUser.getEmailAddress();
        lastAccess = aUser.getLastAccess();
        enrolDate = aUser.getEnrolDate();
        enabled = aUser.isEnabled();
        type = aUser.getType();
        enabled = aUser.isEnabled();
        String lastAccessAsStr = SQL_DF.format(lastAccess);
        String enrolDateAsStr = SQL_DF.format(enrolDate);


        User ExistingRecord = User.retrieve(id);
        password = aUser.getPassword();

        if (!Objects.equals(password, ExistingRecord.getPassword()))
        {
            PasswordHasher Pass = new PasswordHasher(password);
            password = Pass.Hash();
        }

        Connection c = DatabaseConnect.initialize();
        User.initialize(c);

        PreparedStatement psUsersUpdate = aConnection.prepareStatement(
                "UPDATE Users SET password= ?, firstname= ?, lastname= ?, emailaddress= ?, lastaccess = ?, EnrolDate= ?, Type= ?, " +
                    "Enabled= ? WHERE userid = ?");

        psUsersUpdate.setString(1, password);
        psUsersUpdate.setString(2, firstName);
        psUsersUpdate.setString(3, lastName);
        psUsersUpdate.setString(4, emailAddress);
        psUsersUpdate.setString(5, lastAccessAsStr);
        psUsersUpdate.setString(6, enrolDateAsStr);
        psUsersUpdate.setString(7, String.valueOf(type));
        psUsersUpdate.setBoolean(8, enabled);
        psUsersUpdate.setLong(9,id);





        // see if this student exists in the database
        // NotFoundException is thrown by find method
        try
        {
            retrieve(id);  //determine if there is a student record to be updated
            // if found, execute the SQL update statement
            records = psUsersUpdate.executeUpdate();
        }catch(NotFoundException e)
        {
            throw new NotFoundException("User with User ID " + id
                    + " cannot be updated, does not exist in the system.");
        }

        return records;
    }


    /**
     * method that deletes a record from the database
     * @param aUser an instance of student
     * @return an integer, number of records deleted
     * @throws NotFoundException when a record cannot be found
     * @throws SQLException when an error in the SQL is found
     */
    public static int delete(User aUser) throws NotFoundException, SQLException
    {
        int records = 0;

        // retrieve the data
        id = aUser.getId();
        firstName = aUser.getFirstName();

        Connection c = DatabaseConnect.initialize();
        User.initialize(c);

        PreparedStatement psDelete = aConnection.prepareStatement(
                "DELETE FROM Users WHERE userid = ?;");

        psDelete.setLong(1, id);

        // see if this student already exists in the database
        try
        {
            retrieve(id);  //used to determine if record exists for the passed User
            // if found, execute the SQL update statement
            records = psDelete.executeUpdate();

        }catch(NotFoundException e)
        {
            throw new NotFoundException("User with User ID " + id
                    + " cannot be deleted, does not exist.");
        }catch (SQLException e)
        { System.out.println(e);	}
        return records;
    }


    /**
     * Method to authenticate the User's account exists
     * @param userid is the user's id number
     * @param password is the user's password
     * @return a User object
     * @throws NotFoundException when user is not found
     */
    public static User authenticate(long userid, String password) throws NotFoundException
    {
        aUser = null;
        try
        {

            aUser = retrieve(userid);

            String RetrievedPassword = aUser.getPassword();

            String HashedGivenPassword = new PasswordHasher(password).Hash();

            if (Objects.equals(RetrievedPassword, HashedGivenPassword))
            {
                return aUser;
            }
            else
            {
                throw new NotFoundException("Password does not match");
            }

        }
        catch (NotFoundException | SQLException e)
        {
            throw new NotFoundException(e.getMessage());
        }
    }


}

