package Transaction;

import java.io.*;
import java.util.Date;

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

                writer.write(transactionsData+transaction.getBooksISBN()+"//"+transaction.getUserName()+"//"+transaction.getBooksISBN()+"//"+transaction.getDueDate());
                writer.close();
                return  true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;

            }
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
}
