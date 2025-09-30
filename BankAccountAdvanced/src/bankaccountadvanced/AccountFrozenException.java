package bankaccountadvanced;

/**
 * Exception thrown when an operation is attempted on a frozen account.
 * Code Reviewer: MarkSayson
 */
public class AccountFrozenException extends Exception {

    /**
     * Constructs a new AccountFrozenException 
     * with a specified message.
     * @param message the detail message
     */
    public AccountFrozenException(final String message) {
        super(message);
    }
}
