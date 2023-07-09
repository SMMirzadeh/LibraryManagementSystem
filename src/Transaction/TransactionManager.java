package Transaction;

import Book.Book;
import User.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionManager implements ITransactionManager{

    private final String path = "TransactionsData.txt";

    public TransactionManager(){

        //Creating UsersData.txt file into the path direction
        try {
            File usersData = new File(path);
            usersData.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }

    @Override
    public boolean addTransaction(Transaction transaction) {

            String transactionsData = getAllTransactions();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));

                writer.write(transactionsData+transaction.getBooksISBN()+"//"+transaction.getUserName()+"//"+transaction.getBorrowDate()+"//"+transaction.getDueDate()+"//"+transaction.getValidState());
                writer.close();
                return  true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;

            }
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

        String transactionsInfo = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = "";
            while ((line = reader.readLine()) != null){

                transactionsInfo +=line+"\n";
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactionsInfo;

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
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            writer.write(transactions);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
