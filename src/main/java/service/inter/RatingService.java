package service.inter;

import model.User;

import java.util.List;
import java.util.Map;

public interface RatingService
{
    Map<String, Integer> buildRatingMap();
    List buildRatingList();
    List buildSortedRatingListByAvgScore(boolean ascOrder);
    List buildSortedRatingListByAvgScoreAndAdvCount(boolean ascOrder);
    int countAverageScore(long userId);
    int countAverageScore(String login);
    int countAverageScore(User user);
    int getTotalTurns(User user);
    int getTotalTurns(long userId);
    int getTotalTurns(String login);
    int getTotalAdventures(User user);
    int getTotalAdventures(long userId);
    int getTotalAdventures(String login);
}
