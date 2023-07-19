package com.fsb.gameapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GameNameDuplicatedAdvice {
	  @ResponseBody
	  @ExceptionHandler(GameNameDuplicatedException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  String gameNotFoundHandler(GameNameDuplicatedException ex) {
	    return ex.getMessage();
	  }
}
