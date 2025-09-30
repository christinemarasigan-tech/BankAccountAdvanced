package bankaccountadvanced;

/**
 * Represents a bank transaction with a type and an amount.
 * Code Reviewer: MarkSayson
 */
public class Transaction {

    /** Type of the transaction, e.g., "Deposit" or "Withdraw". */
    private String type;

    /** Amount involved in the transaction. */
    private double amount;

    /**
     * Constructs a Transaction with the specified type and amount.
     *
     * @param transactionType   the type of the transaction
     * @param transactionAmount the amount of the transaction
     */
    public Transaction(final String transactionType, final double transactionAmount) {
        this.type = transactionType;
        this.amount = transactionAmount;
    }

    /**
     * Returns the type of the transaction.
     *
     * @return the transaction type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the amount of the transaction.
     *
     * @return the transaction amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns a string representation of the transaction in the format:
     * "Type: Php Amount".
     *
     * @return string representation of the transaction
     */
    @Override
    public final String toString() {
        return type + ": Php " + amount;
    }
}
