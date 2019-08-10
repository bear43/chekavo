package service.impl;

import model.Adventure;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.inter.RatingService;
import service.inter.UserService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class RatingServiceImpl implements RatingService
{
    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager em;

    @Override
    public List buildRatingList()
    {
        @SuppressWarnings("JpaQlInspection")
        Query query = em.createQuery(
                "select usr.login, usr.score from model.User usr");
        return query.getResultList();
    }

    @Override
    public List buildSortedRatingListByAvgScoreAndAdvCount(boolean ascOrder)
    {
        @SuppressWarnings("JpaQlInspection")
        Query query = em.createQuery(
                "select usr.login, usr.score, count(distinct adv) as advCount from model.User usr " +
                        "inner join usr.adventureList adv " +
                        "where usr.score != 0 and usr.id = adv.user.id group by usr.login, usr.score " +
                        "order by usr.score "+
                        (ascOrder ? "asc" : "desc") +", advCount " +
                        (ascOrder ? "desc" : "asc"));
        return query.getResultList();
    }

    @Override
    public List buildSortedRatingListByAvgScore(boolean ascOrder)
    {
        @SuppressWarnings("JpaQlInspection")
        Query query = em.createQuery(
                "select usr.login, usr.score from model.User usr " +
                        "where usr.score != 0 order by usr.score " +
                        (ascOrder ? "asc" : "desc"));
        return query.getResultList();
    }

    @Override
    public Map<String, Integer> buildRatingMap()
    {
        List result = buildRatingList();
        Map<String, Integer> map = new HashMap<>();
        Object[] arr;
        for(Object obj : result)
        {
            arr = (Object[])obj;
            if(arr.length == 2)
                map.put((String) arr[0], (Integer)arr[1]);
        }
        return map;
    }

    @Override
    public int countAverageScore(long userId)
    {
        @SuppressWarnings("JpaQlInspection")
        Query query = em.createQuery(
                "select COUNT(turn), COUNT(distinct adv) from model.Turn turn " +
                        "inner join turn.adventure adv " +
                        "inner join adv.user usr " +
                        "where usr.id = " + userId + " and " +
                        "turn.adventure.id = adv.id and " +
                        "adv.done = true");
        Object[] obj = (Object[])query.getSingleResult();
        int totalTurns = ((Long)obj[0]).intValue();
        int totalAdventures = ((Long)obj[1]).intValue();
        return Math.round((float)totalTurns/(float)totalAdventures);
    }

    @Override
    public int countAverageScore(String login)
    {
        @SuppressWarnings("JpaQlInspection")
        Query query = em.createQuery(
                "select COUNT(turn), COUNT(distinct adv) from model.Turn turn " +
                        "inner join turn.adventure adv " +
                        "inner join adv.user usr " +
                        "where usr.login = " + login + " and " +
                        "turn.adventure.id = adv.id and " +
                        "adv.done = true");
        Object[] obj = (Object[])query.getSingleResult();
        int totalTurns = ((Long)obj[0]).intValue();
        int totalAdventures = ((Long)obj[1]).intValue();
        return Math.round((float)totalTurns/(float)totalAdventures);
    }

    @Override
    public int countAverageScore(User user)
    {
        return Math.round(
                (float)getTotalTurns(user)/
                        (float)getTotalAdventures(user));
    }

    @Override
    public int getTotalTurns(User user)
    {
        int totalTurns = 0;
        for(Adventure adventure : user.getAdventureList())
        {
            if(adventure.isDone())
                totalTurns += adventure.getTurns().size();
        }
        return totalTurns;
    }

    @Override
    public int getTotalTurns(long userId)
    {
        @SuppressWarnings("JpaQlInspection")
        Query query = em.createQuery(
                "select COUNT(turn) from model.Turn turn " +
                        "inner join turn.adventure adv " +
                        "inner join adv.user usr " +
                        "where usr.id = " + userId + " and " +
                        "turn.adventure.id = adv.id and " +
                        "adv.done = true");
        Object obj = query.getSingleResult();
        return ((Long)obj).intValue();
    }

    @Override
    public int getTotalTurns(String login)
    {
        @SuppressWarnings("JpaQlInspection")
        Query query = em.createQuery(
                "select COUNT(turn) from model.Turn turn " +
                        "inner join turn.adventure adv " +
                        "inner join adv.user usr " +
                        "where usr.login = " + login + " and " +
                        "turn.adventure.id = adv.id and " +
                        "adv.done = true");
        Object obj = query.getSingleResult();
        return ((Long)obj).intValue();
    }

    @Override
    public int getTotalAdventures(User user)
    {
        return user.getAdventureList().size();
    }

    @Override
    public int getTotalAdventures(long userId)
    {
        @SuppressWarnings("JpaQlInspection")
        Query query = em.createQuery(
                "select COUNT(distinct adv) from model.Turn turn " +
                        "inner join turn.adventure adv " +
                        "inner join adv.user usr " +
                        "where usr.id = " + userId + " and " +
                        "turn.adventure.id = adv.id and " +
                        "adv.done = true");
        Object obj = query.getSingleResult();
        return ((Long)obj).intValue();
    }

    @Override
    public int getTotalAdventures(String login)
    {
        @SuppressWarnings("JpaQlInspection")
        Query query = em.createQuery(
                "select COUNT(distinct adv) from model.Turn turn " +
                        "inner join turn.adventure adv " +
                        "inner join adv.user usr " +
                        "where usr.login = " + login + " and " +
                        "turn.adventure.id = adv.id and " +
                        "adv.done = true");
        Object obj = query.getSingleResult();
        return ((Long)obj).intValue();
    }
}
