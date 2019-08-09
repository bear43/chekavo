package service.inter;

import java.util.Map;

public interface GameService
{
    void generateNumber();
    int getBullCount(String userNumber) throws Exception;
    int getCowCount(String userNumber) throws Exception;
    boolean checkNumber(String userNumber) throws Exception;
    Map<String, Object> makeTurn(String userNumber) throws Exception;
    String getNumber();
}
