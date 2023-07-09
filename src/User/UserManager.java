package User;

import Tools.FileManager;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class UserManager implements IUserManager{

    private final String path = "UsersData.txt";
    FileManager fileManager;

    public UserManager(boolean useFileSystem){

        fileManager = new FileManager(path,useFileSystem);

        fileManager.firstInit();

    }
    @Override
    public boolean signUp(User user){

        if (userExistence(user.getUserName()) == false ){

            String usersData = getAllUsers();
            setAllUsers(usersData+user.getUserName()+"//"+user.getPassword()+"//"+user.getName());
            return true;
        }else {

            return false;

        }


    }
    @Override
    public boolean userExistence(String userName){

        String usersData = getAllUsers();
        boolean isUserExist = false;
        if (!usersData.equals("")){

            String[] users = usersData.split("\n");
            for (int i = 0 ; i<users.length ; i++){

                String[] userInText = users[i].split("//");

                if (userName.equals(userInText[0])){

                    isUserExist = true;
                    break;
                }

            }
        }


        return isUserExist;


    }
    @Override
    public boolean logIn(String userName , String hashedPassword){

        String usersData = getAllUsers();
        String[] users = usersData.split("\n");
        boolean isUserLogin = false;
        for (int i = 0 ; i<users.length ; i++){

            String[] userInText = users[i].split("//");
            if (userName.equals(userInText[0])&&hashedPassword.equals(userInText[1])){

                isUserLogin = true;
                break;
            }

        }

        return isUserLogin;
    }
    @Override
    public boolean editUser(User user){
        String usersData = getAllUsers();
        String newUsersData = "";
        String[] users = usersData.split("\n");
        boolean isUserEdited = false;
        for (int i = 0 ; i<users.length ; i++){

            String[] userInText = users[i].split("//");
            if (!user.getUserName().equals(userInText[0])){

                if (i==(users.length-1)){

                    newUsersData +=users[i];
                }else {
                    newUsersData +=users[i]+"\n";
                }

            }else {
                userInText[0]=user.getUserName();
                userInText[1]=user.getPassword();
                userInText[2]=user.getName();

                if (i==(users.length-1)){

                    newUsersData +=String.join("//",userInText);
                }else {
                    newUsersData +=String.join("//",userInText)+"\n";
                }
                isUserEdited = true;
            }



        }
        if (!usersData.equals(newUsersData)){

            setAllUsers(newUsersData);
        }
        return isUserEdited;



    }
    @Override
    public boolean deleteUser(String userName){

        String usersData = getAllUsers();
        String newUsersData = "";
        String[] users = usersData.split("\n");
        boolean isUserDeleted = false;
        for (int i = 0 ; i<users.length ; i++){

            String[] userInText = users[i].split("//");
            if (!userName.equals(userInText[0])){

                if (i==(users.length-1)){

                    newUsersData +=users[i];
                }else {
                    newUsersData +=users[i]+"\n";
                }

            }else {

                isUserDeleted = true;
            }



        }
        if (!usersData.equals(newUsersData)){

            setAllUsers(newUsersData);
        }
        return isUserDeleted;


    }
    @Override
    public User setUserInformation(){

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

            user.setPassword(passwordToHash(password1));
            return user;

        }else {

            return null;
        }

    }
    @Override
    public String getAllUsers(){

        return fileManager.getAllData();
    }
    @Override
    public void setAllUsers(String users){

        fileManager.setAllData(users);

    }

    @Override
    public User getUser(String userName){

        String usersData = getAllUsers();
        User user = new User();
        String[] users = usersData.split("\n");
        for (int i = 0 ; i<users.length ; i++){

            String[] userInText = users[i].split("//");

            if (userName.equals(userInText[0])){

                user.setUserName(userInText[0]);
                user.setPassword(userInText[1]);
                user.setName(userInText[2]);

            }


        }
        return user;

    }
    @Override
    public String passwordToHash(String password){

        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] massageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1,massageDigest);
            String hashPassword = no.toString(16);
            while (hashPassword.length() <32){
                hashPassword = "0"+hashPassword;
            }
            result = hashPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}
