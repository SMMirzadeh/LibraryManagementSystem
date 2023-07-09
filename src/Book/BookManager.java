package Book;

import Tools.FileManager;
import Transaction.Transaction;
import User.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class BookManager implements IBookManager{

    private final String path = "BooksData.txt";
    private FileManager fileManager;

    public BookManager(boolean useFileSystem){

        fileManager = new FileManager(path,useFileSystem);

        fileManager.firstInit();


    }
    @Override
    public boolean addBook(Book book) {

        if (bookExistence(book.getISBN()) == false){

            String booksData = getAllBooks();
            fileManager.setAllData(booksData+book.getISBN()+"//"+book.getTitle()+"//"+book.getAuthor()+"//"+book.getGenre()+"//"+book.getUnit());
            return true;
        }else {

            return false;

        }
    }

    @Override
    public boolean updateBook(Book book,String ISBN) {
        String booksData = getAllBooks();
        String newBooksData = "";
        String[] books = booksData.split("\n");
        boolean isBookUpdate = false;
        for (int i = 0 ; i<books.length ; i++){

            String[] bookInText = books[i].split("//");
            if (!ISBN.equals(bookInText[0])){

                if (i==(books.length-1)){

                    newBooksData +=books[i];
                }else {
                    newBooksData +=books[i]+"\n";
                }

            }else {
                bookInText[0]=book.getISBN();
                bookInText[1]=book.getTitle();
                bookInText[2]=book.getAuthor();
                bookInText[3]=String.valueOf(book.getGenre());
                bookInText[4]=Integer.toString(book.getUnit());

                if (i==(books.length-1)){

                    newBooksData +=String.join("//",bookInText);
                }else {
                    newBooksData +=String.join("//",bookInText)+"\n";
                }
                isBookUpdate = true;
            }



        }
        if (!booksData.equals(newBooksData)){

            setAllBooks(newBooksData);
        }
        return isBookUpdate;


    }

    @Override
    public ArrayList<Book> searchByTitle(String title) {

        ArrayList<Book> result = new ArrayList<Book>();
        String booksData = getAllBooks();

        if (!booksData.equals("")){

            String[] books = booksData.split("\n");
            for (int i = 0 ; i<books.length ; i++){

                String[] bookInText = books[i].split("//");

                if (bookInText[1].toLowerCase().contains(title.toLowerCase())){

                    result.add(StringToBook(books[i]));
                }

            }
        }


        return result;

    }

    @Override
    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> result = new ArrayList<Book>();
        String booksData = getAllBooks();

        if (!booksData.equals("")){

            String[] books = booksData.split("\n");
            for (int i = 0 ; i<books.length ; i++){

                String[] bookInText = books[i].split("//");

                if (bookInText[2].toLowerCase().contains(author.toLowerCase())){

                    result.add(StringToBook(books[i]));
                }

            }
        }


        return result;
    }

    @Override
    public ArrayList<Book> searchByGenre(String genreName) {
        ArrayList<Book> result = new ArrayList<Book>();
        String booksData = getAllBooks();

        if (!booksData.equals("")){

            String[] books = booksData.split("\n");
            for (int i = 0 ; i<books.length ; i++){

                String[] bookInText = books[i].split("//");

                if (bookInText[3].contains(genreName)){

                    result.add(StringToBook(books[i]));
                }
                else if (bookInText[3].toLowerCase().contains(genreName.toLowerCase())){

                    result.add(StringToBook(books[i]));
                }

            }
        }


        return result;
    }

    @Override
    public boolean bookExistence(String ISBN) {
        String booksData = getAllBooks();
        boolean isBookExist = false;
        if (!booksData.equals("")){

            String[] books = booksData.split("\n");
            for (int i = 0 ; i<books.length ; i++){

                String[] bookInText = books[i].split("//");

                if (ISBN.equals(bookInText[0])){

                    isBookExist = true;
                    break;
                }

            }
        }


        return isBookExist;
    }

    @Override
    public String getAllBooks() {

        return fileManager.getAllData();
    }

    @Override
    public void setAllBooks(String books) {
        fileManager.setAllData(books);
    }

    @Override
    public Book StringToBook(String BookInLine) {
        Book result = new Book();
        String[] book = BookInLine.split("//");
        result.setISBN(book[0]);
        result.setTitle(book[1]);
        result.setAuthor(book[2]);
        result.setGenre(Genre.valueOf(book[3]));
        result.setUnit(Integer.parseInt(book[4]));

        return result;
    }

    @Override
    public Book findByTitle(String title) {

        Book result = new Book();
        String booksData = getAllBooks();

        if (!booksData.equals("")){

            String[] books = booksData.split("\n");
            for (int i = 0 ; i<books.length ; i++){

                String[] bookInText = books[i].split("//");

                if (title.equals(bookInText[1])){

                    return StringToBook(books[i]);
                }

            }
        }


        return null;
    }

    @Override
    public Book findByAuthor(String author) {
        Book result = new Book();
        String booksData = getAllBooks();

        if (!booksData.equals("")){

            String[] books = booksData.split("\n");
            for (int i = 0 ; i<books.length ; i++){

                String[] bookInText = books[i].split("//");

                if (author.equals(bookInText[2])){

                    return StringToBook(books[i]);
                }

            }
        }


        return result;
    }

    @Override
    public Book findByISBN(String ISBN) {

        Book result = new Book();
        String booksData = getAllBooks();

        if (!booksData.equals("")){

            String[] books = booksData.split("\n");
            for (int i = 0 ; i<books.length ; i++){

                String[] bookInText = books[i].split("//");

                if (ISBN.equals(bookInText[0])){

                    return StringToBook(books[i]);
                }

            }
        }


        return null;

    }

    @Override
    public Transaction borrowBook(User user, Book book) {

        if (user == null || book == null){
            return null;
        }
        long thirtyDaysInMillis = 30L * 24L * 60L * 60L * 1000L;
        long borrowDate = System.currentTimeMillis();
        long dueDate = new Date(borrowDate+thirtyDaysInMillis).getTime();
        Transaction transaction = new Transaction();
        transaction.setBooksISBN(book.getISBN());
        transaction.setUserName(user.getUserName());
        transaction.setBorrowDate(borrowDate);
        transaction.setDueDate(dueDate);
        transaction.setValidState(true);
        int bookUnit = book.getUnit() - 1;
        book.setUnit(bookUnit);
        updateBook(book,book.getISBN());

        return transaction;
    }


}
