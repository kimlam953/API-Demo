package com.fsb.gameapp.exception;

public class GameNotFoundException extends RuntimeException {
	public GameNotFoundException(long id) {
		super("This game id does not exist: " + id);
	}

	public GameNotFoundException(String name) {
		super("This game name does not exist: " + name);
	}
}
