package com.fsb.gameapp.init;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fsb.gameapp.dao.GameDao;
import com.fsb.gameapp.entity.Game;

@Component
public class DataInit implements ApplicationRunner {
	
	private GameDao gameDao;
	
	private static final Logger log = LoggerFactory.getLogger(DataInit.class);	
	
    @Autowired
    public DataInit(GameDao gameDao) {
        this.gameDao = gameDao;
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		long count = gameDao.count();
		
		if (count == 0) {			
			
			Game g1 = new Game();
			
			g1.setName("football");
			g1.setCreateDate(new Date());
			g1.setActive(true);
			
			Game g2 = new Game();
			
			g2.setName("basketball");
			g2.setCreateDate(new Date());
			g2.setActive(true);
			
			gameDao.save(g1);
			gameDao.save(g2);
			
			gameDao.findAll().forEach(game ->{
				log.info("Preloaded game : id = " + game.getId() + ", name = " + game.getName());
			});
			
		}
		
	}

}
