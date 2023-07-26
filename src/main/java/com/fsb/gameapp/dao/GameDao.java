package com.fsb.gameapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsb.gameapp.entity.Game;

public interface GameDao extends JpaRepository<Game, Long> {
	Optional<Game> findByName(String name); 
	
	boolean existsByName(String name);
}
