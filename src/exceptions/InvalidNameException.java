package exceptions;

public class InvalidNameException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Exception for invalid Names
     */
    public InvalidNameException() {
        super();
    }

    /**
     * Overloaded constructor for InvalidNameException
     * @param message a variable that holds the message to be displayed
     */
    public InvalidNameException(String message) {

        super(message);

    }

}
