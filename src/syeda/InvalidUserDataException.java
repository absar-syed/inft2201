package syeda;

public class InvalidUserDataException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Exception for invalid user data
     */
    public InvalidUserDataException() {
        super();
    }

    /**
     * Overloaded constructor for InvalidUserDataException
     * @param message a variable that holds the message to be displayed
     */
    public InvalidUserDataException(String message) {

        super(message);

    }

}
