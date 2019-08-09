package repository;

import model.Adventure;
import model.Turn;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnRepository extends JpaRepository<Turn, Long>
{
    List<Turn> findByAdventure_User(User user);
    List<Turn> findByAdventure(Adventure adventure);
    int countTurnsByAdventure(Adventure adventure);
}
