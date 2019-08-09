package exception;

public class UserAlreadyDefinedException extends Exception
{
    public UserAlreadyDefinedException()
    {
        super("User already defined");
    }

    public UserAlreadyDefinedException(String message)
    {
        super(message);
    }
}
