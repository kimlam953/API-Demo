package com.fsb.gameapp.exception;

public class GameNameDuplicatedException extends RuntimeException {
	public GameNameDuplicatedException(String name) {
		super("This game name already exist: " + name);
	}
}
