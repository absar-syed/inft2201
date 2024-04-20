/**
 * FacultyDA - this file contains all the data access methods, that actually get/set data to the database.
 * Note: that all the methods are static this is because you do not really create FacultyDA objects (does not make sense)
 * @author Absar Syed
 */

package syeda;

import exceptions.DuplicateException;
import exceptions.InvalidUserDataException;
import exceptions.NotFoundException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class FacultyDA
{
	/**
	 * an instance of SimpleDateFormat that formats the date
	 */
	private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Variable that holds an instance of User
	 */
	static User aUser;

	/**
	 * Variable that hold an instance of Faculty
	 */
	static Faculty aFaculty;

	// declare variables for the database connection
	/**
	 * Variable that hold an instance of a connection
	 */
	static Connection aConnection;

	/**
	 * Variable that hold an instance of a statement
	 */
	static Statement aStatement;

	// declare static variables for all Faculty instance attribute values
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
	 * Initialized variable that will store a code for the school the faculty is associated with
	 */
	static String schoolCode;

	/**
	 * Initialized variable that will store the name of the school
	 */
	static String schoolDescription;

	/**
	 * Initialized variable that will store the office number of the school
	 */
	static String office;

	/**
	 * Initialized variable that will store the phone number extension code of the school's office
	 */
	static int extension;


//	 establish the database connection

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
	 * @param aFaculty an  instance of Faculty
	 * @return a boolean
	 * @throws DuplicateException when a duplicate record is found
	 * @throws SQLException when an error in the SQL is found
	 * @throws ParseException when string to date parse is unsuccessful
	 */
	public static boolean create(Faculty aFaculty) throws DuplicateException, SQLException, ParseException {

		boolean inserted = false; //insertion success flag

		// retrieve the Faculty attribute values
		id = aFaculty.getId();
		password = aFaculty.getPassword();
		firstName = aFaculty.getFirstName();
		lastName = aFaculty.getLastName();
		emailAddress = aFaculty.getEmailAddress();
		lastAccess =  aFaculty.getLastAccess();
		enrolDate =  aFaculty.getEnrolDate();
		enabled = aFaculty.isEnabled();
		type = aFaculty.getType();
		schoolCode = aFaculty.getSchoolCode();
		schoolDescription = aFaculty.getSchoolDescription();
		office = aFaculty.getOffice();
		extension = aFaculty.getExtension();

		PasswordHasher pass = new PasswordHasher(password);
		String lastAccessAsStr = SQL_DF.format(lastAccess);
		String enrolDateAsStr = SQL_DF.format(enrolDate);

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

		PreparedStatement psCreateFaculty = aConnection.prepareStatement(
		"INSERT INTO Faculty (userid, schoolcode, schooldescription, office, extension) " +
			"VALUES (?, ?, ?, ?, ?); ");

		psCreateFaculty.setLong(1, id);
		psCreateFaculty.setString(2, schoolCode);
		psCreateFaculty.setString(3, schoolDescription);
		psCreateFaculty.setString(4, office);
		psCreateFaculty.setInt(5, extension);



		//see if this User/Faculty already exists in the database
		try
		{
			retrieve(id);
			throw new DuplicateException("Problem with creating Faculty record, " + id +" already exists in the system.");
		}
		// if NotFoundException, add User/Faculty to database
		catch(NotFoundException e)
		{
			try
			{  // execute the SQL update statement
				boolean Query1 = psCreateUser.execute();
				boolean Query2 = psCreateFaculty.execute();

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
	 * method to retrieve a record from the database
	 * @param userid the id of the Faculty
	 * @return an instance of Faculty
	 * @throws NotFoundException when a record cannot be found
	 * @throws SQLException when an error in the SQL is found
	 */
	public static Faculty retrieve(long userid) throws NotFoundException, SQLException {

		// retrieve Faculty data
		Faculty aFaculty = null;

		aUser = User.retrieve(userid);

		id = aUser.getId();
		password = aUser.getPassword();
		firstName = aUser.getFirstName();
		lastName = aUser.getLastName();
		emailAddress = aUser.getEmailAddress();
		lastAccess = aUser.getLastAccess();
		enrolDate = aUser.getEnrolDate();
		enabled = aUser.isEnabled();
		type = aUser.getType();


		// define the SQL query statement using the userid key
		PreparedStatement sqlSelect = aConnection.prepareStatement(
		"SELECT * FROM Faculty WHERE userid = ?;");
		sqlSelect.setLong(1, userid);
		ResultSet rs = sqlSelect.executeQuery();

		//process the result set like normal

		// next method sets cursor & returns true if there is data
		boolean gotIt = rs.next();

		if (gotIt)
		{

			// extract the data
			schoolCode = rs.getString("schoolcode");
			schoolDescription = rs.getString("schooldescription");
			office = rs.getString("office");
			extension = rs.getInt("extension");

			// create faculty
			try{
				aFaculty = new Faculty(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate,
						enabled, type, schoolCode, schoolDescription, office, extension);
//					System.out.println(aFaculty.toString());

			}catch (InvalidUserDataException e)
			{ System.out.println("Error making a record: " + e.getMessage());}

		}
		else	// nothing was retrieved
		{
			throw new NotFoundException("Problem retrieving Faculty record, Faculty ID " + userid +" does not exist in the system.");
		}

		rs.close();

        return aFaculty;
	}

	/**
	 * method that updates a record in the database
	 * @param aFaculty an instance of Faculty
	 * @return an integer, number of records updated
	 * @throws NotFoundException when a record is not found
     */
	public static int update(Faculty aFaculty) throws NotFoundException, SQLException, InvalidUserDataException {

		int records = 0;  //records updated in method

		// retrieve the Faculty argument attribute values
		id = aFaculty.getId();
		password = aFaculty.getPassword();
		firstName = aFaculty.getFirstName();
		lastName = aFaculty.getLastName();
		emailAddress = aFaculty.getEmailAddress();
		lastAccess = aFaculty.getLastAccess();
		enrolDate = aFaculty.getEnrolDate();
		enabled = aFaculty.isEnabled();
		type = aFaculty.getType();

		password = aFaculty.getPassword();
		String ExistingPassword = User.retrieve(id).getPassword();

		if (!Objects.equals(password, ExistingPassword))
		{
			PasswordHasher Pass = new PasswordHasher(password);
			password = Pass.Hash();
		}

		User aUser = new User(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, enabled, type);

		PreparedStatement psFacultyUpdate = aConnection.prepareStatement(
		"UPDATE Faculty SET schoolcode = ?, schooldescription = ?, office = ?, extension = ? WHERE userid = ?;");

		psFacultyUpdate.setString(1, schoolCode);
		psFacultyUpdate.setString(2, schoolDescription);
		psFacultyUpdate.setString(3, office);
		psFacultyUpdate.setInt(4, extension);
		psFacultyUpdate.setLong(5, id);


		// see if this Faculty exists in the database
		// NotFoundException is thrown by find method
		try
		{

			retrieve(id);  //determine if there is a Faculty record to be updated
			// if found, execute the SQL update statement
			records = aUser.update() + psFacultyUpdate.executeUpdate();

		}
		catch(NotFoundException e)
		{
			throw new NotFoundException("Faculty with Faculty ID " + id
					+ " cannot be updated, does not exist in the system.");
		}

		return records;
	}

	/**
	 * method that deletes a record from the database
	 * @param aFaculty an instance of Faculty
	 * @return an integer, number of records deleted
	 * @throws NotFoundException when a record cannot be found
	 * @throws SQLException when an error in the SQL is found
	 */
	public static int delete(Faculty aFaculty) throws NotFoundException, SQLException
	{
		int records = 0;

		// retrieve the data
		id = aFaculty.getId();
		firstName = aFaculty.getFirstName();

		// create the SQL delete statement
		PreparedStatement psDelete = aConnection.prepareStatement(
		"DELETE FROM faculty WHERE userid = ?;");



		psDelete.setLong(1, id);
		// see if this Faculty already exists in the database
		try
		{
			retrieve(id);  //used to determine if record exists for the passed Faculty
    		// if found, execute the SQL update statement
			records = psDelete.executeUpdate();

		}catch(NotFoundException e)
		{
			throw new NotFoundException("Faculty with Faculty ID " + id
					+ " cannot be deleted, does not exist.");
		}catch (SQLException e)
			{ System.out.println(e);	}
		return records;
	}

	/**
	 * Method to authenticate the faculty's account exists
	 * @param facultyid is the faculty's id number
	 * @param password is the faculty's password
	 * @return a faculty object
	 */
	public static Faculty authenticate(long facultyid, String password) throws NotFoundException
	{
		aFaculty = null;
		try
		{

			aFaculty = retrieve(facultyid);

			String RetrievedPassword = aFaculty.getPassword();

			PasswordHasher Pass = new PasswordHasher(password);
			String HashedGivenPassword = Pass.Hash();


			if (Objects.equals(RetrievedPassword, HashedGivenPassword))
			{
				return aFaculty;
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

