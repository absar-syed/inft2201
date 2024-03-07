package syeda;

/**
 * 
 */
public interface CollegeInterface {
	
	/**
	 * Constant that holds the college's name 
	 */
	public static final String COLLEGE_NAME = "Durham College";
	
	/**
	 * Constant that holds phone number
	 */
	public static final String PHONE_NUMBER = "(905)721-2000";
	
	/**
	 * Method header that returns a string
	 * @return String 
	 */
	public abstract String getTypeForDisplay();

}
