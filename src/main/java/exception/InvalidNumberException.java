package exception;

public class InvalidNumberException extends Exception
{
    public InvalidNumberException()
    {
        super("Invalid number");
    }

    public InvalidNumberException(String message)
    {
        super(message);
    }
}
