package Book;

import java.util.ArrayList;

public interface IBookManager {

    boolean addBook(Book book);
    boolean updateBook(Book book,String ISB);
    ArrayList<Book> searchByTitle(String title);
    ArrayList<Book> searchByAuthor(String author);
    ArrayList<Book> searchByGenre(String genreName);
    boolean bookExistence(String ISBN);
    String getAllBooks();
    void setAllBooks(String Books);
    Book StringToBook(String BookInLine);
    Book findByTitle(String title);
    Book findByAuthor(String author);
    Book findByISBN(String ISBN);

}
