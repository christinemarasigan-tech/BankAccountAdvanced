package bankaccountadvanced;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Assertions;

/**
 * Test class for BankAccount and BankAccountManager functionality.
 * Code Reviewer: MarkSayson
 */
public class BankAccountTest {

    /** Bank account manager instance used in tests. */
    private BankAccountManager manager;

    /** Savings account instance used in tests. */
    private SavingsAccount account;

    /** Positive deposit amount constant. */
    private static final double DEPOSIT_AMOUNT = 1000.0;

    /** Withdraw amount constant. */
    private static final double WITHDRAW_AMOUNT = 500.0;

    /** Negative amount constant to test invalid deposits/withdraws. */
    private static final double NEGATIVE_AMOUNT = -100.0;
    
    /** Negative amount constant to test invalid deposits/withdraws. */
    private static final double NEGATIVE_50_AMOUNT = -50.0;

    /** Zero amount constant to test invalid deposits. */
    private static final double ZERO_AMOUNT = 0.0;

    /** Small withdraw amount constant. */
    private static final double SMALL_WITHDRAW = 100.0;
    
    /** Small withdraw amount constant. */
    private static final double SMALL_200_WITHDRAW = 200.0;
    
    /** Small withdraw amount constant. */
    private static final double SMALL_400_WITHDRAW = 400.0;

    /** Amount larger than balance to trigger insufficient funds. */
    private static final double EXCESS_WITHDRAW = 600.0;

    /** Threshold for filtering transactions. */
    private static final double FILTER_THRESHOLD = 500.0;

    /** Expected transaction count. */
    private static final int TRANSACTION_COUNT = 3;

    /** Invalid amount. */
    private static final int INVALID_AMOUNT = 99;
    
    /**
     * Sets up test objects before each test.
     */
    @BeforeEach
    public void setup() {
        manager = new BankAccountManager();
        account = new SavingsAccount("Christine");
        manager.addAccount(account);
    }

    /**
     * Tests deposit branches: positive, zero, negative, frozen deposits.
     *
     * @throws Exception if deposit fails unexpectedly
     */
    @Test
    public void testDepositBranches() throws Exception {
        // Positive deposit
        account.deposit(DEPOSIT_AMOUNT);
        Assertions.assertEquals(DEPOSIT_AMOUNT, account.getBalance());

        // Negative deposit
        Assertions.assertThrows(
            InvalidAmountException.class,
            () -> account.deposit(NEGATIVE_AMOUNT)
        );

        // Zero deposit
        Assertions.assertThrows(
            InvalidAmountException.class,
            () -> account.deposit(ZERO_AMOUNT)
        );

        // Frozen deposit
        account.freezeAccount();
        Assertions.assertThrows(
            AccountFrozenException.class,
            () -> account.deposit(SMALL_WITHDRAW)
        );
        account.unfreezeAccount();
    }

    /**
     * Tests withdraw branches: sufficient, insufficient, negative, frozen.
     *
     * @throws Exception if withdraw fails unexpectedly
     */
    @Test
    public void testWithdrawBranches() throws Exception {
        account.deposit(DEPOSIT_AMOUNT);

        // Positive withdraw
        account.withdraw(WITHDRAW_AMOUNT);
        Assertions.assertEquals(DEPOSIT_AMOUNT - WITHDRAW_AMOUNT,
            account.getBalance()
        );

        // Withdraw more than balance
        Assertions.assertThrows(
            InsufficientFundsException.class,
            () -> account.withdraw(EXCESS_WITHDRAW)
        );

        // Negative withdraw
        Assertions.assertThrows(
            InvalidAmountException.class,
            () -> account.withdraw(NEGATIVE_AMOUNT)
        );

        // Withdraw when frozen
        account.freezeAccount();
        Assertions.assertThrows(
            AccountFrozenException.class,
            () -> account.withdraw(SMALL_WITHDRAW)
        );
        account.unfreezeAccount();
    }

    /**
     * Tests freezing and unfreezing of accounts.
     */
    @Test
    public void testFreezeUnfreeze() {
        Assertions.assertFalse(account.isFrozen());
        account.freezeAccount();
        Assertions.assertTrue(account.isFrozen());
        account.unfreezeAccount();
        Assertions.assertFalse(account.isFrozen());
    }

    /**
     * Tests transaction history, filtering, and sorting.
     *
     * @throws Exception if deposit/withdraw fails unexpectedly
     */
    @Test
    public void testTransactionHistory() throws Exception {
        account.deposit(DEPOSIT_AMOUNT);
        account.withdraw(WITHDRAW_AMOUNT);
        account.withdraw(SMALL_WITHDRAW);

        List<Transaction> history = account.getTransactionHistory();
        Assertions.assertEquals(TRANSACTION_COUNT, history.size());

        // Test transaction getters and toString
        Transaction t1 = history.get(0);
        Assertions.assertEquals("Deposit", t1.getType());
        Assertions.assertEquals(DEPOSIT_AMOUNT, t1.getAmount());
        Assertions.assertEquals("Deposit: Php 1000.0", t1.toString());

        // Filter transactions above threshold
        List<Transaction> filtered = manager.filterTransactionsAbove(
            FILTER_THRESHOLD,
            history
        );
        Assertions.assertEquals(1, filtered.size());
        Assertions.assertEquals(DEPOSIT_AMOUNT, filtered.get(0).getAmount());

        // Sort transactions
        List<Transaction> sorted = manager.sortTransactionsByAmount(history);
        Assertions.assertEquals(SMALL_WITHDRAW, sorted.get(0).getAmount());
        Assertions.assertEquals(WITHDRAW_AMOUNT, sorted.get(1).getAmount());
        Assertions.assertEquals(DEPOSIT_AMOUNT, sorted.get(2).getAmount());
    }

    /**
     * Tests that SavingsAccount returns the correct owner name.
     */
    @Test
    public void testSavingsAccount() {
        Assertions.assertEquals("Christine", account.getOwnerName());
    }

    /**
     * Tests BankAccountManager methods: getAccount, listAccounts, filter, sort.
     *
     * @throws Exception if operation fails unexpectedly
     */
    @Test
    public void testBankAccountManagerMethods() throws Exception {
        // getAccount valid
        BankAccount acc = manager.getAccount(1);
        Assertions.assertNotNull(acc);

        // getAccount invalid
        Assertions.assertNull(manager.getAccount(INVALID_AMOUNT));

        // listAccounts just runs
        manager.listAccounts();

        // filter and sort with empty list
        List<Transaction> emptyList = List.of();
        Assertions.assertEquals(
            0,
            manager.filterTransactionsAbove(FILTER_THRESHOLD, emptyList).size()
        );
        Assertions.assertEquals(
            0,
            manager.sortTransactionsByAmount(emptyList).size()
        );
    }
    
    // Deposit
    @Test
    @DisplayName("Deposit valid amount updates balance")
    void testDepositValid() throws Exception {
        account.deposit(WITHDRAW_AMOUNT);
        assertEquals(WITHDRAW_AMOUNT, account.getBalance());
    }

    @Test
    @DisplayName("Deposit zero throws exception")
    void testDepositZero() {
        assertThrows(InvalidAmountException.class, () -> account.deposit(
        		ZERO_AMOUNT));
    }

    @Test
    @DisplayName("Deposit negative throws exception")
    void testDepositNegative() {
        assertThrows(InvalidAmountException.class, () -> account.deposit(
        		NEGATIVE_AMOUNT));
    }

    // Withdraw
    @Test
    @DisplayName("Withdraw valid amount updates balance")
    void testWithdrawValid() throws Exception {
        account.deposit(DEPOSIT_AMOUNT );
        account.withdraw(WITHDRAW_AMOUNT);
        assertEquals(WITHDRAW_AMOUNT, account.getBalance());
    }


    // BankAccountManager
    @Test
    @DisplayName("Add and get account works")
    void testAddAndGetAccount() {
        BankAccount retrieved = manager.getAccount(1);
        assertNotNull(retrieved);
    }

    @Test
    @DisplayName("List accounts executes without error")
    void testListAccounts() {
        manager.listAccounts(); // executes, no assertion needed
    }

    @Test
    @DisplayName("Filter transactions above threshold works")
    void testFilterTransactionsAbove() throws Exception {
        account.deposit(DEPOSIT_AMOUNT );
        account.withdraw(WITHDRAW_AMOUNT);
        List<Transaction> filtered = manager.filterTransactionsAbove(
        		SMALL_400_WITHDRAW, account.getTransactionHistory());
        assertEquals(2, filtered.size());
    }

    @Test
    @DisplayName("Sort transactions by amount works")
    void testSortTransactionsByAmount() throws Exception {
        account.deposit(DEPOSIT_AMOUNT );
        account.withdraw(WITHDRAW_AMOUNT);
        account.withdraw(SMALL_200_WITHDRAW);
        List<Transaction> sorted = manager.sortTransactionsByAmount(
            account.getTransactionHistory());
        assertEquals(SMALL_200_WITHDRAW, sorted.get(0).getAmount());
        assertEquals(WITHDRAW_AMOUNT, sorted.get(1).getAmount());
        assertEquals(DEPOSIT_AMOUNT , sorted.get(2).getAmount());
    }

    @Test
    @DisplayName("Invalid account returns null")
    void testInvalidAccountAccess() {
        assertNull(manager.getAccount(INVALID_AMOUNT));
    }

    // Exceptions
    @Test
    @DisplayName("Custom exceptions can be constructed")
    void testExceptions() {
        assertEquals("msg", new InvalidAmountException("msg").getMessage());
        assertEquals("msg", new InsufficientFundsException("msg").getMessage());
        assertEquals("msg", new AccountFrozenException("msg").getMessage());
    }
}

