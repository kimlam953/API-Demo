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
		
		return gameDao.findByName(name)
				.orElseThrow(() -> new GameNotFoundException(name)); 
			
	}

	@Override
	@Transactional
	public Game updateGame(Long id, Game updatedGame) {
		log.info("Update game by id : " + id + " with details : " + "id = " + updatedGame.getId() + ", name = " + updatedGame.getName());
		
		Game game = gameDao.findById(id).orElseThrow(() -> new GameNotFoundException(id));		
		
		if(!game.getName().equals(updatedGame.getName()) && gameDao.existsByName(updatedGame.getName())) {
			throw new GameNameDuplicatedException(updatedGame.getName());
		}
		
		game.setActive(updatedGame.isActive());
		game.setName(updatedGame.getName());
		
		return gameDao.save(game);
	}

	@Override
	public void deleteGame(Long id) {
		log.info("Delete game by id : " + id);
		gameDao.deleteById(id);
	}
	
	private boolean isGameNameExist(String name) {
		return gameDao.existsByName(name);
	}
}
