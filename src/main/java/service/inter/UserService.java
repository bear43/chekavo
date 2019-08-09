package service.inter;

import model.User;

import java.util.List;

public interface UserService
{
    User findByLogin(String login);
    boolean exists(String login);
    void createUser(User user) throws Exception;
    void deleteUser(String login) throws Exception;
    User createStandardUser(String login, String password) throws Exception;
    void saveUser(User user);
    List<User> getAllUsers();
    User getCurrentUser();
}
