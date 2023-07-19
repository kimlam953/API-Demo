package com.fsb.gameapp.service;

import java.util.List;

import com.fsb.gameapp.entity.Game;

public interface GameService {
	List<Game> listAllGames();
	Game createGame(Game game);
	Game getGameById(Long id);
	Game getGameByName(String Name);
	Game updateGame(Long id, Game game);
	void deleteGame(Long id);
}
