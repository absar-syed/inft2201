/**
 * StudentDA - this file contains all the data access methods, that actually get/set data to the database.
 * Note: that all the methods are static this is because you do not really create StudentDA objects (does not make sense)
 * @author Absar Syed
 */

package syeda;
import exceptions.*;
import syeda.User;

import java.text.SimpleDateFormat;
import java.util.Objects;
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
	 * Variable that holds an instance of User
	 */
	static User aUser;

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
	public static boolean create(Student aStudent) throws DuplicateException, SQLException, ParseException, InvalidUserDataException {

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

		PreparedStatement psCreateStudent = aConnection.prepareStatement(
		"INSERT INTO Students (userid, programcode, programdescription, year) " +
			"VALUES (?, ?, ?, ?); ");

		psCreateStudent.setLong(1, id);
		psCreateStudent.setString(2, programCode);
		psCreateStudent.setString(3, programDescription);
		psCreateStudent.setInt(4, year);

		aUser = new User(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, enabled, type);

		//see if this User/Student already exists in the database
		try
		{
			retrieve(id);
			throw new DuplicateException("Problem with creating Student record, " + id +" already exists in the system.");
		}
		// if NotFoundException, add User/Student to database
		catch(NotFoundException e)
		{
			// create user and student record transaction
			try
			{
				aConnection.setAutoCommit(false);

				if (aUser.create())
				{
					// execute the SQL update statement
					psCreateStudent.execute();
					inserted = true;
					aConnection.commit();
				}
				else
				{
					aConnection.rollback();
				}

				aConnection.setAutoCommit(true);

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

		// define the SQL query statement using the studentid key
		PreparedStatement sqlSelect = aConnection.prepareStatement(
		"SELECT * FROM Students WHERE userid = ?;");
		sqlSelect.setLong(1, userid);
		ResultSet rs = sqlSelect.executeQuery();

		//process the result set like normal

        // next method sets cursor & returns true if there is data
        boolean gotIt = rs.next();

        if (gotIt) {

            // extract the data
            programCode = String.valueOf(rs.getString("programcode"));
            programDescription = String.valueOf(rs.getString("programdescription"));
            year = rs.getInt("year");

            // create student
            try{
                aStudent = new Student(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate,
                        enabled, type, programCode, programDescription, year);
//					System.out.println(aStudent.toString());

            }catch (InvalidUserDataException e)
            { System.out.println("Error making a record: " + e.getMessage());}

        } else	{// nothing was retrieved

            throw new NotFoundException("Problem retrieving Student record, Student ID " + userid +" does not exist in the system.");

        }

        rs.close();

        return aStudent;
	}


	/**
	 * method that updates a record in the database
	 * @param aStudent an instance of student
	 * @return an integer, number of records updated
	 * @throws NotFoundException when a record is not found
	 * @throws SQLException when an error in the SQL is found
	 */
	public static int update(Student aStudent) throws NotFoundException, SQLException, InvalidUserDataException {

		int records = 0;  //records updated in method

		// retrieve the student argument attribute values
		id = aStudent.getId();
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

		Student ExistingRecord = Student.retrieve(id);
		password = aStudent.getPassword();

		if (!Objects.equals(password, ExistingRecord.getPassword()))
		{
			PasswordHasher Pass = new PasswordHasher(password);
			password = Pass.Hash();
		}

		aUser = new User(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, enabled, type);

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
			records = aUser.update() + psStudentsUpdate.executeUpdate();
		}catch(NotFoundException e)
		{
			throw new NotFoundException("Student with student ID " + id
					+ " cannot be updated, does not exist in the system.");
		}

		return records;
	}


	/**
	 * method that deletes a record from the database
	 * @param aStudent an instance of student
	 * @return an integer, number of records deleted
	 * @throws NotFoundException when a record cannot be found
	 * @throws SQLException when an error in the SQL is found
	 */
	public static int delete(Student aStudent) throws NotFoundException, SQLException, InvalidUserDataException {
		int records = 0;

		// retrieve the data
		id = aStudent.getId();
		firstName = aStudent.getFirstName();

		// create the SQL delete statement
		PreparedStatement psDelete = aConnection.prepareStatement(
		"DELETE FROM Students WHERE userid = ?;");

		aUser = new User(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, enabled, type);

		psDelete.setLong(1, id);

		// see if this student already exists in the database
		try
		{
			retrieve(id);  //used to determine if record exists for the passed student
    		// if found, execute the SQL update statement
			records = psDelete.executeUpdate() + aUser.delete();

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
	public static Student authenticate(long studentid, String password) throws NotFoundException {
		aStudent = null;
		try
		{

			aStudent = retrieve(studentid);

			String RetrievedPassword = aStudent.getPassword();

            PasswordHasher Pass = new PasswordHasher(password);
            String HashedGivenPassword = Pass.Hash();


            if (Objects.equals(RetrievedPassword, HashedGivenPassword))
			{
				return aStudent;
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

