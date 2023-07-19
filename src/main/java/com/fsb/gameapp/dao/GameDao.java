package com.fsb.gameapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsb.gameapp.entity.Game;

public interface GameDao extends JpaRepository<Game, Long> {
	List<Game> findByName(String name); 
}
