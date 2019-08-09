package service.inter;

import model.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdventureService
{
    void save(Adventure adventure);
}
