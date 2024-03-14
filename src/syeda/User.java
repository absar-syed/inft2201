package syeda;

import exceptions.InvalidIdException;
import exceptions.InvalidNameException;
import exceptions.InvalidPasswordException;
import exceptions.InvalidUserDataException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static jdk.nashorn.internal.runtime.JSType.isNumber;

/**
 * Default User
 * @author Absar Syed
 * @version 1.0
 */

public abstract class User implements CollegeInterface {

	/**
	 * Default user ID
	 */
	public static final Long DEFAULT_ID = 100123456L;
	
	/**
	 * Default user's password
	 */
	public static final String DEFAULT_PASSWORD = "password";
	
	/**
	 * Default user's minimum password length
	 */
	public static final Byte MINIMUM_PASSWORD_LENGTH = 8;
	
	/**
	 * Default user's maximum password length
	 */
	public static final Byte MAXIMUM_PASSWORD_LENGTH = 40;
	
	/**
	 * Default user's first name
	 */
	public static final String DEFAULT_FIRST_NAME = "John";
	
	/**
	 * Default user's last name
	 */
	public static final	String DEFAULT_LAST_NAME = "Doe";
	
	/**
	 * Default user's email address
	 */
	public static final String DEFAULT_EMAIL_ADDRESS = "john.doe@dcmail.com";
	
	/**
	 * Default user's enabled status
	 */
	public static final Boolean DEFAULT_ENABLED_STATUS = true;
	
	/**
	 * Default User's type set to 'Student'
	 */
	public static final char DEFAULT_TYPE = 's';
	
	/**
	 * Sets the ID's length to 9
	 */
	public static final byte ID_NUMBER_LENGTH = 9;
	
	/**
	 * Sets the date format the same for everything
	 */
	public static final DateFormat DF = new SimpleDateFormat("d-MMM-yyyy");
	
	/**
	 * user's banner id
	 */
	private static Long id;
	
	/**
	 * user's password
	 */
	private String password;
	
	/**
	 * User's first name
	 */
	private String firstName;
	
	/**
	 * User's last name 
	 */
	private String lastName;
	
	/**
	 * User's email address
	 */
	private String emailAddress;
	
	/**
	 * User's date of latest log in
	 */
	private Date lastAccess;
	
	/**
	 * User's date of enrollment 
	 */
	private Date enrolDate;
	
	/**
	 * User's account is enabled or not
	 */
	private boolean enabled;

	/**
	 * User's account type; either student or faculty 
	 */
	private char type;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id number to set
	 * @throws InvalidIdException An exception that encapsulates all the other exceptions
	 */
	public void setId(Long id) throws InvalidIdException {

		if (verifyID(id)){

			User.id = id;
		}
		else {
			throw new InvalidIdException("Id is not the correct length");
		}

	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 * @throws InvalidPasswordException An exception that is thrown when incorrect password is set
	 */
	public void setPassword(String password) throws InvalidPasswordException {

		if (password.length() > MAXIMUM_PASSWORD_LENGTH) {

			throw new InvalidPasswordException("Password is too long");

		}

		if ( password.length() < MINIMUM_PASSWORD_LENGTH ) {

			throw new InvalidPasswordException("Password is too short");

		}

		this.password = password;

	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 * @throws InvalidNameException An exception that is thrown when an invalid name is set
	 */
	public void setFirstName(String firstName) throws InvalidNameException {

		if (Objects.equals(firstName, "")) {

			throw new InvalidNameException("First name must not be empty");

		}
		else {
			this.firstName = firstName;
		}

		if (isNumber(firstName)) {

			throw new InvalidNameException("First name cannot be a number");

		}
		else {
			this.firstName = firstName;
		}

	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 * @throws InvalidNameException an exception that is thrown when an invalid name is set
	 */
	public void setLastName(String lastName) throws InvalidNameException {

		if ("".equals(lastName)) {

			throw new InvalidNameException("Invalid: Last name must not be empty");

		}

		if (isNumber(lastName)) {

			throw new InvalidNameException("Invalid: Last name cannot be a number");

		}

		this.lastName = lastName;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the lastAccess
	 */
	public Date getLastAccess() {
		return lastAccess;
	}

	/**
	 * @param lastAccess the lastAccess to set
	 */
	public void setLastAccess(Date lastAccess) {
		this.lastAccess = (lastAccess);
	}

	/**
	 * @return the enrolDate
	 */
	public Date getEnrolDate() {
		return enrolDate;
	}

	/**
	 * @param enrolDate the enrolDate to set
	 */
	public void setEnrolDate(Date enrolDate) {
		this.enrolDate = (enrolDate);
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the type
	 */
	public char getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(char type) {
		this.type = type;
	}


	/**
	 * @return the idNumberLength
	 */
	public static Byte getIdNumberLength() {
		return ID_NUMBER_LENGTH;
	}

	/**
	 * @return the df
	 */
	public static DateFormat getDf() {
		return DF;
	}

	/**
	 * User constructor that sets all user attributes
	 * @param id the identification number
	 * @param password user's password
	 * @param firstName user's first name
	 * @param lastName user's last name
	 * @param emailAddress user's email address
	 * @param lastAccess user's last access
	 * @param enrolDate user's enrollment date
	 * @param enabled user's account status
	 * @param type user's account type
	 * @throws InvalidUserDataException An exception that encapsulates all the other exceptions
	 */
	public User(Long id, String password, String firstName, String lastName, String emailAddress, Date lastAccess,
			Date enrolDate, boolean enabled, char type) throws InvalidUserDataException {

		try {
			setId(id);
			setPassword(password);
			setFirstName(firstName);
			setLastName(lastName);
			setEmailAddress(emailAddress);
			setLastAccess(lastAccess);
			setEnrolDate(enrolDate);
			setEnabled(enabled);
			setType(type);
		}
		catch (InvalidIdException | InvalidPasswordException | InvalidNameException ex) {

			throw new InvalidUserDataException("Error, Here's what happened: " + ex.getMessage());

		}

    }

	/**
	 * Default constructor, with no parameters given by user it will set all to default values and lastaccess and
	 * enroldate are set to today's date
	 * @throws InvalidUserDataException An exception that encapsulates all the other exceptions
	 */
	public User() throws InvalidUserDataException {

		try {
			setId(DEFAULT_ID);
			setPassword(DEFAULT_PASSWORD);
			setFirstName(DEFAULT_FIRST_NAME);
			setLastName(DEFAULT_LAST_NAME);
			setEmailAddress(DEFAULT_EMAIL_ADDRESS);
			setLastAccess(new Date());
			setEnrolDate(new Date());
			setEnabled(DEFAULT_ENABLED_STATUS);
			setType(DEFAULT_TYPE);
		}
		catch (InvalidIdException | InvalidPasswordException | InvalidNameException ex) {
			throw new InvalidUserDataException("Error, Here's what happened: " + ex.getMessage());
		}

	
	}

	/**
	 *Method that overrides System.out.println(toString()) to return user info as a string
	 */
	@Override
	public String toString() {

        return "User Info for: " + id +
							"\n \tName: " + firstName + " " + lastName + " (" + emailAddress + ")" +
							"\n \tCreated on: " + DF.format(enrolDate) +
							"\n \tLast access: " + DF.format(lastAccess);
	}
	
	
	/**
	 * Method that displays the string from toString() method
	 */
	public void dump() {

		System.out.println(toString());

	}
	
	/**
	 * Method to verify the id length to be 9
	 * @param id insert id number 
	 * @return true if id length is 9 and false if otherwise
	 */
	public static boolean verifyID(Long id) {
		
		int idLength = Long.toString(id).length();  //Long.bitCount(getId()); //String.valueOf(id).length();

        return idLength == ID_NUMBER_LENGTH;
    }
}
