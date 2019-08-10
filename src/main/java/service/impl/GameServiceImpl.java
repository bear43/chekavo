package service.impl;

import exception.InvalidNumberException;
import model.Adventure;
import model.Turn;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import service.inter.AdventureService;
import service.inter.GameService;
import service.inter.TurnService;
import service.inter.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameServiceImpl implements GameService
{

    @Autowired
    private UserService userService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private TurnService turnService;

    private Adventure adventure;

    private int turnCount;

    private byte[] number = new byte[4];

    private static boolean doesNumberRepeat(byte number, byte[] restrictedNumbers)
    {
        for (byte b : restrictedNumbers)
        {
            if (b == number) return true;
        }
        return false;
    }

    private static byte generateSingleDivisionNumber(byte[] restrictedNumbers)
    {
        byte generatedValue;
        Random r = new Random();
        do
        {
            generatedValue = (byte) r.nextInt(10);
        } while (doesNumberRepeat(generatedValue, restrictedNumbers));
        return generatedValue;
    }

    private static int compositeNumber(byte[] divisions)
    {
        return divisions[0] +
                10 * divisions[1] +
                100 * divisions[2] +
                1000 * divisions[3];
    }

    private static String compositeNumberString(byte[] number)
    {
        return String.valueOf(number[3]) + String.valueOf(number[2]) +
                String.valueOf(number[1]) + String.valueOf(number[0]);
    }

    private static byte[] decompositeNumber(int number)
    {
        byte[] divisions = new byte[4];
        divisions[0] = (byte) (number % 10);
        divisions[1] = (byte) (number / 10 % 10);
        divisions[2] = (byte) (number / 100 % 10);
        divisions[3] = (byte) (number / 1000);
        return divisions;
    }

    private static byte[] decompositeNumber(String number) throws Exception
    {
        if(number.length() > 4) throw new InvalidNumberException();
        byte[] divisions = new byte[4];
        char[] chars = new char[4];
        number.getChars(0, 4, chars, 0);
        divisions[0] = Byte.parseByte(String.valueOf(chars[3]));
        divisions[1] = Byte.parseByte(String.valueOf(chars[2]));
        divisions[2] = Byte.parseByte(String.valueOf(chars[1]));
        divisions[3] = Byte.parseByte(String.valueOf(chars[0]));
        return divisions;
    }

    private static byte compareNumbersAbsolute(byte[] a, byte[] b)
    {
        byte counter = 0;
        for(int i = 0; i < 4; i++)
        {
            if (a[i] == b[i])
                counter++;
        }
        return counter;
    }

    private static byte compareNumbersRelative(byte[] a, byte[] b)
    {
        byte counter = 0;
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                if(a[i] == b[j])
                {
                    if(i != j)
                        counter++;
                    break;
                }
            }
        }
        return counter;
    }

    private static boolean isValidNumber(int number)
    {
        byte[] divisions;
        try
        {
            divisions = decompositeNumber(number);
        }
        catch (Exception ex)
        {
            return false;
        }
        return isValidNumber(divisions);
    }

    private static boolean isValidNumber(byte[] number)
    {
        for(byte i = 0; i < 4; i++)
        {
            for(byte j = 0; j < 4; j++)
            {
                if (i != j && number[i] == number[j])
                {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidNumber(String number)
    {
        if(number.length() > 4) return false;
        byte[] divisions;
        try
        {
            divisions = decompositeNumber(number);
        }
        catch (Exception ignored)
        {
            return false;
        }
        return isValidNumber(divisions);
    }

    @Override
    public void generateNumber()
    {
        byte[] divisions = new byte[4];
        divisions[0] = generateSingleDivisionNumber(divisions);
        divisions[1] = generateSingleDivisionNumber(divisions);
        divisions[2] = generateSingleDivisionNumber(divisions);
        divisions[3] = generateSingleDivisionNumber(divisions);
        number = divisions;
        turnCount = 0;
        adventure = new Adventure(compositeNumberString(number));
        adventure.setUser(userService.getCurrentUser());
        adventureService.save(adventure);
    }

    public int getBullCount(int userNumber) throws InvalidNumberException
    {
        return getBullCount(decompositeNumber(userNumber));
    }

    public int getCowCount(int userNumber) throws InvalidNumberException
    {
        return getCowCount(decompositeNumber(userNumber));
    }

    public int getBullCount(byte[] userNumber) throws InvalidNumberException
    {
        if(!isValidNumber(userNumber)) throw new InvalidNumberException();
        return compareNumbersAbsolute(userNumber, number);
    }

    public int getCowCount(byte[] userNumber) throws InvalidNumberException
    {
        if(!isValidNumber(userNumber)) throw new InvalidNumberException();
        return compareNumbersRelative(userNumber, number);
    }

    @Override
    public int getBullCount(String userNumber) throws Exception
    {
        if(userNumber.length() > 4) throw new InvalidNumberException();
        byte[] divisions = decompositeNumber(userNumber);
        return getBullCount(divisions);
    }

    @Override
    public int getCowCount(String userNumber) throws Exception
    {
        if(userNumber.length() > 4) throw new InvalidNumberException();
        byte[] divisions = decompositeNumber(userNumber);
        return getCowCount(divisions);
    }

    public boolean checkNumber(int userNumber) throws InvalidNumberException
    {
        if(!isValidNumber(userNumber)) throw new InvalidNumberException();
        return checkNumber(decompositeNumber(userNumber));
    }

    public boolean checkNumber(byte[] userNumber) throws InvalidNumberException
    {
        if(!isValidNumber(userNumber)) throw new InvalidNumberException();
        return compareNumbersAbsolute(userNumber, number) == 4;
    }

    @Override
    public boolean checkNumber(String userNumber) throws Exception
    {
        if(userNumber.length() > 4) throw new InvalidNumberException();
        return checkNumber(decompositeNumber(userNumber));
    }

    @Override
    public String getNumber()
    {
        return compositeNumberString(number);
    }

    @Override
    public Map<String, Object> makeTurn(String userNumber) throws Exception
    {
        Map<String, Object> answer = new HashMap<>();
        Turn turn = new Turn(
                adventure,
                turnCount + 1,
                userNumber,
                getBullCount(userNumber),
                getCowCount(userNumber),
                checkNumber(userNumber));
        answer.put("bulls", turn.getBullCount());
        answer.put("cows", turn.getCowCount());
        answer.put("equals", turn.isLastTurn());
        turnService.save(turn);
        if(turn.isLastTurn())
            turnCount = 0;
        else
            turnCount++;
        return answer;
    }
}
