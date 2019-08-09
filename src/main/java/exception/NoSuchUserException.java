package exception;

public class NoSuchUserException extends Exception
{
    public NoSuchUserException()
    {
        super("No such user");
    }

    public NoSuchUserException(String message)
    {
        super(message);
    }
}
