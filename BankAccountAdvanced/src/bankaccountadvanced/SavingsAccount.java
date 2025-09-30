package bankaccountadvanced;

/**
 * Represents a savings account with an owner name.
 * Extends AbstractBankAccount to inherit common bank account behavior.
 * Code Reviewer: MarkSayson
 */
public class SavingsAccount extends AbstractBankAccount {

    /** Name of the account owner. */
    private String ownerName;

    /**
     * Constructs a SavingsAccount with the specified owner name.
     *
     * @param savingsAccountOwnerName the name of the account owner
     */
    public SavingsAccount(String savingsAccountOwnerName) {
        super();
        this.ownerName = savingsAccountOwnerName;
    }

    /**
     * Returns the name of the account owner.
     *
     * @return the owner name
     */
    public String getOwnerName() {
        return ownerName;
    }
}
