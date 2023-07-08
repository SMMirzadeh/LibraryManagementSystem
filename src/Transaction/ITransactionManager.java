package Transaction;

public interface ITransactionManager {

    boolean addTransaction(Transaction transaction);
    boolean toInvalidTransaction(Transaction transaction);
    Transaction findTransactionByISBN(String bookISBN , String userName);
    String getAllTransactions();
    void setAllTransactions(String transactions);
}
