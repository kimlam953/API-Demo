package com.fsb.gameapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fsb.gameapp.entity.Game;
import com.fsb.gameapp.service.GameService;

@RestController
@RequestMapping("/games")
public class GameController {
	private final GameService gameService;
	
	@Autowired
	public GameController (GameService gs) {
		this.gameService = gs;
	}
	
	@GetMapping("/list")
	public List<Game> listAllGames() {
		return gameService.listAllGames();
	}	

	@PostMapping("/newgame")
	public Game createGame(@RequestBody Game game) {
		return gameService.createGame(game);
	}
	
	@GetMapping("/id/{id}")
	public Game getGameById(@PathVariable Long id) {
		return gameService.getGameById(id);
	}
	
	@GetMapping("/name/{name}")
	public Game getGameByName(@PathVariable String name) {
		return gameService.getGameByName(name);
	}
	
	@PutMapping("/id/{id}")
	public Game updateGame(@PathVariable Long id, @RequestBody Game game) {
		return gameService.updateGame(id, game);
	}
	
	@DeleteMapping("/id/{id}")
	public void deleteGame(@PathVariable Long id) {
		gameService.deleteGame(id);
	}
		
}
