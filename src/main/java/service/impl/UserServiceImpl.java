package service.impl;

import exception.NoSuchUserException;
import exception.UserAlreadyDefinedException;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import service.inter.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    public User findByLogin(String login)
    {
        return userRepository.findByLogin(login);
    }

    public boolean exists(String login)
    {
        return userRepository.existsUserByLogin(login);
    }

    public void createUser(User user) throws Exception
    {
        if(exists(user.getLogin())) throw new UserAlreadyDefinedException();
        else
        {
            saveUser(user);
        }
    }

    public void deleteUser(String login) throws Exception
    {
        User user = findByLogin(login);
        if(user == null) throw new NoSuchUserException();
        userRepository.delete(user);
    }

    public User createStandardUser(String login, String password) throws Exception
    {
        User user = new User(login, password);
        createUser(user);
        return user;
    }

    public void saveUser(User user)
    {
        userRepository.saveAndFlush(user);
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User getCurrentUser()
    {
        org.springframework.security.core.userdetails.User obj =
                (org.springframework.security.core.userdetails.User)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByLogin(obj.getUsername());
    }


}
