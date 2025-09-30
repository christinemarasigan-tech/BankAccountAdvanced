package bankaccountadvanced;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages multiple bank accounts, allowing addition, retrieval,
 * listing of accounts, and transaction filtering/sorting.
 * Code Reviewer: MarkSayson
 */
public class BankAccountManager {

    /** Maps account IDs to BankAccount objects. */
    private Map<Integer, BankAccount> accounts;

    /** Tracks the next account ID to assign. */
    private int nextAccountId;

    /**
     * Constructs a new BankAccountManager with an empty account map
     * and initializes the next account ID to 1.
     */
    public BankAccountManager() {
        accounts = new HashMap<>();
        nextAccountId = 1;
    }

    /**
     * Adds a BankAccount to the manager and assigns a unique account ID.
     *
     * @param account the BankAccount to add
     */
    public void addAccount(BankAccount account) {
        accounts.put(nextAccountId++, account);
    }

    /**
     * Retrieves a BankAccount by its ID.
     *
     * @param accountId the ID of the account to retrieve
     * @return the BankAccount associated with the ID, or null if not found
     */
    public BankAccount getAccount(int accountId) {
        return accounts.get(accountId);
    }

    /**
     * Lists all accounts and their current balances.
     */
    public void listAccounts() {
        accounts.forEach((id, account) ->
                System.out.println("Account ID: " + id + ", Balance: Php " + account.getBalance()));
    }

    /**
     * Filters transactions in the given list above a specified amount.
     *
     * @param amount the threshold amount
     * @param txList the list of transactions to filter
     * @return a list of transactions with amounts greater than the specified amount
     */
    public List<Transaction> filterTransactionsAbove(double amount, List<Transaction> txList) {
        return txList.stream()
                .filter(tx -> tx.getAmount() > amount)
                .collect(Collectors.toList());
    }

    /**
     * Sorts the given list of transactions by amount in ascending order.
     *
     * @param txList the list of transactions to sort
     * @return a list of transactions sorted by amount
     */
    public List<Transaction> sortTransactionsByAmount(List<Transaction> txList) {
        return txList.stream()
                .sorted((t1, t2) -> Double.compare(t1.getAmount(), t2.getAmount()))
                .collect(Collectors.toList());
    }
}
