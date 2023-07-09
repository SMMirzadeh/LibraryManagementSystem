package Transaction;

import User.User;

import java.util.List;

public interface ITransactionManager {

    boolean addTransaction(Transaction transaction);
    boolean toInvalidTransaction(Transaction transaction);
    Transaction findTransactionByISBN(String bookISBN , String userName);
    String getAllTransactions();
    List<Transaction> getAllTransactions(User user, boolean validState);
    void setAllTransactions(String transactions);
}
