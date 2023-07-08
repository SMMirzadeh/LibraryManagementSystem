package Transaction;

import Book.Book;
import User.User;

public class Transaction {

    private String booksISBN;
    private String userName;
    private long borrowDate;
    private long dueDate;

    public String getBooksISBN() {
        return booksISBN;
    }

    public String getUserName() {
        return userName;
    }
    public long getBorrowDate() {
        return borrowDate;
    }

    public long getDueDate() {
        return dueDate;
    }
}
