package service.impl;

import model.Adventure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AdventureRepository;
import service.inter.AdventureService;

@Service
public class AdventureServiceImpl implements AdventureService
{

    @Autowired
    private AdventureRepository adventureRepository;

    @Override
    public void save(Adventure adventure)
    {
        adventureRepository.save(adventure);
    }
}
