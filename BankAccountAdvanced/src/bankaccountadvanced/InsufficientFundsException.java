package bankaccountadvanced;

/**
 * Exception thrown when a withdrawal operation exceeds
 * the available balance of a bank account.
 * Code Reviewer: MarkSayson
 */
public class InsufficientFundsException extends Exception {

    /**
     * Constructs a new InsufficientFundsException with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception
     */
    public InsufficientFundsException(final String message) {
        super(message);
    }
}
