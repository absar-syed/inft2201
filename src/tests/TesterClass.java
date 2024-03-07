/**
 * 
 */
package tests;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;


/**
 * 
 */
public class TesterClass {

	/**
	 * @param args just the main for a lesson demo
	 */
	public static void main(String[] args) {
		
//		dateTest();
		
//		numberFormattingTest();
		
		lastStringTest();
		
		
	}
	
	public static void lastStringTest() {
		
		String firstName = "Absar";
		String id = "100706764";
		
			
		
		String template = "User info for: {id} \n\t "
							+ "Name: {firstName}";
		
		String output = template
				.replace("{id}", id)
				.replace("{firstName}", firstName);
		
		System.out.println(output);
	}
	
	public static void numberFormattingTest() {
		
//		NumberFormat nfmt = NumberFormat.getCurrencyInstance();
//		double money = 99.99;
//		System.out.println(nfmt.format(money));
		
		DecimalFormat dfmt = new DecimalFormat("#0.00");
		double money = 123456.345689;
		String moneyString = dfmt.format(money);
		System.out.println(moneyString);
	}
	
	
//	public class DateFormatTest {
//		public static void main(String[] args) {
//		Date today = new Date();
//		Locale[] locales = {"US", "UK", "GERMANY", "FRANCE"};
//		int[] styles = {FULL, LONG, MEDIUM, SHORT};
//		String[] styleNames = {"FULL", "LONG", "MEDIUM", "SHORT"};
//		// Output the date for each locale in four styles
//		DateFormat fmt = null;
//		for(int j = 0; j < locales.length; j++) {
//		System.out.println("\nThe Date for " 
//		+ locales[j].getDisplayCountry() + ":");
//		for(int i = 0 ; i<styles.length ; i++) {
//		fmt = DateFormat.getDateInstance(styles[i], locales[j]);
//		System.out.println( "\tIn " + styleNames[i] +
//		" is " + fmt.format(today));
//		}
//		}
//		}
//		}
	
	public static void dateTest() {
		
		Date today = new Date();
		@SuppressWarnings("deprecation")
		Date milienium = new Date(200, 0, 1);
		System.out.println(today);
		System.out.println(milienium);
		
		Calendar myCalendar = Calendar.getInstance();
		System.out.println(myCalendar);
		System.out.println(myCalendar.getTime()); //returns date object
		myCalendar.set(2000, Calendar.JANUARY, 1);
		System.out.println(myCalendar.getTime());  //makes more sense
		
		DateFormat fmt = DateFormat.getInstance();
		System.out.println(fmt);
		
//		String dateString fmt = fmt.format(myCalendar.getTime());
		
	}
	
	
	public static void stringTest() {
		
		String hello = "hello";
		String world = "world";
		String hello2 = hello;
		System.out.println(hello.equals(world));
		System.out.println(hello == hello2);
		System.out.println(hello.equals(hello2));
		
		
	}
	
	public static void vectorTest() {
		
		Vector<Date> invoiceDates = new Vector<>(2, 1);
		int capacity = invoiceDates.capacity();
		int size = invoiceDates.size();
		System.out.println("this vector has " + size + " of " + "elements used.");
		invoiceDates.add(new Date());
		System.out.println("this vector has " + size + " of " + "elements used.");
		
		Date someDate = (Date) invoiceDates.get(0);
		System.out.println(someDate);
		
		try {
			
			Date someOtherDate = invoiceDates.get(99);
			System.out.println(someDate);
			
		}
		catch (Exception ex) {
			
			System.out.println("###EXCEPTION THROWN");
			System.out.println(ex.getMessage());
			
			
		}
		
	}
	
	
	
	public static void arrayTest() {
		
		System.out.println("Hello World");

		double[] samples = {2, 4, 6};
		double average = 0.0;
		
		for (int x = 0;  x < samples.length; x++) {
			
			average += samples[x];
					
		}
		
		average /= samples.length;
		
		System.out.println(average);
		
	}

}
