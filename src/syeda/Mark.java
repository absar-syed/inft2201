package syeda;

import java.text.DecimalFormat;

public class Mark {

	/**
	 * Constant sets the minimum gpa to 0
	 */
	public static final float MINIMUM_GPA = 0.0f;
	
	/**
	 * Constant that sets maximum gpa to 5
	 */
	public static final float MAXIMUM_GPA = 5.0f;
	
	/**
	 * Constant that sets the gpa decimal format to 0.00
	 */
	public static final DecimalFormat GPA = new DecimalFormat("0.0");
	
	/**
	 * a variable that holds the course code
	 */
	private static String courseCode;
	
	/**
	 * a variable that will contain the course code for marks
	 */
	private static String courseName;
	
	/**
	 * a variable that will contain the student's final mark
	 */
	private static int result;
	
	/**
	 * a variable that will contain the course's gpa weighting
	 */
	private static float gpaWeighting;

	/**
	 * @return the courseCode
	 */
	public static String getCourseCode() {
		return courseCode;
	}

	/**
	 * @param courseCode the courseCode to set
	 */
	public static void setCourseCode(String courseCode) {
		Mark.courseCode = courseCode;
	}

	/**
	 * @return the courseName
	 */
	public static String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName the courseName to set
	 */
	public static void setCourseName(String courseName) {
		Mark.courseName = courseName;
	}

	/**
	 * @return the result
	 */
	public static int getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public static void setResult(int result) {
		Mark.result = result;
	}

	/**
	 * @return the gpaWeighting
	 */
	public static float getGpaWeighting() {
		return gpaWeighting;
	}

	/**
	 * @param gpaWeighting the gpaWeighting to set
	 */
	public static void setGpaWeighting(float gpaWeighting) {
		Mark.gpaWeighting = gpaWeighting;
	}

	/**
	 * A constructor method that sets the attributes: courseCode, courseName, result, gpaWeighting
	 * @param courseCode is the code of the course
	 * @param courseName is the name of the course
	 * @param result is the grade received
	 * @param gpaWeighting is the weight of the gpa
	 */
	public Mark(String courseCode, String courseName, int result, float gpaWeighting) {
		
		setCourseCode(courseCode);
		setCourseName(courseName);
		setResult(result);
		setGpaWeighting(gpaWeighting);
		
	}

	/**
	 * toString override to return the mark info as a string
	 * @return mark info
	 */
	@Override
	public String toString() {

		String code = getCourseCode();
		String name = String.format("%-35s", getCourseName());
		int mark = getResult();
		float weight = getGpaWeighting();
		String weightFormatted = GPA.format(weight);

        return code + "\t" + name + "\t" + mark + "\t" + weightFormatted;
	}
}
