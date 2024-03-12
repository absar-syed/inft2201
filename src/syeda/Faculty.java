package syeda;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 * 
 */
public class Faculty extends User {
	
	/**
	 * Constant set to SET for default school code 
	 */
	public static final String DEFAULT_SCHOOL_CODE = "SET";
	
	/**
	 * Constant set to School of Engineering and Technology for default school description
	 */
	public static final String DEFAULT_SCHOOL_DESCRIPTION = "School of Engineering & Technology";
	
	/**
	 * Constant set to H-140 for default office
	 */
	public static final String DEFAULT_OFFICE = "H-140";
	
	/**
	 * Constant set to  1234 for default phone extension
	 */
	public static final int DEFAULT_PHONE_EXTENSION = 1234;

	/**
	 * Initialized variable that will store a code for the school the faculty is associated with
	 */
	private String schoolCode;
	
	/**
	 * Initialized variable that will store the name of the school
	 */
	private String schoolDescription;
	
	/**
	 * Initialized variable that will store the office number of the school
	 */
	private String office;
	
	/**
	 * Initialized variable that will store the phone number extension code of the school's office
	 */
	private int extension;

	/**
	 * @return the schoolCode
	 */
	public String getSchoolCode() {
		return schoolCode;
	}

	/**
	 * @param schoolCode the schoolCode to set
	 */
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	/**
	 * @return the schoolDescription
	 */
	public String getSchoolDescription() {
		return schoolDescription;
	}

	/**
	 * @param schoolDescription the schoolDescription to set
	 */
	public void setSchoolDescription(String schoolDescription) {
		this.schoolDescription = schoolDescription;
	}

	/**
	 * @return the office
	 */
	public String getOffice() {
		return office;
	}

	/**
	 * @param office the office to set
	 */
	public void setOffice(String office) {

		this.office = office;
	}

	/**
	 * @return the extension
	 */
	public int getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(int extension) {
		this.extension = extension;
	}
	
	/**
	 * Faculty constructor that takes in all parameters in and sets them accordingly
	 * @param id the identification number
	 * @param password user's password
	 * @param firstName user's first name
	 * @param lastName user's last name
	 * @param emailAddress user's email address
	 * @param lastAccess user's last access
	 * @param enrolDate user's enrollment date
	 * @param enabled user's account status
	 * @param type user's account type
	 * @param schoolCode user's school code
	 * @param schoolDescription user's school description
	 * @param office user's office number
	 * @param extension user's phone extension number
	 * @throws InvalidUserDataException an exception that encapsulates all the other exceptions
	 */
	public Faculty(Long id, String password, String firstName, String lastName, String emailAddress, Date lastAccess,
			Date enrolDate, boolean enabled, char type, String schoolCode, String schoolDescription, String office,
			int extension) throws InvalidUserDataException {
		
		super(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, enabled, type);

        setSchoolCode(schoolCode);
        setSchoolDescription(schoolDescription);
        setOffice(office);
        setExtension(extension);

    }
	
	
	/**
	 * Default Faculty constructor, calls the parent default constructor and sets all values to its defaults
	 * @throws InvalidUserDataException an exception that encapsulates all the other exceptions
	 */
	public Faculty() throws InvalidUserDataException {
		
		super();
		
		setSchoolCode(DEFAULT_SCHOOL_CODE);
		setSchoolDescription(DEFAULT_SCHOOL_DESCRIPTION);
		setOffice(DEFAULT_OFFICE);
		setExtension(DEFAULT_PHONE_EXTENSION);
		
	}
	
	/**
	 * Method that return the string "Faculty"
	 */
	public String getTypeForDisplay() {
		
		return "Faculty";
		
	}
	
	/**
	 *Method that return string of faculty information
	 */
	@Override
	public String toString() {
		
		String facultyInfo = super.toString().replace("User", getTypeForDisplay());

        return facultyInfo +
				"\n\t" + getSchoolDescription() + " (" + getSchoolCode() + ")" +
				"\n\tOffice: " + getOffice() +
				"\n\t(905)721-2000 x" + getExtension();
	}

	public static void initialize(Connection c) {
		FacultyDA.initialize(c);
	}

	public static void terminate() {
		FacultyDA.terminate();
	}

	public static Faculty retrieve(long studentid) throws NotFoundException, SQLException {
		return FacultyDA.retrieve(studentid);
	}

	public void create() throws DuplicateException, ParseException, SQLException {
		FacultyDA.create(this);
	}

	public int update() throws NotFoundException, SQLException {
		return FacultyDA.update(this);
	}

	public void delete() throws NotFoundException, SQLException {

		FacultyDA.delete(this);
	}


}
