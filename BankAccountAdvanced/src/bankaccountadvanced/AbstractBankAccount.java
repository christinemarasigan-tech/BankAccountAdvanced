package bankaccountadvanced;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of a BankAccount.
 * Provides shared functionality for deposit, withdraw, balance tracking,
 * freezing/unfreezing accounts, and transaction history.
 * Code Reviewer: MarkSayson
 */
public abstract class AbstractBankAccount implements BankAccount {

    /** Current balance of the account. */
    private double balance;

    /** Indicates whether the account is frozen. */
    private boolean frozen;

    /** List of all transactions performed on the account. */
    private List<Transaction> transactionHistory;

    /**
     * Constructs a new AbstractBankAccount with initial balance 0,
     * account unfrozen, and empty transaction history.
     */
    public AbstractBankAccount() {
        this.balance = 0.0;
        this.frozen = false;
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * Deposits the specified amount into the account.
     * Records the transaction in the transaction history.
     *
     * @param amount the amount to deposit
     * @throws InvalidAmountException if amount is zero or negative
     * @throws AccountFrozenException if the account is currently frozen
     */
    @Override
    public final synchronized void deposit(final double amount)
            throws InvalidAmountException, AccountFrozenException {
        if (frozen) {
            throw new AccountFrozenException("Account is frozen");
        }
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Deposit amount must be positive"
            );
        }
        balance += amount;
        transactionHistory.add(
                new Transaction("Deposit", amount)
        );
    }


    /**
     * Withdraws the specified amount from the account.
     * Records the transaction in the transaction history.
     *
     * @param amount the amount to withdraw
     * @throws InvalidAmountException if amount is zero or negative
     * @throws InsufficientFundsException if amount exceeds current balance
     * @throws AccountFrozenException if the account is currently frozen
     */
    @Override
    public final synchronized void withdraw(final double amount)
            throws InvalidAmountException,
            InsufficientFundsException,
            AccountFrozenException {
        if (frozen) {
            throw new AccountFrozenException("Account is frozen");
        }
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be positive"
            );
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= amount;
        transactionHistory.add(
                new Transaction("Withdraw", amount)
        );
    }


    /**
     * Returns the current balance of the account.
     *
     * @return current balance
     */
    @Override
    public final double getBalance() {
        return balance;
    }

    /**
     * Returns whether the account is frozen.
     *
     * @return true if frozen, false otherwise
     */
    @Override
    public final boolean isFrozen() {
        return frozen;
    }

    /**
     * Freezes the account, preventing deposits and withdrawals.
     */
    @Override
    public final void freezeAccount() {
        frozen = true;
    }

    /**
     * Unfreezes the account, allowing deposits and withdrawals.
     */
    @Override
    public final void unfreezeAccount() {
        frozen = false;
    }

    /**
     * Returns a list of all transactions performed on the account.
     *
     * @return transaction history
     */
    @Override
    public final List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}
