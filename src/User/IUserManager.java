package User;

public interface IUserManager {

    boolean signUp(User user);
    boolean userExistence(String userName);
    boolean logIn(String userName , String hashedPassword);
    boolean editUser(User user);
    boolean deleteUser(String userName);
    User setUserInformation();
    String getAllUsers();
    void setAllUsers(String users);
    User getUser(String userName);
    String passwordToHash(String password);
}
