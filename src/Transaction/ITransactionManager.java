package Transaction;

public interface ITransactionManager {

    boolean addTransaction(Transaction transaction);
    String getAllTransactions();
}
