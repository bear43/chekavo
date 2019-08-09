package repository;

import model.Adventure;
import model.Turn;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdventureRepository extends JpaRepository<Adventure, Long>
{
    List<Adventure> findByUser(User user);
}
