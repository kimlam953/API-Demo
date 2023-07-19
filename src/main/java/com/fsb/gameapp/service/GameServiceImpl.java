package com.fsb.gameapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsb.gameapp.dao.GameDao;
import com.fsb.gameapp.entity.Game;
import com.fsb.gameapp.exception.*;


@Service
public class GameServiceImpl implements GameService{
	private final GameDao gameDao;
	
	private static final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);	

    @Autowired
    public GameServiceImpl(GameDao gameDao) {
        this.gameDao = gameDao;
    }
	
	@Override
	@Transactional(readOnly = true)
	public List<Game> listAllGames() {
		log.info("List all game");
		return this.gameDao.findAll();
	}    
    
	@Override
	@Transactional
	public Game createGame(Game game) {
		log.info("Create new game : " + game.getName());
		if (!isGameNameExist(game.getName())) {			
	        game.setCreateDate(new Date());
	        game.setActive(true);	        
	        return gameDao.save(game);		
		} else {
			throw new GameNameDuplicatedException(game.getName());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Game getGameById(Long id) {
		log.info("Find game by id : " + id);
	    return gameDao.findById(id)
	    	      .orElseThrow(() -> new GameNotFoundException(id));						
	}
	
	@Override
	@Transactional(readOnly = true)
	public Game getGameByName(String name) {
		log.info("Find game by name : " + name);
		List<Game> list = gameDao.findByName(name);
		if (list.size() > 0) {
			return list.get(0); //game name is unique
		} else {
			throw new GameNotFoundException(name);
		}				
	}

	@Override
	@Transactional
	public Game updateGame(Long id, Game updatedGame) {
		log.info("Update game by id : " + id + " with details : " + "id = " + updatedGame.getId() + ", name = " + updatedGame.getName());
		if (!isGameNameExist(updatedGame.getName())) {
		    return gameDao.findById(id)
		    	      .map(game -> {
		    	    	  game.setName(updatedGame.getName());
		    	    	  game.setActive(updatedGame.isActive());
		    	        return gameDao.save(game);
		    	      })
		    	      .orElseGet(() -> {
		    	    	updatedGame.setId(id);
		    	        return gameDao.save(updatedGame);
		    	      });	
		} else {
			throw new GameNameDuplicatedException(updatedGame.getName());
		}					
	}

	@Override
	public void deleteGame(Long id) {
		log.info("Delete game by id : " + id);
		gameDao.deleteById(id);
	}
	
	private boolean isGameNameExist(String name) {
		List<Game> list = gameDao.findByName(name); 
		if (list.size() > 0) {
			log.info("This game name already exist: " + name);
			return true;
		} else {
			return false;
		}
	}
}
