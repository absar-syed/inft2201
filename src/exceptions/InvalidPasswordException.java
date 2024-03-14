package exceptions;

public class InvalidPasswordException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Exception for invalid passwords
     */
    public InvalidPasswordException() {
        super();
    }

    /**
     * Overloaded constructor for InvalidPasswordException
     * @param Message a variable that holds the message to be displayed
     */
    public InvalidPasswordException(String Message) {

        super(Message);

    }


}
