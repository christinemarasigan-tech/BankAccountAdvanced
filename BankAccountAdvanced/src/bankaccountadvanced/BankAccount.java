package bankaccountadvanced;

import java.util.List;

/**
 * Interface representing a generic bank account.
 * Supports deposit, withdraw, balance checking, freezing, and transaction history retrieval.
 * Code Reviewer: MarkSayson
 */
public interface BankAccount {

    /**
     * Deposits a specified amount into the account.
     *
     * @param amount the amount to deposit
     * @throws InvalidAmountException if the amount is zero or negative
     * @throws AccountFrozenException if the account is currently frozen
     */
	void deposit(final double amount)
	        throws InvalidAmountException,
	               AccountFrozenException;

    /**
     * Withdraws a specified amount from the account.
     *
     * @param amount the amount to withdraw
     * @throws InvalidAmountException if the amount is zero or negative
     * @throws InsufficientFundsException if the balance is insufficient
     * @throws AccountFrozenException if the account is currently frozen
     */
	void withdraw(final double amount)
	        throws InvalidAmountException,
	               InsufficientFundsException,
	               AccountFrozenException;

    /**
     * Returns the current balance of the account.
     *
     * @return the account balance
     */
    double getBalance();

    /**
     * Checks whether the account is frozen.
     *
     * @return true if the account is frozen, false otherwise
     */
    boolean isFrozen();

    /**
     * Freezes the account, preventing deposits or withdrawals.
     */
    void freezeAccount();

    /**
     * Unfreezes the account, allowing deposits and withdrawals.
     */
    void unfreezeAccount();

    /**
     * Retrieves the transaction history of the account.
     *
     * @return a list of all transactions performed on the account
     */
    List<Transaction> getTransactionHistory();
}
