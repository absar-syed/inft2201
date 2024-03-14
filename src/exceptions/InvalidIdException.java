package exceptions;

public class InvalidIdException extends Exception {

    /**
     * Exception for invalid ID's
     */
    public InvalidIdException() {
        super();
    }

    /**
     * Overloaded constructor for InvalidIdExceptions
     * @param message a variable that holds the message to be displayed
     */
    public InvalidIdException(String message) {
        super(message);
    }

}
