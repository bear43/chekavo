package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.inter.GameService;
import service.inter.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController
{

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @PostMapping("/newGame")
    @ResponseBody
    public void newGame()
    {
        gameService.generateNumber();
    }

    @PostMapping("/check")
    @ResponseBody
    public Object check(@RequestBody Map<String, String> param)
    {
        Map<String, Object> answer = new HashMap<>();
        String userValue;
        try
        {
            userValue = param.get("userValue");
            answer.putAll(gameService.makeTurn(userValue));
        }
        catch (Exception ex)
        {
            answer.put("successful", false);
            answer.put("exception", ex.getMessage());
            return answer;
        }
        answer.put("successful", true);
        return answer;
    }

    @PostMapping("/giveup")
    @ResponseBody
    public Object giveUp()
    {
        Map<String, Object> answer = new HashMap<>();
        try
        {
            answer.put("number", gameService.getNumber());
        }
        catch (Exception ex)
        {
            answer.put("successful", false);
            answer.put("exception", ex.getMessage());
            return answer;
        }
        answer.put("successful", true);
        return answer;
    }

    @PostMapping("/history")
    @ResponseBody
    public Object history()
    {
        return userService.getCurrentUser().getAdventureList();
    }

    @GetMapping("/")
    public String main()
    {
        return "main";
    }
}
