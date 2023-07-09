package Transaction;

import Book.Book;
import Tools.FileManager;
import User.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionManager implements ITransactionManager{

    private final String path = "TransactionsData.txt";

    FileManager fileManager;

    public TransactionManager(boolean useFileSystem){

        fileManager = new FileManager(path,useFileSystem);

        try {
            fileManager.firstInit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean addTransaction(Transaction transaction) {

            String transactionsData = getAllTransactions();
        try {
            fileManager.setAllData(transactionsData+transaction.getBooksISBN()+"//"+transaction.getUserName()+"//"+transaction.getBorrowDate()+"//"+transaction.getDueDate()+"//"+transaction.getValidState());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
        }

    @Override
    public Transaction findTransactionByISBN(String bookISBN , String userName) {
        Transaction result = new Transaction();
        String transactionData = getAllTransactions();

        if (!transactionData.equals("")){

            String[] transactions = transactionData.split("\n");
            for (int i = 0 ; i<transactions.length ; i++){

                String[] transactionInText = transactions[i].split("//");

                if (bookISBN.equals(transactionInText[0])&&userName.equals(transactionInText[1])){
                    result.setBooksISBN(transactionInText[0]);
                    result.setUserName(transactionInText[1]);
                    result.setBorrowDate(Long.parseLong(transactionInText[2]));
                    result.setDueDate(Long.parseLong(transactionInText[3]));
                    result.setValidState(Boolean.parseBoolean(transactionInText[4]));
                    return result;
                }

            }
        }


        return null;
    }

    @Override
    public boolean toInvalidTransaction(Transaction transaction) {

        String TransactionsData = getAllTransactions();
        String newTransactionsData = "";
        String[] transactions = TransactionsData.split("\n");
        boolean isTransactionInvalid = false;
        for (int i = 0 ; i<transactions.length ; i++){

            String[] transactionInText = transactions[i].split("//");
            if (!(transaction.getBooksISBN().equals(transactionInText[0]) && transaction.getUserName().equals(transactionInText[1]))){

                if (i==(transactions.length-1)){

                    newTransactionsData +=transactions[i];
                }else {
                    newTransactionsData +=transactions[i]+"\n";
                }

            }else {
                transactionInText[4] = ""+false;
                if (i==(transactions.length-1)){

                    newTransactionsData +=String.join("//",transactionInText);
                }else {
                    newTransactionsData +=String.join("//",transactionInText)+"\n";
                }
                isTransactionInvalid = true;
            }



        }
        if (!TransactionsData.equals(newTransactionsData)){

            setAllTransactions(newTransactionsData);
        }
        return isTransactionInvalid;


    }

    @Override
    public String getAllTransactions() {


        try {
            return fileManager.getAllData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Transaction> getAllTransactions(User user, boolean validState) {

        ArrayList<Transaction> result = new ArrayList<Transaction>();
        String transactionsData = getAllTransactions();

        if (!transactionsData.equals("")){

            String[] transactions = transactionsData.split("\n");
            for (int i = 0 ; i<transactions.length ; i++){

                String[] transactionInText = transactions[i].split("//");

                if (user.getUserName().equals(transactionInText[1]) && Boolean.parseBoolean(transactionInText[4]) == validState){

                    Transaction transaction = new Transaction();
                    transaction.setBooksISBN(transactionInText[0]);
                    transaction.setUserName(transactionInText[1]);
                    transaction.setBorrowDate(Long.parseLong(transactionInText[2]));
                    transaction.setDueDate(Long.parseLong(transactionInText[3]));
                    transaction.setValidState(Boolean.parseBoolean(transactionInText[4]));
                    result.add(transaction);

                }

            }
        }


        return result;

    }

    @Override
    public void setAllTransactions(String transactions) {

        try {
            fileManager.setAllData(transactions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
