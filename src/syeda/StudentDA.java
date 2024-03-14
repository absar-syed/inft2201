/**
 * StudentDA - this file contains all the data access methods, that actually get/set data to the database.
 * Note: that all the methods are static this is because you do not really create StudentDA objects (does not make sense)
 * @author Absar Syed
 */

package syeda;
import exceptions.*;
import org.postgresql.util.PSQLException;

import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.*;
import java.util.Date;
import java.text.ParseException;

public class StudentDA
{
	/**
	 * an instance of SimpleDateFormat that formats the date
	 */
	private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Variable that hold an instance of Student
	 */
	static Student aStudent;

	// declare variables for the database connection
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
	 * String variable that holds Student's program code
	 */
	static String programCode;

	/**
	 * String variable that holds Student's program description
	 */
	static String programDescription;

	/**
	 * int variable that holds Student's year
	 */
	static int year;

	/**
	 * String variable that holds Student's marks
	 */
	static Vector<Mark> marks;

//	 establish the database connection

	/**
	 * method that initializes the database connection
	 * @param c is a instance of a connection
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
	 * @param aStudent an  instance of Student
	 * @return a boolean
	 * @throws DuplicateException when a duplicate record is found
	 * @throws SQLException when an error in the SQL is found
	 * @throws ParseException when string to date parse is unsuccessful
	 */
	public static boolean create(Student aStudent) throws DuplicateException, SQLException, ParseException {

		boolean inserted = false; //insertion success flag

		// retrieve the student attribute values
		id = aStudent.getId();
		password = aStudent.getPassword();
		firstName = aStudent.getFirstName();
		lastName = aStudent.getLastName();
		emailAddress = aStudent.getEmailAddress();
		lastAccess =  aStudent.getLastAccess();
		enrolDate =  aStudent.getEnrolDate();
		enabled = aStudent.isEnabled();
		type = aStudent.getType();
		programCode = aStudent.getProgramCode();
		programDescription = aStudent.getProgramDescription();
		year = aStudent.getYear();

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

		PreparedStatement psCreateStudent = aConnection.prepareStatement(
		"INSERT INTO Students (userid, programcode, programdescription, year) " +
			"VALUES (?, ?, ?, ?); ");

		psCreateStudent.setLong(1, id);
		psCreateStudent.setString(2, programCode);
		psCreateStudent.setString(3, programDescription);
		psCreateStudent.setInt(4, year);



		//see if this User/Student already exists in the database
		try
		{
			retrieve(id);
			throw new DuplicateException("Problem with creating Student record, " + id +" already exists in the system.");
		}
		// if NotFoundException, add User/Student to database
		catch(NotFoundException e)
		{
			try
			{  // execute the SQL update statement
				boolean Query1 = psCreateUser.execute();
				boolean Query2 = psCreateStudent.execute();

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
	public static Student retrieve(long userid) throws NotFoundException, SQLException {

		// retrieve Student data
		Student aStudent = null;

		// define the SQL query statement using the studentid key
		PreparedStatement sqlSelect = aConnection.prepareStatement(
		"SELECT * FROM Students JOIN Users ON Students.userid = Users.userid WHERE students.userid = ?;");
		sqlSelect.setLong(1, userid);
		ResultSet rs = sqlSelect.executeQuery();

		//process the result set like normal
		try {

			// next method sets cursor & returns true if there is data
			boolean gotIt = rs.next();

			if (gotIt) {

				// extract the data
				id = Long.valueOf(rs.getString("userid"));
				programCode = String.valueOf(rs.getString("programcode"));
				programDescription = String.valueOf(rs.getString("programdescription"));
				year = rs.getInt("year");
				password = User.DEFAULT_PASSWORD;
				firstName = String.valueOf(rs.getString("firstname"));
				lastName = String.valueOf(rs.getString("lastname"));
				emailAddress = String.valueOf(rs.getString("emailaddress"));
				lastAccess = SQL_DF.parse(rs.getString("lastaccess"));
				enrolDate = SQL_DF.parse(rs.getString("enroldate"));
				enabled = Boolean.parseBoolean(rs.getString("enabled"));
				type = rs.getString("type").charAt(0); //=============================================================================

				// create student
				try{
					aStudent = new Student(id, password, firstName, lastName, emailAddress, enrolDate, lastAccess,
							enabled, type, programCode, programDescription, year);
//					System.out.println(aStudent.toString());

				}catch (InvalidUserDataException e)
				{ System.out.println("Error making a record: " + e.getMessage());}

			} else	{// nothing was retrieved

				throw new NotFoundException("Problem retrieving Student record, Student ID " + userid +" does not exist in the system.");

			}

			rs.close();

		}catch (PSQLException e) {
			System.out.println(e);
		} catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return aStudent;
	}

	/**
	 * method that updates a record in the database
	 * @param aStudent an instance of student
	 * @return an integer, number of records updated
	 * @throws NotFoundException when a record is not found
	 * @throws ParseException when string to date parse is unsuccessful
	 * @throws SQLException when an error in the SQL is found
	 */
	public static int update(Student aStudent) throws NotFoundException, ParseException, SQLException {
		int records = 0;  //records updated in method

		// retrieve the student argument attribute values
		id = aStudent.getId();
		programCode = aStudent.getProgramCode();
		programDescription = aStudent.getProgramDescription();
		year = aStudent.getYear();
		password = aStudent.getPassword();
		firstName = aStudent.getFirstName();
		lastName = aStudent.getLastName();
		emailAddress = aStudent.getEmailAddress();
		lastAccess = aStudent.getLastAccess();
		enrolDate = aStudent.getEnrolDate();
		enabled = aStudent.isEnabled();
		type = aStudent.getType();
		programCode = aStudent.getProgramCode();
		programDescription = aStudent.getProgramDescription();
		year = aStudent.getYear();

		PasswordHasher pass = new PasswordHasher(password);
		String lastAccessAsStr = SQL_DF.format(lastAccess);
		String enrolDateAsStr = SQL_DF.format(enrolDate);

		PreparedStatement psUsersUpdate = aConnection.prepareStatement(
		"UPDATE Users SET password = ?, firstname= ?, lastname= ?, emailaddress= ?, lastaccess = ?, EnrolDate= ?, Type= ?," +
			" Enabled= ? WHERE userid IN (SELECT userid FROM Students WHERE userid = ?); ");

		psUsersUpdate.setString(1, pass.Hash());
		psUsersUpdate.setString(2, firstName);
		psUsersUpdate.setString(3, lastName);
		psUsersUpdate.setString(4, emailAddress);
		psUsersUpdate.setString(5, lastAccessAsStr);
		psUsersUpdate.setString(6, enrolDateAsStr);
		psUsersUpdate.setString(7, String.valueOf(type));
		psUsersUpdate.setBoolean(8, enabled);
		psUsersUpdate.setLong(9,id);

		PreparedStatement psStudentsUpdate = aConnection.prepareStatement(
		"UPDATE Students SET programcode = ?, programdescription = ?, year = ? " +
			"WHERE userid = ?");

		psStudentsUpdate.setString(1, programCode);
		psStudentsUpdate.setString(2, programDescription);
		psStudentsUpdate.setInt(3, year);
		psStudentsUpdate.setLong(4, id);




		// see if this student exists in the database
		// NotFoundException is thrown by find method
		try
		{
			retrieve(id);  //determine if there is a student record to be updated
			// if found, execute the SQL update statement
			records = psUsersUpdate.executeUpdate() + psStudentsUpdate.executeUpdate();
		}catch(NotFoundException e)
		{
			throw new NotFoundException("Student with student ID " + id
					+ " cannot be updated, does not exist in the system.");
		}catch (SQLException e)
		{ System.out.println(e);}
		return records;
	}

	/**
	 * method that deletes a record from the database
	 * @param aStudent an instance of student
	 * @return an integer, number of records deleted
	 * @throws NotFoundException when a record cannot be found
	 * @throws SQLException when an error in the SQL is found
	 */

	public static int delete(Student aStudent) throws NotFoundException, SQLException
	{
		int records = 0;

		// retrieve the data
		id = aStudent.getId();
		firstName = aStudent.getFirstName();

		// create the SQL delete statement
		PreparedStatement psDelete1 = aConnection.prepareStatement(
		"DELETE FROM Students WHERE userid = ?;");


		PreparedStatement psDelete2 = aConnection.prepareStatement(
		"DELETE FROM Users WHERE userid = ?;");


		psDelete1.setLong(1, id);
		psDelete2.setLong(1, id);

		// see if this student already exists in the database
		try
		{
			retrieve(id);  //used to determine if record exists for the passed student
    		// if found, execute the SQL update statement
			records = psDelete1.executeUpdate() + psDelete2.executeUpdate();

		}catch(NotFoundException e)
		{
			throw new NotFoundException("Student with Student ID " + id
					+ " cannot be deleted, does not exist.");
		}catch (SQLException e)
			{ System.out.println(e);	}
		return records;
	}

	/**
	 * Method to authenticate the student's account exists
	 * @param studentid is the student's id number
	 * @param password is the student's password
	 * @return a Student object
	 */
	public static Student authenticate(long studentid, String password)
	{
		aStudent = null;
//		try
//		{
//
//		}
//		catch ()
//		{
//
//		}
		return aStudent;
	}
}

