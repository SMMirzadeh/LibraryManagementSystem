package Transaction;

import Book.Book;
import User.User;

public class Transaction {

    private String booksISBN;
    private String userName;
    private long borrowDate;
    private long dueDate;
    private boolean validState;


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

    public void setValidState(boolean validState) {
        this.validState = validState;
    }

    public boolean getValidState(){

        return validState;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBooksISBN(String booksISBN) {
        this.booksISBN = booksISBN;
    }

    public void setBorrowDate(long borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }
}
