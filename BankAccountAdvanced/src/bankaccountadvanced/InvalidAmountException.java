package bankaccountadvanced;

/**
 * Exception thrown when an invalid amount (negative or zero) 
 * is deposited or withdrawn from a bank account.
 * Code Reviewer: MarkSayson
 */
public class InvalidAmountException extends Exception {

    /**
     * Constructs a new InvalidAmountException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidAmountException(final String message) {
        super(message);
    }
}
