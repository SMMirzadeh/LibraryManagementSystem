package Console;

import Book.BookManager;
import Book.IBookManager;
import Book.Book;
import Book.Genre;
import Tools.DateCalculator;
import Transaction.ITransactionManager;
import Transaction.Transaction;
import Transaction.TransactionManager;
import User.IUserManager;
import User.User;
import User.UserManager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static IUserManager userManager = new UserManager();
    private static IBookManager bookManager = new BookManager();
    private static ITransactionManager transactionManager = new TransactionManager();
    private static User currentUser;
    public static void start(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("+----------------------------------+");
        System.out.println("| 1_ for SignUp please press key s |");
        System.out.println("| 2_ for Login please press key l  |");
        System.out.println("+----------------------------------+");
        while(true){

            String command = scanner.next();
            if (command.equals("s")){

                signup();
                break;
            }
            else if(command.equals("l")){

                login();
                break;

            }else {

                System.out.println("Wrong command ,please try again");
            }
        }

    }

    public static void signup(){

        User user = setUserInformation();

        if (user != null){

            boolean isSignUp =  userManager.signUp(user);
            if (isSignUp){

                currentUser = user;
                afterLogin();
            }else {
                System.out.println("SingUp failed probably UserName is taken , Please try again. ");
                start();
            }
        }else {

            System.out.println("Passwords are not match , Please try again . \n\n");

            start();
        }
    }
    public static void login(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("+------------------------------------------+");
        System.out.println("| for back to main page please press key m |");
        System.out.println("+------------------------------------------+\n");

        System.out.println("Please enter your UserName");
        String userName = scanner.next();
        if (userName.equals("m")){
            currentUser = null;
            start();
        }
        System.out.println("Please enter your Password");
        String password = scanner.next();
        if (password.equals("m")){
            currentUser = null;
            start();
        }

        boolean isLoginSuccess =  userManager.logIn(userName,userManager.passwordToHash(password));
        if (isLoginSuccess ==false){

            System.out.println("Login failed ,Probably password is incorrect.");
            currentUser = null;
            start();
        }else {
            System.out.println("Login Succeed");
            currentUser = userManager.getUser(userName);
            afterLogin();
        }
    }
    public static void afterLogin(){

        System.out.println("Welcome Mr/Ms "+currentUser.getName());

        Scanner scanner = new Scanner(System.in);

        System.out.println("+-------------------------------------------------+");
        System.out.println("| for logout from your account please press key l |");
        System.out.println("| for edit your information please press key e    |");
        System.out.println("| for delete your account please press key d      |");
        System.out.println("| for Track borrowed books please press key t      |");
        System.out.println("| for add book please press key a                 |");
        System.out.println("| for find book by title please press key fbt     |");
        System.out.println("| for find book by author please press key fba    |");
        System.out.println("| for find book by genre please press key fbg     |");
        System.out.println("+-------------------------------------------------+\n");

        while(true){

            String command = scanner.next();
            if (command.equals("a")){

                Book book = setBookInformation();

                    boolean isBookAdded =  bookManager.addBook(book);
                    if (isBookAdded){

                        System.out.println("Book successfully added");
                        afterLogin();
                    }else {
                        System.out.println("process failed , probably Book ISBN exist , Please try again. ");
                        afterLogin();
                    }


            }
            else if (command.equals("t")){

                trackBooks();
            }
            else if(command.equals("l")){

                currentUser = null;
                start();
                break;

            }
            else if (command.equals("e")){

                User tempUser = setUserInformation();

                if (!tempUser.getUserName().equals("")){

                    currentUser.setUserName(tempUser.getUserName());
                }
                if (!tempUser.getPassword().equals("d41d8cd98f00b204e9800998ecf8427e")){

                    currentUser.setPassword(tempUser.getPassword());
                }
                if (!tempUser.getName().equals("")){

                    currentUser.setName(tempUser.getName());
                }


                boolean isEdited =  userManager.editUser(currentUser);
                if (isEdited){

                    System.out.println("Edit your account Succeed.");
                    afterLogin();

                }else {

                    System.out.println("Edit account failed ,please try again.");
                    afterLogin();

                }

            }
            else if(command.equals("d")){

                boolean isDeleted = userManager.deleteUser(currentUser.getUserName());

                if (isDeleted){

                    System.out.println("Delete your account Succeed.");
                    currentUser = null;
                    start();
                    break;
                }
                else {

                    System.out.println("Delete account failed ,please try again.");
                }

            }
            else if(command.equals("fbt")){

                System.out.println("please enter title for starting the search :");
                String title = scanner.next();
                ArrayList<Book> books =  bookManager.searchByTitle(title);

                if (books != null){

                    for (Book item : books){

                        System.out.println("title : " +item.getTitle()+"   "+"author : "+item.getAuthor()+"   "+"ISBN : "+item.getISBN());

                    }

                    System.out.println("please enter ISBN of the book : ");
                    String ISBN = scanner.next();
                    Book book = bookManager.findByISBN(ISBN);

                    if (book != null){

                        bookOption(book);

                    }else {

                        System.out.println("Book not found.");
                        afterLogin();
                    }


                }else {

                    System.out.println("Book not found , please try again .");
                    afterLogin();
                }

            }
            else if(command.equals("fba")){

                System.out.println("please enter author for starting the search :");
                ArrayList<Book> books =  bookManager.searchByAuthor(scanner.next());

                if (books != null){

                    for (Book item : books){

                        System.out.println("title : " +item.getTitle()+"   "+"author : "+item.getAuthor()+"   "+"ISBN : "+item.getISBN());

                    }

                    System.out.println("please enter ISBN of the book : ");
                    Book book = bookManager.findByISBN(scanner.next());

                    if (book != null){

                        bookOption(book);

                    }else {

                        System.out.println("Book not found.");
                        afterLogin();
                    }


                }else {

                    System.out.println("Book not found , please try again .");
                    afterLogin();
                }
            }
            else if(command.equals("fbg")){

                System.out.println("please enter genre for starting the search :");
                ArrayList<Book> books =  bookManager.searchByGenre(scanner.next());

                if (books != null){

                    for (Book item : books){

                        System.out.println("title : " +item.getTitle()+"   "+"author : "+item.getAuthor()+"   "+"ISBN : "+item.getISBN());

                    }

                    System.out.println("please enter ISBN of the book : ");
                    Book book = bookManager.findByISBN(scanner.next());

                    if (book != null){

                        bookOption(book);

                    }else {

                        System.out.println("Book not found.");
                        afterLogin();
                    }


                }else {

                    System.out.println("Book not found , please try again .");
                    afterLogin();
                }

            }

            else {

                System.out.println("Wrong command ,please try again");
            }
        }

    }


    private static void bookOption(Book book) {

        clearConsole();

        System.out.print(book.getTitle()+" By " +book.getAuthor()+"     "+ "Unit : ");

        if (book.getUnit() > 0){
            System.out.print("\u001B[32m"+book.getUnit());
        }else {
            System.out.print("\u001B[31m"+book.getUnit());
        }
        System.out.println("\u001B[0m");

        Scanner scanner = new Scanner(System.in);


        System.out.println("+------------------------------------------+");
        System.out.println("| for borrow book please press key b       |");
        System.out.println("| for update book please press key u       |");
        System.out.println("| for back to main page please press key m |");
        System.out.println("+------------------------------------------+\n");

        while(true){

            String command = scanner.next();
            if (command.equals("b")){

                if (book.getUnit() > 0){

                    Transaction transaction = bookManager.borrowBook(currentUser,book);
                    if (transaction != null){

                        transactionManager.addTransaction(transaction);
                        System.out.println("Book successfully borrowed .");

                    }else {
                        System.out.println("Borrowing book failed , please try again .");
                    }


                }else {

                    System.out.println("There is no unit left to borrow , please try again later.");

                }
                afterLogin();
            }
            else if(command.equals("u")){

                Book tempBook = setBookInformation();

                boolean isBookUpdate =  bookManager.updateBook(tempBook, book.getISBN());
                if (isBookUpdate){

                    System.out.println("Book successfully update");
                    afterLogin();
                }else {
                    System.out.println("process failed , Please try again. ");
                    afterLogin();
                }

            }
            else if(command.equals("m")){

               afterLogin();

            }

            else {

                System.out.println("Wrong command ,please try again");
            }
        }

    }

    public static User setUserInformation(){

        User user = new User();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter UserName :");
        user.setUserName(scanner.nextLine());

        System.out.println("Please enter Name :");
        user.setName(scanner.nextLine());

        String password1 = "";
        System.out.println("Please enter your new Password :");
        password1 = scanner.nextLine();

        String password2 = "";
        System.out.println("Please enter Password again :");
        password2 = scanner.nextLine();

        if (password1.equals(password2)){

            user.setPassword(userManager.passwordToHash(password1));
            return user;

        }else {

            return null;
        }

    }
    public static Book setBookInformation(){

        Book book = new Book();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter title :");
        book.setTitle(scanner.nextLine());

        System.out.println("Please enter author :");
        book.setAuthor(scanner.nextLine());

        System.out.println("Please enter genre from bottom :");
        System.out.println("\n HISTORY|MYSTERY|NOVEL|FANTASY|GUIDEBOOK|COMEDY|POETRY|DRAMA|NONFICTION \n");
        book.setGenre(Genre.valueOf(scanner.next().toUpperCase()));

        System.out.println("Please enter ISBN :");
        book.setISBN(scanner.next());

        System.out.println("Please enter unit :");
        book.setUnit(scanner.nextInt());

        return book;
    }

    public static void clearConsole(){

        System.out.print("\n\n\n\n \n\n\n\n \n\n\n\n \n\n\n\n");

    }
    public static void trackBooks(){

        clearConsole();

        System.out.println("| for back to main page please press key m |");
        System.out.println("| for return book please press key r       |");

        List<Transaction> transactions = transactionManager.getAllTransactions(currentUser,true);
        if (transactions.size()>0){
            for (Transaction item : transactions){

                Book book = bookManager.findByISBN(item.getBooksISBN());
                Date dueDate = new Date(item.getDueDate());
                System.out.println("ISBN : "+ book.getISBN() +"     title : "+book.getTitle()+"     dueTime : " + DateCalculator.timeFormatter(dueDate));

            }
        }else {
            System.out.println("There is no borrowing book");
        }

        Scanner scanner = new Scanner(System.in);

        while (true){
            String command = scanner.next();
            if(command.equals("r")){

                System.out.println("please enter ISBN of the book : ");
                String ISBN = scanner.next();
                Book book = bookManager.findByISBN(ISBN);

                if (book == null) {

                    System.out.println("Book not found.");
                    afterLogin();

                }
                Transaction transaction = transactionManager.findTransactionByISBN(book.getISBN(),currentUser.getUserName());
                boolean isTransactionInvalid = transactionManager.toInvalidTransaction(transaction);
                if (isTransactionInvalid){
                    int bookUnit = book.getUnit()+1;
                    book.setUnit(bookUnit);
                    bookManager.updateBook(book,book.getISBN());
                    System.out.println("Book successfully returned .");

                }else {
                    System.out.println("Return book failed , please try again .");
                }



                afterLogin();
            }
            else if(command.equals("m")){
                afterLogin();
            }
            else {

                System.out.println("Wrong command ,please try again");
            }

        }

    }
}
