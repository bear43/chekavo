package service.impl;

import model.Turn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TurnRepository;
import service.inter.TurnService;

@Service
public class TurnServiceImpl implements TurnService
{

    @Autowired
    private TurnRepository turnRepository;

    @Override
    public void save(Turn turn)
    {
        turnRepository.saveAndFlush(turn);
    }
}
